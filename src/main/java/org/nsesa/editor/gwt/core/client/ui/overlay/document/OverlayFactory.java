package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;

/**
 * Date: 04/08/12 17:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface OverlayFactory {
    AmendableWidget getAmendableWidget(Element element);
}
