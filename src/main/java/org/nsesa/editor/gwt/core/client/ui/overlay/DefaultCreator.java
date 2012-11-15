package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.LinkedHashMap;

/**
 * Date: 08/11/12 11:23
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultCreator implements Creator {

    private final OverlayFactory overlayFactory;

    @Inject
    public DefaultCreator(OverlayFactory overlayFactory) {
        this.overlayFactory = overlayFactory;
    }

    @Override
    public LinkedHashMap<String, AmendableWidget> getAllowedSiblings(DocumentController documentController, AmendableWidget amendableWidget) {
        return getAllowedChildren(documentController, amendableWidget.getParentAmendableWidget());
    }

    @Override
    public LinkedHashMap<String, AmendableWidget> getAllowedChildren(DocumentController documentController, AmendableWidget amendableWidget) {
        final LinkedHashMap<String, AmendableWidget> allowedChildren = new LinkedHashMap<String, AmendableWidget>();
        final String[] allowedTypes = amendableWidget.getAllowedChildTypes();
        for (final String type : allowedTypes) {
            allowedChildren.put(type, overlayFactory.getAmendableWidget(type, documentController.getNamespaces()));
        }
        return allowedChildren;
    }
}
