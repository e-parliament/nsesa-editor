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
package org.nsesa.editor.gwt.core.client.event.visualstructure;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;

/**
 * An event occurred in editor area when drafting <code>OverlayWidget</code> by changing the cursor location or
 * selecting a text inside of the editor
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 9:59
 */
public class VisualStructureSelectionChangedEvent extends GwtEvent<VisualStructureSelectionChangedEventHandler> {
    public static final Type<VisualStructureSelectionChangedEventHandler> TYPE = new Type<VisualStructureSelectionChangedEventHandler>();

    private Element parentElement;
    private boolean moreTagsSelected;
    private String selectedText;

    /**
     * Create <code>VisualStructureSelectionChangedEvent</code> with the given input data
     * @param parentElement The parent element whereas the selection occur
     * @param moreTagsSelected True when the browser selection contains more elements
     * @param selectedText The selected text
     */
    public VisualStructureSelectionChangedEvent(Element parentElement, boolean moreTagsSelected, String selectedText) {
        this.parentElement = parentElement;
        this.moreTagsSelected = moreTagsSelected;
        this.selectedText = selectedText;
    }

    @Override
    public Type<VisualStructureSelectionChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VisualStructureSelectionChangedEventHandler handlerVisualStructure) {
        handlerVisualStructure.onEvent(this);
    }

    /**
     * Return the parent element whereas the selection occur
     * @return <code>parentElement</code>
     */
    public Element getParentElement() {
        return parentElement;
    }

    /**
     * True when the browser selection contains more elements
     * @return Whether or not the browser selection contain more elements
     */
    public boolean isMoreTagsSelected() {
        return moreTagsSelected;
    }

    /**
     * Return the selected text in the browser selection
     * @return <code>selectedText</code>
     */
    public String getSelectedText() {
        return selectedText;
    }
}
