package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerInjectedEventHandler;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerStatusUpdatedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.util.Selection;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsActionEventHandler;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEvent;
import org.nsesa.editor.gwt.editor.client.event.amendments.AmendmentsSelectionEventHandler;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEvent;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentRefreshRequestEventHandler;
import org.nsesa.editor.gwt.editor.client.ui.amendments.pagination.PaginationCallback;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.*;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class AmendmentsPanelController {

    private static final int DEFAULT_AMENDMENTS_PER_PAGE = 2;

    private final ClientFactory clientFactory;
    private final AmendmentsPanelView view;
    private DocumentEventBus documentEventBus;
    private DocumentController documentController;

    private int amendmentsPerPage = DEFAULT_AMENDMENTS_PER_PAGE;

    @Inject
    public AmendmentsPanelController(ClientFactory clientFactory,
                                     AmendmentsPanelView view,
                                     DocumentEventBus documentEventBus) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.documentEventBus = documentEventBus;
        registerListeners();
    }

    public AmendmentsPanelView getView() {
        return view;
    }

    protected void setAmendmentsPerPage(int amendmentsPerPage) {
        this.amendmentsPerPage = amendmentsPerPage;
    }

    private void registerListeners() {
        //EventBus eventBus = clientFactory.getEventBus();
        documentEventBus.addHandler(DocumentRefreshRequestEvent.TYPE, new DocumentRefreshRequestEventHandler() {
            @Override
            public void onEvent(DocumentRefreshRequestEvent event) {
                refreshTotalPages();
                refreshAmendments(1);
            }
        });

        documentEventBus.addHandler(AmendmentContainerInjectedEvent.TYPE, new AmendmentContainerInjectedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerInjectedEvent event) {
                refreshTotalPages();
                refreshAmendments(1);
            }
        });

        documentEventBus.addHandler(AmendmentContainerStatusUpdatedEvent.TYPE, new AmendmentContainerStatusUpdatedEventHandler() {
            @Override
            public void onEvent(AmendmentContainerStatusUpdatedEvent event) {
                refreshTotalPages();
                refreshAmendments(1);
            }
        });

        documentEventBus.addHandler(AmendmentsSelectionEvent.TYPE, new AmendmentsSelectionEventHandler() {
            @Override
            public void onEvent(AmendmentsSelectionEvent event) {
                applySelection(event.getSelection());
            }
        });

        documentEventBus.addHandler(AmendmentsActionEvent.TYPE, new AmendmentsActionEventHandler() {
            @Override
            public void onEvent(AmendmentsActionEvent event) {
                view.getSelectedAmendments();
                event.getAction().execute();
            }
        });

    }


    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
        registerPaginationCallback();
    }

    private void applySelection(Selection<AmendmentController> selection) {
        List<String> ids = new ArrayList<String>();
        for (AmendmentController amendmentController : documentController.getAmendmentManager().getAmendmentControllers()) {
            if (selection.apply(amendmentController)) {
                ids.add(amendmentController.getAmendment().getId());
            }
        }
        view.selectAmendments(ids);
    }

    private void refreshTotalPages() {
        float totalSize = documentController.getAmendmentManager().getAmendmentControllers().size();
        getView().getPaginationView().setTotalPages(Math.round(totalSize / amendmentsPerPage));
    }

    private void refreshAmendments(int pageNr) {
        final Map<String, AmendmentView> amendments = new LinkedHashMap<String, AmendmentView>();
        final List<AmendmentController> amendmentControllers = documentController.getAmendmentManager().getAmendmentControllers();
        // always sort them according to their order number
        sortAmendments(amendmentControllers);
        for (int i = ((pageNr - 1) * amendmentsPerPage); i < Math.min(pageNr * amendmentsPerPage, amendmentControllers.size()); i++) {
            amendments.put(amendmentControllers.get(i).getAmendment().getId(), amendmentControllers.get(i).getExtendedView());
        }
        view.refreshAmendments(amendments);
    }

    protected void sortAmendments(final List<AmendmentController> toSort) {
        Collections.sort(toSort, new Comparator<AmendmentController>() {
            @Override
            public int compare(AmendmentController a, AmendmentController b) {
                return Integer.valueOf(a.getOrder()).compareTo(b.getOrder());
            }
        });
    }

    private void registerPaginationCallback() {
        getView().getPaginationView().setCallback(new PaginationCallback() {
            @Override
            public void moveToPage(int pageNr) {
                refreshAmendments(pageNr);
            }
        });
    }
}
