package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentViewImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentManager implements AmendmentInjector, AmendmentWalker {

    private final ClientFactory clientFactory;

    private final ArrayList<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    private final HashMap<String, AmendableWidget> elementIDCache = new HashMap<String, AmendableWidget>();

    @Inject
    public AmendmentManager(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void inject(final AmendableWidget root) {
        // if we're going to do multiple injections, it's faster to create a temporary lookup cache with all IDs
        if (elementIDCache.isEmpty()) {
            initializeElementIDCache(root);
        }
        for (AmendmentController amendmentController : amendmentControllers) {
            inject(amendmentController, root);
        }
        // all injected, so clear the lookup cache
        elementIDCache.clear();
    }

    @Override
    public void inject(final AmendmentContainerDTO amendment, final AmendableWidget root) {
        final AmendmentController amendmentController = getAmendmentController(amendment);
        inject(amendmentController, root);
    }

    private void inject(final AmendmentController amendmentController, final AmendableWidget root) {

        final String element = amendmentController.getAmendment().getSourceReference().getElement();
        if (!elementIDCache.isEmpty() && elementIDCache.containsKey(element)) {
            elementIDCache.get(element).addAmendmentController(amendmentController);
            clientFactory.getEventBus().fireEvent(new AmendmentContainerInjectedEvent(amendmentController));
        } else {
            // not in our cache? Can happen if we inject a single amendment
            walk(root, new AmendableVisitor() {
                @Override
                public boolean visit(final AmendableWidget visited) {
                    if (element.equalsIgnoreCase(visited.getId())) {
                        visited.addAmendmentController(amendmentController);
                        clientFactory.getEventBus().fireEvent(new AmendmentContainerInjectedEvent(amendmentController));
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
        AmendmentController amendmentController = new AmendmentController(clientFactory, new AmendmentViewImpl());
        amendmentController.setAmendment(amendment);
        return amendmentController;
    }

    private void initializeElementIDCache(AmendableWidget root) {
        walk(root, new AmendableVisitor() {
            @Override
            public boolean visit(final AmendableWidget visited) {
                // todo: detection mechanism
                if (visited.getId() != null) {
                    elementIDCache.put(visited.getId(), visited);
                }
                return true;
            }
        });
    }

    @Override
    public void walk(final AmendableWidget toVisit, final AmendableVisitor visitor) {
        if (visitor.visit(toVisit)) {
            for (final AmendableWidget child : toVisit.getChildAmendableWidgets()) {
                walk(child, visitor);
            }
        }
    }

    public void setAmendmentContainerDTOs(final AmendmentContainerDTO[] amendmentContainerDTOs) {
        for (final AmendmentContainerDTO amendmentContainerDTO : amendmentContainerDTOs) {
            amendmentControllers.add(createAmendmentController(amendmentContainerDTO));
        }
    }
}
