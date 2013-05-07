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
package org.nsesa.editor.gwt.core.client.ui.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * Core messages for the editor.
 * Date: 24/06/12 17:18
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface CoreMessages extends Messages {
    @Key("window.title.bootstrap")
    String windowTitleBootstrap();

    @Key("window.title.document")
    String windowTitleDocument(String documentIdentifier);

    @Key("window.title.amendments")
    String windowTitleAmendments(String documentIdentifier, String numberOfAmendments);

    @Key("authentication.failed")
    String authenticationFailed();

    @Key("error.title.default")
    String errorTitleDefault();

    @Key("error.document.id.missing")
    String errorDocumentIdMissing();

    @Key("error.document.error")
    String errorDocumentError(String p0);

    @Key("error.document.notfound")
    String errorDocumentNotfound(String p0);

    @Key("error.document.inprogress")
    String errorDocumentInprogress(String p0);

    @Key("error.document.forbidden")
    String errorDocumentForbidden(String p0);

    @Key("error.amendment.error")
    String errorAmendmentError(String p0);

    @Key("error.amendments.error")
    String errorAmendmentsError();

    @Key("document.deadline.default.format")
    String documentDeadlineDefaultFormat();

    @Key("document.deadline.default.message")
    String documentDeadlineDefaultMessage(String arg);

    @Key("document.deadline.tomorrow.format")
    String documentDeadlineTomorrowFormat();

    @Key("document.deadline.tomorrow.message")
    String documentDeadlineTomorrowMessage(String arg);

    @Key("document.deadline.today.format")
    String documentDeadlineTodayFormat();

    @Key("document.deadline.today.message")
    String documentDeadlineTodayMessage(String arg);

    @Key("document.deadline.h1.format")
    String documentDeadlineH1Format();

    @Key("document.deadline.h1.message")
    String documentDeadlineH1Message(String arg);

    @Key("document.deadline.passed.format")
    String documentDeadlinePassedFormat();

    @Key("document.deadline.passed.message")
    String documentDeadlinePassedMessage(String arg);

    @Key("confirmation.amendment.delete.title")
    String confirmationAmendmentDeleteTitle();

    @Key("confirmation.amendment.delete.message")
    String confirmationAmendmentDeleteMessage();

    @Key("confirmation.amendment.delete.button.confirm")
    String confirmationAmendmentDeleteButtonConfirm();

    @Key("confirmation.amendment.delete.button.cancel")
    String confirmationAmendmentDeleteButtonCancel();

    @Key("amendment.action.table")
    String amendmentActionTable();

    @Key("amendment.action.delete")
    String amendmentActionDelete();

    @Key("amendment.action.withdraw")
    String amendmentActionWithdraw();

    @Key("amendment.action.register")
    String amendmentActionRegister();

    @Key("amendment.action.return")
    String amendmentActionReturn();

    @Key("amendment.action.save.successful")
    String amendmentActionSaveSuccessful(@PluralCount int arg);

    @Key("amendment.action.table.successful")
    String amendmentActionTableSuccessful(@PluralCount int arg);

    @Key("amendment.action.withdraw.successful")
    String amendmentActionWithdrawSuccessful(@PluralCount int arg);

    @Key("amendment.action.return.successful")
    String amendmentActionReturnSuccessful(@PluralCount int arg);

    @Key("amendment.action.register.successful")
    String amendmentActionRegisterSuccessful(@PluralCount int arg);

    @Key("amendment.action.delete.successful")
    String amendmentActionDeleteSuccessful(@PluralCount int arg);

    @Key("amendment.status.candidate")
    String amendmentStatusCandidate();

    @Key("amendment.status.tabled")
    String amendmentStatusTabled();

    @Key("amendment.status.withdrawn")
    String amendmentStatusWithdrawn();

    @Key("amendment.status.deleted")
    String amendmentStatusDeleted();

    @Key("amendment.status.registered")
    String amendmentStatusRegistered();

    @Key("amendment.status.returned")
    String amendmentStatusReturned();

    @Key("confirmation.amendments.delete.message")
    String confirmationAmendmentsDeleteMessage();

    @Key("amendment.action.cancel")
    String amendmentActionCancel();

    @Key("amendment.selection.number")
    String amendmentSelection(@PluralCount int arg);

    @Key("amendment.action.moveup")
    String amendmentActionMoveUp();

    @Key("amendment.action.movedown")
    String amendmentActionMoveDown();

    @Key("error.document.content.error")
    String errorDocumentContentError(String p0);
}
