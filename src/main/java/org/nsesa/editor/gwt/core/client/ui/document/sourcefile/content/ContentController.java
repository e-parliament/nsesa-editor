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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;

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
    private SourceFileController sourceFileController;
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
                if (sourceFileController != null)
                    documentEventBus.fireEvent(new DocumentScrollEvent(sourceFileController.getDocumentController()));
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

    public OverlayWidget getCurrentVisibleAmendableWidget() {
        final OverlayWidget[] temp = new OverlayWidget[1];
        sourceFileController.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
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

    public void setSourceFileController(SourceFileController sourceFileController) {
        this.sourceFileController = sourceFileController;
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
