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

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of {@link OverlaySnippetEvaluator} interface. It will
 * replace the place holders in the {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlaySnippet#getTemplate()}
 * with their corresponding registered {@link OverlaySnippetEvaluator.Evaluator} evaluators.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 12:33
 */
public class DefaultOverlaySnippetEvaluator implements OverlaySnippetEvaluator {

    private static RegExp PLACE_HOLDER_REG = RegExp.compile(PLACE_HOLDER_PATTERN, "g");

    private Map<String, Evaluator> evaluators = new LinkedHashMap<String, Evaluator>();

    /**
     *  Generate the content by going through the template, finding out all the place holders
     *  and replace them with their corresponding evaluators, if exist
     * @param overlaySnippet {@link OverlaySnippet} processed
     * @return String
     */
    @Override
    public String evaluate(OverlaySnippet overlaySnippet) {
        String templateContent = overlaySnippet.getTemplate();
        //find all place holders
        List<String> placeHolders = getPlaceHolders(templateContent);
        for (String placeHolder : placeHolders) {
            Evaluator evaluator = evaluators.get(placeHolder);
            if (evaluator != null) {
                String replacement = evaluator.evaluate();
                if (replacement != null) {
                    templateContent = templateContent.replace(placeHolder, replacement);
                }
            }
        }

        return templateContent;
    }

    @Override
    public void removeEvaluator(Evaluator evaluator) {
        evaluators.remove(evaluator.getPlaceHolder());
    }

    /**
     * Registers new evaluator
     * @param evaluator The new registered evaluator
     */
    @Override
    public void addEvaluator(Evaluator evaluator) {
        evaluators.put(evaluator.getPlaceHolder(), evaluator);
    }

    /**
     * Returns the place holders from template
     * @param template the template to be checked
     * @return A List of place holders
     */
    private List<String> getPlaceHolders(String template) {
        List<String> matches = new ArrayList<String>();
        for(MatchResult matchResult = PLACE_HOLDER_REG.exec(template);
            matchResult != null; matchResult = PLACE_HOLDER_REG.exec(template)) {
            matches.add(matchResult.getGroup(0));
        }
        return matches;
    }
}
