package org.nsesa.editor.gwt.core.client.ui.overlay.document;

/**
 * Default implementation for <code>OverlayLocalizableResource<code/> interface returning the amendable widget type
 * User: groza
 * Date: 28/01/13
 * Time: 9:59
 */
public class DefaultOverlayLocalizableResource implements OverlayLocalizableResource {
    @Override
    public String getName(AmendableWidget widget) {
        return widget.getType();
    }

    @Override
    public String getDescription(AmendableWidget widget) {
        return widget.getType();
    }
}
