package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DeadlineViewImpl.class)
public interface DeadlineView extends IsWidget {

    void setDeadline(String deadline);

    void setPastStyle();

    void set24HourStyle();

    void set1HourStyle();

    void setStyleName(String style);
}
