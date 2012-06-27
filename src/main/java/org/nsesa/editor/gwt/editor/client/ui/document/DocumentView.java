package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface DocumentView extends IsWidget {

    Panel getDocumentHeaderPanel();

    Panel getContentPanel();

    Panel getMarkerPanel();
}
