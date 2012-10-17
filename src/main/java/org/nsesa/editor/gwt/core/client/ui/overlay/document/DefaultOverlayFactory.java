package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;

/**
 * Date: 17/10/12 21:30
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayFactory implements OverlayFactory {
    @Override
    public AmendableWidget getAmendableWidget(Element element) {
        return new AmendableWidgetImpl(element);
    }
}
