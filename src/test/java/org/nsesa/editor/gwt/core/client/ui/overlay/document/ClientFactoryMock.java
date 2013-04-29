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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.testing.StubScheduler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.keyboard.KeyboardListener;
import org.nsesa.editor.gwt.core.client.ui.i18n.CoreMessages;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.ClientContextImpl;

/**
 * Date: 18/01/13 15:23
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ClientFactoryMock implements ClientFactory {

    protected final EventBus eventBus = new SimpleEventBus();
    protected final PlaceController placeController = new PlaceController(eventBus);
    protected final Scheduler scheduler = new StubScheduler();
    protected ClientContext clientContext = new ClientContextImpl();
    protected KeyboardListener keyboardListener = new KeyboardListener(eventBus);
    protected JavaScriptObject configuration = JavaScriptObject.createObject();

    final CoreMessages coreMessages = new CoreMessages() {
        @Override
        public String windowTitleBootstrap() {
            return "windowTitleBootstrap";
        }

        @Override
        public String windowTitleDocument(String documentIdentifier) {
            return "windowTitleDocument";
        }

        @Override
        public String windowTitleAmendments(String documentIdentifier, String numberOfAmendments) {
            return "windowTitleAmendments";
        }

        @Override
        public String authenticationFailed() {
            return "authenticationFailed";
        }

        @Override
        public String errorTitleDefault() {
            return "errorTitleDefault";
        }

        @Override
        public String errorDocumentIdMissing() {
            return "errorDocumentIdMissing";
        }

        @Override
        public String errorDocumentError(String p0) {
            return "errorDocumentError";
        }

        @Override
        public String errorDocumentContentError(String p0) {
            return "errorDocumentContentError";
        }

        @Override
        public String errorDocumentNotfound(String p0) {
            return "errorDocumentNotfound";
        }

        @Override
        public String errorDocumentInprogress(String p0) {
            return "errorDocumentInprogress";
        }

        @Override
        public String errorDocumentForbidden(String p0) {
            return "errorDocumentForbidden";
        }

        @Override
        public String errorAmendmentError(String p0) {
            return "errorAmendmentError";
        }

        @Override
        public String errorAmendmentsError() {
            return "errorAmendmentsError";
        }

        @Override
        public String documentDeadlineDefaultFormat() {
            return "documentDeadlineDefaultFormat";
        }

        @Override
        public String documentDeadlineDefaultMessage(String arg) {
            return "documentDeadlineDefaultMessage";
        }

        @Override
        public String documentDeadlineTomorrowFormat() {
            return "documentDeadlineTomorrowFormat";
        }

        @Override
        public String documentDeadlineTomorrowMessage(String arg) {
            return "documentDeadlineTomorrowMessage";
        }

        @Override
        public String documentDeadlineTodayFormat() {
            return "documentDeadlineTodayFormat";
        }

        @Override
        public String documentDeadlineTodayMessage(String arg) {
            return "documentDeadlineTodayMessage";
        }

        @Override
        public String documentDeadlineH1Format() {
            return "documentDeadlineH1Format";
        }

        @Override
        public String documentDeadlineH1Message(String arg) {
            return "documentDeadlineH1Message";
        }

        @Override
        public String documentDeadlinePassedFormat() {
            return "documentDeadlinePassedFormat";
        }

        @Override
        public String documentDeadlinePassedMessage(String arg) {
            return "documentDeadlinePassedMessage";
        }

        @Override
        public String confirmationAmendmentDeleteTitle() {
            return "confirmationAmendmentDeleteTitle";
        }

        @Override
        public String confirmationAmendmentDeleteMessage() {
            return "confirmationAmendmentDeleteMessage";
        }

        @Override
        public String confirmationAmendmentDeleteButtonConfirm() {
            return "confirmationAmendmentDeleteButtonConfirm";
        }

        @Override
        public String confirmationAmendmentDeleteButtonCancel() {
            return "confirmationAmendmentDeleteButtonCancel";
        }

        @Override
        public String amendmentActionTable() {
            return "amendmentActionTable";
        }

        @Override
        public String amendmentActionDelete() {
            return "amendmentActionDelete";
        }

        @Override
        public String amendmentActionWithdraw() {
            return "amendmentActionWithdraw";
        }

        @Override
        public String amendmentActionRegister() {
            return "amendmentActionRegister";
        }

        @Override
        public String amendmentActionReturn() {
            return "amendmentActionReturn";
        }

        @Override
        public String amendmentActionSaveSuccessful(@PluralCount int arg) {
            return "amendmentActionSaveSuccessful";
        }

        @Override
        public String amendmentActionTableSuccessful(@PluralCount int arg) {
            return "amendmentActionTableSuccessful";
        }

        @Override
        public String amendmentActionWithdrawSuccessful(@PluralCount int arg) {
            return "amendmentActionWithdrawSuccessful";
        }

        @Override
        public String amendmentActionReturnSuccessful(@PluralCount int arg) {
            return "amendmentActionReturnSuccessful";
        }

        @Override
        public String amendmentActionRegisterSuccessful(@PluralCount int arg) {
            return "amendmentActionRegisterSuccessful";
        }

        @Override
        public String amendmentActionDeleteSuccessful(@PluralCount int arg) {
            return "amendmentActionDeleteSuccessful";
        }

        @Override
        public String amendmentStatusCandidate() {
            return "amendmentStatusCandidate";
        }

        @Override
        public String amendmentStatusTabled() {
            return "amendmentStatusTabled";
        }

        @Override
        public String amendmentStatusWithdrawn() {
            return "amendmentStatusWithdrawn";
        }

        @Override
        public String amendmentStatusDeleted() {
            return "amendmentStatusDeleted";
        }

        @Override
        public String amendmentStatusRegistered() {
            return "amendmentStatusRegistered";
        }

        @Override
        public String amendmentStatusReturned() {
            return "amendmentStatusReturned";
        }

        @Override
        public String confirmationAmendmentsDeleteMessage() {
            return "confirmationAmendmentsDeleteMessage";
        }

        @Override
        public String amendmentActionCancel() {
            return "amendmentActionCancel";
        }

        @Override
        public String amendmentSelection(@PluralCount int arg) {
            return "amendmentSelection";
        }

        @Override
        public String amendmentActionMoveUp() {
            return null;
        }

        @Override
        public String amendmentActionMoveDown() {
            return null;
        }
    };

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    @Override
    public CoreMessages getCoreMessages() {
        return coreMessages;
    }

    @Override
    public JavaScriptObject getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(JavaScriptObject configuration) {
        this.configuration = configuration;
    }

    @Override
    public KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }
}
