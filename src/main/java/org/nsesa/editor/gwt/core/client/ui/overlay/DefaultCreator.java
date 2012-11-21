package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.LinkedHashMap;

/**
 * Date: 08/11/12 11:23
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultCreator implements Creator {

    @Override
    public LinkedHashMap<String, AmendableWidget> getAllowedSiblings(DocumentController documentController, AmendableWidget amendableWidget) {
        return new LinkedHashMap<String, AmendableWidget>();
    }

    @Override
    public LinkedHashMap<String, AmendableWidget> getAllowedChildren(DocumentController documentController, AmendableWidget amendableWidget) {
        return new LinkedHashMap<String, AmendableWidget>();
    }
}
