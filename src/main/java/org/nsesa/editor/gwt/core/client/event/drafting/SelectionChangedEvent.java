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
package org.nsesa.editor.gwt.core.client.event.drafting;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;

/**
 * An event occurred on drafting an amendment by changing the cursor location or selecting a text
 * inside of an amendment
 * User: groza
 * Date: 17/01/13
 * Time: 9:59
 */
public class SelectionChangedEvent extends GwtEvent<SelectionChangedEventHandler> {
    public static final Type<SelectionChangedEventHandler> TYPE = new Type<SelectionChangedEventHandler>();

    private Element parentElement;
    private boolean moreTagsSelected;
    private String selectedText;

    public SelectionChangedEvent(Element parentElement, boolean moreTagsSelected, String selectedText) {
        this.parentElement = parentElement;
        this.moreTagsSelected = moreTagsSelected;
        this.selectedText = selectedText;
    }

    @Override
    public Type<SelectionChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectionChangedEventHandler handler) {
        handler.onEvent(this);
    }

    public Element getParentElement() {
        return parentElement;
    }

    public boolean isMoreTagsSelected() {
        return moreTagsSelected;
    }

    public String getSelectedText() {
        return selectedText;
    }
}
