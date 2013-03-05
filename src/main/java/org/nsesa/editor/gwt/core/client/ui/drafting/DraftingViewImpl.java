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
package org.nsesa.editor.gwt.core.client.ui.drafting;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayLocalizableResource;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.HashMap;
import java.util.Map;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Default implementation of {@link DraftingView} based on {@link UiBinder} GWT mechanism.
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 16/01/13 13:37
 */
@Scope(DOCUMENT)
public class DraftingViewImpl extends ResizeComposite implements DraftingView {

    interface MyUiBinder extends UiBinder<Widget, DraftingViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * Used to retrieve the localizable details about overlay widgets
     */
    private OverlayLocalizableResource overlayResource;

    /**
     * stores draft title
     */
    @UiField
    Label draftTitle;
    /**
     * holder for allowed children
     */
    @UiField
    VerticalPanel allowedPanel;

    /**
     * holder for mandatory children
     */
    @UiField
    VerticalPanel mandatoryPanel;

    /**
     * Create <code>DraftingViewImpl</code> object by setting the <code>overlayResource</code> and then
     * initialize the widgets
     */
    @Inject
    public DraftingViewImpl(OverlayLocalizableResource overlayResource) {
        this.overlayResource = overlayResource;

        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        allowedPanel.getElement().addClassName("drafting");
        mandatoryPanel.getElement().addClassName("drafting");
        if (!GWT.isScript())
            this.setTitle(this.getClass().getName());
    }

    /**
     * Clean up the content of holder panels
     */
    @Override
    public void clearAll() {
        allowedPanel.clear();
        mandatoryPanel.clear();
    }

    /**
     * Set the title of this view
     * @param title Title as String
     */
    @Override
    public void setDraftTitle(String title) {
        draftTitle.setText(title);
    }

    /**
     * Create a list of anchors/labels containing information about the allowed children.
     * @param allowedChildren A Map containing the allowed widget children with their occurrence
     * @param callback gets called when the user select a child from the interface
     */
    @Override
    public void refreshAllowedChildren(HashMap<OverlayWidget, Occurrence> allowedChildren, DraftingCallback callback) {
        for (final Map.Entry<OverlayWidget, Occurrence> child : allowedChildren.entrySet()) {
            // when selected text is empty do not add any click handler just display the tags
            IsWidget allowedChild, mandatoryChild = null;
            if (callback == null) {
                allowedChild = createLabelFrom(child.getKey());
                if (child.getValue().getMinOccurs() >= 1) {
                    mandatoryChild = createLabelFrom(child.getKey());
                }
            } else {
                allowedChild = createAnchorFrom(child.getKey(), callback);
                if (child.getValue().getMinOccurs() >= 1) {
                    mandatoryChild = createAnchorFrom(child.getKey(), callback);
                }
            }
            allowedPanel.add(allowedChild);
            if (mandatoryChild != null) {
                mandatoryPanel.add(mandatoryChild);
            }
        }
    }

    /**
     * Create a label based on the given overlay widget
     * @param overlayWidget The {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget} used
     *                      to create a label
     * @return The label result
     */
    private Label createLabelFrom(OverlayWidget overlayWidget) {
        Label label = new Label(overlayResource.getName(overlayWidget));
        label.setTitle(overlayResource.getDescription(overlayWidget));
        label.getElement().addClassName("drafting-" + overlayWidget.getType());
        return label;
    };

    /**
     * Create an anchor from the given overlay widget and call <code>callback</code> when click on it
     * @param overlayWidget The {@link OverlayWidget} used to create an anchor
     * @param callback gets called when click on anchor
     * @return The anchor result
     */
    private Anchor createAnchorFrom(final OverlayWidget overlayWidget, final DraftingCallback callback) {
        Anchor anchor = new Anchor(overlayResource.getName(overlayWidget));
        anchor.setTitle(overlayResource.getDescription(overlayWidget));
        anchor.getElement().addClassName("drafting-" + overlayWidget.getType());
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (callback != null)
                    callback.onChildrenSelect(overlayWidget);
            }
        });
        return anchor;
    }
}
