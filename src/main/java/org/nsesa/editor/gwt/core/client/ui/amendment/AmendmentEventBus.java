package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.AMENDMENT;

/**
 * Date: 19/10/12 12:04
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(AMENDMENT)
public class AmendmentEventBus extends SimpleEventBus {
}
