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
package org.nsesa.editor.gwt.amendment.client.amendment;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayWidgetInjectionStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;

import java.util.List;

/**
 * Date: 26/02/13 11:43
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DefaultAmendmentInjectionPointFinderTest extends GwtTest {

    OverlayWidget root;
    OverlayWidget child1;
    OverlayWidget child2;
    OverlayWidget child3;

    AmendableWidgetReference reference = new AmendableWidgetReference();


    @Before
    public void setup() {
        AmendmentContainerDTO amendmentContainerDTO = new AmendmentContainerDTO();
        amendmentContainerDTO.setSourceReference(reference);

        root = new OverlayWidgetImpl();
        root.setType("root");
        child1 = new OverlayWidgetImpl();
        child1.setType("typeA");
        child2 = new OverlayWidgetImpl();
        child2.setType("typeB");
        child3 = new OverlayWidgetImpl();
        child3.setType("typeB");

        root.addOverlayWidget(child1);
        root.addOverlayWidget(child2);
        root.addOverlayWidget(child3);
    }

    AmendmentInjectionPointFinder finder = new DefaultAmendmentInjectionPointFinder(new DefaultOverlayWidgetInjectionStrategy());

    @Test
    public void testFindInjectionPointsViaID() throws Exception {
        child2.setId("foo");
        reference.setPath("#foo");
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(reference.getPath(), root, null);
        Assert.assertEquals(injectionPoints.get(0), child2);
    }

    @Test
    public void testFindInjectionPointsXpath() throws Exception {
        reference.setPath("//root/typeA");
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(reference.getPath(), root, null);
        Assert.assertEquals(injectionPoints.get(0), child1);
    }

    @Test
    public void testFindInjectionPointsXpathMultipleChildren() throws Exception {
        reference.setPath("//root/typeB[1]");
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(reference.getPath(), root, null);
        Assert.assertEquals(injectionPoints.get(0), child3);
    }

    @Test
    public void testGetInjectionPointPath() throws Exception {
        Assert.assertEquals("//root[0]", finder.getInjectionPoint(null, null, root).getPath());
        Assert.assertEquals("//root[0]/typeA[0]", finder.getInjectionPoint(null, null, child1).getPath());
        Assert.assertEquals("//root[0]/typeB[0]", finder.getInjectionPoint(null, null, child2).getPath());
        Assert.assertEquals("//root[0]/typeB[1]", finder.getInjectionPoint(null, null, child3).getPath());
        child2.setId("foo");
        Assert.assertEquals("#foo", finder.getInjectionPoint(null, null, child2).getPath());
        child2.setId(null); // cleanup
    }

    @Test
    public void testGetInjectionPointType() throws Exception {
        Assert.assertEquals(root.getType(), finder.getInjectionPoint(null, null, root).getType());
        Assert.assertEquals(child1.getType(), finder.getInjectionPoint(null, null, child1).getType());
        Assert.assertEquals(child2.getType(), finder.getInjectionPoint(null, null, child2).getType());
        Assert.assertEquals(child3.getType(), finder.getInjectionPoint(null, null, child3).getType());
    }

    @Test
    public void testGetInjectionPointNewAfter() throws Exception {
        AmendableWidgetReference injectionPoint = finder.getInjectionPoint(null, null, child2);
        Assert.assertTrue(!injectionPoint.isCreation());
        Assert.assertTrue(!injectionPoint.isSibling());
        child2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        root.removeOverlayWidget(child2);
        injectionPoint = finder.getInjectionPoint(root, child1, child2);
        Assert.assertTrue(injectionPoint.isCreation());
        Assert.assertTrue(injectionPoint.isSibling());
        Assert.assertEquals(child2.getType(), injectionPoint.getType());
        Assert.assertEquals("Make sure the referenced widget's path is used", "//root[0]/typeA[0]", injectionPoint.getPath());
        Assert.assertEquals(1, injectionPoint.getOffset());

        root.addOverlayWidget(child2);


        child3.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        root.removeOverlayWidget(child3);
        injectionPoint = finder.getInjectionPoint(root, child1, child3);
        Assert.assertTrue(injectionPoint.isCreation());
        Assert.assertTrue(injectionPoint.isSibling());
        Assert.assertEquals(child3.getType(), injectionPoint.getType());
        Assert.assertEquals("Make sure the referenced widget's path is used", "//root[0]/typeA[0]", injectionPoint.getPath());
        Assert.assertEquals(2, injectionPoint.getOffset());

        // cleanup
        child2.setOrigin(null);
        child3.setOrigin(null);
        root.addOverlayWidget(child3);
    }

    @Test
    public void testGetInjectionPointNewBefore() throws Exception {

        OverlayWidget childMinus1 = new OverlayWidgetImpl();
        childMinus1.setType("typeMinus");
        childMinus1.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        AmendableWidgetReference proposedInjectionPoint = finder.getInjectionPoint(root, null, childMinus1);
        Assert.assertEquals("Make sure the root widget's path is used", "//root[0]", proposedInjectionPoint.getPath());
        Assert.assertEquals(3, proposedInjectionPoint.getOffset());
        root.addOverlayWidget(childMinus1, 0);
        AmendableWidgetReference actualInjectionPoint = finder.getInjectionPoint(root, child1, childMinus1);
        Assert.assertEquals("Make sure the referenced widget's path is used", "//root[0]/typeA[0]", actualInjectionPoint.getPath());
        Assert.assertEquals(-1, actualInjectionPoint.getOffset());
        root.removeOverlayWidget(childMinus1);
    }
}
