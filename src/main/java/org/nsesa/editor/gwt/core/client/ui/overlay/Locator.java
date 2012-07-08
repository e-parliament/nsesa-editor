package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 06/07/12 17:21
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Locator {
    String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded);
}
