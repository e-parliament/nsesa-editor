package org.nsesa.editor.gwt.core.client.ui.actionbar;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ActionBarView extends IsWidget {
    Widget getModifyHandler();

    Widget getDeleteHandler();

    Widget getBundleHandler();

    Widget getMoveHandler();

    Widget getChildHandler();

    Widget getTranslateHandler();

    void setLocation(String location);
}
