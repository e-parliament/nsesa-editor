/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.event.deadline.*;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.Date;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * A controller for the deadline that might be set on a document controller. This controller also has a tracker
 * that will fire the following events on the local document event bus:
 * <ul>
 * <li>{@link Deadline24HourEvent}</li>
 * <li>{@link Deadline1HourEvent}</li>
 * <li>{@link DeadlinePassedEvent}</li>
 * </ul>
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DeadlineController {

    /**
     * The view.
     */
    protected final DeadlineView view;

    /**
     * A tracker for the deadlines, fires events.
     */
    protected final DeadlineTracker deadlineTracker;

    /**
     * The document event bus.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * The parent document controller.
     */
    protected DocumentController documentController;

    /**
     * The deadline, if any.
     */
    protected Date deadline;
    private HandlerRegistration deadlinePassedEventHandlerRegistration;
    private HandlerRegistration hour24DeadlineEventHandlerRegistration;
    private HandlerRegistration hour1DeadlineEventHandlerRegistration;

    @Inject
    public DeadlineController(final DocumentEventBus documentEventBus,
                              final DeadlineTracker deadlineTracker,
                              final DeadlineView view) {
        this.documentEventBus = documentEventBus;
        this.deadlineTracker = deadlineTracker;
        this.deadlineTracker.setDeadlineController(this);
        this.view = view;
        // register private listeners
        registerListeners();
    }

    /**
     * Set the parent document controller.
     *
     * @param documentController the document controller
     */

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    /**
     * Return a reference to the parent document controller.
     *
     * @return the document controller
     */
    public DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Registers the listeners for events that are being fired by the deadline tracker.
     */
    private void registerListeners() {
        deadlinePassedEventHandlerRegistration = documentEventBus.addHandler(DeadlinePassedEvent.TYPE, new DeadlinePassedEventHandler() {
            @Override
            public void onEvent(DeadlinePassedEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.setPastStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        hour24DeadlineEventHandlerRegistration = documentEventBus.addHandler(Deadline24HourEvent.TYPE, new Deadline24HourEventHandler() {
            @Override
            public void onEvent(Deadline24HourEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.set24HourStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
        hour1DeadlineEventHandlerRegistration = documentEventBus.addHandler(Deadline1HourEvent.TYPE, new Deadline1HourEventHandler() {
            @Override
            public void onEvent(Deadline1HourEvent event) {
                if (event.getDocumentController() == documentController) {
                    view.set1HourStyle();
                    view.setDeadline(getFormattedDeadline());
                }
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        deadlinePassedEventHandlerRegistration.removeHandler();
        hour24DeadlineEventHandlerRegistration.removeHandler();
        hour1DeadlineEventHandlerRegistration.removeHandler();
    }


    /**
     * Get the formatted deadline. See the {@link CoreMessages} for the messages and date formats.
     *
     * @return the formatted deadline.
     */
    protected String getFormattedDeadline() {
        // TODO switch to gwt-joda-time
        final Date now = new Date();
        final Date midnight = new Date(now.getDay(), now.getMonth(), now.getYear(), 0, 0, 0);
        final Date oneDayBefore = new Date(midnight.getTime() - (24 * 60 * 60 * 1000));
        final Date oneHourBefore = new Date(deadline.getTime() - (60 * 60 * 1000));

        final CoreMessages coreMessages = documentController.getClientFactory().getCoreMessages();
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

    /**
     * Set the actual deadline, or <tt>null</tt> if there is no deadline.
     *
     * @param deadline the deadline
     */
    public void setDeadline(final Date deadline) {
        this.deadline = deadline;
        deadlineTracker.setDeadline(deadline);
    }

    /**
     * Return the view associated with this controller.
     *
     * @return the view
     */
    public DeadlineView getView() {
        return view;
    }
}
