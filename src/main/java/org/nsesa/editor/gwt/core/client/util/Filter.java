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

import java.util.Comparator;

/**
 * A class to keep the filter parameters
 * User: groza
 * Date: 3/12/12
 * Time: 13:49
 */
public class Filter<T> {

    private int start;
    private int size;
    private Comparator<T> comparator;
    private Selection<T> selection;

    public Filter(int start, int size, Comparator<T> comparator, Selection<T> selection) {
        this.start = start;
        this.size = size;
        this.selection = selection;
        this.comparator = comparator;

    }


    public int getStart() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public Selection<T> getSelection() {
        return selection;
    }
}
