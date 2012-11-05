package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.deadline.*;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.Date;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DeadlineController {

    private final DeadlineView view;

    private final DeadlineTracker deadlineTracker;

    private DocumentController documentController;

    private final DocumentEventBus documentEventBus;

    private Date deadline;

    @Inject
    public DeadlineController(final DocumentEventBus documentEventBus, final DeadlineTracker deadlineTracker, final DeadlineView view) {
        this.documentEventBus = documentEventBus;
        this.deadlineTracker = deadlineTracker;
        this.deadlineTracker.setDeadlineController(this);
        this.view = view;

        registerListeners();
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    private void registerListeners() {
        documentEventBus.addHandler(DeadlinePassedEvent.TYPE, new DeadlinePassedEventHandler() {
            @Override
            public void onEvent(DeadlinePassedEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.setPastStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        documentEventBus.addHandler(Deadline24HourEvent.TYPE, new Deadline24HourEventHandler() {
            @Override
            public void onEvent(Deadline24HourEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.set24HourStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        documentEventBus.addHandler(Deadline1HourEvent.TYPE, new Deadline1HourEventHandler() {
            @Override
            public void onEvent(Deadline1HourEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.set1HourStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
    }


    protected String getFormattedDeadline() {
        return DateTimeFormat.getFormat("kk:mm").format(deadline);
    }

    public void setDeadline(final Date deadline) {
        this.deadline = deadline;
        deadlineTracker.setDeadline(deadline);
    }

    public DeadlineView getView() {
        return view;
    }
}
