package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 30/11/12 11:50
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointProvider implements AmendmentInjectionPointProvider {
    @Override
    public AmendableWidget provideInjectionPoint(AmendmentController amendmentController, AmendableWidget root, DocumentController documentController) {
        // simply return the root - we don't support new elements in the default provider.
        return root;
    }
}
