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
 * An event initiated as a response to a filter request
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/11/12 10:42
 */
public class FilterResponseEvent extends GwtEvent<FilterResponseEventHandler> {
    public static final GwtEvent.Type<FilterResponseEventHandler> TYPE = new GwtEvent.Type<FilterResponseEventHandler>();
    private int totalSize;
    private Filter filter;

    /**
     * Create a <code>FilterResponseEvent</code> as an answer to a filter request event
     * @param totalSize The total size
     * @param filter the original filter request
     */
    public FilterResponseEvent(int totalSize, Filter filter) {
        this.totalSize = totalSize;
        this.filter = filter;
    }

    @Override
    public GwtEvent.Type<FilterResponseEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterResponseEventHandler handler) {
        handler.onEvent(this);
    }

    /**
     * Return the total size
     * @return <code>totalSize</code>
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Return the original filter request
     * @return <code>filter</code>
     */
    public Filter getFilter() {
        return filter;
    }
}
