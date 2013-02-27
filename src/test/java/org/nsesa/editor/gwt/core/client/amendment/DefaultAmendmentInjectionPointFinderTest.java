package org.nsesa.editor.gwt.core.client.amendment;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentViewImpl;
import org.nsesa.editor.gwt.core.client.ui.amendment.DefaultAmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

import java.util.List;

/**
 * Date: 26/02/13 11:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DefaultAmendmentInjectionPointFinderTest extends GwtTest {

    OverlayWidget root;
    OverlayWidget child1;
    OverlayWidget child2;
    OverlayWidget child3;

    AmendmentController amendmentController;
    AmendableWidgetReference reference = new AmendableWidgetReference();


    @Before
    public void setup() {
        AmendmentContainerDTO amendmentContainerDTO = new AmendmentContainerDTO();
        amendmentContainerDTO.setSourceReference(reference);
        amendmentController = new DefaultAmendmentController(new AmendmentViewImpl(null), new AmendmentViewImpl(null));
        amendmentController.setAmendment(amendmentContainerDTO);

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
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(amendmentController, root, null);
        Assert.assertEquals(injectionPoints.get(0), child2);
    }

    @Test
    public void testFindInjectionPointsXpath() throws Exception {
        reference.setPath("//root/typeA");
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(amendmentController, root, null);
        Assert.assertEquals(injectionPoints.get(0), child1);
    }
    @Test
    public void testFindInjectionPointsXpathMultipleChildren() throws Exception {
        reference.setPath("//root/typeB[1]");
        final List<OverlayWidget> injectionPoints = finder.findInjectionPoints(amendmentController, root, null);
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
