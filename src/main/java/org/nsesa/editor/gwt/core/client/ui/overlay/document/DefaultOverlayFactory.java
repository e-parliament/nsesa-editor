/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Counter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of the {@link OverlayFactory}.
 * Date: 17/10/12 21:30
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultOverlayFactory implements OverlayFactory {

    private static final Logger LOG = Logger.getLogger(DefaultOverlayFactory.class.getName());
    private static final int DEFERRED_THRESHOLD = 1000;

    protected final OverlayStrategy overlayStrategy;
    protected final Scheduler scheduler;

    private Counter elementCounter;

    @Inject
    public DefaultOverlayFactory(final OverlayStrategy overlayStrategy) {
        this.overlayStrategy = overlayStrategy;
        this.scheduler = Scheduler.get();
    }

    @Override
    public OverlayWidget getAmendableWidget(final String namespaceURI, final String tag) {
        com.google.gwt.user.client.Element element = DOM.createSpan();
        element.setAttribute("data-ns", namespaceURI);
        element.setAttribute("data-type", tag);
        element.setClassName("widget " + tag);
        return getAmendableWidget(element);
    }

    @Override
    public OverlayWidget getAmendableWidget(final Element element) {
        return getAmendableWidget(element, -1);
    }

    @Override
    public OverlayWidget getAmendableWidget(Element element, int maxDepth) {
        elementCounter = new Counter();
        final OverlayWidget root = wrap(null, element, 0, -1);
        LOG.log(Level.FINE, "Total number of wrapped elements: " + elementCounter.get());
        return root;
    }

    @Override
    public OverlayWidget toAmendableWidget(final Element element) {
        return null;
    }

    @Override
    public String getNamespaceURI() {
        // no namespace defined for default implementation
        return null;
    }

    @Override
    public OverlayStrategy getOverlayStrategy() {
        return overlayStrategy;
    }

    protected OverlayWidget wrap(final OverlayWidget parent, final com.google.gwt.dom.client.Element element, final int depth, final int maxDepth) {
        final OverlayWidget overlayWidget = toAmendableWidget(element);
        if (overlayWidget != null) {
            elementCounter.increment();
            setProperties(parent, overlayWidget, element, depth, maxDepth);

        } else {
            LOG.warning("No overlay widget found for " + element);
        }
        return overlayWidget;
    }

    protected void setProperties(final OverlayWidget parent, final OverlayWidget overlayWidget, final Element element, final int depth, final int maxDepth) {
        overlayWidget.setParentOverlayWidget(parent);

        // process all eager properties
        overlayWidget.setAmendable(overlayStrategy.isAmendable(element));
        overlayWidget.setImmutable(overlayStrategy.isImmutable(element));
        overlayWidget.setType(overlayStrategy.getType(element));
        // set the overlay strategy for lazy processing
        overlayWidget.setOverlayStrategy(overlayStrategy);

        // check if we're not yet at the max depth
        if (depth <= maxDepth || maxDepth == -1) {
            if (elementCounter.get() > 1 && elementCounter.get() % DEFERRED_THRESHOLD == 0) {
                LOG.info("--> overlay process splitting at " + elementCounter.get() + " for '" + element + "' as " + overlayWidget + " with parent " + parent);
                scheduler.scheduleDeferred(new Scheduler.ScheduledCommand() {
                    @Override
                    public void execute() {
                        visitChildren(overlayWidget, element, depth + 1, maxDepth, true);
                    }
                });
            } else {
                visitChildren(overlayWidget, element, depth + 1, maxDepth, false);
            }

        }
        // post process the widget (eg. hide large tables)
        postProcess(overlayWidget);
    }

    protected void visitChildren(final OverlayWidget overlayWidget, final Element element, final int depth, final int maxDepth, boolean deferred) {
        // attach all children (note, this is a recursive call)
        final Element[] children = overlayStrategy.getChildren(element);
        if (children != null) {
            for (final Element child : children) {
                final OverlayWidget amendableChild = wrap(overlayWidget, child, depth, maxDepth);
                if (amendableChild != null) {
                    overlayWidget.addOverlayWidget(amendableChild, -1, true);
                }
            }
        }
        // mark the children as overlaid
        overlayWidget.setChildrenInitialized(true);
    }

    protected void postProcess(final OverlayWidget overlayWidget) {
        // currently unused, left as an extension point
    }
}
