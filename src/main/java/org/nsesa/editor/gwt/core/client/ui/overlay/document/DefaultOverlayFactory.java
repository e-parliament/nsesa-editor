package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Counter;

import java.util.logging.Logger;

/**
 * Date: 17/10/12 21:30
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultOverlayFactory implements OverlayFactory {

    private static final Logger LOG = Logger.getLogger(DefaultOverlayFactory.class.getName());

    protected final OverlayStrategy overlayStrategy;
    protected final ClientFactory clientFactory;

    private Counter elementCounter;

    @Inject
    public DefaultOverlayFactory(final OverlayStrategy overlayStrategy, final ClientFactory clientFactory) {
        this.overlayStrategy = overlayStrategy;
        this.clientFactory = clientFactory;
    }

    @Override
    public AmendableWidget getAmendableWidget(String tag) {
        com.google.gwt.user.client.Element element = DOM.createSpan();
        element.setAttribute("type", tag);
        element.setClassName("widget " + tag);
        return getAmendableWidget(element);
    }

    @Override
    public AmendableWidget getAmendableWidget(final Element element) {
        elementCounter = new Counter();
        final AmendableWidget root = wrap(null, element, 0);
        LOG.info("Total number of wrapped elements: " + elementCounter.get());
        return root;
    }

    @Override
    public AmendableWidget toAmendableWidget(final Element element) {
        return null;
    }

    @Override
    public String getNamespace() {
        // no namespace defined for default implementation
        return null;
    }

    protected AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element, final int depth) {
        final AmendableWidget amendableWidget = toAmendableWidget(element);
        if (amendableWidget != null) {
            elementCounter.increment();
            if (elementCounter.get() % 1000 == 0) {
                LOG.info("--> overlay process splitting at " + elementCounter.get() + " for " + amendableWidget + " with parent " + parent);
                clientFactory.getScheduler().scheduleDeferred(new Scheduler.ScheduledCommand() {
                    @Override
                    public void execute() {
                        setProperties(parent, amendableWidget, element, depth);
                    }
                });
            } else {
                setProperties(parent, amendableWidget, element, depth);
            }

        }
        return amendableWidget;
    }

    protected void setProperties(final AmendableWidget parent, final AmendableWidget amendableWidget, final Element element, int depth) {
        amendableWidget.setParentAmendableWidget(parent);

        // process all eager properties
        amendableWidget.setAmendable(overlayStrategy.isAmendable(element));
        amendableWidget.setImmutable(overlayStrategy.isImmutable(element));
        amendableWidget.setType(overlayStrategy.getType(element));
        // set the overlay strategy for lazy processing
        amendableWidget.setOverlayStrategy(overlayStrategy);


        // attach all children (note, this is a recursive call)
        final Element[] children = overlayStrategy.getChildren(element);
        if (children != null) {
            for (final Element child : children) {
                final AmendableWidget amendableChild = wrap(amendableWidget, child, depth + 1);
                if (amendableChild != null) {
                    amendableWidget.addAmendableWidget(amendableChild, true);
                }
            }
        }

        // post process the widget (eg. hide large tables)
        postProcess(amendableWidget);
    }

    protected void postProcess(final AmendableWidget amendableWidget) {

    }

    private String spaces(int number) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
