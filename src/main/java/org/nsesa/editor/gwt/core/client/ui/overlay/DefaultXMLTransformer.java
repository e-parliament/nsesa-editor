package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of <code>XMLTransformer</code> interface
 * User: groza
 * Date: 20/11/12
 * Time: 11:02
 */
public class DefaultXMLTransformer implements XMLTransformer {
    @Override
    public String toXML(AmendableWidget widget) {
        //TODO we have to find out a proper way to escape XML chars
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(widget.getType());
        //get the attributes
        LinkedHashMap<String, String> attrs = widget.getAttributes();
        if(attrs.size() > 0) {
            for(Map.Entry<String, String> entry : attrs.entrySet()) {
                if (entry.getValue() != null && entry.getValue().length() > 0) {
                    sb.append(" ").append(entry.getKey()).append("=").append("\"").append(TextUtils.escapeXML(entry.getValue())).append("\"");
                }
            }
        }
        sb.append(">");
        // apply xml transformation for children
        for(AmendableWidget child : widget.getChildAmendableWidgets()) {
            sb.append(toXML(child));
        }
        String content = widget.getContent();
        if (content != null && content.length() > 0) {
            sb.append(TextUtils.escapeXML(content));
        }
        sb.append("</").append(widget.getType()).append(">");
        return sb.toString();
    }
}
