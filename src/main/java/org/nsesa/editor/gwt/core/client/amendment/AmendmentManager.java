package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentViewImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentManager implements AmendmentInjector, AmendmentWalker {

    private final ClientFactory clientFactory;

    private final ArrayList<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    @Inject
    public AmendmentManager(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void inject(AmendableWidget root) {
        for (AmendmentController amendmentController : amendmentControllers) {
            inject(amendmentController, root);
        }
    }

    @Override
    public void inject(final AmendmentContainerDTO amendment, final AmendableWidget root) {
        final AmendmentController amendmentController = getAmendmentController(amendment);
        inject(amendmentController, root);
    }

    private void inject(final AmendmentController amendmentController, final AmendableWidget root) {
        walk(root, new AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                // todo: detection mechanism
                if (amendmentController.getAmendment().getPosition().equalsIgnoreCase(visited.getType())) {
                    visited.addAmendmentController(amendmentController);
                    return false;
                }
                return true;
            }
        });
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
