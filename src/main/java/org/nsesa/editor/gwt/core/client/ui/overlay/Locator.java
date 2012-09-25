package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso.AkomaNtoso20Locator;

/**
 * Date: 06/07/12 17:21
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AkomaNtoso20Locator.class)
public interface Locator {
    String getLocation(AmendableWidget amendableWidget, String languageIso, boolean childrenIncluded);
}
