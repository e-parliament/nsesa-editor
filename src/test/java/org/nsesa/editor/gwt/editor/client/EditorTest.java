package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.junit.client.GWTTestCase;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Date: 24/06/12 17:49
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return Editor.class.getName();
    }

    @Test
    public void testNothing() {
        Assert.assertEquals(Editor.class.getName(), getModuleName());
    }
}
