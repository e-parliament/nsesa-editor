/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.amendment;

import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

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
    public OverlayWidget provideInjectionPoint(AmendmentController amendmentController, OverlayWidget root, DocumentController documentController) {
        final AmendableWidgetReference reference = amendmentController.getModel().getSourceReference();
        if (reference.isCreation()) {
            final OverlayWidget child = documentController.getOverlayFactory().getAmendableWidget(reference.getType());
            // mark the origin so we know it was introduced by amendments.
            child.setOrigin(OverlayWidgetOrigin.AMENDMENT);
            // make sure we're listening to the UI events
            child.setUIListener(documentController.getSourceFileController());

            com.google.gwt.user.client.Element parentElement = root.getOverlayElement().cast();
            com.google.gwt.user.client.Element childElement = child.getOverlayElement().cast();

            // attach to the DOM
            if (root.getChildOverlayWidgets().isEmpty()) {
                // ok, insert as the last child
                DOM.insertChild(parentElement, childElement, parentElement.getChildCount());
                root.addOverlayWidget(child, -1, false);
            } else {
                // insert before the first child amendable widget
                com.google.gwt.user.client.Element beforeElement = root.getChildOverlayWidgets().get(reference.getOffset()).getOverlayElement().cast();
                DOM.insertBefore(parentElement, childElement, beforeElement);
                // logical
                root.addOverlayWidget(child, reference.getOffset(), true);
            }

            LOG.info("Added new " + child + " as a child to " + root + " at position " + reference.getOffset());
            return child;
        } else {
            LOG.info("Added amendment directly on " + root);
            return root;
        }
    }
}
