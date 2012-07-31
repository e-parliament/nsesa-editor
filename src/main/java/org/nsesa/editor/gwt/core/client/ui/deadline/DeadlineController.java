package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.deadline.*;

import java.util.Date;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DeadlineController {

    private final DeadlineView view;

    private final ClientFactory clientFactory;

    private DeadlineTracker deadlineTracker;

    private EventBus documentEventBus;

    private Date deadline;

    @Inject
    public DeadlineController(final ClientFactory clientFactory, final DeadlineView view) {
        this.clientFactory = clientFactory;
        this.view = view;

        registerListeners();

        view.asWidget().setVisible(false);
    }

    private void registerListeners() {

    }

    private void registerPrivateListeners() {
        documentEventBus.addHandler(DeadlinePassedEvent.TYPE, new DeadlinePassedEventHandler() {
            @Override
            public void onEvent(DeadlinePassedEvent event) {
                view.setPastStyle();
                view.setDeadline(getFormattedDeadline());
            }
        });
        documentEventBus.addHandler(Deadline24HourEvent.TYPE, new Deadline24HourEventHandler() {
            @Override
            public void onEvent(Deadline24HourEvent event) {
                view.set24HourStyle();
                view.setDeadline(getFormattedDeadline());
            }
        });
        documentEventBus.addHandler(Deadline1HourEvent.TYPE, new Deadline1HourEventHandler() {
            @Override
            public void onEvent(Deadline1HourEvent event) {
                view.set1HourStyle();
                view.setDeadline(getFormattedDeadline());
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

    public void setDocumentEventBus(final EventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        registerPrivateListeners();
        this.deadlineTracker = new DeadlineTracker(documentEventBus);
    }

    public DeadlineView getView() {
        return view;
    }
}
