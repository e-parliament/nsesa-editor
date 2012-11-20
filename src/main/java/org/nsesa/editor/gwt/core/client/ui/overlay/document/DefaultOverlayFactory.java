package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.LocatorUtil;

import java.util.Map;

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
    public AmendableWidget getAmendableWidget(String tag, Map<String, String> namespaces) {
        return getAmendableWidget(DOM.createElement(tag), namespaces);
    }

    @Override
    public AmendableWidget getAmendableWidget(final Element element, final Map<String, String> namespaces) {
        return wrap(null, element, namespaces, 0);
    }

    @Override
    public AmendableWidget toAmendableWidget(final Element element, final Map<String, String> namespaces) {
        if (element.getNodeName().contains(":")) {
            final String[] prefixAndName = element.getNodeName().split(":");
            return toAmendableWidget(element, namespaces.get(prefixAndName[0].toLowerCase()));
        }
        else {
            // assume default namespace
            return toAmendableWidget(element, namespaces.get(""));
        }
    }

    public AmendableWidget toAmendableWidget(final Element element, final String namespace) {
        return null;
    }

    protected AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element, Map<String, String> namespaces, int depth) {
        AmendableWidget amendableWidget = toAmendableWidget(element, namespaces);
        if (amendableWidget != null) {
            amendableWidget.setParentAmendableWidget(parent);

            // process all properties
            amendableWidget.setAmendable(overlayStrategy.isAmendable(element));
            amendableWidget.setImmutable(overlayStrategy.isImmutable(element));
            amendableWidget.setType(overlayStrategy.getType(element));
            Integer assignedNumber = LocatorUtil.getAssignedNumber(amendableWidget);
            amendableWidget.setAssignedNumber(assignedNumber != null ? assignedNumber : 1);

            if (amendableWidget.isAmendable()) {
                amendableWidget.setFormat(overlayStrategy.getFormat(element));
                amendableWidget.setNumberingType(overlayStrategy.getNumberingType(element, amendableWidget.getAssignedNumber()));
                amendableWidget.setContent(overlayStrategy.getContent(element));
            }

//            System.out.println(spaces(depth) + "<"+ amendableWidget.getType() + ">");

            // attach all children (note, this is a recursive call)
            final Element[] children = overlayStrategy.getChildren(element);
            if (children != null) {
                for (final Element child : children) {
                    final AmendableWidget amendableChild = wrap(amendableWidget, child, namespaces, depth + 1);
                    if (amendableChild != null) {
                        amendableWidget.addAmendableWidget(amendableChild);
                    }
                }
            }
//            System.out.println(spaces(depth) + "</"+ amendableWidget.getType() + ">");

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
