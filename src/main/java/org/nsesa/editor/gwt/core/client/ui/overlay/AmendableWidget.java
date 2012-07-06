package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidget extends IsWidget, HasWidgets {

    Boolean isAmendable();

    void setAmendable(Boolean amendable);

    Boolean isImmutable();

    void setImmutable(Boolean immutable);

    void setListener(AmendableWidgetListener listener);

    void setParentAmendableWidget(AmendableWidget parent);

    void addAmendableWidget(AmendableWidget child);

    void postProcess();
}
