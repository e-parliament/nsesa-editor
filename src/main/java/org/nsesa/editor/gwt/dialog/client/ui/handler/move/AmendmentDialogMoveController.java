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
package org.nsesa.editor.gwt.dialog.client.ui.handler.move;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetUIListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.shared.DocumentContentDTO;
import org.nsesa.editor.gwt.dialog.client.event.CloseDialogEvent;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandler;
import org.nsesa.editor.gwt.dialog.client.ui.handler.AmendmentUIHandlerImpl;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.action.BeforeAfterActionBarController;

/**
 * Dialog controller to handle the creation and editing of a movement amendments (amendments suggesting the move of
 * pre-existing a (complex) structure from the document).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogMoveController extends AmendmentUIHandlerImpl implements ProvidesResize, AmendmentUIHandler {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final AmendmentDialogMoveView view;

    private HandlerRegistration cancelClickHandlerRegistration;

    private final ContentController contentController;

    private final BeforeAfterActionBarController beforeAfterActionBarController;

    private OverlayWidget root;

    @Inject
    public AmendmentDialogMoveController(final ClientFactory clientFactory, final AmendmentDialogMoveView view,
                                         final ContentController contentController,
                                         final BeforeAfterActionBarController beforeAfterActionBarController) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.contentController = contentController;
        this.beforeAfterActionBarController = beforeAfterActionBarController;
        this.beforeAfterActionBarController.setContentController(contentController);
    }

    public void registerListeners() {
        cancelClickHandlerRegistration = view.getCancelLink().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clientFactory.getEventBus().fireEvent(new CloseDialogEvent());
            }
        });

        beforeAfterActionBarController.registerListeners();
        contentController.registerListeners();
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        cancelClickHandlerRegistration.removeHandler();
        beforeAfterActionBarController.removeListeners();
        contentController.removeListeners();
    }

    /**
     * Get the associated view.
     *
     * @return the view
     */
    @Override
    public AmendmentDialogMoveView getView() {
        return view;
    }

    /**
     * Handle the context and pass it on to child
     * {@link org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController}s, validated it, ..
     */
    @Override
    public void handle() {

        final DocumentController documentController = dialogContext.getDocumentController();
        documentController.getServiceFactory().getGwtDocumentService().getDocumentContent(clientFactory.getClientContext(), documentController.getDocumentID(), new AsyncCallback<DocumentContentDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not retrieve document content with ID " + documentController.getDocumentID(), caught));
            }

            @Override
            public void onSuccess(DocumentContentDTO result) {
                contentController.setContent(result.getContent());
                root = documentController.getOverlayFactory().getAmendableWidget(contentController.getContentElements()[0]);

                root.walk(new OverlayWidgetWalker.DefaultOverlayWidgetVisitor() {
                    @Override
                    public boolean visit(final OverlayWidget visited) {
                        if (visited.getId() != null && !"".equals(visited.getId())) {
                            visited.setUIListener(new OverlayWidgetUIListener() {
                                @Override
                                public void onClick(OverlayWidget sender, Event event) {
                                    beforeAfterActionBarController.setOverlayWidgetToMove(dialogContext.getOverlayWidget());
                                    beforeAfterActionBarController.setOverlayWidget(sender);
                                }

                                @Override
                                public void onDblClick(OverlayWidget sender, Event event) {
                                    // do nothing
                                }

                                @Override
                                public void onMouseOver(OverlayWidget sender, Event event) {
                                    //beforeAfterActionBarController.setOverlayWidget(sender);
                                }

                                @Override
                                public void onMouseOut(OverlayWidget sender, Event event) {
                                    // do nothing
                                }
                            });
                        }
                        return true;
                    }
                });
            }
        });
    }
}
