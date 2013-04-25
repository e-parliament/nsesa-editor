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
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.AmendmentViewImpl;
import org.nsesa.editor.gwt.amendment.client.ui.amendment.DefaultAmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

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

    AmendmentInjectionPointFinder finder = new DefaultAmendmentInjectionPointFinder();

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
    public void testGetInjectionPoint() throws Exception {
        Assert.assertEquals("//root[0]", finder.getInjectionPoint(root));
        Assert.assertEquals("//root[0]/typeA[0]", finder.getInjectionPoint(child1));
        Assert.assertEquals("//root[0]/typeB[0]", finder.getInjectionPoint(child2));
        Assert.assertEquals("//root[0]/typeB[1]", finder.getInjectionPoint(child3));
        child2.setId("foo");
        Assert.assertEquals("#foo", finder.getInjectionPoint(child2));
    }


}
