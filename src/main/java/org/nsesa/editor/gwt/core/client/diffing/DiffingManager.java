package org.nsesa.editor.gwt.core.client.diffing;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.shared.DiffMethod;

/**
 * Date: 07/01/13 17:45
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultDiffingManager.class)
public interface DiffingManager {
    void diff(final DiffMethod method, final AmendmentController... amendmentControllers);
}
