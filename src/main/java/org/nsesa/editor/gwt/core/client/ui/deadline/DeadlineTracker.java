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

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.event.deadline.Deadline1HourEvent;
import org.nsesa.editor.gwt.core.client.event.deadline.Deadline24HourEvent;
import org.nsesa.editor.gwt.core.client.event.deadline.DeadlinePassedEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.*;

/**
 * This class is responsible for tracking a deadline by publishing events when the date nears.
 * Date: 30/07/12 23:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DOCUMENT)
public class DeadlineTracker {

    private static final Logger LOG = Logger.getLogger(DeadlineTracker.class.getName());

    private final DocumentEventBus documentEventBus;

    private DeadlineController deadlineController;

    @Inject
    public DeadlineTracker(final DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
    }

    public void setDeadlineController(DeadlineController deadlineController) {
        this.deadlineController = deadlineController;
    }

    /**
     * Sets a new deadline and schedules the timers to fire accordingly.
     *
     * @param deadline the deadline.
     */
    public void setDeadline(Date deadline) {
        // register timers, if necessary
        timer24hour.cancel();
        timer1hour.cancel();
        timer0hour.cancel();
        if (deadline != null) {
            final Date aDayBeforeTheDeadline = new Date(deadline.getTime());
            aDayBeforeTheDeadline.setHours(aDayBeforeTheDeadline.getHours() - 24);
            final Date anHourBeforeTheDeadline = new Date(deadline.getTime());
            anHourBeforeTheDeadline.setHours(anHourBeforeTheDeadline.getHours() - 1);
            final Date now = new Date();

            if (now.before(deadline)) {
                if (now.before(aDayBeforeTheDeadline)) {
                    timer24hour.cancel();
                    // diff until this period
                    long diff = aDayBeforeTheDeadline.getTime() - now.getTime();
                    final long diffHours = diff / (1000 * 60 * 60);
                    LOG.info("Scheduling 24-hour timer to fire in " + diffHours + " hours.");
                    if (diffHours < 24 * 3) { // if clients keep open this window longer than 3 days ..
                        try {
                            timer24hour.schedule((int) diff + (60 * 1000));
                        } catch (Exception exception) {
                            LOG.log(Level.FINER, "Overflow - Deadline probably too far in the future" + exception.getMessage(), exception);
                        }
                    }
                }
                if (now.before(anHourBeforeTheDeadline)) {
                    timer1hour.cancel();
                    long diff = anHourBeforeTheDeadline.getTime() - now.getTime();
                    final long diffMinutes = diff / (1000 * 60);
                    LOG.info("Scheduling 60-min. timer to fire in " + diffMinutes + " minutes.");
                    if (diffMinutes < 3 * 24 * 60) {
                        try {
                            timer1hour.schedule((int) diff + (60 * 1000));
                        } catch (Exception exception) {
                            LOG.log(Level.FINER, "Overflow - Deadline probably too far in the future" + exception.getMessage(), exception);
                        }
                    }
                }
                // timer for the transition to 'past deadline'
                timer0hour.cancel();
                long diff = deadline.getTime() - now.getTime();
                final long diffMinutes = diff / (1000 * 60);
                LOG.info("Scheduling deadline timer to fire in " + diffMinutes + " minutes.");
                if (diffMinutes < 3 * 24 * 60) {
                    try {
                        timer0hour.schedule((int) diff + (60 * 1000));
                    } catch (Exception exception) {
                        LOG.log(Level.FINER, "Overflow - Deadline probably too far in the future" + exception.getMessage(), exception);
                    }
                }
            }

            // determine the timer to fire immediately
            if (now.before(deadline)) {
                if (now.after(anHourBeforeTheDeadline)) {
                    timer1hour.run();
                } else if (now.after(aDayBeforeTheDeadline)) {
                    timer24hour.run();
                }
            } else {
                // deadline already passed
                timer0hour.run();
            }
        }
        // nothing to schedule, we're past the deadline already
    }

    private Timer timer24hour = new Timer() {
        @Override
        public void run() {
            documentEventBus.fireEvent(new Deadline24HourEvent(deadlineController.getDocumentController()));
            LOG.info("24 hour deadline mark passed.");
        }
    };

    private Timer timer1hour = new Timer() {
        @Override
        public void run() {
            documentEventBus.fireEvent(new Deadline1HourEvent(deadlineController.getDocumentController()));
            LOG.info("1 hour deadline mark passed.");
        }
    };

    private Timer timer0hour = new Timer() {
        @Override
        public void run() {
            documentEventBus.fireEvent(new DeadlinePassedEvent(deadlineController.getDocumentController()));
            LOG.info("Deadline mark passed.");
        }
    };
}
