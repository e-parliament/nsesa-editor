package org.nsesa.editor.gwt.core.client.mode;

/**
 * Date: 23/11/12 23:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface DocumentMode<S extends DocumentState> {
    boolean apply(S state);

    S getState();
}
