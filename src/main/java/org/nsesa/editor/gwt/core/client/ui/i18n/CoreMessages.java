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
}
