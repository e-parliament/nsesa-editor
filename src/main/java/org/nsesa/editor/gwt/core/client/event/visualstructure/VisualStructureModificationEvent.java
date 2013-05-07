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
package org.nsesa.editor.gwt.core.client.event.visualstructure;

import com.google.gwt.event.shared.GwtEvent;

import java.util.Map;

/**
 * An event occurred when the user wants to modify element attributes.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 15:24
 *
 */
public class VisualStructureModificationEvent extends GwtEvent<VisualStructureModificationEventHandler> {

    public static final Type<VisualStructureModificationEventHandler> TYPE = new Type<VisualStructureModificationEventHandler>();

    private Map<String, String> attributes;

    public VisualStructureModificationEvent(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Type<VisualStructureModificationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VisualStructureModificationEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * A map of attributes having the key attribute name and value the attribute value
     * @return A map of attributes having the key attribute name and value the attribute value
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }
}
