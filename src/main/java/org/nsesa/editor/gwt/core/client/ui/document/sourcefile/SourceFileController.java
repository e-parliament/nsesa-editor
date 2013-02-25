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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.event.CriticalErrorEvent;
import org.nsesa.editor.gwt.core.client.event.amendment.AmendmentContainerCreateEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentScrollEvent;
import org.nsesa.editor.gwt.core.client.event.widget.AmendableWidgetSelectEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.header.SourceFileHeaderController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerController;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetUIListener;
import org.nsesa.editor.gwt.core.client.util.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 28/01/13 15:27
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class SourceFileController implements OverlayWidgetUIListener, OverlayWidgetWalker {

    private static final Logger LOG = Logger.getLogger(SourceFileController.class.getName());

    protected final DocumentEventBus documentEventBus;

    protected final MarkerController markerController;
    protected final SourceFileHeaderController sourceFileHeaderController;
    protected final ContentController contentController;
    protected final ActionBarController actionBarController;

    protected final SourceFileView view;

    // logical parent
    protected DocumentController documentController;

    protected List<OverlayWidget> overlayWidgets;

    protected OverlayWidget activeOverlayWidget;

    @Inject
    public SourceFileController(DocumentEventBus documentEventBus, MarkerController markerController,
                                SourceFileHeaderController sourceFileHeaderController,
                                SourceFileView sourceFileView,
                                ContentController contentController, ActionBarController actionBarController) {
        this.view = sourceFileView;
        this.documentEventBus = documentEventBus;
        this.markerController = markerController;
        this.sourceFileHeaderController = sourceFileHeaderController;
        this.contentController = contentController;
        this.actionBarController = actionBarController;

        this.markerController.setSourceFileController(this);
        this.contentController.setSourceFileController(this);
        this.sourceFileHeaderController.setSourceFileController(this);
        this.actionBarController.setSourceFileController(this);

        registerListeners();
    }

    private void registerListeners() {
        contentController.getView().getScrollPanel().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                documentEventBus.fireEvent(new DocumentScrollEvent(documentController));
            }
        });
    }

    public OverlayWidget getTopVisibleAmenableWidget() {
        return contentController.getCurrentVisibleAmendableWidget();
    }

    public void setContent(String documentContent) {

        // clean up
        if (overlayWidgets != null && !overlayWidgets.isEmpty()) {
            for (final OverlayWidget overlayWidget : overlayWidgets) {
                overlayWidget.onDetach();
            }
        }
        contentController.setContent(documentContent);
    }

    public OverlayWidget overlay(final com.google.gwt.dom.client.Element element, final OverlayWidgetUIListener UIListener) {
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

    @Override
    public void walk(OverlayWidgetVisitor visitor) {
        for (final OverlayWidget root : overlayWidgets) {
            root.walk(visitor);
        }
    }

    public void walk(final OverlayWidget toVisit, final OverlayWidgetVisitor visitor) {
        toVisit.walk(visitor);
    }

    public void scrollTo(Widget widget) {
        contentController.scrollTo(widget);
    }

    @Override
    public void onClick(OverlayWidget sender) {
        //        printDetails(sender);
        documentEventBus.fireEvent(new AmendableWidgetSelectEvent(sender, documentController));
    }

    private void printDetails(final OverlayWidget overlayWidget) {
        final OverlayWidget previousNonIntroducedOverlayWidget = overlayWidget.getPreviousNonIntroducedOverlayWidget(false);
        System.out.println(">>>> " + (previousNonIntroducedOverlayWidget != null ? documentController.getLocator().getLocation(previousNonIntroducedOverlayWidget, "EN", false) : null));
        final OverlayWidget previousNonIntroducedOverlayWidget1 = overlayWidget.getPreviousNonIntroducedOverlayWidget(true);
        System.out.println(">>>> " + (previousNonIntroducedOverlayWidget1 != null ? documentController.getLocator().getLocation(previousNonIntroducedOverlayWidget1, "EN", false) : null));
        final OverlayWidget nextNonIntroducedOverlayWidget = overlayWidget.getNextNonIntroducedOverlayWidget(false);
        System.out.println(">>>> " + (nextNonIntroducedOverlayWidget != null ? documentController.getLocator().getLocation(nextNonIntroducedOverlayWidget, "EN", false) : null));
        final OverlayWidget nextNonIntroducedOverlayWidget1 = overlayWidget.getNextNonIntroducedOverlayWidget(true);
        System.out.println(">>>> " + (nextNonIntroducedOverlayWidget1 != null ? documentController.getLocator().getLocation(nextNonIntroducedOverlayWidget1, "EN", false) : null));
    }

    @Override
    public void onDblClick(OverlayWidget sender) {
        documentEventBus.fireEvent(new AmendmentContainerCreateEvent(sender, null, 0, AmendmentAction.MODIFICATION, documentController));
    }

    @Override
    public void onMouseOver(OverlayWidget sender) {
        // we do not allow nested amendments, so if this amendable widget is already introduced by an amendment, do not
        // allow the action bar to be shown.
        if (!sender.isIntroducedByAnAmendment()) {
            actionBarController.attach(sender);
            actionBarController.setLocation(documentController.getLocator().getLocation(sender, documentController.getDocument().getLanguageIso(), false));
        }
    }

    @Override
    public void onMouseOut(OverlayWidget sender) {
        // ignore
    }

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
        LOG.info("Overlaying took " + (System.currentTimeMillis() - start) + "ms.");
    }

    public void renumberAmendments() {
        final Counter counter = new Counter();
        walk(new OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                if (visited.isAmended()) {
                    for (final AmendmentController amendmentController : visited.getAmendmentControllers()) {
                        amendmentController.setOrder(counter.incrementAndGet());
                    }
                }
                return true;
            }
        });
    }

    public OverlayWidget getActiveOverlayWidget() {
        return activeOverlayWidget;
    }

    public void setActiveOverlayWidget(OverlayWidget activeOverlayWidget) {
        this.activeOverlayWidget = activeOverlayWidget;
    }

    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }

    public List<OverlayWidget> getOverlayWidgets() {
        return overlayWidgets;
    }

    public void clearAmendableWidgets() {
        this.overlayWidgets = new ArrayList<OverlayWidget>();
    }

    public MarkerController getMarkerController() {
        return markerController;
    }

    public SourceFileHeaderController getSourceFileHeaderController() {
        return sourceFileHeaderController;
    }

    public ContentController getContentController() {
        return contentController;
    }

    public ActionBarController getActionBarController() {
        return actionBarController;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }

    public SourceFileView getView() {
        return view;
    }
}
