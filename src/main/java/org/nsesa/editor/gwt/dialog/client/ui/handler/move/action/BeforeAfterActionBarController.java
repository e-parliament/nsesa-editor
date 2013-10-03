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
package org.nsesa.editor.gwt.dialog.client.ui.handler.move.action;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.dialog.client.ui.handler.move.action.resources.Messages;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Dialog controller to handle the creation and editing of a movement amendments (amendments suggesting the move of
 * pre-existing a (complex) structure from the document).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(DIALOG)
public class BeforeAfterActionBarController {

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The associated view.
     */
    protected final BeforeAfterActionBarView view;

    private OverlayWidget overlayWidget;

    private ContentController contentController;

    private Messages messages;

    @Inject
    public BeforeAfterActionBarController(final ClientFactory clientFactory,
                                          final BeforeAfterActionBarView view,
                                          final Messages messages) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.messages = messages;
    }

    public void registerListeners() {

    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {

    }

    public void setOverlayWidget(final OverlayWidget overlayWidget) {
        boolean different = this.overlayWidget != overlayWidget;
        this.overlayWidget = overlayWidget;
        if (different) attach();
    }

    public void setContentController(ContentController contentController) {
        this.contentController = contentController;
    }

    public BeforeAfterActionBarView getView() {
        return view;
    }

    protected void attach() {
        // attach to the element before/after
        adaptPosition(contentController.getView().getContentPanel());
        if (overlayWidget != null) {
            String desc = overlayWidget.getType();
            if (overlayWidget.getUnformattedIndex() != null) {
                desc += " " + overlayWidget.getUnformattedIndex();
            }
            view.getBeforeAnchor().setText(messages.actionInsertBefore(desc));
            view.getAfterAnchor().setText(messages.actionInsertAfter(desc));
        }
    }

    /**
     * Adapts the position of this 'hoovering' toolbar to be placed just above the {@link OverlayWidget}.
     */
    public void adaptPosition(final Widget container) {
        if (overlayWidget != null && overlayWidget.getParentOverlayWidget() != null) {

            if (getView().asWidget().getParent() != null) getView().asWidget().removeFromParent();

            DOM.insertBefore(overlayWidget.getParentOverlayWidget().asWidget().getElement(), getView().asWidget().getElement(), overlayWidget.asWidget().getElement());

            if (!view.asWidget().isAttached()) {
                view.attach();
            }

            getView().asWidget().getElement().getStyle().setPosition(Style.Position.STATIC);

            view.asWidget().setVisible(true);
        }

    }
}
