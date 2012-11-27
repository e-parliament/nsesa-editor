package org.nsesa.editor.gwt.editor.client.ui.amendments;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentsPanelViewImpl.class)
public interface AmendmentsPanelView extends IsWidget {
    public void refreshAmendments(List<AmendmentView> amendmentsView);
    public void setStyleName(String styleName);

}
