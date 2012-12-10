package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of <code>XMLTransformer</code> interface
 * User: groza
 * Date: 20/11/12
 * Time: 11:02
 */
public class DefaultXMLTransformer implements XMLTransformer {

    public static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    public static final String DEFAULT_NAMESPACE = "";

    private static final Logger LOG = Logger.getLogger(DefaultXMLTransformer.class.getName());

    @Override
    public String toXML(final AmendableWidget widget) {
        final Map<String, String> namespaces = gatherNamespaces(widget);
        namespaces.put(widget.getNamespaceURI(), DEFAULT_NAMESPACE);
        final StringBuilder sb = new StringBuilder(XML_DECLARATION);
        return sb.append(toXMLElement(widget, namespaces, true)).toString();
    }

    public String toXMLElement(final AmendableWidget widget, final Map<String, String> namespaces, final boolean rootNode) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<");
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
        Element element = widget.getAmendableElement();
        NodeList<Node> nodes = element.getChildNodes();
        int length = nodes.getLength();
        if (length == 0) {
            // the root is all the time a new one
            // apply xml transformation for children
            for (final AmendableWidget child : widget.getChildAmendableWidgets()) {
                sb.append(toXMLElement(child, namespaces, false));
            }
        } else {
            for (int i = 0; i < length; i++) {
                final short nodeType = nodes.getItem(i).getNodeType();
                Element childElement = nodes.getItem(i).cast();
                switch (nodeType) {
                    case Node.ELEMENT_NODE :
                        // get the amendable widget corresponding to this child and apply xml transformation
                        // hopefully there is one amendable widget linked to this node

//                        if (i >= widget.getChildAmendableWidgets().size()) {
//                            LOG.warning("no correlation between amendable widgets and DOM elements");
//                            break;
//                        }
                        //final AmendableWidget child = widget.getChildAmendableWidgets().get(i);
                        //sb.append(toXMLElement(child, namespaces, false));
                        AmendableWidget child = null;
                        // try to find out a amendable widget linked to childElement
                        for (AmendableWidget aw : widget.getChildAmendableWidgets()) {
                            if (aw.getAmendableElement().equals(childElement)) {
                                child = aw;
                                break;
                            }
                        }
                        if (child != null) {
                            sb.append(toXMLElement(child, namespaces, false));
                        } else {
                            LOG.warning("No amendable child widget found for element " + childElement.getInnerHTML());
                        }
                        break;
                    case Node.TEXT_NODE:
                        Element childText = nodes.getItem(i).cast();
                        sb.append(childText.getInnerText());
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

    protected Map<String, String> gatherNamespaces(final AmendableWidget root) {
        final Map<String, String> namespaces = new HashMap<String, String>();
        root.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
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

    protected String getPrefix(final String namespaceURI) {
        return null;
    }
}
