package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Date: 24/06/12 17:49
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "org.nsesa.editor.gwt.editor.Editor";
    }

    public void testName() {
        assertEquals("org.nsesa.editor.gwt.editor.Editor", getModuleName());
    }
}