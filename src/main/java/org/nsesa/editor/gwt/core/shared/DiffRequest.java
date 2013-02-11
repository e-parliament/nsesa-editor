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
package org.nsesa.editor.gwt.core.shared;

import java.io.Serializable;

public class DiffRequest implements Serializable {

    private String original;
    private String amendment;

    private DiffMethod diffMethod;

    private String diffStyle = "epStyle"; // or msStyle

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

    public DiffRequest(String original, String amendment, DiffMethod diffMethod, String diffStyle) {
        this.original = original;
        this.amendment = amendment;
        this.diffMethod = diffMethod;
        this.diffStyle = diffStyle;
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

    public String getDiffStyle() {
        return diffStyle;
    }

    public void setDiffStyle(String diffStyle) {
        this.diffStyle = diffStyle;
    }
}
