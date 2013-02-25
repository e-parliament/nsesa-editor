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
package org.nsesa.editor.gwt.core.client.event.filter;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.util.Filter;

/**
 * An event triggered by the filter component
 * User: groza
 * Date: 29/11/12
 * Time: 10:42
 */
public class FilterRequestEvent extends GwtEvent<FilterRequestEventHandler> {
    public static final Type<FilterRequestEventHandler> TYPE = new Type<FilterRequestEventHandler>();
    private Filter filter;

    public FilterRequestEvent(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Type<FilterRequestEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterRequestEventHandler handler) {
        handler.onEvent(this);
    }

    public Filter getFilter() {
        return filter;
    }

}
