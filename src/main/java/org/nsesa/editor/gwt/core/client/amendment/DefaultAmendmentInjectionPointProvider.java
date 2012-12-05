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
            child.setOrigin(AmendableWidgetOrigin.AMENDMENT);
            // check if there is already an existing child for to add this amendment to (this can happen if there's
            // out-of-order injection)
            if (!root.getChildAmendableWidgets().isEmpty() && root.getChildAmendableWidgets().size() > reference.getOffset()) {
                if (root.getChildAmendableWidgets().get(reference.getOffset()).getOrigin() == AmendableWidgetOrigin.AMENDMENT) {
                    // child was already created ...
                    return root.getChildAmendableWidgets().get(reference.getOffset());
                }
            }

            com.google.gwt.user.client.Element parentElement = root.getAmendableElement().cast();
            com.google.gwt.user.client.Element childElement = child.getAmendableElement().cast();
            root.addAmendableWidget(child, reference.getOffset(), true);
            // attach to the DOM
            // note: do not use the same offset, since other elements might have been introduced ..
            DOM.insertChild(parentElement, childElement, root.getChildAmendableWidgets().indexOf(child));

            LOG.info("Added new " + child + " as a child to " + root + " at position " + reference.getOffset());
            return child;
        } else {
            LOG.info("Added amendment directly on " + root);
            return root;
        }
    }
}
