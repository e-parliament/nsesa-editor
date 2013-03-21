/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link Transformer} interface. It tries to generate an XML representation of an
 * overlay widget by retrieving text content and attributes values from the corresponding DOM element.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 20/11/12 11:02
 */
public class DefaultTransformer implements Transformer {

    public static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    public static final String DEFAULT_NAMESPACE = "";

    private static final Logger LOG = Logger.getLogger(DefaultTransformer.class.getName());

    private boolean withIndentation = true;

    /**
     * Generate an Xml representation for the given <code>OverlayWidget</code>
     * @param widget The overlay widget that will be XML-ized.
     * @return Xml representation as String
     */
    @Override
    public String transform(final OverlayWidget widget) {
        final Map<String, String> namespaces = gatherNamespaces(widget);
        namespaces.put(widget.getNamespaceURI(), DEFAULT_NAMESPACE);
        final StringBuilder sb = new StringBuilder(XML_DECLARATION).append("\n");
        return sb.append(toXMLElement(widget, namespaces, true, 0)).toString();
    }

    /**
     * This method is in facto responsible with the xml generation. For each child of the given
     * overlay widget ({@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getChildOverlayWidgets()})
     * it tries to find out the corresponding browser element node and to extract from there the text content and the
     * css attributes values. The process continue then recursively for all descendants of the original overlay widget.
     *
     * @param widget The overlay widget that will be processed
     * @param namespaces The map of all namespaces used in overlay widget and its descendants
     * @param rootNode True if the
     * @param depth
     * @return Xml representation of overlay widget as String
     */
    public String toXMLElement(final OverlayWidget widget,
                               final Map<String, String> namespaces,
                               final boolean rootNode,
                               int depth)
    {
        final StringBuilder sb = new StringBuilder();
        final String indent = withIndentation ? TextUtils.repeat(depth, "  ") : "";
        sb.append(indent).append("<");
        if (rootNode) {
            sb.append(widget.getType());
            for (final Map.Entry<String, String> entry : namespaces.entrySet()) {
                if (DEFAULT_NAMESPACE.equals(entry.getValue())) {
                    // this is the default namespace
                    sb.append(" xmlns=\"").append(widget.getNamespaceURI()).append("\"");
                } else {
                    // prefixed namespace
                    sb.append(" xmlns:").append(entry.getValue()).append("=\"").append(entry.getKey()).append("\"");
                }
            }
        } else {
            final String prefix = namespaces.get(widget.getNamespaceURI());
            if (!DEFAULT_NAMESPACE.equals(prefix)) {
                sb.append(prefix).append(":");
            }
            sb.append(widget.getType());
        }
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
        sb.append(">");
        Element element = widget.getOverlayElement();
        NodeList<Node> nodes = element.getChildNodes();
        int length = nodes.getLength();
        if (length == 0) {
            // the root is all the time a new one
            // apply xml transformation for children
            for (final OverlayWidget child : widget.getChildOverlayWidgets()) {
                sb.append(toXMLElement(child, namespaces, false, depth + 1).trim());
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
                            sb.append(toXMLElement(child, namespaces, false, depth + 1).trim());
                        } else {
                            LOG.warning("No amendable child widget found for element " + childElement.getInnerHTML());
                        }
                        break;
                    case Node.TEXT_NODE:
                        sb.append(SafeHtmlUtils.htmlEscapeAllowEntities(nodes.getItem(i).getNodeValue().trim()));
                        break;
                    case Node.DOCUMENT_NODE:
                        LOG.log(Level.WARNING, "There should be no document node here for " + element.getInnerHTML());
                        break;
                }
            }
        }
        sb.append("</").append(widget.getType()).append(">");
        return sb.toString();
    }

    /**
     * Gather all distinct namespaces from the given <code>OverlayWidget</code> and its chidren
     * @param root The OverlayWidget that will be processed
     * @return A Map of String
     */
    protected Map<String, String> gatherNamespaces(final OverlayWidget root) {
        final Map<String, String> namespaces = new HashMap<String, String>();
        root.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                if (!namespaces.containsKey(visited.getNamespaceURI())) {
                    String prefix = getPrefix(visited.getNamespaceURI());
                    if (prefix == null) {
                        // generate namespace (ns1, ns2, ...)
                        prefix = "ns" + namespaces.size() + 1;
                    }
                    namespaces.put(visited.getNamespaceURI(), prefix);
                }
                return true;
            }
        });
        return namespaces;
    }

    /**
     * Return null but can be overridden by subclasses.
     * @param namespaceURI The namesapce to be processed
     * @return <code>null</code>
     */
    protected String getPrefix(final String namespaceURI) {
        return null;
    }
}
