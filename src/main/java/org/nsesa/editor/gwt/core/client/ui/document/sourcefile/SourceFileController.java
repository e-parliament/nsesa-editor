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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentOverlayCompletedEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetModifyEvent;
import org.nsesa.editor.gwt.core.client.event.widget.OverlayWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header.SourceFileHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetUIListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.util.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 28/01/13 15:27
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class SourceFileController implements OverlayWidgetUIListener, OverlayWidgetWalker {

    private static final Logger LOG = Logger.getLogger(SourceFileController.class.getName());

    /**
     * Private, document scoped event bus.
     */
    protected final DocumentEventBus documentEventBus;

    /**
     * Marker controller to mark injected amendments in the content.
     */
    protected final MarkerController markerController;

    /**
     * Source file header to control some mode-buttons, allow selection of translation, etc ..
     */
    protected final SourceFileHeaderController sourceFileHeaderController;

    /**
     * Actual content of the source text.
     */
    protected final ContentController contentController;

    /**
     * Action bar for all amendable overlay widgets, allows different amendment actions.
     */
    protected final ActionBarController actionBarController;

    /**
     * The main view of the source file controller.
     */
    protected final SourceFileView view;

    protected final SourceFileViewCss style;

    /**
     * Parent document controller.
     */
    protected DocumentController documentController;

    /**
     * List of overlay widgets from the root node(s) in the content controller.
     */
    protected List<OverlayWidget> overlayWidgets;

    /**
     * A list of active overlay widgets (after a {@link OverlayWidgetSelectEvent} has been caught).
     */
    protected OverlayWidget activeOverlayWidget;

    /**
     * Event handler registration.
     */
    private HandlerRegistration documentScrollEventHandlerRegistration;

    @Inject
    public SourceFileController(final DocumentEventBus documentEventBus,
                                final MarkerController markerController,
                                final SourceFileHeaderController sourceFileHeaderController,
                                final SourceFileView sourceFileView,
                                final ContentController contentController,
                                final ActionBarController actionBarController,
                                final SourceFileViewCss style) {

        this.view = sourceFileView;
        this.documentEventBus = documentEventBus;
        this.markerController = markerController;
        this.sourceFileHeaderController = sourceFileHeaderController;
        this.contentController = contentController;
        this.actionBarController = actionBarController;
        this.style = style;

        this.markerController.setSourceFileController(this);
        this.contentController.setSourceFileController(this);
        this.sourceFileHeaderController.setSourceFileController(this);
        this.actionBarController.setSourceFileController(this);

        registerListeners();
    }

    private void registerListeners() {
        documentScrollEventHandlerRegistration = contentController.getView().getScrollPanel().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                documentEventBus.fireEvent(new DocumentScrollEvent(documentController));
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        documentScrollEventHandlerRegistration.removeHandler();
    }

    /**
     * Return the top overlay widget that is completely visible in the content controller. Not very reliable ...
     *
     * @return the top visible overlay widget.
     */
    public OverlayWidget getTopVisibleOverlayWidget() {
        return contentController.getCurrentVisibleAmendableWidget();
    }

    /**
     * Set the content on the content controller via {@link ContentController#setContent(String)}.
     * Does a cleanup of the overlay widgets.
     *
     * @param documentContent the document content to set
     */
    public void setContent(String documentContent) {
        /*if (activeOverlayWidget != null) {
            activeOverlayWidget = null;
        }*/
        // clean up
        if (overlayWidgets != null && !overlayWidgets.isEmpty()) {
            for (final OverlayWidget overlayWidget : overlayWidgets) {
                overlayWidget.onDetach();
            }
        }
        contentController.setContent(documentContent);
    }

    /**
     * Perform the overlay of the DOM tree into a higher level tree of {@link OverlayWidget}s for each of the
     * elements returned from
     * {@link org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController#getContentElements()}.
     * <p/>
     * Fires a {@link CriticalErrorEvent} is something goes wrong during the overlay.
     *
     * @see #overlay(com.google.gwt.dom.client.Element, org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetUIListener)
     */
    public void overlay() {
        long start = System.currentTimeMillis();
        final Element[] contentElements = contentController.getContentElements();
        if (overlayWidgets == null) overlayWidgets = new ArrayList<OverlayWidget>();
        for (final Element element : contentElements) {
            try {
                final OverlayWidget rootOverlayWidget = overlay(element, this);
                overlayWidgets.add(rootOverlayWidget);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Exception while overlaying.", e);
                documentEventBus.fireEvent(new CriticalErrorEvent("Exception while overlaying.", e));
            }
        }
        // notify the event bus that this document controller is ready
        documentEventBus.fireEvent(new DocumentOverlayCompletedEvent(documentController));
        LOG.info("Overlaying took " + (System.currentTimeMillis() - start) + "ms.");
    }

    /**
     * The actual overlaying routine to transform a given DOM tree with root <tt>element</tt> into a tree of higher
     * level {@link OverlayWidget}s. All amendable overlay widgets will have a {@link OverlayWidgetUIListener}
     * <tt>UIListener</tt> set so they can respond to UI events such as clicking.
     *
     * @param element    the root DOM element to create the overlay tree for
     * @param UIListener the UI listener to set on each amendable overlay widget
     * @return the root overlay widget for a given <tt>element</tt>
     */
    protected OverlayWidget overlay(final com.google.gwt.dom.client.Element element, final OverlayWidgetUIListener UIListener) {
        // Assert that the element is attached.
        // assert Document.get().getBody().isOrHasChild(element) : "element is not attached to the document -- BUG";

        final OverlayWidget root = documentController.getOverlayFactory().getAmendableWidget(element);
        if (root != null) {
            walk(root, new OverlayWidgetVisitor() {
                @Override
                public boolean visit(OverlayWidget visited) {
                    // if the widget is amendable, register a listener for its events
                    if (visited != null && visited.isAmendable() != null && visited.isAmendable()) {
                        visited.setUIListener(UIListener);
                    }
                    return true;
                }
            });
        }
        return root;
    }

    /**
     * This will use a
     * depth-first search using {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget#getChildOverlayWidgets()}.
     * </P>
     * Depending on the visitor's return value from {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetWalker.OverlayWidgetVisitor#visit(org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget)},
     * we will continue going deeper into the tree's leaves.
     * <p/>
     * Note that when a search is stopped short by the visitor, this will <strong>NOT</strong> prevent the search from
     * visiting the sibling of this node that has not yet been visited.
     *
     * @param visitor the visitor
     */
    @Override
    public void walk(OverlayWidgetVisitor visitor) {
        for (final OverlayWidget root : overlayWidgets) {
            if (root != null) {
                root.walk(visitor);
            } else {
                LOG.warning("There is no root widget set - probably no overlay took place?");
            }
        }
    }

    /**
     * Depth-first walk of the given {@link OverlayWidget} <tt>toVisit</tt> with a given <tt>visitor</tt>.
     *
     * @param toVisit the overlay widget to start the tree walk
     * @param visitor the visitor that will visit each node
     */
    public void walk(final OverlayWidget toVisit, final OverlayWidgetVisitor visitor) {
        toVisit.walk(visitor);
    }

    /**
     * Asks the content controller to scroll to a given widget.
     *
     * @param widget the widget to scroll to
     */
    public void scrollTo(final Widget widget) {
        contentController.scrollTo(widget, 40);
    }

    /**
     * Asks the content controller to scroll to a given widget.
     *
     * @param widget the widget to scroll to
     * @param offset the offset in pixels
     */
    public void scrollTo(final Widget widget, int offset) {
        contentController.scrollTo(widget, offset);
    }

    public void highlight(final Widget overlayWidget, final String color, final int seconds) {
        if (seconds == -1) {

        } else {
            // permanent highlight
            Animation animation = new Animation() {
                @Override
                protected void onUpdate(double progress) {
                    setOpacity(overlayWidget, color, interpolate(progress));
                }
            };
            animation.run(seconds * 1000);
        }
    }

    private void setOpacity(Widget overlayWidget, String color, double opacity) {
        final Style style = overlayWidget.getElement().getStyle();
        style.setOpacity(opacity);
        if (opacity != 0.0 && opacity < 0.3) {

        }
    }

    /**
     * Click callback; fires a {@link OverlayWidgetSelectEvent} on the private document bus.
     *
     * @param sender the overlay widget that was clicked
     */
    @Override
    public void onClick(final OverlayWidget sender, final Event event) {
        String href = null;
        try {
            href = extractAttribute(event.getEventTarget(), "href");
        } catch (Exception e) {
            // ignore
        }
        if (href != null && !"".equals(href.trim())) {
            // this contains a href - pass it on to the document controller's ref handler
            if (documentController != null && documentController.getLocalOverlayWidgetReferenceHandler() != null) {
                documentController.getLocalOverlayWidgetReferenceHandler().resolve("href", href, sender, new AsyncCallback<OverlayWidget>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // ignore atm
                    }

                    @Override
                    public void onSuccess(OverlayWidget result) {
                        if (result != null)
                            scrollTo(result.asWidget());
                    }
                });
            }
        }
        documentEventBus.fireEvent(new OverlayWidgetSelectEvent(sender, documentController));
    }

    private native String extractAttribute(final JavaScriptObject target, final String attributeName) /*-{
        //$wnd.console.log(target);
        return target.getAttribute(attributeName);
    }-*/;

    /**
     * Double click callback; fires a {@link OverlayWidgetModifyEvent} on the private document bus.
     *
     * @param sender the overlay widget that was double clicked
     */
    @Override
    public void onDblClick(final OverlayWidget sender, final Event event) {
        documentEventBus.fireEvent(new OverlayWidgetModifyEvent(sender));
    }

    /**
     * Mouse over callback; if the {@link OverlayWidget} <tt>sender</tt> is amendable, we will attach the
     * {@link ActionBarController} with the available amendment options, and set the location obtained via
     * {@link org.nsesa.editor.gwt.core.client.ui.document.DocumentController#getLocator()}.
     *
     * @param sender the overlay widget that was hovered
     */
    @Override
    public void onMouseOver(final OverlayWidget sender, final Event event) {
        // we do not allow nested amendments, so if this amendable widget is already introduced by an amendment, do not
        // allow the action bar to be shown.
        if (!sender.isIntroducedByAnAmendment() && sender.isAmendable()) {
            actionBarController.attach(sender, documentController);
            actionBarController.setLocation(documentController.getLocator().getLocation(sender, documentController.getDocument().getLanguageIso(), false));
        }
    }

    /**
     * We ignore on-mouse-out events since they tend to be unreliable. Rather, we're passing the
     * {@link ActionBarController} as a single token around.
     *
     * @param sender the overlay widget that lost the mouse hoover
     */
    @Override
    public void onMouseOut(final OverlayWidget sender, final Event event) {
        // ignore
    }

    /**
     * Renumbers the amendments by assigning a local number to be assigned to the amendments in the order they appear
     * in the document.
     */
    public void renumberOverlayWidgetsAware() {
        final Counter counter = new Counter();
        walk(new OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                if (visited.isAmended()) {
                    for (final OverlayWidgetAware amendmentController : visited.getOverlayWidgetAwareList()) {
                        amendmentController.setOrder(counter.incrementAndGet());
                    }
                }
                return true;
            }
        });
    }

    /**
     * Return the active overlay widget, if any.
     *
     * @return the active overlay widget
     */
    public OverlayWidget getActiveOverlayWidget() {
        return activeOverlayWidget;
    }

    /**
     * Set the active overlay widget
     *
     * @param activeOverlayWidget the active overlay widget
     */
    public void setActiveOverlayWidget(OverlayWidget activeOverlayWidget) {
        if (this.activeOverlayWidget != null) {
            this.activeOverlayWidget.asWidget().removeStyleName(style.selected());
        }
        LOG.info("Setting " + activeOverlayWidget + " as active widget on " + documentController);
        this.activeOverlayWidget = activeOverlayWidget;

        if (this.activeOverlayWidget != null) {
            this.activeOverlayWidget.asWidget().addStyleName(style.selected());
        }
    }

    /**
     * Set the parent document controller.
     *
     * @param documentController the document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    /**
     * Get a list of all the root overlay widget nodes under this source file controller.
     *
     * @return the list of overlay widget root nodes
     */
    public List<OverlayWidget> getOverlayWidgets() {
        return overlayWidgets;
    }

    /**
     * Clear the root overlay widget nodes.
     * Does not do any detaching.
     */
    public void clearOverlayWidgets() {
        this.overlayWidgets = new ArrayList<OverlayWidget>();
    }

    /**
     * Get the marker controller for the markers component.
     *
     * @return the marker controller.
     */
    public MarkerController getMarkerController() {
        return markerController;
    }

    /**
     * Get the source file header controller.
     *
     * @return the header controller
     */
    public SourceFileHeaderController getSourceFileHeaderController() {
        return sourceFileHeaderController;
    }

    /**
     * Get the content controller.
     *
     * @return the content controller
     */
    public ContentController getContentController() {
        return contentController;
    }

    /**
     * Get the action bar controller.
     *
     * @return the action bar controller
     */
    public ActionBarController getActionBarController() {
        return actionBarController;
    }

    /**
     * Get the parent document controller.
     *
     * @return the document controller
     */
    public DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Get the associated view.
     *
     * @return the view
     */
    public SourceFileView getView() {
        return view;
    }
}
