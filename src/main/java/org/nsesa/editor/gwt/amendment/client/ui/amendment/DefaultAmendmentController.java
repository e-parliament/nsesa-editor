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
import org.nsesa.editor.gwt.amendment.client.ui.amendment.resources.Constants;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.resources.Messages;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.shared.DiffStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(DefaultAmendmentController.class.getName());

    protected AmendmentView view;

    protected AmendmentView extendedView;

    protected final Map<String, AmendmentView> availableViews = new HashMap<String, AmendmentView>();

    protected final Map<String, AmendmentView> availableExtendedViews = new HashMap<String, AmendmentView>();

    protected List<String> viewKeys = new ArrayList<String>(), extendedViewKeys = new ArrayList<String>();

    protected final Constants constants;

    protected final Messages messages;

    protected AmendmentContainerDTO amendment;

    protected AmendmentActionPanelController amendmentActionPanelController;

    protected DiffStyle diffStyle = DiffStyle.EP;

    protected DiffMethod diffMethod = DiffMethod.WORD;

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
                                      final AmendmentActionPanelController amendmentActionPanelController,
                                      final Constants constants,
                                      final Messages messages) {
        this.view = amendmentView;
        this.extendedView = amendmentExtendedView;

        this.amendmentActionPanelController = amendmentActionPanelController;
        this.constants = constants;
        this.messages = messages;
    }

    public void registerListeners() {
        if (view != null) {
            deleteButtonClickHandlerRegistration = view.getDeleteButton() != null ? view.getDeleteButton().addClickHandler(new ClickHandler() {
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
            }) : null;
            editButtonClickHandlerRegistration = view.getEditButton() != null ? view.getEditButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            }) : null;
            moreButtonClickHandlerRegistration = view.getMoreActionsButton() != null ? view.getMoreActionsButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final Element relativeElement = event.getRelativeElement();
                    amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
                }
            }) : null;
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
            extDeleteButtonClickHandlerRegistration = extendedView.getDeleteButton() != null ? extendedView.getDeleteButton().addClickHandler(new ClickHandler() {
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
            }) : null;

            extEditButtonClickHandlerRegistration = extendedView.getEditButton() != null ? extendedView.getEditButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
                }
            }) : null;

            extMoreButtonClickHandlerRegistration = extendedView.getMoreActionsButton() != null ? extendedView.getMoreActionsButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final Element relativeElement = event.getRelativeElement();
                    amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
                }
            }) : null;

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

    public void registerViews() {
        this.availableViews.put(AmendmentView.DEFAULT, this.view);
        this.availableExtendedViews.put(AmendmentView.DEFAULT, this.extendedView);
    }

    protected void switchTemplate(final AmendmentView newAmendmentView, final AmendmentView newExtendedView) {

        // keep track
        final AmendmentView oldView = this.view;
        final AmendmentView oldExtendedView = this.extendedView;

        // cleanup
        removeListeners();
        this.view = newAmendmentView;
        this.extendedView = newExtendedView;

        if (oldView != this.view) {
            DOM.insertBefore((com.google.gwt.user.client.Element) oldView.asWidget().getElement().getParentElement(), this.view.asWidget().getElement(), oldView.asWidget().getElement());
            oldView.detach();
            DOM.removeChild((com.google.gwt.user.client.Element) oldView.asWidget().getElement().getParentElement(), oldView.asWidget().getElement());
            this.view.attach();
        }

        if (oldExtendedView != this.extendedView) {
            DOM.insertBefore((com.google.gwt.user.client.Element) oldExtendedView.asWidget().getElement().getParentElement(), this.extendedView.asWidget().getElement(), oldExtendedView.asWidget().getElement());
            oldExtendedView.detach();
            DOM.removeChild((com.google.gwt.user.client.Element) oldExtendedView.asWidget().getElement().getParentElement(), oldExtendedView.asWidget().getElement());
            this.extendedView.attach();
        }

        setModel(amendment);

        // register listeners again
        registerListeners();
    }

    public void switchTemplate(final String amendmentViewKey, final String extendedViewKey) {
        AmendmentView amendmentView = availableViews.get(amendmentViewKey);
        if (amendmentView == null)
            throw new NullPointerException("Could not find view registered with " + amendmentViewKey);
        AmendmentView extendedView = availableExtendedViews.get(extendedViewKey);
        if (extendedView == null)
            throw new NullPointerException("Could not find extended view registered with " + extendedViewKey);
        switchTemplate(amendmentView, extendedView);
        // keep track
        this.viewKeys.add(amendmentViewKey);
        this.extendedViewKeys.add(extendedViewKey);
    }

    public void resetTemplate() {
        if (viewKeys.size() > 1) {
            final String amendmentViewKey = viewKeys.get(viewKeys.size() - 2);
            final String extendedViewKey = extendedViewKeys.get(extendedViewKeys.size() - 2);
            LOG.info("Resetting template back to " + amendmentViewKey + ", " + extendedViewKey);
            switchTemplate(amendmentViewKey, extendedViewKey);
        } else {
            // reset to default
            LOG.info("Resetting template back to defaults.");
            switchTemplate(AmendmentView.DEFAULT, AmendmentView.DEFAULT);
        }
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    @Override
    public void removeListeners() {
        if (deleteButtonClickHandlerRegistration != null) deleteButtonClickHandlerRegistration.removeHandler();
        if (extDeleteButtonClickHandlerRegistration != null) extDeleteButtonClickHandlerRegistration.removeHandler();
        if (editButtonClickHandlerRegistration != null) editButtonClickHandlerRegistration.removeHandler();
        if (extEditButtonClickHandlerRegistration != null) extEditButtonClickHandlerRegistration.removeHandler();
        if (moreButtonClickHandlerRegistration != null) moreButtonClickHandlerRegistration.removeHandler();
        if (extMoreButtonClickHandlerRegistration != null) extMoreButtonClickHandlerRegistration.removeHandler();
        if (clickHandlerRegistration != null) clickHandlerRegistration.removeHandler();
        if (doubleClickHandlerRegistration != null) doubleClickHandlerRegistration.removeHandler();
        if (extClickHandlerRegistration != null) extClickHandlerRegistration.removeHandler();
        if (extDoubleClickHandlerRegistration != null) extDoubleClickHandlerRegistration.removeHandler();
    }

    @Override
    public void removeViews() {
        for (Map.Entry<String, AmendmentView> view : availableViews.entrySet()) {
            view.getValue().detach();
        }
        availableViews.clear();
        for (Map.Entry<String, AmendmentView> view : availableExtendedViews.entrySet()) {
            view.getValue().detach();
        }
        availableExtendedViews.clear();
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

    public void setBody(String xmlContent) {
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
        if (view != null) {
            view.setTitle(messages.amendmentTitle(Integer.toString(order)));
        }
        if (extendedView != null) {
            extendedView.setTitle(messages.amendmentTitle(Integer.toString(order)));
        }
    }

    @Override
    public int getInjectionPosition() {
        if (amendment.getSourceReference() == null) throw new RuntimeException("Not yet set. --BUG");
        return amendment.getSourceReference().getOffset();
    }

    @Override
    public DiffStyle getDiffStyle() {
        return diffStyle;
    }

    @Override
    public DiffMethod getDiffMethod() {
        return diffMethod;
    }

    @Override
    public void setDiffStyle(DiffStyle diffStyle) {
        this.diffStyle = diffStyle;
    }

    @Override
    public void setDiffMethod(DiffMethod diffMethod) {
        this.diffMethod = diffMethod;
    }
}
