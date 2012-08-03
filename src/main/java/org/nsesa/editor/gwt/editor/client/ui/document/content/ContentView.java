package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ContentViewImpl.class)
public interface ContentView extends IsWidget {
    void setContent(String documentContent);

    Element getContentElement();

    ScrollPanel getScrollPanel();

    void setStyleName(String style);
}
