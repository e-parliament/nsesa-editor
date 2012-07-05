package org.nsesa.editor.gwt.core.client.util;

/**
 * Date: 30/06/12 17:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DOMHelper {


    public static String indent(int amount) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
}
