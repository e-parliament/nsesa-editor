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

/**
 * A <code>Selection</code> is an object which determines true or false for a given input
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 28/11/12 10:28
 */
public interface Selection<T extends OverlayWidgetAware> {

    /**
     * Check whether or not the input is "selected"
     * @param t
     * @return True if <code>t</code> object is "selected"
     */
    boolean select(T t);

    /**
     * Special selection to return false for any given input
     * @param <T>
     */
    static class NoneSelection<T extends OverlayWidgetAware> implements Selection<T> {
        /**
         * Return false all the time
         * @param o
         * @return false
         */
        @Override
        public boolean select(T o) {
            return false;
        }
    }

    /**
     * Special selection to return true for any given input
     * @param <T>
     */
    static class AllSelection<T extends OverlayWidgetAware> implements Selection<T> {
        /**
         * Return True all the time
         * @param o
         * @return True
         */
        @Override
        public boolean select(T o) {
            return true;
        }
    }

}
