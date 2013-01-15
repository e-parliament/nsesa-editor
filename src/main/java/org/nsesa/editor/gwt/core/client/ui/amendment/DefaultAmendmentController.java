package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.ConfirmationEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerEditEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.action.AmendmentActionPanelController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class DefaultAmendmentController implements AmendmentController {

    protected final AmendmentView view;

    protected final AmendmentView extendedView;

    protected final ClientFactory clientFactory;

    protected AmendmentContainerDTO amendment;

    /**
     * Reference to the parent amendable widget we've been added to.
     */
    protected AmendableWidget amendedAmendableWidget;

    protected AmendableWidget overlayAmendableWidget;

    protected AmendmentActionPanelController amendmentActionPanelController;

    protected int order;

    /**
     * The document controller into which we are injected. If it is not set, we're not injected anywhere.
     */
    protected DocumentController documentController;

    @Inject
    public DefaultAmendmentController(final ClientFactory clientFactory,
                                      final AmendmentView amendmentView,
                                      final AmendmentView amendmentExtendedView) {
        this.clientFactory = clientFactory;
        this.view = amendmentView;
        this.extendedView = amendmentExtendedView;
        registerListeners();
    }

    private void registerListeners() {

        final ClickHandler confirmationHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(DefaultAmendmentController.this));
            }
        };

        final ClickHandler cancelHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // this does not do anything
            }
        };

        final ConfirmationEvent confirmationEvent = new ConfirmationEvent(
                clientFactory.getCoreMessages().confirmationAmendmentDeleteTitle(),
                clientFactory.getCoreMessages().confirmationAmendmentDeleteMessage(),
                clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonConfirm(),
                confirmationHandler,
                clientFactory.getCoreMessages().confirmationAmendmentDeleteButtonCancel(),
                cancelHandler);

        view.getDeleteButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(confirmationEvent);
            }
        });
        extendedView.getDeleteButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(confirmationEvent);
            }
        });
        view.getEditButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
            }
        });

        extendedView.getEditButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
            }
        });

        view.getMoreActionsButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final Element relativeElement = event.getRelativeElement();
                amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
            }
        });

        extendedView.getMoreActionsButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final Element relativeElement = event.getRelativeElement();
                amendmentActionPanelController.setAmendmentController(DefaultAmendmentController.this);
                amendmentActionPanelController.show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
            }
        });

        view.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // don't let it bubble up to its parent amended widget
                event.preventDefault();
            }
        });

        view.addDoubleClickHandler(new DoubleClickHandler() {
            @Override
            public void onDoubleClick(DoubleClickEvent event) {
                // don't let it bubble up to its parent amended widget
                event.preventDefault();
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerEditEvent(DefaultAmendmentController.this));
            }
        });
    }

    @Override
    public AmendmentContainerDTO getModel() {
        return amendment;
    }

    @Override
    public AmendableWidget asAmendableWidget() {
        if (overlayAmendableWidget == null) {
            overlayAmendableWidget = documentController.getOverlayFactory().getAmendableWidget(view.getBody().getFirstChildElement());
        }
        return overlayAmendableWidget;
    }

    @Override
    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
        setBody(amendment.getXmlContent());
        setStatus(amendment.getAmendmentContainerStatus());
    }

    private void setBody(String xmlContent) {
        // reset the overlay, it will be recreated later
        overlayAmendableWidget = null;
        view.setBody(xmlContent);
        extendedView.setBody(xmlContent);
    }

    @Override
    public DocumentController getDocumentController() {
        return documentController;
    }

    @Override
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        this.amendmentActionPanelController = documentController.getInjector().getAmendmentActionPanelController();
        this.documentController.getDocumentEventBus().addHandler(DocumentScrollEvent.TYPE, new DocumentScrollEventHandler() {
            @Override
            public void onEvent(DocumentScrollEvent event) {
                amendmentActionPanelController.hide();
            }
        });
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
        this.view.setTitle(title);
        this.extendedView.setTitle(title);
    }

    @Override
    public void setStatus(String status) {
        this.view.setStatus(status);
        this.extendedView.setStatus(status);
    }

    @Override
    public void setAmendmentContent(String amendmentContent) {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public void setOriginalContent(String originalContent) {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public String getAmendmentContent() {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public String getOriginalContent() {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public void setAmendmentNum(String num) {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public void setOriginalNum(String num) {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public String getAmendmentNum() {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public String getOriginalNum() {
        throw new UnsupportedOperationException("Should be overridden in subclass.");
    }

    @Override
    public void setAmendedAmendableWidget(AmendableWidget amendedAmendableWidget) {
        this.amendedAmendableWidget = amendedAmendableWidget;
    }

    @Override
    public AmendableWidget getAmendedAmendableWidget() {
        return amendedAmendableWidget;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
        view.setTitle("Amendment " + order);
        extendedView.setTitle("Amendment " + order);
    }
}