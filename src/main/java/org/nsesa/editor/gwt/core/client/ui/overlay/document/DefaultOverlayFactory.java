package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.LocatorUtil;

/**
 * Date: 17/10/12 21:30
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultOverlayFactory implements OverlayFactory {

    protected final OverlayStrategy overlayStrategy;

    @Inject
    public DefaultOverlayFactory(OverlayStrategy overlayStrategy) {
        this.overlayStrategy = overlayStrategy;
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
        return wrap(null, element, 0);
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

    protected AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element, int depth) {
        AmendableWidget amendableWidget = toAmendableWidget(element);
        if (amendableWidget != null) {
            amendableWidget.setParentAmendableWidget(parent);

            // process all properties
            amendableWidget.setAmendable(overlayStrategy.isAmendable(element));
            amendableWidget.setImmutable(overlayStrategy.isImmutable(element));
            amendableWidget.setType(overlayStrategy.getType(element));
            amendableWidget.setId(overlayStrategy.getID(element));
            Integer assignedNumber = LocatorUtil.getAssignedNumber(amendableWidget);
            amendableWidget.setAssignedNumber(assignedNumber != null ? assignedNumber : 1);

            if (amendableWidget.isAmendable()) {
                amendableWidget.setFormat(overlayStrategy.getFormat(element));
                amendableWidget.setNumberingType(overlayStrategy.getNumberingType(element, amendableWidget.getAssignedNumber()));
                amendableWidget.setContent(overlayStrategy.getContent(element));
            }

            // attach all children (note, this is a recursive call)
            final Element[] children = overlayStrategy.getChildren(element);
            if (children != null) {
                for (final Element child : children) {
                    final AmendableWidget amendableChild = wrap(amendableWidget, child, depth + 1);
                    if (amendableChild != null) {
                        amendableWidget.addAmendableWidget(amendableChild);
                    }
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

    private String spaces(int number) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
