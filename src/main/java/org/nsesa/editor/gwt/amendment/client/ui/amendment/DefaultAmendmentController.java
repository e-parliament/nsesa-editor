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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Default implementation of the {@link AmendmentController}
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class DefaultAmendmentController implements AmendmentController {

    protected final AmendmentView view;

    protected final AmendmentView extendedView;

    protected AmendmentContainerDTO amendment;

    protected AmendmentActionPanelController amendmentActionPanelController;

    /**
     * Reference to the parent amendable widget we've been added to.
     */
    protected OverlayWidget amendedOverlayWidget;

    protected int order;

    /**
     * The document controller into which we are injected. If it is not set, we're not injected anywhere.
     */
    protected DocumentController documentController;

    private ClickHandler confirmationHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            documentController.getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(DefaultAmendmentController.this));
        }
    };
    private ClickHandler cancelHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // this does not do anything
        }
    };

    private HandlerRegistration deleteButtonClickHandlerRegistration;
    private HandlerRegistration extDeleteButtonClickHandlerRegistration;
    private HandlerRegistration editButtonClickHandlerRegistration;
    private HandlerRegistration extEditButtonClickHandlerRegistration;
    private HandlerRegistration moreButtonClickHandlerRegistration;
    private HandlerRegistration extMoreButtonClickHandlerRegistration;
    private HandlerRegistration clickHandlerRegistration;
    private HandlerRegistration doubleClickHandlerRegistration;
    private HandlerRegistration extClickHandlerRegistration;
    private HandlerRegistration extDoubleClickHandlerRegistration;

    private Integer injectionPosition;

    @Inject
    public DefaultAmendmentController(final AmendmentView amendmentView,
                                      final AmendmentView amendmentExtendedView,
                                      final AmendmentActionPanelController amendmentActionPanelController) {
        this.view = amendmentView;
        this.extendedView = amendmentExtendedView;
        this.amendmentActionPanelController = amendmentActionPanelController;
        registerListeners();
    }

    private void registerListeners() {
        if (view != null) {
            deleteButtonClickHandlerRegistration = view.getDeleteButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {

                    final ClientFactory clientFactory = documentController.getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonConfirm(),
                            confirmationHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonCancel(),
                            cancelHandler);

                    documentController.getDocumentEventBus().fireEvent(confirmationEvent);
                }
            });
            editButtonClickHandlerRegistration = view.getEditButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            });
            moreButtonClickHandlerRegistration = view.getMoreActionsButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final Element relativeElement = event.getRelativeElement();
                    amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
                }
            });
            clickHandlerRegistration = view.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    // don't let it bubble up to its parent amended widget
                    event.preventDefault();
                }
            });
            doubleClickHandlerRegistration = view.addDoubleClickHandler(new DoubleClickHandler() {
                @Override
                public void onDoubleClick(DoubleClickEvent event) {
                    // don't let it bubble up to its parent amended widget
                    event.preventDefault();
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            });
        }
        if (extendedView != null) {
            extDeleteButtonClickHandlerRegistration = extendedView.getDeleteButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {

                    final ClientFactory clientFactory = documentController.getClientFactory();
                    final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(),
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonConfirm(),
                            confirmationHandler,
                            clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonCancel(),
                            cancelHandler);
                    documentController.getDocumentEventBus().fireEvent(confirmationEvent);
                }
            });

            extEditButtonClickHandlerRegistration = extendedView.getEditButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            });

            extMoreButtonClickHandlerRegistration = extendedView.getMoreActionsButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final Element relativeElement = event.getRelativeElement();
                    amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
                }
            });

            extClickHandlerRegistration = extendedView.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    // don't let it bubble up to its parent amended widget
                    event.preventDefault();
                }
            });

            extDoubleClickHandlerRegistration = extendedView.addDoubleClickHandler(new DoubleClickHandler() {
                @Override
                public void onDoubleClick(DoubleClickEvent event) {
                    // don't let it bubble up to its parent amended widget
                    event.preventDefault();
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            });
        }
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        deleteButtonClickHandlerRegistration.removeHandler();
        extDeleteButtonClickHandlerRegistration.removeHandler();
        editButtonClickHandlerRegistration.removeHandler();
        extEditButtonClickHandlerRegistration.removeHandler();
        moreButtonClickHandlerRegistration.removeHandler();
        extMoreButtonClickHandlerRegistration.removeHandler();
        clickHandlerRegistration.removeHandler();
        doubleClickHandlerRegistration.removeHandler();
        extClickHandlerRegistration.removeHandler();
        extDoubleClickHandlerRegistration.removeHandler();
    }

    @Override
    public AmendmentContainerDTO getModel() {
        return amendment;
    }

    @Override
    public OverlayWidget asAmendableWidget(final String source) {
        final com.google.gwt.user.client.Element span = DOM.createSpan();
        span.setInnerHTML(source);
        return asAmendableWidget(span.getFirstChildElement());
    }

    @Override
    public OverlayWidget asAmendableWidget(final Element element) {
        return documentController.getOverlayFactory().getAmendableWidget(element);
    }

    @Override
    public void setModel(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
        setBody(amendment.getBody());
        setStatus(amendment.getAmendmentContainerStatus());
    }

    private void setBody(String xmlContent) {
        if (view != null)
            view.setBody(xmlContent);
        if (extendedView != null)
            extendedView.setBody(xmlContent);
    }

    @Override
    public DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Sets the document controller. If the document controller is not <tt>null</tt> (which can happen if an amendment
     * controller is no longer injected in a document controller), then we also register the event listeners.
     *
     * @param documentController the document controller
     */
    @Override
    public void setDocumentController(final DocumentController documentController) {
        this.documentController = documentController;
    }

    @Override
    public AmendmentView getView() {
        return view;
    }

    @Override
    public AmendmentView getExtendedView() {
        return extendedView;
    }

    @Override
    public void setTitle(String title) {
        if (view != null)
            this.view.setTitle(title);
        if (extendedView != null)
            this.extendedView.setTitle(title);
    }

    @Override
    public void setStatus(String status) {
        if (view != null)
            this.view.setStatus(status);
        if (extendedView != null)
            this.extendedView.setStatus(status);
    }

    @Override
    public void setOverlayWidget(OverlayWidget amendedOverlayWidget) {
        this.amendedOverlayWidget = amendedOverlayWidget;
    }

    @Override
    public OverlayWidget getOverlayWidget() {
        return amendedOverlayWidget;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
        if (view != null)
            view.setTitle("Amendment " + order);
        if (extendedView != null)
            extendedView.setTitle("Amendment " + order);
    }

    @Override
    public void setInjectionPosition(int injectionPosition) {
        this.injectionPosition = injectionPosition;
    }

    @Override
    public int getInjectionPosition() {
        if (amendment.getSourceReference() == null) throw new RuntimeException("Not yet set. --BUG");
        return amendment.getSourceReference().getOffset();
    }
}
