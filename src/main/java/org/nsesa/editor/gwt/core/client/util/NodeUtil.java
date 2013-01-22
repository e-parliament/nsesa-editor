package org.nsesa.editor.gwt.core.client.util;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;

/**
 * Date: 22/01/13 14:07
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class NodeUtil {

    public static void walk(final Node node, final NodeVisitor visitor) {
        visitor.visit(node);
        final NodeList<Node> childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.getItem(i);
            walk(child, visitor);
        }
    }

    public static interface NodeVisitor {
        void visit(Node node);
    }
}
