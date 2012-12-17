package org.nsesa.editor.gwt.core.client.ui.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * Date: 24/06/12 17:18
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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

}
