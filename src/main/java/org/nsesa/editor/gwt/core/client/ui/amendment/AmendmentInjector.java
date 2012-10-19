package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * Date: 19/10/12 10:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({AmendmentModule.class})
public interface AmendmentInjector extends Ginjector {

    AmendmentView getAmendmentView();

    AmendmentEventBus getAmendmentEventBus();
}
