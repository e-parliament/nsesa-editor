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
package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;

import java.util.List;

/**
 * <code>FilterResponse</code> class encapsulate the response of filter request. It has reference to the
 * original filter and also to the result of the filter operation.
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 5/12/12 11:41
 */
public class FilterResponse<T extends OverlayWidgetAware> {
    private final int totalSize;
    private final Filter<T> filter;
    private final List<T> result;

    /**
     * Create <code>FilterResponse</code> instance with the given input
     * @param filter The original filter used to filter the data
     * @param totalSize The total size of data before applying pagination if case
     * @param result The result of the filter operation
     */
    public FilterResponse(Filter<T> filter, int totalSize, List<T> result) {
        this.totalSize = totalSize;
        this.filter = filter;
        this.result = result;
    }

    /**
     * Return the total size before applying pagination if case
     * @return <code>totalSize</code> as int
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Return the original filter request
     * @return <code>filter</code>
     */
    public Filter<T> getFilter() {
        return filter;
    }

    /**
     * Returns the result of the filter operation after applying pagination
     * @return List
     */
    public List<T> getResult() {
        return result;
    }
}
