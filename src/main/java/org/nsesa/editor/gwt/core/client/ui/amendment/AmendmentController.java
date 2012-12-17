package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerDeleteEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.Comparator;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(AMENDMENT)
public class AmendmentController {

    public static Comparator<AmendmentController> ORDER_COMPARATOR = new Comparator<AmendmentController>() {
        @Override
        public int compare(AmendmentController a, AmendmentController b) {
            return Integer.valueOf(a.getOrder()).compareTo(b.getOrder());
        }
    };

    private final AmendmentInjector amendmentInjector = GWT.create(AmendmentInjector.class);

    private final AmendmentView view;
    private final AmendmentView extendedView;

    private final ClientFactory clientFactory;

    private final AmendmentEventBus amendmentEventBus;

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
    public AmendmentController(final ClientFactory clientFactory,
                               final AmendmentView amendmentView, final AmendmentView amendmentExtendedView) {
        this.clientFactory = clientFactory;

        this.view = amendmentView;
        this.extendedView = amendmentExtendedView;
        this.amendmentEventBus = amendmentInjector.getAmendmentEventBus();
        registerListeners();
    }

    private void registerListeners() {
        view.getDeleteButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(AmendmentController.this));
            }
        });
        extendedView.getDeleteButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                documentController.getDocumentEventBus().fireEvent(new AmendmentContainerDeleteEvent(AmendmentController.this));
            }
        });
    }

    public AmendmentContainerDTO getModel() {
        return amendment;
    }

    // TEMPORARY!!!

    public String getOriginalContent() {
        final List<AmendableWidget> quotedStructures = OverlayUtil.find("quotedStructure", overlay());
        return quotedStructures.get(0).getInnerHTML();
    }

    public void setOriginalContent(final String originalContent) {
        final List<AmendableWidget> quotedStructures = OverlayUtil.find("quotedStructure", overlay());
        quotedStructures.get(0).setInnerHTML(originalContent);
    }

    public String getAmendmendContent() {
        final List<AmendableWidget> quotedStructures = OverlayUtil.find("quotedStructure", overlay());
        return quotedStructures.get(1).getInnerHTML();
    }

    public void setAmendmentContent(final String amendmentContent) {
        final List<AmendableWidget> quotedStructures = OverlayUtil.find("quotedStructure", overlay());
        quotedStructures.get(1).setInnerHTML(amendmentContent);
    }

    protected AmendableWidget overlay() {
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
