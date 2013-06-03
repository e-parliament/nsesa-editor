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
package org.nsesa.editor.gwt.amendment.client.ui.amendment;

import com.google.gwt.dom.client.Element;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.Comparator;

/**
 * A controller for an amendment model. Can be injected on an {@link OverlayWidget} by calling its
 * {@link OverlayWidget#addOverlayWidgetAware(OverlayWidget)} method. Represents a single injected
 * amendment in an overlay tree if the {@link #getDocumentController()} is returning a not-<tt>null</tt> value.
 * <p/>
 * Date: 09/01/13 16:46
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentController.class)
public interface AmendmentController extends OverlayWidgetAware {

    /**
     * Simple amendment controller {@link Comparator} to sort amendment controllers according to their
     * {@link org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController#getOrder()} values.
     */
    public static Comparator<AmendmentController> ORDER_COMPARATOR = new Comparator<AmendmentController>() {
        @Override
        public int compare(AmendmentController a, AmendmentController b) {
            return Integer.valueOf(a.getOrder()).compareTo(b.getOrder());
        }
    };

    /**
     * Returns the underlying DTO model.
     *
     * @return the model
     */
    AmendmentContainerDTO getModel();

    /**
     * Set the model on this controller.
     *
     * @param amendment the model to set.
     */
    void setModel(AmendmentContainerDTO amendment);

    /**
     * Transforms the given <tt>source</tt> into an overlay widget tree.
     *
     * @param source the XML payload to transform into the overlay tree
     * @return the overlay widget, or <tt>null</tt> if it cannot be created
     */
    OverlayWidget asAmendableWidget(String source);

    /**
     * Transforms the given <tt>element</tt> into an overlay widget tree.
     *
     * @param element the element to transform into the overlay tree
     * @return the overlay widget, or <tt>null</tt> if it cannot be created
     */
    OverlayWidget asAmendableWidget(Element element);

    /**
     * Returns the document controller set on this model. This will return a non-<tt>null</tt> value if the amendent
     * controller has actually been injected into a document controller.
     *
     * @return the document controller, or <tt>null</tt> if it has not been set
     */
    DocumentController getDocumentController();

    /**
     * Sets the document controller.
     *
     * @param documentController the document controller
     */
    void setDocumentController(DocumentController documentController);

    /**
     * Get the main amendment view (the one injected into the document controller)
     *
     * @return the amendment view
     */
    AmendmentView getView();

    /**
     * Get the extended view (used in the amendments tab) with extended UI elements
     *
     * @return the extended amendment view
     */
    AmendmentView getExtendedView();

    /**
     * Set the title on the amendment views
     *
     * @param title the title to set
     */
    void setTitle(String title);

    /**
     * Set the status on the amendment views
     *
     * @param status the status to set
     */
    void setStatus(String status);

    /**
     * Get the local order of the amendment.
     *
     * @return the order
     */
    int getOrder();

    /**
     * Sets the local order of the amendment in the document.
     *
     * @param order
     */
    void setOrder(int order);


    void setInjectionPosition(int injectionPosition);

    /**
     * Removes all event listeners that have been added to the eventbus, and any listeners for UI callbacks.
     */
    void removeListeners();
}
