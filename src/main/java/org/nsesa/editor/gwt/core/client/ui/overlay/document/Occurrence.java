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

import java.io.Serializable;

/**
 * Keeps occurrence information (minOccurs, maxOccurs) about an element
 * when it is used as an allowed child of another element
 * User: groza
 * Date: 29/01/13
 * Time: 11:52
 */
public class Occurrence implements Serializable {
    public static Integer UNBOUNDED = new Integer(-1);

    private Integer minOccurs;
    private Integer maxOccurs;

    public Occurrence(Integer minOccurs, Integer maxOccurs) {
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public Integer getMinOccurs() {
        return minOccurs;
    }

    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    public boolean isUnbounded() {
        return maxOccurs != null && maxOccurs.intValue() == UNBOUNDED.intValue();
    }

}
