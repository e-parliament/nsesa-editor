package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.inject.ImplementedBy;

/**
 * An interface to provide localizable information about amendable widgets
 * User: groza
 * Date: 28/01/13
 * Time: 9:55
 */
@ImplementedBy(DefaultOverlayLocalizableResource.class)
public interface OverlayLocalizableResource {
    /**
     * Returns localized tag name for the given amendable widget
     * @param widget
     * @return
     */
    abstract String getName(AmendableWidget widget);

    /**
     * Returns localized details for the given amendable widget
     * @param widget
     * @return
     */
    abstract String getDescription(AmendableWidget widget);
}
