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
package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Class representing a diff on 2 Strings with a given {@link DiffMethod}.
 */
public class DiffResult implements IsSerializable, Serializable {

    /**
     * The original string.
     */
    private String original;

    /**
     * The new, modified string to be compared to the original string.
     */
    private String amendment;

    /**
     * The diffing method (word or character based).
     */
    private DiffMethod diffMethod;

    public DiffResult() {
    }

    public DiffResult(String original, String amendment) {
        this.original = original;
        this.amendment = amendment;
        this.diffMethod = DiffMethod.WORD;
    }

    public DiffResult(String original, String amendment, DiffMethod diffMethod) {
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
