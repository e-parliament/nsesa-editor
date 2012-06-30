package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ContentView extends IsWidget {
    void setContent(String documentContent);

    Element getContentElement();
}
