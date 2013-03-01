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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * A test class for <code>TextUtils</code> class
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 23/11/12 11:01
 */
public class TextUtilsTest {
    @Test
    public void testEscapeXML() {
        final String toTest = "<>&'\"test<>&'\"";
        String result = TextUtils.escapeXML(toTest);
        assertTrue(result.equalsIgnoreCase("&lt;&gt;&amp;&apos;&quot;test&lt;&gt;&amp;&apos;&quot;"));


    }
}
