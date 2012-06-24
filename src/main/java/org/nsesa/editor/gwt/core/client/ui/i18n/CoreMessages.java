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
}
