package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentController {

    private final AmendmentInjector amendmentInjector = GWT.create(AmendmentInjector.class);

    private final AmendmentView view;
    private final ClientFactory clientFactory;
    private OverlayFactory overlayFactory;
    private final AmendmentEventBus amendmentEventBus;

    private AmendmentContainerDTO amendment;

    private AmendableWidget parentAmendableWidget;

    private AmendableWidget amendmentWidget;

    /**
     * The document controller into which we are injected. If it is not set, we're not injected anywhere.
     */
    private DocumentController documentController;

    @Inject
    public AmendmentController(final ClientFactory clientFactory, final OverlayFactory overlayFactory) {
        this.clientFactory = clientFactory;
        this.overlayFactory = overlayFactory;

        this.view = amendmentInjector.getAmendmentView();
        this.amendmentEventBus = amendmentInjector.getAmendmentEventBus();
        registerListeners();
    }

    private void registerListeners() {

    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;

        setJustification("Justification for " + amendment.getAmendmentContainerID());
        setTitle("Title for " + amendment.getAmendmentContainerID());
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

    public void setJustification(String justification) {
        this.view.setJustification(justification);
    }

    public void setTitle(String title) {
        this.view.setTitle(title);
    }

    public void setParentAmendableWidget(AmendableWidget parentAmendableWidget) {
        this.parentAmendableWidget = parentAmendableWidget;
    }

    public void setAmendmentWidget(AmendableWidget amendmentWidget) {
        this.amendmentWidget = amendmentWidget;
    }
}
