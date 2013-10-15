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
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.diffing.DiffingManager;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.SetWindowTitleEvent;
import org.nsesa.editor.gwt.core.client.mode.DocumentMode;
import org.nsesa.editor.gwt.core.client.mode.DocumentState;
import org.nsesa.editor.gwt.core.client.ref.ReferenceHandler;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Creator;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.DocumentContentDTO;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

/**
 * Main controller, responsible for loading and rendering, and entry point for executing actions on a document.
 * <p/>
 * Date: 24/06/12 18:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultDocumentController.class)
public interface DocumentController {

    /**
     * Set the {@link DocumentInjector} to use to provide the dependencies for this controller.
     *
     * @param documentInjector the injector
     */
    void setInjector(DocumentInjector documentInjector);

    /**
     * A callback method for subclasses to register available {@link DocumentMode}s.
     */
    void registerModes();

    /**
     * A callback method for subclasses to register available {@link org.nsesa.editor.gwt.core.client.keyboard.KeyboardListener.KeyCombo}s.
     */
    void registerKeyCombos();

    /**
     * A callback method for subclasses to register available listeners (on the event bus or views).
     */
    void registerListeners();

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    void removeListeners();

    /**
     * Shows or hides a loading indicator when the document content is retrieved and overlaid.
     *
     * @param show    <tt>true</tt> to show the loading indicator, <tt>false</tt> to hide it
     * @param message the message to show, if any
     */
    void showLoadingIndicator(boolean show, String message);

    /**
     * Register a new mode to use under a given <tt>key</tt>. We can then check for the existing of this mode
     * via {@link #getMode(String)} with the same key, and request/change its state.
     *
     * @param key  the key to register the mode under - it if already exists, it will be overridden with the new mode
     * @param mode the mode to register
     */
    void registerMode(final String key, final DocumentMode<? extends DocumentState> mode);

    <S extends DocumentState> boolean applyState(String key, S state);

    /**
     * Get back the mode that was registered under the given <tt>key</tt>, or <tt>null</tt> if it does not exist.
     *
     * @param key the key under which the mode was registered
     * @return the mode, or <tt>null</tt> if it cannot be found
     */
    DocumentMode<? extends DocumentState> getMode(final String key);

    /**
     * Loads a document from the backend using the {@link ServiceFactory} with the {@link #getDocumentID()}. Note that
     * this is only the {@link DocumentDTO}, not the actual content call (which is loaded via
     * {@link #loadDocumentContent()}.
     * <p/>
     * If this call fails, a {@link CriticalErrorEvent} is fired on the global event bus.
     */
    void loadDocument();

    /**
     * Callback when the document is loaded successfully from the backend after {@link #loadDocument()}.
     * This will set the document using {@link #setDocument(org.nsesa.editor.gwt.core.shared.DocumentDTO)},
     * update the title via a {@link SetWindowTitleEvent}, and request the actual document to be loaded via
     * {@link #loadDocumentContent()}.
     *
     * @param document the received document DTO
     */
    void onDocumentLoaded(DocumentDTO document);

    /**
     * Requests the loading of the document content for the {@link #getDocument()} DTO by its {@link #getDocumentID()}
     * via the {@link #getServiceFactory()}.
     * <p/>
     * If this call fails, a {@link CriticalErrorEvent} is fired on the global event bus. If the request was successful,
     * we call {@link #onDocumentContentLoaded(DocumentContentDTO)} with the received content.
     */
    void loadDocumentContent();

    /**
     * Callback when the document content was successfully received. Will set the received content on the
     * {@link #getSourceFileController()}..
     *
     * @param content the received document content to be placed in the source file controller
     */
    void onDocumentContentLoaded(final DocumentContentDTO content);

    /**
     * Sets the document Data Transfer Object (DTO) on this controller, and this marks the beginning of our document
     * loading, meaning that we have to:
     * <ul>
     * <li>Clear any existing amendments</li>
     * <li>Update the window title</li>
     * <li>Set the new deadline for the document, if any</li>
     * <li>Get all available translations via {@link ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService#getAvailableTranslations(org.nsesa.editor.gwt.core.shared.ClientContext, String)}</li>
     * <li>Get all available related documents (annexes, introductions, draft legislative resolutions, ...) via
     * {@link ServiceFactory}'s
     * {@link org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentService#getRelatedDocuments(org.nsesa.editor.gwt.core.shared.ClientContext, String)}</li>
     * </ul>
     *
     * @param document the document DTO to set
     */
    void setDocument(final DocumentDTO document);

    /**
     * Return a reference to the view.
     *
     * @return the view
     */
    DocumentView getView();

    /**
     * Set the width of the view.
     *
     * @param width the with to set
     */
    void setWidth(final String width);

    /**
     * Get a {@link DocumentInjector}, responsible for getting the various lower components used in this document
     * controller.
     *
     * @return the document injector
     */
    DocumentInjector getInjector();

    /**
     * Sets the document ID on this controller.
     *
     * @param documentID the document ID
     */
    void setDocumentID(String documentID);

    /**
     * Get the document ID.
     *
     * @return the document ID
     */
    String getDocumentID();

    /**
     * Get the document DTO.
     *
     * @return the document DTO.
     */
    DocumentDTO getDocument();

    /**
     * Get a reference to the creator for this document controller.
     *
     * @return the creator
     */
    Creator getCreator();

    /**
     * Get a reference to the locator for this document controller.
     *
     * @return the locator
     */
    Locator getLocator();

    /**
     * Get a reference to the client factory for this document controller.
     *
     * @return the client factory
     */
    ClientFactory getClientFactory();

    /**
     * Get a reference to the service factory for this document controller.
     *
     * @return the service factory
     */
    ServiceFactory getServiceFactory();

    /**
     * Get a reference to the private event bus for this document controller.
     *
     * @return the private event bus
     */
    DocumentEventBus getDocumentEventBus();

    /**
     * Get a reference to the overlay factory for this document controller.
     *
     * @return the overlay factory
     */
    OverlayFactory getOverlayFactory();

    /**
     * Get a reference to the reference handler for (local) overlay widgets.
     *
     * @return the reference handler
     */
    ReferenceHandler<OverlayWidget> getLocalOverlayWidgetReferenceHandler();

    /**
     * Get a reference to the diffing manager for this document controller.
     *
     * @return the diffing manager
     */
    DiffingManager getDiffingManager();

    /**
     * Get a reference to the underlying source file content controller for this document controller.
     *
     * @return the source file controller
     */
    SourceFileController getSourceFileController();
}
