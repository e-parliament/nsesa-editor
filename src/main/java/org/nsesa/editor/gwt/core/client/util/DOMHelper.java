package org.nsesa.editor.gwt.core.client.util;

import com.google.gwt.dom.client.Document;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableElement;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidgetListener;

import java.util.List;

/**
 * Date: 30/06/12 17:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DOMHelper {

    public static AmendableWidget wrap(final com.google.gwt.dom.client.Element element, final AmendableWidgetListener listener) {
        return wrap(null, element, listener, 0);
    }

    public static AmendableWidget wrap(final AmendableWidget parent, final com.google.gwt.dom.client.Element element, final AmendableWidgetListener listener, int depth) {
        // Assert that the element is attached.
        assert Document.get().getBody().isOrHasChild(element) : "element is not attached.";

        // get the widget by its type from the factory
        final AmendableWidget amendableWidget = new AmendableWidgetImpl((AmendableElement) element);

        //amendableWidget.setAssignedSequence(sequenceGenerator.nextSequence());
        amendableWidget.setParentAmendableWidget(parent);

        if (!amendableWidget.isAttached()) {
            amendableWidget.onAttach();
        }

        // attach all children (note, this is a recursive call)
        final List<AmendableElement> children = element.<AmendableElement>cast().getChildren();
        ++depth;
        for (int i = 0; i < children.size(); i++) {
            AmendableElement child = children.get(i);
            final AmendableWidget amendableChild = wrap(amendableWidget, child, listener, depth);
            amendableWidget.addAmendableWidget(amendableChild);
            //Log.info(indent(depth) + " " + amendableChild.asWidget().getElement().getNodeName());
        }

        // if the widget is amendable, register a listener for its events
        if (amendableWidget.isAmendable()) {
            amendableWidget.setListener(listener);
        }
        // post process the widget (eg. hide large tables)
        amendableWidget.postProcess();
        return amendableWidget;
    }

    public static String indent(int amount) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
}
