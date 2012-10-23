package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;

/**
 * Date: 17/10/12 21:30
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayFactory implements OverlayFactory {

    private final OverlayStrategy overlayStrategy;

    @Inject
    public DefaultOverlayFactory(OverlayStrategy overlayStrategy) {
        this.overlayStrategy = overlayStrategy;
    }

    public AmendableWidget getAmendableWidget(String tag) {
        return getAmendableWidget(DOM.createElement(tag));
    }

    @Override
    public AmendableWidget getAmendableWidget(Element element) {
        return wrap(null, element);
    }

    public AmendableWidget toAmendableWidget(final Element element) {
        return new AmendableWidgetImpl(element);
    }

    protected AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element) {
        AmendableWidget amendableWidget = toAmendableWidget(element);
        if (amendableWidget != null) {
            amendableWidget.setParentAmendableWidget(parent);

            // process all properties
            amendableWidget.setAmendable(overlayStrategy.isAmendable(element));
            amendableWidget.setImmutable(overlayStrategy.isImmutable(element));

            if (amendableWidget.isAmendable()) {
                amendableWidget.setContent(overlayStrategy.getContent(element));
            }

            // attach all children (note, this is a recursive call)
            final Element[] children = overlayStrategy.getChildren(element);
            for (final Element child : children) {
                final AmendableWidget amendableChild = wrap(amendableWidget, child);
                if (amendableChild != null) {
                    amendableWidget.addAmendableWidget(amendableChild);
                }
            }

            // post process the widget (eg. hide large tables)
            amendableWidget = postProcess(amendableWidget);
        }
        return amendableWidget;
    }

    protected AmendableWidget postProcess(AmendableWidget amendableWidget) {
        return amendableWidget;
    }
}
