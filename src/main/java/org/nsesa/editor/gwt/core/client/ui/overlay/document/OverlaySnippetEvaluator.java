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

import com.google.inject.ImplementedBy;

/**
 * An overlay snippet resolver is responsible for replacing the place holders which might exist into the
 * template of {@link OverlaySnippet} with values determined from the current context
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 11:48
 */
@ImplementedBy(DefaultOverlaySnippetEvaluator.class)
public interface OverlaySnippetEvaluator {
    /** the place holder pattern **/
    final String PLACE_HOLDER_PATTERN = "\\$\\{.+?\\}";
    /**
     * Generate the content for the given {@link OverlaySnippet} by replacing the place holders from template
     * with real values
     * @param overlaySnippet {@link OverlaySnippet} processed
     * @return A String
     */
    abstract String evaluate(OverlaySnippet overlaySnippet);

    /**
     * Remove the evaluator
     * @param evaluator The evaluator to be removed
     */
    abstract void removeEvaluator(Evaluator evaluator);

    /**
     * Add an evaluator
     * @param evaluator
     */
    abstract void addEvaluator(Evaluator evaluator);

    /**
     * A {@link Evaluator} is responsible to evaluate the place holders by returning the real values based on
     * the current context
     */
    public static interface Evaluator {
        abstract String getPlaceHolder();
        abstract String evaluate();
    }
}
