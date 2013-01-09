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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
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

    private final AmendmentView view;

    private final AmendmentView extendedView;

    private final ClientFactory clientFactory;

    private AmendmentContainerDTO amendment;

    /**
     * Reference to the parent amendable widget we've been added to.
     */
    private AmendableWidget amendedAmendableWidget;

    private AmendableWidget overlayAmendableWidget;

    private int order;

    /**
     * The document controller into which we are injected. If it is not set, we're not injected anywhere.
     */
    private DocumentController documentController;

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
                documentController.getInjector().getAmendmentActionPanelController().show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
            }
        });

        extendedView.getMoreActionsButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final Element relativeElement = event.getRelativeElement();
                documentController.getInjector().getAmendmentActionPanelController().show(relativeElement.getAbsoluteLeft(), relativeElement.getAbsoluteTop() + relativeElement.getOffsetHeight());
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

    public AmendmentContainerDTO getModel() {
        return amendment;
    }

    public AmendableWidget asAmendableWidget() {
        if (overlayAmendableWidget == null) {
            overlayAmendableWidget = documentController.getOverlayFactory().getAmendableWidget(view.getBody().getFirstChildElement());
        }
        return overlayAmendableWidget;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
        setBody(amendment.getXmlContent());
    }

    private void setBody(String xmlContent) {
        view.setBody(xmlContent);
        extendedView.setBody(xmlContent);
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public AmendmentView getView() {
        return view;
    }

    public AmendmentView getExtendedView() {
        return extendedView;
    }

    public void setTitle(String title) {
        this.view.setTitle(title);
        this.extendedView.setTitle(title);
    }

    public void setAmendedAmendableWidget(AmendableWidget amendedAmendableWidget) {
        this.amendedAmendableWidget = amendedAmendableWidget;
    }

    public AmendableWidget getAmendedAmendableWidget() {
        return amendedAmendableWidget;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
        view.setTitle("Amendment " + order);
        extendedView.setTitle("Amendment " + order);
    }
}
