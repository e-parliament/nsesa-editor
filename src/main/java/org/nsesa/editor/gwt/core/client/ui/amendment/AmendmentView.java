package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentViewImpl.class)
public interface AmendmentView extends IsWidget {

    void setTitle(String title);

    void setBody(String xmlContent);

    Element getBody();

    HasClickHandlers getMoreActionsButton();

    HasClickHandlers getEditButton();

    HasClickHandlers getDeleteButton();
}
