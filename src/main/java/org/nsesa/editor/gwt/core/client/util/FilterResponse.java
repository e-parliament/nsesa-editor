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
package org.nsesa.editor.gwt.core.client.util;

import java.util.List;

/**
 * A response occurred after a filter operation
 * User: groza
 * Date: 5/12/12
 * Time: 11:41
 */
public class FilterResponse<T> {
    private final int totalSize;
    private final Filter<T> filter;
    private final List<T> result;

    public FilterResponse(Filter<T> filter, int totalSize, List<T> result) {
        this.totalSize = totalSize;
        this.filter = filter;
        this.result = result;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public Filter<T> getFilter() {
        return filter;
    }

    public List<T> getResult() {
        return result;
    }
}
