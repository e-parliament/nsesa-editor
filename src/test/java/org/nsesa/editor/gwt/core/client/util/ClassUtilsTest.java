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
package org.nsesa.editor.gwt.core.client.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Date: 20/11/12 11:24
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
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
