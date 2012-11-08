package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;

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
    public LinkedHashMap<String, AmendableWidget> getAllowedSiblings(AmendableWidget amendableWidget) {
        return getAllowedChildren(amendableWidget.getParentAmendableWidget());
    }

    @Override
    public LinkedHashMap<String, AmendableWidget> getAllowedChildren(AmendableWidget amendableWidget) {
        final LinkedHashMap<String, AmendableWidget> allowedChildren = new LinkedHashMap<String, AmendableWidget>();
        final String[] allowedTypes = amendableWidget.getAllowedChildTypes();
        for (final String type : allowedTypes) {
            allowedChildren.put(type, overlayFactory.getAmendableWidget(type));
        }
        return allowedChildren;
    }
}
