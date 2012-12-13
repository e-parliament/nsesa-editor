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
import org.nsesa.editor.gwt.core.shared.ComplexDiffContext;
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

    // style for BI diffing
    public static final String originalChangeTemplate = "<span class=\"widget change highlight-diff\">{0}</span>";

    // style for complex diffing
    public static final String originalComplexChangeTemplate = "<span class=\"widget change highlight-red\">{0}</span>";
    public static final String complexInsertTemplate = "<span class=\"widget change highlight-ins\">{0}</span>";
    public static final String complexDeleteTemplate = "<span class=\"widget change highlight-del\">{0}</span>";
    public static final String complexInsertNormalTemplate = "<span class=\"widget change highlight-ins-normal\">{0}</span>";
    public static final String complexDeleteNormalTemplate = "<span class=\"widget change highlight-del-normal\">{0}</span>";
    public static final String complexChangeTemplate = "<span class=\"widget change highlight-change\">{0}</span>";

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
                command = new ComplexDiffCommand(amendmentController.getOriginalContent(), amendmentController.getAmendmendContent(), amendmentController.getAmendmendContent(), getComplexDiffContext());
            } else {
                command = new ComplexDiffCommand(amendmentController.getOriginalContent(), amendmentController.getOriginalContent(), amendmentController.getAmendmendContent(), getComplexDiffContext());
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

    private ComplexDiffContext getComplexDiffContext() {
        return new ComplexDiffContext(originalChangeTemplate, originalComplexChangeTemplate, complexInsertTemplate, complexDeleteTemplate, complexChangeTemplate, DiffMethod.WORD);
    }

}
