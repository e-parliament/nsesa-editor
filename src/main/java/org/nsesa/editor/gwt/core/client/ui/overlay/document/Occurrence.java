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
