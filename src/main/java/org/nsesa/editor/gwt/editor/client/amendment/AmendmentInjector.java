package org.nsesa.editor.gwt.editor.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 07/07/12 23:21
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentInjector {

    void inject(AmendmentContainerDTO amendment, AmendableWidget root);
}
