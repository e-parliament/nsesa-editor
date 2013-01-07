package org.nsesa.editor.gwt.core.client.util.select;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * Date: 07/01/13 14:24
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Matcher {
    boolean matches(String expression, AmendableWidget amendableWidget);

    boolean applicable(String expression);
}
