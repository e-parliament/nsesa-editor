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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.content;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingAttributesToggleEventHandler;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEvent;
import org.nsesa.editor.gwt.core.client.event.drafting.DraftingToggleEventHandler;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

/**
 * Content tab to display the original content of an amendment.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentPanelController implements AmendmentDialogAwareController {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The main view.
     */
    protected final ContentControllerView view;

    /**
     * The dialog context with runtime information.
     */
    protected DialogContext dialogContext;
    private HandlerRegistration draftingToggleEventHandlerRegistration;
    private HandlerRegistration draftingAttributesToggleEventHandlerRegistration;

    @Inject
    public ContentPanelController(final ClientFactory clientFactory, final ContentControllerView view) {
        this.clientFactory = clientFactory;
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        draftingToggleEventHandlerRegistration = clientFactory.getEventBus().addHandler(DraftingToggleEvent.TYPE, new DraftingToggleEventHandler() {
            @Override
            public void onEvent(DraftingToggleEvent event) {
                view.getRichTextEditor().toggleDraftingTool(event.isShown());
            }
        });
        draftingAttributesToggleEventHandlerRegistration = clientFactory.getEventBus().addHandler(DraftingAttributesToggleEvent.TYPE, new DraftingAttributesToggleEventHandler() {
            @Override
            public void onEvent(DraftingAttributesToggleEvent event) {
                view.getRichTextEditor().toggleDraftingAttributes(event.isShown());
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        draftingAttributesToggleEventHandlerRegistration.removeHandler();
        draftingToggleEventHandlerRegistration.removeHandler();
    }

    /**
     * Validate the data in the content panel. Defaults to returning <tt>true</tt>
     *
     * @return <tt>true</tt> if the content is valid
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Set the dialog context on this panel.
     *
     * @param dialogContext the dialog context
     */
    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }

    /**
     * Get the view to add to the tab.
     *
     * @return the view
     */
    @Override
    public ContentControllerView getView() {
        return view;
    }

    /**
     * Get the title for the tab.
     * TODO i18n
     *
     * @return the title
     */
    @Override
    public String getTitle() {
        return "Original";
    }
}
