package org.nsesa.editor.gwt.core.client.amendment;

import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
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
        final AmendableWidgetReference reference = amendmentController.getAmendment().getSourceReference();
        if (reference.isCreation()) {

            final AmendableWidget child = documentController.getOverlayFactory().getAmendableWidget(reference.getType());
            child.setOrigin(AmendableWidgetOrigin.AMENDMENT);
            if (reference.isSibling()) {
                final AmendableWidget grandParent = root.getParentAmendableWidget();
                com.google.gwt.user.client.Element parentElement = grandParent.getAmendableElement().cast();
                com.google.gwt.user.client.Element childElement = child.getAmendableElement().cast();
                // attach to the DOM
                DOM.insertChild(parentElement, childElement, grandParent.getChildAmendableWidgets().indexOf(root));
                grandParent.addAmendableWidget(child);
            } else {
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
                // attach to the DOM
                DOM.appendChild(parentElement, childElement);

                root.addAmendableWidget(child);
            }
            return child;
        } else {
            return root;
        }
    }
}
