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
package org.nsesa.editor.gwt.amendment.client.ui.document.marker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.amendment.client.amendment.AmendmentManager;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerView;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.logging.Logger;

/**
 * Date: 23/04/13 14:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentMarkerController extends MarkerController {

    private static final Logger LOG = Logger.getLogger(AmendmentMarkerController.class.getName());

    private AmendmentManager amendmentManager;

    @Inject
    public AmendmentMarkerController(final DocumentEventBus documentEventBus,
                                     final MarkerView view, final AmendmentManager amendmentManager) {
        super(documentEventBus, view);
        this.amendmentManager = amendmentManager;
    }

    @Override
    protected void onTimerRun() {
        if (sourceFileController != null) {
            view.clearMarkers();
            final ScrollPanel scrollPanel = sourceFileController.getContentController().getView().getScrollPanel();
            final int documentHeight = scrollPanel.getMaximumVerticalScrollPosition();
            if (amendmentManager != null) {
                for (final AmendmentController amendmentController : amendmentManager.getAmendmentControllers()) {
                    if (amendmentController.getDocumentController() == sourceFileController.getDocumentController() && amendmentController.getView().asWidget().isAttached()) {
                        final int amendmentTop = amendmentController.getView().asWidget().getAbsoluteTop() - scrollPanel.asWidget().getAbsoluteTop() + scrollPanel.getVerticalScrollPosition();
                        final double division = (double) amendmentTop / (double) (documentHeight);
                        LOG.finest("Amendment is: " + amendmentTop + ", and division is at " + division);
                        final FocusWidget focusWidget = view.addMarker(division, colorCodes.get(amendmentController.getModel().getAmendmentContainerStatus()));
                        focusWidget.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                // TODO: this is a very poor solution to find a amendable widget to scroll to ...
                                if (!amendmentController.getAmendedOverlayWidget().asWidget().isVisible()) {
                                    final OverlayWidget amendedOverlayWidget = amendmentController.getAmendedOverlayWidget();
                                    if (amendedOverlayWidget != null) {
                                        amendedOverlayWidget.getOverlayElement().getPreviousSiblingElement();

                                        OverlayWidget previousNonIntroducedOverlayWidget = amendedOverlayWidget.getPreviousNonIntroducedOverlayWidget(false);
                                        while (previousNonIntroducedOverlayWidget != null && !previousNonIntroducedOverlayWidget.asWidget().isVisible()) {
                                            previousNonIntroducedOverlayWidget = previousNonIntroducedOverlayWidget.getPreviousNonIntroducedOverlayWidget(false);
                                        }
                                        if (previousNonIntroducedOverlayWidget != null)
                                            sourceFileController.scrollTo(previousNonIntroducedOverlayWidget.asWidget());
                                        else {
                                            sourceFileController.scrollTo(amendedOverlayWidget.getParentOverlayWidget().asWidget());
                                        }
                                    }
                                } else {
                                    sourceFileController.scrollTo(amendmentController.getView().asWidget());
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
