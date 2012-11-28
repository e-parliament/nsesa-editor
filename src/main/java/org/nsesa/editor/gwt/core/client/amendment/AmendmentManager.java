package org.nsesa.editor.gwt.core.client.amendment;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerSaveEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.XMLTransformer;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentInjector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentManager implements AmendmentInjectionCapable {

    private final ServiceFactory serviceFactory;

    private final ClientFactory clientFactory;

    private final XMLTransformer xmlTransformer;

    private final DocumentEventBus documentEventBus;

    private DocumentInjector injector;

    private final ArrayList<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    @Inject
    public AmendmentManager(final ClientFactory clientFactory,
                            final ServiceFactory serviceFactory,
                            final XMLTransformer xmlTransformer,
                            final DocumentEventBus documentEventBus) {
        this.clientFactory = clientFactory;
        this.serviceFactory = serviceFactory;
        this.xmlTransformer = xmlTransformer;
        this.documentEventBus = documentEventBus;
        registerListeners();
    }

    private void registerListeners() {
        clientFactory.getEventBus().addHandler(AmendmentContainerSaveEvent.TYPE, new AmendmentContainerSaveEventHandler() {
            @Override
            public void onEvent(final AmendmentContainerSaveEvent event) {

                // serialize amendable widget into XML content
                for (final AmendmentContainerDTO amendment : event.getAmendments()) {
                    amendment.setXmlContent(xmlTransformer.toXML(amendment.getRoot()));
                }
                serviceFactory.getGwtAmendmentService().saveAmendmentContainers(clientFactory.getClientContext(), new ArrayList<AmendmentContainerDTO>(Arrays.asList(event.getAmendments())), new AsyncCallback<AmendmentContainerDTO[]>() {
                    @Override
                    public void onFailure(final Throwable caught) {
                        clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Woops, could not save the amendment(s).", caught));
                    }

                    @Override
                    public void onSuccess(AmendmentContainerDTO[] result) {
                        for (final AmendmentContainerDTO amendmentContainerDTO : result) {
                            final AmendmentController amendmentController = injector.getAmendmentController();
                            amendmentController.setAmendment(amendmentContainerDTO);
                            amendmentControllers.add(amendmentController);
                        }
                        documentEventBus.fireEvent(new AmendmentContainerInjectEvent(result));
                    }
                });
            }
        });
    }

    @Override
    public void injectSingleAmendment(final AmendmentContainerDTO amendment, final AmendableWidget root, final DocumentController documentController) {
        final AmendmentController amendmentController = getAmendmentController(amendment);
        if (amendmentController == null) {
            throw new NullPointerException("AmendmentContainer DTO was not yet registered with a controller?");
        }
        injectInternal(amendmentController, root, documentController);
    }

    @Override
    public void inject(final AmendableWidget root, final DocumentController documentController) {
        // if we're going to do multiple injections, it's faster to create a temporary lookup cache with all IDs
        for (final AmendmentController amendmentController : amendmentControllers) {
            injectInternal(amendmentController, root, documentController);
        }
    }


    private void injectInternal(final AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController) {

        final String element = amendmentController.getAmendment().getSourceReference().getElement();

        // not in our cache? Can happen if we inject a single amendment
        documentController.walk(root, new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(final AmendableWidget visited) {
                if (visited != null && element.equalsIgnoreCase(visited.getId())) {
                    visited.addAmendmentController(amendmentController);
                    amendmentController.setDocumentController(documentController);
                    documentEventBus.fireEvent(new AmendmentContainerInjectedEvent(amendmentController));
                    return false;
                }
                return true;
            }
        });

    }

    private AmendmentController getAmendmentController(final AmendmentContainerDTO amendment) {
        for (final AmendmentController amendmentController : amendmentControllers) {
            if (amendmentController.getAmendment().equals(amendment)) {
                return amendmentController;
            }
        }
        return null;
    }

    private AmendmentController createAmendmentController(final AmendmentContainerDTO amendment) {
        final AmendmentController amendmentController = injector.getAmendmentController();
        amendmentController.setAmendment(amendment);
        return amendmentController;
    }


    public void setAmendmentContainerDTOs(final AmendmentContainerDTO[] amendmentContainerDTOs) {
        for (final AmendmentContainerDTO amendmentContainerDTO : amendmentContainerDTOs) {
            amendmentControllers.add(createAmendmentController(amendmentContainerDTO));
        }
    }

    public List<AmendmentController> getAmendmentControllers() {
        return amendmentControllers;
    }

    public void setInjector(final DocumentInjector injector) {
        this.injector = injector;
    }
}
