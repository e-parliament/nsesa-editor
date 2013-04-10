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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test for {@link OverlaySnippetEvaluator}
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 15:25
 */
public class DefaultOverlaySnippetEvaluatorTest {

    @Test
    public void testEvaluatePlaceHolder() {
        OverlaySnippetEvaluator snippetEvaluator = new DefaultOverlaySnippetEvaluator();
        snippetEvaluator.addEvaluator(new OverlaySnippetEvaluator.Evaluator() {
            @Override
            public String getPlaceHolder() {
                return "${widget.num}";
            }

            @Override
            public String evaluate() {
                return "next_num";
            }
        });
        snippetEvaluator.addEvaluator(new OverlaySnippetEvaluator.Evaluator() {
            @Override
            public String getPlaceHolder() {
                return "${widget.p}";
            }

            @Override
            public String evaluate() {
                return "next_p";
            }
        });
        String evaluate = snippetEvaluator.evaluate(new OverlaySnippet("recital", "<span class=\"widget num\" ns=\"http://www.akomantoso.org/2.0\" type=\"num\">${widget.num}</span> <span class=\"widget p\" ns=\"http://www.akomantoso.org/2.0\" type=\"p\">type ${widget.p} me ${widget.num} ${widget.p}</p>"));
        final String expected = "<span class=\"widget num\" ns=\"http://www.akomantoso.org/2.0\" type=\"num\">next_num</span> <span class=\"widget p\" ns=\"http://www.akomantoso.org/2.0\" type=\"p\">type next_p me next_num next_p</p>";
        assertEquals(evaluate, expected);

    }
}
