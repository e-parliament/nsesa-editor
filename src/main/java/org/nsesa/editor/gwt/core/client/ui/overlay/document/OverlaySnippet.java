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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

/**
 * A class to store information about the snippet associated to a overlay widget. Basically a snippet contain
 * a template with place holders. The place holders will be replaced with the real values as soon as getContent method is
 * invoked.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 11:33
 */
public class OverlaySnippet {
    /** the snippet name**/
    private final String name;
    /** the snippet template**/
    private final String template;

    public OverlaySnippet(String name, String template) {
        this.name = name;
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }

    public String getContent(OverlaySnippetEvaluator snippetEvaluator) {
        return snippetEvaluator.evaluate(this);
    }
}
