package org.nsesa.editor.gwt.core.shared;

import java.io.Serializable;

public class DiffRequest implements Serializable {

    private String original;
    private String amendment;

    private DiffMethod diffMethod;

    public DiffRequest() {
    }

    public DiffRequest(String original, String amendment) {
        this.original = original;
        this.amendment = amendment;
        this.diffMethod = DiffMethod.WORD;
    }

    public DiffRequest(String original, String amendment, DiffMethod diffMethod) {
        this.original = original;
        this.amendment = amendment;
        this.diffMethod = diffMethod;
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

    public DiffMethod getDiffMethod() {
        return diffMethod;
    }

    public void setDiffMethod(DiffMethod diffMethod) {
        this.diffMethod = diffMethod;
    }
}
