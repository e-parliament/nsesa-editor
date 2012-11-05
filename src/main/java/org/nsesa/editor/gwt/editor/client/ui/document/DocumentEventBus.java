package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.SimpleEventBus;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 19/10/12 11:33
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
public class DocumentEventBus extends SimpleEventBus {
}
