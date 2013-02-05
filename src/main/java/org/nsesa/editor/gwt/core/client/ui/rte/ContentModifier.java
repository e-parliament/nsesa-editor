package org.nsesa.editor.gwt.core.client.ui.rte;

/**
 * Date: 15/10/12 09:41
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ContentModifier {

    String modifyContent(String content);

    String undoModification(String content);
}
