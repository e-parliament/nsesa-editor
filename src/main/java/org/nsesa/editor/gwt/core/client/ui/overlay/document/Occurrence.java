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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

/**
 * <code>Occurrence</code> class stores xsd occurrence indicators (minOccurs, maxOccurs) about a "child_name" element
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/01/13 11:52
 */
public final class Occurrence {
    public static Integer UNBOUNDED = -1;

    private Integer minOccurs;
    private Integer maxOccurs;

    /**
     * Create <code>Occurrence</code> instance with the given input data
     * @param minOccurs The minimum number of times an element can occur
     * @param maxOccurs The maximum number of times an element can occur
     */
    public Occurrence(Integer minOccurs, Integer maxOccurs) {
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    /**
     * Returns minimum number of times an element can occur
     * @return <code>minOccurs</code> as Integer
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * Returns maximum number of times an element can occur
     * @return <code>maxOccurs</code> as Integer
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * True when an element appear an unlimited number of times
     * @return Whether is unbounded or not
     */
    public boolean isUnbounded() {
        return maxOccurs != null && maxOccurs.intValue() == UNBOUNDED.intValue();
    }

    @Override
    public String toString() {
        return "[" + minOccurs + ", " + (isUnbounded() ? "*" : maxOccurs) + "]";
    }
}
