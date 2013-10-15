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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.*;
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

    private OverlayWidget toMove;

    private ContentController contentController;

    private Messages messages;
    private HandlerRegistration beforeHandlerRegistration;
    private HandlerRegistration afterHandlerRegistration;

    private Element beforePlaceHolderDiv = DOM.createDiv();
    private Element afterPlaceHolderDiv = DOM.createDiv();

    @Inject
    public BeforeAfterActionBarController(final ClientFactory clientFactory,
                                          final BeforeAfterActionBarView view,
                                          final Messages messages) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.messages = messages;
    }

    public void registerListeners() {
        beforeHandlerRegistration = this.view.getBeforeAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();
                BeforeAfterActionBarController.this.onClick(toMove, overlayWidget, true);
            }
        });

        afterHandlerRegistration = this.view.getAfterAnchor().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();
                BeforeAfterActionBarController.this.onClick(toMove, overlayWidget, false);
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        beforeHandlerRegistration.removeHandler();
        afterHandlerRegistration.removeHandler();
    }

    public void setOverlayWidget(final OverlayWidget overlayWidget) {
        boolean different = this.overlayWidget != overlayWidget;
        this.overlayWidget = overlayWidget;
        if (different) attach();
    }

    public void setOverlayWidgetToMove(OverlayWidget toMove) {
        this.toMove = toMove;
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

            adaptPlaceholders();
        }
    }

    protected void adaptPlaceholders() {
        // detach the old place holders

        if (beforePlaceHolderDiv != null && beforePlaceHolderDiv.hasParentElement()) {
            beforePlaceHolderDiv.removeFromParent();
        }
        if (afterPlaceHolderDiv != null && afterPlaceHolderDiv.hasParentElement()) {
            afterPlaceHolderDiv.removeFromParent();
        }

        beforePlaceHolderDiv = createPlaceHolder(true);
        afterPlaceHolderDiv = createPlaceHolder(false);

        DOM.insertBefore(overlayWidget.getParentOverlayWidget().asWidget().getElement(), beforePlaceHolderDiv, overlayWidget.asWidget().getElement());
        DOM.insertChild(overlayWidget.getParentOverlayWidget().asWidget().getElement(), afterPlaceHolderDiv, overlayWidget.getDomIndex() + 1);
    }

    protected Element createPlaceHolder(boolean before) {
        Element div = DOM.clone(toMove.asWidget().getElement(), true);
        DOM.sinkEvents(div, Event.MOUSEEVENTS | Event.BUTTON_LEFT);
        DOM.setEventListener(div, createEventListener(div, before));
        div.getStyle().setOpacity(0.2);
        return div;
    }

    protected EventListener createEventListener(final Element element, final boolean before) {
        return new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                event.preventDefault();
                event.stopPropagation();
                if (event.getTypeInt() == Event.ONMOUSEOVER) {
                    element.getStyle().setOpacity(1.0);
                    element.getStyle().setCursor(Style.Cursor.POINTER);
                } else if (event.getTypeInt() == Event.ONMOUSEOUT) {
                    element.getStyle().setOpacity(0.2);
                    element.getStyle().setCursor(Style.Cursor.AUTO);
                } else if (event.getTypeInt() == Event.ONCLICK) {
                    // build and fire event
                    onClick(toMove, overlayWidget, before);
                }
            }
        };
    }

    protected void onClick(OverlayWidget toMove, OverlayWidget selected, boolean before) {
        Window.alert("Sorry, not implemented yet :(");
    }
}
