package org.nsesa.editor.gwt.core.client.util;

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

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;

/**
 * Date: 25/09/13 11:00
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DefaultIDGeneratorTest extends GwtTest {

    private DefaultIDGenerator idGenerator = new DefaultIDGenerator();

    final OverlayWidget overlayWidget1 = new OverlayWidgetImpl() {
        @Override
        public String getType() {
            return "myType";
        }

        @Override
        public String getNamespaceURI() {
            return "my-namespace";
        }
    };

    final OverlayWidget overlayWidget2 = new OverlayWidgetImpl() {
        @Override
        public String getType() {
            return "myType";
        }

        @Override
        public String getNamespaceURI() {
            return "my-namespace";
        }
    };

    final OverlayWidget overlayWidget3 = new OverlayWidgetImpl() {
        @Override
        public String getType() {
            return "myType";
        }

        @Override
        public String getNamespaceURI() {
            return "another-namespace";
        }
    };

    final OverlayWidget overlayWidget4 = new OverlayWidgetImpl() {
        @Override
        public String getType() {
            return "anotherType";
        }

        @Override
        public String getNamespaceURI() {
            return "another-namespace";
        }
    };

    @Test
    public void testGenerateGlobalId() throws Exception {
        final String globalID1 = idGenerator.generateGlobalId();
        Assert.assertNotNull(globalID1);
        Assert.assertTrue(globalID1.startsWith("global-"));
        final String globalID2 = idGenerator.generateGlobalId();
        Assert.assertNotNull(globalID2);
        Assert.assertTrue(globalID2.startsWith("global-"));
        Assert.assertNotSame(globalID1, globalID2);
    }

    @Test
    public void testGenerateLocalId() throws Exception {

        final String id1 = idGenerator.generateLocalId(overlayWidget1);
        Assert.assertNotNull(id1);
        Assert.assertEquals(id1, "local-mytype-1");

        final String id2 = idGenerator.generateLocalId(overlayWidget2);
        Assert.assertNotNull(id2);
        Assert.assertEquals(id2, "local-mytype-2");

        final String id3 = idGenerator.generateLocalId(overlayWidget3);
        Assert.assertNotNull(id3);
        Assert.assertEquals(id3, "local-mytype-3");

        final String id4 = idGenerator.generateLocalId(overlayWidget4);
        Assert.assertNotNull(id4);
        Assert.assertEquals(id4, "local-anothertype-1");
    }

    @Test
    public void testGenerateCurrentId() throws Exception {
        final String id1 = idGenerator.generateCurrentId(overlayWidget1);
        Assert.assertNotNull(id1);
        Assert.assertEquals(id1, "current-mytype-1");

        final String id2 = idGenerator.generateCurrentId(overlayWidget2);
        Assert.assertNotNull(id2);
        Assert.assertEquals(id2, "current-mytype-2");

        final String id3 = idGenerator.generateCurrentId(overlayWidget3);
        Assert.assertNotNull(id3);
        Assert.assertEquals(id3, "current-mytype-3");

        final String id4 = idGenerator.generateCurrentId(overlayWidget4);
        Assert.assertNotNull(id4);
        Assert.assertEquals(id4, "current-anothertype-1");
    }
}

