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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link org.nsesa.editor.gwt.core.client.ui.overlay.Transformer} interface. It tries to generate an XML representation of an
 * overlay widget by retrieving text content and attributes values from the corresponding DOM element.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 20/11/12 11:02
 */
public class HTMLTransformer implements Transformer {

    private static final Logger LOG = Logger.getLogger(HTMLTransformer.class.getName());

    /**
     * Generate an HTML representation for the given <code>OverlayWidget</code>
     *
     * @param widget The overlay widget that will be XML-ized.
     * @return Xml representation as String
     */
    @Override
    public String transform(final OverlayWidget widget) {
        final StringBuilder sb = new StringBuilder();
        return sb.append(toHTMLElement(widget, false, 0)).toString();
    }

    /**
     * This method is in facto responsible with the xml generation. For each child of the given
     * overlay widget ({@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getChildOverlayWidgets()})
     * it tries to find out the corresponding browser element node and to extract from there the text content and the
     * css attributes values. The process continue then recursively for all descendants of the original overlay widget.
     *
     * @param widget The overlay widget that will be processed
     * @param depth
     * @return Xml representation of overlay widget as String
     */
    public String toHTMLElement(final OverlayWidget widget, boolean withIndentation,
                                int depth) {

        final StringBuilder sb = new StringBuilder();
        final String indent = withIndentation ? TextUtils.repeat(depth, "  ") : "";
        if (!widget.getOverlayElement().getClassName().contains(widget.getType())) widget.getOverlayElement().addClassName(widget.getType());
        sb.append(indent).append("<span class=\"").append(widget.getOverlayElement().getClassName())
                .append("\" type=\"").append(widget.getType()).append("\"").append(" ns=\"")
                .append(widget.getNamespaceURI()).append("\"");
        //get the attributes
        final LinkedHashMap<String, String> attrs = widget.getAttributes();
        if (!attrs.isEmpty()) {
            for (final Map.Entry<String, String> entry : attrs.entrySet()) {
                // do not add the class attribute
                if (!"class".equalsIgnoreCase(entry.getKey())) {
                    if (entry.getValue() != null && entry.getValue().length() > 0) {
                        sb.append(" ").append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"");
                    }
                }
            }
        }
        // add class names
        sb.append(">");
        Element element = widget.getOverlayElement();
        NodeList<Node> nodes = element.getChildNodes();
        int length = nodes.getLength();
        if (length == 0) {
            // the root is all the time a new one
            // apply xml transformation for children
            for (final OverlayWidget child : widget.getChildOverlayWidgets()) {
                sb.append(toHTMLElement(child, false, depth + 1).trim());
            }
        } else {
            for (int i = 0; i < length; i++) {
                final short nodeType = nodes.getItem(i).getNodeType();
                Element childElement = nodes.getItem(i).cast();
                switch (nodeType) {
                    case Node.ELEMENT_NODE:
                        // get the amendable widget corresponding to this child and apply xml transformation
                        // hopefully there is one amendable widget linked to this node
                        OverlayWidget child = null;
                        // try to find out a amendable widget linked to childElement
                        for (OverlayWidget aw : widget.getChildOverlayWidgets()) {
                            if (aw.getOverlayElement().equals(childElement)) {
                                child = aw;
                                break;
                            }
                        }
                        if (child != null) {
                            sb.append(toHTMLElement(child, false, depth + 1).trim());
                        } else {
                            LOG.warning("No amendable child widget found for element " + childElement.getNodeName());
                            sb.append(DOM.toString((com.google.gwt.user.client.Element) childElement.cast()));
                        }
                        break;
                    case Node.TEXT_NODE:
                        sb.append(TextUtils.escapeXML(nodes.getItem(i).getNodeValue().trim()));
                        //sb.append(nodes.getItem(i).getNodeValue().trim());
                        break;
                    case Node.DOCUMENT_NODE:
                        LOG.log(Level.WARNING, "There should be no document node here for " + element.getInnerHTML());
                        break;
                }
            }
        }
        sb.append("</span>");
        return sb.toString();
    }

}
