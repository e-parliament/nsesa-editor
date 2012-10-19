package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.ArrayList;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class ContentController {

    private ContentView view;
    private DocumentController documentController;
    private final DocumentEventBus documentEventBus;
    private boolean contentLoaded;

    @Inject
    public ContentController(final DocumentEventBus documentEventBus, final ContentView view) {
        assert view != null : "View is not set --BUG";

        this.view = view;
        this.documentEventBus = documentEventBus;
        registerListeners();
    }

    private void registerListeners() {
        view.getScrollPanel().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {

            }
        });
    }

    public ContentView getView() {
        return view;
    }

    public boolean isVisible(Widget widget) {
        if (widget != null) {
            final int absoluteTop = widget.asWidget().getAbsoluteTop();
            final int scrollPanelAbsoluteTop = view.getScrollPanel().getAbsoluteTop();
            return absoluteTop > scrollPanelAbsoluteTop && absoluteTop < (view.getScrollPanel().getOffsetHeight() + scrollPanelAbsoluteTop);
        }
        return false;
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
        final NodeList<Node> childNodes = view.getContentElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.getItem(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements.add(Element.as(node));
            }
        }
        return elements.toArray(new Element[elements.size()]);
    }

    /**
     * Scroll to the given widget (if it exists)
     *
     * @param widget the widget to scroll to
     */
    public void scrollTo(final Widget widget) {
        view.getScrollPanel().setVerticalScrollPosition(widget.getAbsoluteTop());
    }
}
