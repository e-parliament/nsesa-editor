package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentController extends Composite {

    private ContentView view;
    private DocumentController documentController;
    private boolean contentLoaded;

    @Inject
    public ContentController(final EventBus eventBus, final ContentView view) {
        assert eventBus != null : "EventBus not set in constructor --BUG";
        assert view != null : "View is not set --BUG";

        this.view = view;
    }

    public ContentView getView() {
        return view;
    }

    public void setContent(String documentContent) {
        view.setContent(documentContent);
        this.contentLoaded = true;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public Element[] getContentElements() {
        if (!contentLoaded) {
            throw new RuntimeException("Content not yet available.");
        }
        ArrayList<Element> elements = new ArrayList<Element>();
        final NodeList<Element> elementsByTagName = view.getContentElement().getElementsByTagName("component");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node node = elementsByTagName.getItem(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements.add(Element.as(node));
            }
        }
        return elements.toArray(new Element[elements.size()]);
    }
}
