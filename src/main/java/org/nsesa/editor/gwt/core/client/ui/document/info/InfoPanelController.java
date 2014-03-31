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
package org.nsesa.editor.gwt.core.client.ui.document.info;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.event.document.DocumentContentLoadedEvent;
import org.nsesa.editor.gwt.core.client.event.document.DocumentContentLoadedEventHandler;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Controller for the (meta) information tab under a {@link DocumentController}.
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class InfoPanelController {

    /**
     * View part of the component.
     */
    protected final InfoPanelView view;

    /**
     * The parent document controller.
     */
    protected DocumentController documentController;
    private HandlerRegistration documentContentLoadedEventHandlerRegistration;

    @Inject
    public InfoPanelController(InfoPanelView view) {
        this.view = view;
    }

    public void registerListeners() {
        documentContentLoadedEventHandlerRegistration = documentController.getDocumentEventBus().addHandler(DocumentContentLoadedEvent.TYPE, new DocumentContentLoadedEventHandler() {
            @Override
            public void onEvent(DocumentContentLoadedEvent event) {
                // extract the meta data
                List<OverlayWidget> roots = documentController.getSourceFileController().getOverlayWidgets();
                for (final OverlayWidget root : roots) {
                    OverlayWidget metadata = OverlayUtil.findSingle("meta", root);
                    if (metadata != null) {
                        metadata.walk(new OverlayWidgetWalker.DefaultOverlayWidgetVisitor() {
                            @Override
                            public boolean visit(OverlayWidget visited) {
                                return super.visit(visited);
                            }
                        });
                    }
                    view.getMainPanel().add(new Label(metadata.getInnerHTML()));
                }
            }
        });
    }

    public void removeListeners() {
        documentContentLoadedEventHandlerRegistration.removeHandler();
    }

    /**
     * Return the main view.
     *
     * @return the view
     */
    public InfoPanelView getView() {
        return view;
    }

    /**
     * Set the parent document controller.
     *
     * @param documentController the document controller
     */
    public void setDocumentController(DocumentController documentController) {
        this.documentController = documentController;
    }
}
