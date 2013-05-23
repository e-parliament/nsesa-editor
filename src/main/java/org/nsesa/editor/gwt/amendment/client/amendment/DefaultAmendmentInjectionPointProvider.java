/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.amendment.client.amendment;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetInjectionStrategy;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;

import java.util.logging.Logger;

/**
 * The actual provider of the injection point (an overlay widget which will receive an amendment).
 * This injection point can either be an existing overlay widget, or a new one created and injected in the tree
 * (and DOM) prior to returning if the amendment introduces a new element.
 * <p/>
 * Date: 30/11/12 11:50
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultAmendmentInjectionPointProvider implements AmendmentInjectionPointProvider {

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentInjectionPointProvider.class.getName());

    private final OverlayWidgetInjectionStrategy overlayWidgetInjectionStrategy;

    @Inject
    public DefaultAmendmentInjectionPointProvider(OverlayWidgetInjectionStrategy overlayWidgetInjectionStrategy) {
        this.overlayWidgetInjectionStrategy = overlayWidgetInjectionStrategy;
    }

    /**
     * Provides an injection point based on the given {@link AmendmentController} using its model's {@link AmendableWidgetReference}.
     * If the injection point does not yet exist (meaning {@link org.nsesa.editor.gwt.core.shared.AmendableWidgetReference#isCreation()} == <tt>true</tt>),
     * it will be created, and injected in the DOM.
     *
     * @param amendmentController the amendment controller to provide the injection point for
     * @param injectionPoint      the amendable widget that has been declared an injection point by the {@link AmendmentInjectionPointFinder}
     * @param documentController  the containing document controller
     * @return the overlay widget (which will be the same as the provided <tt>injectionPoint</tt>, or a newly created one if the amendment introduces a new element
     */
    @Override
    public OverlayWidget provideInjectionPoint(final AmendmentController amendmentController,
                                               final OverlayWidget injectionPoint,
                                               final DocumentController documentController) {

        final AmendableWidgetReference reference = amendmentController.getModel().getSourceReference();
        if (reference == null)
            throw new NullPointerException("No reference set on the amendment controller's model with ID: " + amendmentController.getModel().getId());

        // check if we need to create a new element or not
        if (reference.isCreation()) {
            final OverlayWidget toInject = documentController.getOverlayFactory().getAmendableWidget(reference.getNamespaceURI(), reference.getType());

            if (toInject == null)
                throw new NullPointerException("The provided overlay factory " + documentController.getOverlayFactory() + " was not able to provide an injection point for " + reference);

            // mark the origin so we know it was introduced by amendments.
            toInject.setOrigin(OverlayWidgetOrigin.AMENDMENT);
            // make sure our document controller is listening to the UI events
            toInject.setUIListener(documentController.getSourceFileController());

            if (reference.isSibling()) {
                overlayWidgetInjectionStrategy.injectAsSibling(injectionPoint, toInject);
            } else {
                overlayWidgetInjectionStrategy.injectAsChild(injectionPoint, toInject);
            }
            return toInject;
        } else {
            LOG.info("Added amendment directly on " + injectionPoint);
            return injectionPoint;
        }
    }
}
