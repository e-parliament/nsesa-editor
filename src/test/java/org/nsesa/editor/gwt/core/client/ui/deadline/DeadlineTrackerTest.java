/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithEasyMock;
import com.googlecode.gwt.test.Mock;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;

import java.util.Date;

/**
 * Date: 13/06/13 13:47
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DeadlineTrackerTest extends GwtTestWithEasyMock {

    private static final String MESSAGE = "formattedDeadline";

    @Mock
    DocumentController documentController;

    DocumentEventBus documentEventBus = new DocumentEventBus();

    @Mock
    DeadlineView view;

    Date now = new Date();

    DeadlineTracker tracker = new DeadlineTracker(documentEventBus) {
        @Override
        protected Date getNow() {
            return now;
        }
    };

    @Mock
    DeadlineController deadlineController;

    @Before
    public void setup() {
        deadlineController = new DeadlineController(documentEventBus, tracker, view) {
            @Override
            protected String getFormattedDeadlineMessage() {
                return MESSAGE;
            }
        };
        deadlineController.setDocumentController(documentController);
        deadlineController.registerListeners();
    }

    @Test
    public void testSetPastDeadline() throws Exception {
        now = new Date();
        view.setPastStyle();
        EasyMock.expectLastCall().once();

        view.setDeadline(EasyMock.eq(MESSAGE));
        EasyMock.expectLastCall().once();

        EasyMock.replay(view);
        final Date deadline = new Date(now.getTime() - 1);
        deadlineController.setDeadline(deadline);
        EasyMock.verify(view);
    }

    @Test
    public void testSetBeforeDeadline() throws Exception {
        now = new Date();
        EasyMock.replay(view);
        final Date deadline = new Date(now.getTime() + (48 * 60 * 60 * 1000));
        deadlineController.setDeadline(deadline);
        EasyMock.verify(view);
    }

    @Test
    public void testSet24hBeforeDeadline() throws Exception {
        now = new Date();
        view.set24HourStyle();
        EasyMock.expectLastCall().once();

        view.setDeadline(EasyMock.eq(MESSAGE));
        EasyMock.expectLastCall().once();

        EasyMock.replay(view);
        // Changed to use 22 hours: using getTime + long is not taking summertime into account.
        final Date deadline = new Date(now.getTime() + (22 * 60 * 60 * 1000));
        deadlineController.setDeadline(deadline);
        EasyMock.verify(view);
    }

    @Test
    public void testSet1hBeforeDeadline() throws Exception {
        now = new Date();
        view.set24HourStyle();
        EasyMock.expectLastCall().once();

        view.setDeadline(EasyMock.eq(MESSAGE));
        EasyMock.expectLastCall().once();

        EasyMock.replay(view);
        final Date deadline = new Date(now.getTime() + (30 * 60 * 1000));
        deadlineController.setDeadline(deadline);
        EasyMock.verify(view);
    }
}
