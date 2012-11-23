package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * A test class for TextUtils class
 * User: groza
 * Date: 23/11/12
 * Time: 11:01
 */
public class TextUtilsTest {
    @Test
    public void testEscapeXML() {
        final String toTest = "<>&'\"test<>&'\"";
        String result = TextUtils.escapeXML(toTest);
        assertTrue(result.equalsIgnoreCase("&lt;&gt;&amp;&apos;&quot;test&lt;&gt;&amp;&apos;&quot;"));


    }
}
