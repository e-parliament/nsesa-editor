package org.nsesa.editor.gwt.editor.client.ui.document.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.editor.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 24/06/12 18:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DOCUMENT)
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
                documentEventBus.fireEvent(new DocumentScrollEvent(documentController));
            }
        });
    }

    public ContentView getView() {
        return view;
    }

    public boolean isFullyVisible(Widget widget) {
        if (widget != null) {
            final int widgetTop = widget.asWidget().getAbsoluteTop();
            final int scrollTop = view.getScrollPanel().getAbsoluteTop();
            final int scrollBarHeight = view.getScrollPanel().getOffsetHeight();
            return widgetTop > scrollTop && widgetTop < scrollTop + scrollBarHeight;
        }
        return false;
    }

    public AmendableWidget getCurrentVisibleAmendableWidget() {
        final AmendableWidget[] temp = new AmendableWidget[1];
        documentController.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                if (isFullyVisible(visited.asWidget()) && temp[0] == null) {
                    temp[0] = visited;
                    return false;
                }
                return true;
            }
        });
        return temp[0];
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
        List<Element> elements = new ArrayList<Element>();
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
        view.getScrollPanel().scrollToTop();
        view.getScrollPanel().setVerticalScrollPosition(widget.getAbsoluteTop() - view.getScrollPanel().getAbsoluteTop());
    }
}
