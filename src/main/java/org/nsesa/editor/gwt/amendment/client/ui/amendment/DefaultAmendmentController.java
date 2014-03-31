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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.amendment.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.resources.Constants;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.resources.Messages;
import org.nsesa.editor.gwt.amendment.client.ui.document.AmendmentDocumentController;
import org.nsesa.editor.gwt.amendment.client.ui.document.Describer;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.InformationEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.core.shared.DiffStyle;
import org.nsesa.editor.gwt.core.shared.GroupDTO;

import java.util.*;
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

    protected List<AmendmentController> bundledAmendmentControllers = new ArrayList<AmendmentController>();

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
     * Flag to keep track if this controller is part of a bundle or not.
     */
    protected boolean bundled;

    /**
     * The document controller into which we are injected. If it is not set, we're not injected anywhere.
     */
    protected DocumentController documentController;

    private ClickHandler confirmationHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final String status = getModel().getAmendmentContainerStatus();
            if (!STATUS_CANDIDATE.equalsIgnoreCase(status) && !STATUS_WITHDRAWN.equalsIgnoreCase(status)) {
                // you're only allowed to remove
                documentController.getClientFactory().getEventBus().fireEvent(new InformationEvent("Sorry, you cannot do that", "You can only delete candidate or withdrawn amendments. Please withdraw the amendment first."));
            } else {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(DefaultAmendmentController.this));
            }
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

    /**
     * Just for easier testing ...
     */
    public DefaultAmendmentController() {
        this.view = null;
        this.extendedView = null;
        this.amendmentActionPanelController = null;
        this.constants = null;
        this.messages = null;
    }

    @Inject
    public DefaultAmendmentController(final AmendmentView amendmentView,
                                      final AmendmentView amendmentExtendedView,
                                      final AmendmentActionPanelController amendmentActionPanelController,
                                      final Constants constants,
                                      final Messages messages) {
        this.view = amendmentView;
        this.extendedView = amendmentExtendedView;

        this.amendmentActionPanelController = amendmentActionPanelController;
        this.amendmentActionPanelController.registerListeners();
        this.constants = constants;
        this.messages = messages;
    }

    public void registerListeners() {
        registerListenersOnView();
        registerListenersOnExtendedView();
    }

    private void registerListenersOnView() {
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
                    final int offsetWidth = amendmentActionPanelController.getView().asWidget().getOffsetWidth();
                    // we're not guaranteed to have an offset width already, so use 120 in that case (min-width)
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft() - (offsetWidth == 0 ? 112 : offsetWidth), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight() - 2);
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
    }

    private void registerListenersOnExtendedView() {
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
                    final int offsetWidth = amendmentActionPanelController.getView().asWidget().getOffsetWidth();
                    // we're not guaranteed to have an offset width already, so use 120 in that case (min-width)
                    amendmentActionPanelController.show(relativeElement.getAbsoluteLeft() - (offsetWidth == 0 ? 112 : offsetWidth), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
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

        if (newAmendmentView != null) {
            final AmendmentView oldView = this.view;
            removeListenersFromView();
            this.view = newAmendmentView;

            if (oldView != this.view) {

                final Widget parent = oldView.asWidget().getParent();
                final Element parentElement = oldView.asWidget().getElement().getParentElement();

                final int childIndex = DOM.getChildIndex((com.google.gwt.user.client.Element) parentElement, oldView.asWidget().getElement());
                oldView.asWidget().removeFromParent();
                if (parent instanceof HasWidgets) {
                    ((HasWidgets) parent).add(this.view.asWidget());
                } else {
                    DOM.insertChild((com.google.gwt.user.client.Element) parentElement, this.view.asWidget().getElement(), childIndex);
                }
                this.view.attach();
            }
            registerListenersOnView();
        } else {
            LOG.info("View template not switched because null view has been passed.");
        }

        if (newExtendedView != null) {
            final AmendmentView oldExtendedView = this.extendedView;
            removeListenersFromExtendedView();
            this.extendedView = newExtendedView;
            if (oldExtendedView != this.extendedView) {
                final Widget parent = oldExtendedView.asWidget().getParent();
                final Element parentElement = oldExtendedView.asWidget().getElement().getParentElement();

                final int childIndex = DOM.getChildIndex((com.google.gwt.user.client.Element) parentElement, oldExtendedView.asWidget().getElement());
                oldExtendedView.asWidget().removeFromParent();
                if (parent instanceof HasWidgets) {
                    ((HasWidgets) parent).add(this.extendedView.asWidget());
                } else {
                    DOM.insertChild((com.google.gwt.user.client.Element) parentElement, this.extendedView.asWidget().getElement(), childIndex);
                }
                this.extendedView.attach();
            }
            registerListenersOnExtendedView();
        } else {
            LOG.info("View template not switched because null view has been passed.");
        }
        // keep track
        setModel(amendment);
    }

    public void switchTemplate(final String amendmentViewKey, final String extendedViewKey) {
        AmendmentView amendmentView = null;
        if (amendmentViewKey != null) {
            amendmentView = availableViews.get(amendmentViewKey);
            if (amendmentView == null)
                throw new NullPointerException("Could not find view registered with " + amendmentViewKey);
        }
        AmendmentView extendedView = null;
        if (extendedViewKey != null) {
            extendedView = availableExtendedViews.get(extendedViewKey);
            if (extendedView == null)
                throw new NullPointerException("Could not find extended view registered with " + extendedViewKey);
        }
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
        removeListenersFromView();
        removeListenersFromExtendedView();
    }

    private void removeListenersFromView() {
        if (clickHandlerRegistration != null) clickHandlerRegistration.removeHandler();
        if (deleteButtonClickHandlerRegistration != null) deleteButtonClickHandlerRegistration.removeHandler();
        if (doubleClickHandlerRegistration != null) doubleClickHandlerRegistration.removeHandler();
        if (editButtonClickHandlerRegistration != null) editButtonClickHandlerRegistration.removeHandler();
        if (moreButtonClickHandlerRegistration != null) moreButtonClickHandlerRegistration.removeHandler();
    }

    private void removeListenersFromExtendedView() {
        if (extClickHandlerRegistration != null) extClickHandlerRegistration.removeHandler();
        if (extDeleteButtonClickHandlerRegistration != null) extDeleteButtonClickHandlerRegistration.removeHandler();
        if (extDoubleClickHandlerRegistration != null) extDoubleClickHandlerRegistration.removeHandler();
        if (extEditButtonClickHandlerRegistration != null) extEditButtonClickHandlerRegistration.removeHandler();
        if (extMoreButtonClickHandlerRegistration != null) extMoreButtonClickHandlerRegistration.removeHandler();

    }

    public void removeViews() {
        for (Map.Entry<String, AmendmentView> view : availableViews.entrySet()) {
            view.getValue().detach();
        }
        availableViews.clear();
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
    public void setModel(final AmendmentContainerDTO amendment) {
        this.amendment = amendment;
        setBody(amendment.getBody());
        setStatus(amendment.getAmendmentContainerStatus());
        setBundle(amendment.getBundledAmendmentContainerIDs());

        String[] names = new String[amendment.getGroups().size()];
        int index = 0;
        for (GroupDTO groupDTO : amendment.getGroups()) {
            names[index++] = groupDTO.getName();
        }
        setGroups(names);

        if (documentController instanceof AmendmentDocumentController) {
            AmendmentDocumentController amendmentDocumentController = (AmendmentDocumentController) documentController;
            final Describer describer = amendmentDocumentController.getDescriber();
            if (describer != null) {
                setIntroduction(describer.introduction(this, documentController.getDocument().getLanguageIso()));
                setDescription(describer.describe(this, documentController.getDocument().getLanguageIso()));
            }
        }
    }

    @Override
    public void mergeModel(AmendmentContainerDTO amendment, boolean onlyChangedAttributes) {
        if (!getModel().getRevisionID().equals(amendment.getRevisionID())) {
            throw new IllegalArgumentException("You cannot merge an amendment with a different revisionID. Expected "
                    + getModel().getRevisionID() + ", but got " + amendment.getRevisionID());
        }

        if (getModel().getAmendmentAction() != amendment.getAmendmentAction()) {
            getModel().setAmendmentAction(amendment.getAmendmentAction());
        }

        if (!getModel().getBody().equals(amendment.getBody())) {
            getModel().setBody(amendment.getBody());
            setBody(getModel().getBody());
        }

        if (getModel().getBundledAmendmentContainerIDs() != null && amendment.getBundledAmendmentContainerIDs() != null
                && !Arrays.asList(getModel().getBundledAmendmentContainerIDs()).equals(Arrays.asList(amendment.getBundledAmendmentContainerIDs()))) {
            getModel().setBundledAmendmentContainerIDs(amendment.getBundledAmendmentContainerIDs());
            setBundle(getModel().getBundledAmendmentContainerIDs());
        }

        if (!getModel().getAmendmentContainerStatus().equals(amendment.getAmendmentContainerStatus())) {
            getModel().setAmendmentContainerStatus(amendment.getAmendmentContainerStatus());
            setStatus(getModel().getAmendmentContainerStatus());
        }
        if (!getModel().getGroups().equals(amendment.getGroups())) {
            getModel().setGroups(amendment.getGroups());
            String[] names = new String[amendment.getGroups().size()];
            int index = 0;
            for (GroupDTO groupDTO : amendment.getGroups()) {
                names[index++] = groupDTO.getName();
            }
            setGroups(names);
        }

        if (!getModel().getDocumentID().equals(amendment.getDocumentID())) {
            getModel().setDocumentID(amendment.getDocumentID());
        }

        if (!getModel().getLanguageISO().equals(amendment.getLanguageISO())) {
            getModel().setLanguageISO(amendment.getLanguageISO());
        }
    }

    @Override
    public void setBody(final String xmlContent) {
        if (view != null)
            view.setBody(xmlContent);
        if (extendedView != null)
            extendedView.setBody(xmlContent);
    }

    @Override
    public void setBundle(final String[] amendmentContainerIDs) {
        if (view != null)
            view.setBundle(amendmentContainerIDs);
        if (extendedView != null)
            extendedView.setBundle(amendmentContainerIDs);
    }

    public void setIntroduction(final String introduction) {
        if (this.view != null) {
            this.view.setIntroduction(introduction);
        }
        if (this.extendedView != null) {
            this.extendedView.setIntroduction(introduction);
        }
    }

    public void setDescription(final String description) {
        if (this.view != null) {
            this.view.setDescription(description);
        }
        if (this.extendedView != null) {
            this.extendedView.setDescription(description);
        }
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
    public void setGroups(String... groups) {
        if (view != null)
            this.view.setGroups(groups);
        if (extendedView != null)
            this.extendedView.setGroups(groups);
    }

    @Override
    public void setId(String id) {
        if (view != null)
            this.view.setId(id);
        if (extendedView != null)
            this.extendedView.setId(id);
    }

    @Override
    public void setOverlayWidget(final OverlayWidget amendedOverlayWidget) {
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
    public void setOrder(final int order) {
        this.order = order;
        if (view != null) {
            view.setTitle(messages.amendmentTitle(Integer.toString(order)));
        }
        if (extendedView != null) {
            extendedView.setTitle(messages.amendmentTitle(Integer.toString(order)));
        }
    }

    @Override
    public Integer getInjectionPosition() {
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
    public void setDiffStyle(final DiffStyle diffStyle) {
        this.diffStyle = diffStyle;
    }

    @Override
    public void setDiffMethod(final DiffMethod diffMethod) {
        this.diffMethod = diffMethod;
    }

    @Override
    public boolean isBundle() {
        return amendment.getBundledAmendmentContainerIDs() != null && amendment.getBundledAmendmentContainerIDs().length > 0;
    }

    @Override
    public void setBundled(final boolean isBundled) {
        this.bundled = isBundled;
    }

    @Override
    public boolean isBundled() {
        return bundled;
    }

    @Override
    public void mergeIntoBundle(final AmendmentController toBundle) {
        // do nothing - supposed to be overridden
    }

    @Override
    public void removedFromBundle(final AmendmentController amendmentController) {
        // do nothing - supposed to be overridden
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultAmendmentController)) return false;

        DefaultAmendmentController that = (DefaultAmendmentController) o;

        if (amendment != null ? !amendment.equals(that.amendment) : that.amendment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amendment != null ? amendment.hashCode() : 0;
    }
}
