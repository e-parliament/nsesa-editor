package org.nsesa.editor.gwt.core.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;

/**
 * Date: 30/06/12 17:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentUtil {

    public static AmendableWidget createAmendableWidget(AmendableWidget parent, String type) {
        final Element element = DOM.createElement(type);
        AmendableWidget amendableWidget = new AmendableWidgetImpl(element);
        amendableWidget.setParentAmendableWidget(parent);
        return amendableWidget;
    }
}
