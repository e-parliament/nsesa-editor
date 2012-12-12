package org.nsesa.editor.gwt.core.shared;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDiffResult {

    private String original;
    private String amendment;

    public SimpleDiffResult() {
    }

    public SimpleDiffResult(String original, String amendment) {
        super();
        this.original = original;
        this.amendment = amendment;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String deletion) {
        this.original = deletion;
    }

    public String getAmendment() {
        return amendment;
    }

    public void setAmendment(String amendment) {
        this.amendment = amendment;
    }

}
