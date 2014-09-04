/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;

/**
 * Date: 22/01/13 14:07
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class NodeUtil {

    public static Text getText(final Node node) {
        return getText(node, false);
    }
    public static Text getText(final Node node, boolean includeChildren) {
        final NodeList<Node> childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.getItem(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                return (Text) child;
            }
            else if (child.getNodeType() == Node.ELEMENT_NODE) {
                return getText(child, includeChildren);

            }
        }
        return null;
    }

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
