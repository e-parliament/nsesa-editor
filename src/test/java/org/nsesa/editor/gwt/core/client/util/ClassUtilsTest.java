package org.nsesa.editor.gwt.core.client.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Date: 20/11/12 11:24
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ClassUtilsTest {
    @Test
    public void testIsAssignableFrom() throws Exception {
        Assert.assertTrue(ClassUtils.isAssignableFrom(Object.class, String.class));
    }

    @Test
    public void testIsAssignableFromInverse() throws Exception {
        Assert.assertFalse(ClassUtils.isAssignableFrom(String.class, Object.class));
    }

    @Test
    public void testIsAssignableFromEqual() throws Exception {
        Assert.assertTrue(ClassUtils.isAssignableFrom(String.class, String.class));
    }

    @Test
    public void testIsNotAssignableFrom() throws Exception {
        Assert.assertFalse(ClassUtils.isAssignableFrom(String.class, Object.class));
        Assert.assertFalse(ClassUtils.isAssignableFrom(Integer.class, String.class));
    }
}
