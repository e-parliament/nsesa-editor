package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Date: 24/06/12 21:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface MarkerView extends IsWidget {
    void addMarker(double top);

    void clearMarkers();
}
