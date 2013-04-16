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
package org.nsesa.editor.gwt.core.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is available as a convenience base class for all plugins;
 * it provides default empty implementation for all methods.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 14/03/13 13:27
 */
public class DefaultRichTextEditorPlugin implements RichTextEditorPlugin {
    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        //do nothing.
    }

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing.
    }

    /**
     * Create a list of overlay widgets from the editor body
     * @param editor The editor
     * @param overlayFactory The factory used to create widgets
     * @return  A List of overlay widgets
     */
    protected final List<OverlayWidget> overlayEditorBody(JavaScriptObject editor, OverlayFactory overlayFactory) {
        List<OverlayWidget> result = new ArrayList<OverlayWidget>();

        JavaScriptObject body = getBody(editor);
        if (body != null) {
            Element bodyElement = body.cast();
            for (int i = 0; i < bodyElement.getChildCount(); i++) {
                final Node node = bodyElement.getChild(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    final Element element = node.cast();
                    final OverlayWidget widget = overlayFactory.getAmendableWidget(element);
                    if (widget != null) {
                        result.add(widget);
                    }
                }
            }

        }
        return result;

    }

    /**
     * Return  the editor body if exist
      * @param editor
     * @return The body as {@link JavaScriptObject}
     */
    protected final native JavaScriptObject getBody(JavaScriptObject editor) /*-{
        if (editor.document) {
            return editor.document.getBody().$;
        }
        return null;
    }-*/;

    /**
     * Find an overlay widget in the list of the widgets identified by its overlayElement
      * @param overlayElement
     * @param widgets
     * @return
     */
    protected OverlayWidget findOverlayWidget(final Element overlayElement, List<OverlayWidget> widgets) {
        final OverlayWidget[] results = new OverlayWidget[1];
        for (OverlayWidget widget : widgets) {
            widget.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
                @Override
                public boolean visit(OverlayWidget visited) {
                    if (visited.getOverlayElement() == overlayElement) {
                        results[0] = visited;
                    }
                    return true;
                }
            });
        }
        return results[0];
    }

}
