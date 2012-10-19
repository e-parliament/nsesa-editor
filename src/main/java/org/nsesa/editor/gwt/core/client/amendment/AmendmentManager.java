package org.nsesa.editor.gwt.core.client.amendment;

import com.allen_sauer.gwt.log.client.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.Injector;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class AmendmentManager implements AmendmentInjectionCapable, AmendableWidgetWalker {

    private ClientFactory clientFactory;
    private Injector injector;

    private final ArrayList<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    private final HashMap<String, AmendableWidget> elementIDCache = new HashMap<String, AmendableWidget>();

    public AmendmentManager() {
        Log.info("------------- AMENDMENT MANAGER() ------------------");
    }

    @Inject
    public AmendmentManager(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        Log.info("------------- AMENDMENT MANAGER() WITH CF ------------------");

    }

    @Override
    public void injectSingleAmendment(final AmendmentContainerDTO amendment, final AmendableWidget root, final DocumentController documentController) {
        injectInternal(getAmendmentController(amendment), root, documentController);
    }

    @Override
    public void inject(final AmendableWidget root, final DocumentController documentController) {
        // if we're going to do multiple injections, it's faster to create a temporary lookup cache with all IDs
        if (elementIDCache.isEmpty()) {
            initializeElementIDCache(root);
        }
        for (final AmendmentController amendmentController : amendmentControllers) {
            injectInternal(amendmentController, root, documentController);
        }
        // all injected, so clear the lookup cache
        elementIDCache.clear();
    }


    private void injectInternal(final AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController) {

        final String element = amendmentController.getAmendment().getSourceReference().getElement();
        if (!elementIDCache.isEmpty() && elementIDCache.containsKey(element)) {
            elementIDCache.get(element).addAmendmentController(amendmentController);
            clientFactory.getEventBus().fireEvent(new AmendmentContainerInjectedEvent(amendmentController, documentController));
        } else {
            // not in our cache? Can happen if we inject a single amendment
            walk(root, new AmendableVisitor() {
                @Override
                public boolean visit(final AmendableWidget visited) {
                    if (visited != null && element.equalsIgnoreCase(visited.getId())) {
                        visited.addAmendmentController(amendmentController);
                        clientFactory.getEventBus().fireEvent(new AmendmentContainerInjectedEvent(amendmentController, documentController));
                        return false;
                    }
                    return true;
                }
            });
        }
    }

    private AmendmentController getAmendmentController(final AmendmentContainerDTO amendment) {
        for (AmendmentController amendmentController : amendmentControllers) {
            if (amendmentController.getAmendment().equals(amendment)) {
                return amendmentController;
            }
        }
        return null;
    }

    private AmendmentController createAmendmentController(final AmendmentContainerDTO amendment) {
        AmendmentController amendmentController = injector.getAmendmentController();
        amendmentController.setAmendment(amendment);
        return amendmentController;
    }

    private void initializeElementIDCache(final AmendableWidget root) {
        walk(root, new AmendableVisitor() {
            @Override
            public boolean visit(final AmendableWidget visited) {
                // todo: detection mechanism
                if (visited != null && visited.getId() != null) {
                    elementIDCache.put(visited.getId(), visited);
                }
                return true;
            }
        });
    }

    @Override
    public void walk(final AmendableWidget toVisit, final AmendableVisitor visitor) {
        if (visitor.visit(toVisit)) {
            if (toVisit != null) {
                for (final AmendableWidget child : toVisit.getChildAmendableWidgets()) {
                    walk(child, visitor);
                }
            }
        }
    }

    public void setAmendmentContainerDTOs(final AmendmentContainerDTO[] amendmentContainerDTOs) {
        for (final AmendmentContainerDTO amendmentContainerDTO : amendmentContainerDTOs) {
            amendmentControllers.add(createAmendmentController(amendmentContainerDTO));
        }
    }

    public List<AmendmentController> getAmendmentControllers() {
        return amendmentControllers;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
