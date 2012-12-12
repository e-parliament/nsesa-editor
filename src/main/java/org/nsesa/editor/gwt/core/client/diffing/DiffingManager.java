package org.nsesa.editor.gwt.core.client.diffing;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.ComplexDiffCommand;
import org.nsesa.editor.gwt.core.shared.ComplexDiffResult;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentInjector;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DiffingManager {

    private static final Logger LOG = Logger.getLogger(DiffingManager.class.getName());

    private final ServiceFactory serviceFactory;

    private final ClientFactory clientFactory;

    private DocumentInjector injector;


    @Inject
    public DiffingManager(final ServiceFactory serviceFactory,
                          final ClientFactory clientFactory,
                          final DocumentEventBus documentEventBus) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
    }

    public void diff(final String diffingType, final DiffMethod method, final AmendmentController... amendmentControllers) {
        final ArrayList<ComplexDiffCommand> commands = new ArrayList<ComplexDiffCommand>();
        for (final AmendmentController amendmentController : amendmentControllers) {
            ComplexDiffCommand command = null;
            if ("ep".equalsIgnoreCase(diffingType)) {
                command = new ComplexDiffCommand(amendmentController.getOriginalContent(), amendmentController.getAmendmendContent(), amendmentController.getAmendmendContent());
            } else {
                command = new ComplexDiffCommand(amendmentController.getOriginalContent(), amendmentController.getOriginalContent(), amendmentController.getAmendmendContent());
            }
            commands.add(command);
        }

        // request diffing from the backend service
        serviceFactory.getGwtDiffService().complexDiff(commands, new AsyncCallback<ArrayList<ComplexDiffResult>>() {
            @Override
            public void onFailure(Throwable caught) {
                LOG.log(Level.WARNING, "Diffing failed: " + caught, caught);
                clientFactory.getEventBus().fireEvent(new CriticalErrorEvent("Could not perform diffing.", caught));
            }

            @Override
            public void onSuccess(ArrayList<ComplexDiffResult> result) {
                int index = 0;
                for (final ComplexDiffResult complexDiffResult : result) {
                    final AmendmentController amendmentController = amendmentControllers[index];
                    amendmentController.setOriginalContent(complexDiffResult.getTrackChangesOriginal());
                    amendmentController.setAmendmentContent(complexDiffResult.getTrackChangesAmendment());
                    index++;
                }
            }
        });
    }

}
