package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile.marker;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(MarkerViewImpl.class)
public interface MarkerView extends IsWidget {
    FocusWidget addMarker(double top, String color);

    void clearMarkers();

    void setStyleName(String style);
}
