package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.deadline.*;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
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

    private final CoreMessages coreMessages;

    private Date deadline;

    @Inject
    public DeadlineController(final DocumentEventBus documentEventBus,
                              final DeadlineTracker deadlineTracker,
                              final DeadlineView view,
                              final CoreMessages coreMessages) {
        this.documentEventBus = documentEventBus;
        this.deadlineTracker = deadlineTracker;
        this.deadlineTracker.setDeadlineController(this);
        this.view = view;
        this.coreMessages = coreMessages;

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
        // TODO switch to gwt-joda-time
        final Date now = new Date();
        final Date midnight = new Date(now.getDay(), now.getMonth(), now.getYear(), 0, 0, 0);
        final Date oneDayBefore = new Date(midnight.getTime() - (24 * 60 * 60 * 1000));
        final Date oneHourBefore = new Date(deadline.getTime() - (60 * 60 * 1000));

        // check if we already passed the deadline
        if (now.after(deadline)) {
            // already passed
            return coreMessages.documentDeadlinePassedMessage(DateTimeFormat.getFormat(coreMessages.documentDeadlinePassedFormat()).format(deadline));
        }
        if (deadline.after(oneHourBefore)) {
            // deadline in one hour
            return coreMessages.documentDeadlineH1Message(DateTimeFormat.getFormat(coreMessages.documentDeadlineH1Format()).format(deadline));
        }
        if (deadline.after(midnight)) {
            // deadline is today
            return coreMessages.documentDeadlineTodayMessage(DateTimeFormat.getFormat(coreMessages.documentDeadlineTodayFormat()).format(deadline));
        }

        if (deadline.after(oneDayBefore)) {
            // deadline is tomorrow
            return coreMessages.documentDeadlineTomorrowMessage(DateTimeFormat.getFormat(coreMessages.documentDeadlineTomorrowFormat()).format(deadline));
        }
        return coreMessages.documentDeadlineDefaultMessage(DateTimeFormat.getFormat(coreMessages.documentDeadlineDefaultFormat()).format(deadline));
    }

    public void setDeadline(final Date deadline) {
        this.deadline = deadline;
        deadlineTracker.setDeadline(deadline);
    }

    public DeadlineView getView() {
        return view;
    }
}
