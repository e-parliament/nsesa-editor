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

import java.util.Comparator;

/**
 * <code>Filter</code> class offers filtered functionality and it does not generate content by itself.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 3/12/12 13:49
 */
public class Filter<T extends OverlayWidgetAware> {

    private int start;
    private int size;
    private Comparator<T> comparator;
    private Selection<T> selection;

    /**
     * Create <code>Filter</code> object with the given parameters
     * @param start The filter page start as int
     * @param size The size of the page filter as int
     * @param comparator The comparator used when filter the results
     * @param selection The filtering constraint implementation
     */
    public Filter(int start, int size, Comparator<T> comparator, Selection<T> selection) {
        this.start = start;
        this.size = size;
        this.selection = selection;
        this.comparator = comparator;

    }

    /**
     * Returns the filter start
     * @return the filter start
     */
    public int getStart() {
        return start;
    }

    /**
     * Returns the filter page size
     * @return the filter page size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the filter start
     * @param start as int
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Returns the comparator to be used when filter the results
     * @return Comparator
     */
    public Comparator<T> getComparator() {
        return comparator;
    }

    /**
     * Returns the constraints to be used when filter the results
     * @return
     */
    public Selection<T> getSelection() {
        return selection;
    }
}
