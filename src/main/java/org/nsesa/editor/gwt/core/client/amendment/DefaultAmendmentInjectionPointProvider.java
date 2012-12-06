package org.nsesa.editor.gwt.core.client.amendment;

import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.logging.Logger;

/**
 * Date: 30/11/12 11:50
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointProvider implements AmendmentInjectionPointProvider {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentInjectionPointProvider.class.getName());

    @Override
    public AmendableWidget provideInjectionPoint(AmendmentController amendmentController, AmendableWidget root, DocumentController documentController) {
        final AmendableWidgetReference reference = amendmentController.getAmendment().getSourceReference();
        if (reference.isCreation()) {
            final AmendableWidget child = documentController.getOverlayFactory().getAmendableWidget(reference.getType());
            // mark the origin so we know it was introduced by amendments.
            child.setOrigin(AmendableWidgetOrigin.AMENDMENT);
            // make sure we're listening to the UI events
            child.setUIListener(documentController);

            com.google.gwt.user.client.Element parentElement = root.getAmendableElement().cast();
            com.google.gwt.user.client.Element childElement = child.getAmendableElement().cast();

            // attach to the DOM
            if (root.getChildAmendableWidgets().isEmpty()) {
                // ok, insert as the last child
                DOM.insertChild(parentElement, childElement, parentElement.getChildCount());
                root.addAmendableWidget(child, -1, false);
            } else {
                // insert before the first child amendable widget
                com.google.gwt.user.client.Element beforeElement = root.getChildAmendableWidgets().get(reference.getOffset()).getAmendableElement().cast();
                DOM.insertBefore(parentElement, childElement, beforeElement);
                // logical
                root.addAmendableWidget(child, reference.getOffset(), true);
            }

            LOG.info("Added new " + child + " as a child to " + root + " at position " + reference.getOffset());
            return child;
        } else {
            LOG.info("Added amendment directly on " + root);
            return root;
        }
    }
}
