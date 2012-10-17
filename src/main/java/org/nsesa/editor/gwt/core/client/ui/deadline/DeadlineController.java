package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.deadline.*;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.Date;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DeadlineController {

    private final DeadlineView view;

    private final DeadlineTracker deadlineTracker;

    private DocumentController documentController;

    private final EventBus eventBus;

    private Date deadline;

    @Inject
    public DeadlineController(final EventBus eventBus, final DeadlineTracker deadlineTracker, final DeadlineView view) {
        this.eventBus = eventBus;
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
        eventBus.addHandler(DeadlinePassedEvent.TYPE, new DeadlinePassedEventHandler() {
            @Override
            public void onEvent(DeadlinePassedEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.setPastStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        eventBus.addHandler(Deadline24HourEvent.TYPE, new Deadline24HourEventHandler() {
            @Override
            public void onEvent(Deadline24HourEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.set24HourStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        eventBus.addHandler(Deadline1HourEvent.TYPE, new Deadline1HourEventHandler() {
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
