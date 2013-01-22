package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentView;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentViewImpl;
import org.nsesa.editor.gwt.core.client.ui.amendment.DefaultAmendmentController;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 18/01/13 14:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class AmendableWidgetImplTest extends GwtTest {

    @Test
    public void testAddAmendableWidget() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        Assert.assertTrue(amendableWidget.getChildAmendableWidgets().contains(child));
    }

    @Test
    public void testAddAmendableWidgetWithListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[0] = true;
                return false;
            }

            @Override
            public void afterAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[1] = true;
            }
        });
        final AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
    }

    @Test
    public void testAddAmendableWidgetWithVetoListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[0] = true;
                return true;
            }

            @Override
            public void afterAmendableWidgetAdded(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[1] = true;
            }
        });
        final AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertFalse("Make sure the child is not added.", amendableWidget.getChildAmendableWidgets().contains(child));
    }

    @Test
    public void testRemoveAmendableWidget() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        Assert.assertTrue(amendableWidget.getChildAmendableWidgets().contains(child));
        amendableWidget.removeAmendableWidget(child);
        Assert.assertFalse(amendableWidget.getChildAmendableWidgets().contains(child));
    }

    @Test
    public void testRemoveAmendableWidgetWithListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[0] = true;
                return false;
            }

            @Override
            public void afterAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[1] = true;
            }
        });
        final AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        amendableWidget.removeAmendableWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
    }

    @Test
    public void testRemoveAmendableWidgetWithVetoListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[0] = true;
                return true;
            }

            @Override
            public void afterAmendableWidgetRemoved(AmendableWidget amendableWidget, AmendableWidget child) {
                hits[1] = true;
            }
        });
        final AmendableWidget child = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child);
        amendableWidget.removeAmendableWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertTrue("Make sure the child is not removed.", amendableWidget.getChildAmendableWidgets().contains(child));
    }

    @Test
    public void testAddAmendmentController() throws Exception {
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        Assert.assertTrue(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testAddAmendmentControllerWithListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl() {
            {
                // we need an amendment holder element to attach the amendment to ...
                // TODO get rid of this holder element - no need for it
                amendmentHolderElement = new HTMLPanel("");
            }
        };
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public void afterAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[1] = true;
            }

            @Override
            public boolean beforeAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[0] = true;
                return false;
            }
        });
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
        Assert.assertTrue(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testAddAmendmentControllerWithVetoListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public void afterAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[1] = true;
            }

            @Override
            public boolean beforeAmendmentControllerAdded(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[0] = true;
                return true;
            }
        });
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertFalse(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testRemoveAmendmentController() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl() {
            {
                // we need an amendment holder element to attach the amendment to ...
                // TODO get rid of this holder element - no need for it
                amendmentHolderElement = new HTMLPanel("");
            }
        };

        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        amendableWidget.removeAmendmentController(amendmentController);
        Assert.assertFalse(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testRemoveAmendmentControllerWithListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[0] = true;
                return false;
            }

            @Override
            public void afterAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[1] = true;
            }
        });
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        amendableWidget.removeAmendmentController(amendmentController);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
        Assert.assertFalse(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testRemoveAmendmentControllerWithVetoListener() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        amendableWidget.setListener(new AmendableWidgetListenerMock() {
            @Override
            public boolean beforeAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[0] = true;
                return true;
            }

            @Override
            public void afterAmendmentControllerRemoved(AmendableWidget amendableWidget, AmendmentController amendmentController) {
                hits[1] = true;
            }
        });
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExtended = new AmendmentViewImpl(null);
        final AmendmentController amendmentController = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExtended);
        amendableWidget.addAmendmentController(amendmentController);
        amendableWidget.removeAmendmentController(amendmentController);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertTrue(Arrays.asList(amendableWidget.getAmendmentControllers()).contains(amendmentController));
    }

    @Test
    public void testOnBrowserEvent() throws Exception {

    }

    @Test
    public void testGetType() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        Assert.assertEquals("foo", amendableWidget.getType());
    }

    @Test
    public void testGetId() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setId("foo");
        Assert.assertEquals("foo", amendableWidget.getId());
    }

    @Test
    public void testGetInnerHTML() throws Exception {
        final Element span = DOM.createSpan();
        span.setInnerHTML("<foo><bar>baz</bar></foo>");
        AmendableWidget amendableWidget = new AmendableWidgetImpl(span) {
            {
                amendmentHolderElement = new HTMLPanel("<span></span>");
            }
        };
        Assert.assertEquals("<foo><bar>baz</bar></foo>", amendableWidget.getInnerHTML());
    }

    @Test
    public void testGetParentAmendableWidgets() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        AmendableWidget parent = new AmendableWidgetImpl();
        amendableWidget.setParentAmendableWidget(parent);
        AmendableWidget grandParent = new AmendableWidgetImpl();
        parent.setParentAmendableWidget(grandParent);
        AmendableWidget greatGrandParent = new AmendableWidgetImpl();
        grandParent.setParentAmendableWidget(greatGrandParent);

        final List<AmendableWidget> parentAmendableWidgets = amendableWidget.getParentAmendableWidgets();
        Assert.assertEquals("Make sure only the three parents are returned", 3, parentAmendableWidgets.size());
        Assert.assertEquals("Make sure only the order is correct", parent, parentAmendableWidgets.get(2));
        Assert.assertEquals("Make sure only the order is correct", grandParent, parentAmendableWidgets.get(1));
        Assert.assertEquals("Make sure only the order is correct", greatGrandParent, parentAmendableWidgets.get(0));
    }

    @Test
    public void testGetChildAmendableWidgets() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        AmendableWidget child1 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child1);
        AmendableWidget child2 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child2);
        AmendableWidget child3 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child3);

        final List<AmendableWidget> childAmendableWidgets = amendableWidget.getChildAmendableWidgets();

        Assert.assertEquals("Make sure only the three children are returned", 3, childAmendableWidgets.size());
        Assert.assertEquals("Make sure only the order is correct", child1, childAmendableWidgets.get(0));
        Assert.assertEquals("Make sure only the order is correct", child2, childAmendableWidgets.get(1));
        Assert.assertEquals("Make sure only the order is correct", child3, childAmendableWidgets.get(2));
        for (AmendableWidget child : childAmendableWidgets) {
            Assert.assertEquals("Make sure the children have been set the correct parent", amendableWidget, child.getParentAmendableWidget());
        }
    }

    @Test
    public void testGetParentAmendableWidget() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        AmendableWidget parent = new AmendableWidgetImpl();
        amendableWidget.setParentAmendableWidget(parent);
        Assert.assertEquals(parent, amendableWidget.getParentAmendableWidget());
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidget() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        final AmendableWidget previousNonIntroducedAmendableWidget = amendableWidget.getPreviousNonIntroducedAmendableWidget(false);
        Assert.assertEquals(previousNonIntroducedAmendableWidget, neighbour2);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetSameType() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        neighbour1.setType("foo");
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);
        neighbour2.setType("bar");
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        amendableWidget.setType("foo");
        final AmendableWidget previousNonIntroducedAmendableWidget = amendableWidget.getPreviousNonIntroducedAmendableWidget(true);
        Assert.assertEquals(previousNonIntroducedAmendableWidget, neighbour1);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetSkipIntroducedOne() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);

        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        neighbour2.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(neighbour2);

        AmendableWidget neighbour3 = new AmendableWidgetImpl();
        neighbour3.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(neighbour3);

        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        final AmendableWidget previousNonIntroducedAmendableWidget = amendableWidget.getPreviousNonIntroducedAmendableWidget(false);
        Assert.assertEquals(previousNonIntroducedAmendableWidget, neighbour1);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetFirstInCollection() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);

        final AmendableWidget previousNonIntroducedAmendableWidget = amendableWidget.getPreviousNonIntroducedAmendableWidget(false);
        Assert.assertNull(previousNonIntroducedAmendableWidget);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidget() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);

        final AmendableWidget nextNonIntroducedAmendableWidget = amendableWidget.getNextNonIntroducedAmendableWidget(false);
        Assert.assertEquals(nextNonIntroducedAmendableWidget, neighbour1);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetSameType() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        parent.addAmendableWidget(amendableWidget);

        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        neighbour1.setType("bar");

        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);
        neighbour2.setType("foo");

        final AmendableWidget nextNonIntroducedAmendableWidget = amendableWidget.getNextNonIntroducedAmendableWidget(true);
        Assert.assertEquals(nextNonIntroducedAmendableWidget, neighbour2);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetSkipIntroducedOne() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        parent.addAmendableWidget(amendableWidget);

        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        neighbour1.setType("foo");
        neighbour1.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(neighbour1);

        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        neighbour2.setType("foo");
        parent.addAmendableWidget(neighbour2);
        neighbour2.setOrigin(AmendableWidgetOrigin.AMENDMENT);

        AmendableWidget neighbour3 = new AmendableWidgetImpl();
        neighbour3.setType("foo");
        parent.addAmendableWidget(neighbour3);

        final AmendableWidget nextNonIntroducedAmendableWidget = amendableWidget.getNextNonIntroducedAmendableWidget(false);
        Assert.assertEquals(nextNonIntroducedAmendableWidget, neighbour3);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetLastInCollection() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();

        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);

        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);

        final AmendableWidget nextNonIntroducedAmendableWidget = amendableWidget.getNextNonIntroducedAmendableWidget(false);
        Assert.assertNull(nextNonIntroducedAmendableWidget);
    }

    @Test
    public void testIsAmendable() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setAmendable(true);
        Assert.assertTrue(amendableWidget.isAmendable());
    }

    @Test
    public void testIsAmendableParent() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setAmendable(true);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        Assert.assertTrue(amendableWidget.isAmendable());
    }

    @Test
    public void testIsAmended() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        Assert.assertFalse(amendableWidget.isAmended());
        final ClientFactory clientFactory = new ClientFactoryMock();
        final AmendmentView amendmentView = new AmendmentViewImpl(null);
        final AmendmentView amendmentViewExt = new AmendmentViewImpl(null);
        AmendmentController amendmentController1 = new DefaultAmendmentController(clientFactory, amendmentView, amendmentViewExt);
        amendableWidget.addAmendmentController(amendmentController1);
        Assert.assertTrue(amendableWidget.isAmended());
    }

    @Test
    public void testIsImmutable() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setImmutable(true);
        Assert.assertTrue(amendableWidget.isImmutable());
    }

    @Test
    public void testIsImmutableParent() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setImmutable(true);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        Assert.assertTrue(amendableWidget.isImmutable());
    }

    @Test
    public void testGetOrigin() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        Assert.assertEquals(AmendableWidgetOrigin.AMENDMENT, amendableWidget.getOrigin());
        Assert.assertNull("Assert default is null", new AmendableWidgetImpl().getOrigin());
    }

    @Test
    public void testGetFormattedIndex() throws Exception {
        AmendableWidgetImpl amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setFormattedIndex("(15)");
        Assert.assertEquals("(15)", amendableWidget.getFormattedIndex());
    }

    @Test
    public void testGetAssignedNumber() throws Exception {
        AmendableWidgetImpl parent = new AmendableWidgetImpl();
        AmendableWidgetImpl amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        parent.addAmendableWidget(amendableWidget);

        AmendableWidgetImpl amendableWidget2 = new AmendableWidgetImpl();
        amendableWidget2.setType("foo");
        parent.addAmendableWidget(amendableWidget2);

        AmendableWidgetImpl amendableWidget3 = new AmendableWidgetImpl();
        amendableWidget3.setType("foo");
        amendableWidget3.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget3);

        AmendableWidgetImpl amendableWidget4 = new AmendableWidgetImpl();
        amendableWidget4.setType("foo");
        parent.addAmendableWidget(amendableWidget4);

        AmendableWidgetImpl amendableWidget5 = new AmendableWidgetImpl();
        amendableWidget5.setType("bar");
        parent.addAmendableWidget(amendableWidget5);

        Assert.assertEquals(1, (int) amendableWidget.getAssignedNumber());
        Assert.assertEquals(2, (int) amendableWidget2.getAssignedNumber());
        Assert.assertEquals(3, (int) amendableWidget3.getAssignedNumber());
        Assert.assertEquals(3, (int) amendableWidget4.getAssignedNumber());
        Assert.assertEquals(1, (int) amendableWidget5.getAssignedNumber());
    }

    @Test
    public void testGetAssignedNumberSet() throws Exception {
        AmendableWidgetImpl amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setAssignedNumber(1);
        Assert.assertEquals(1, (int) amendableWidget.getAssignedNumber());
    }

    @Test
    public void testGetUnformattedIndex() throws Exception {
        AmendableWidgetImpl amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setUnformattedIndex("15");
        Assert.assertEquals("15", amendableWidget.getUnformattedIndex());
    }

    @Test
    public void testIsIntroducedByAnAmendment() throws Exception {
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        Assert.assertTrue(amendableWidget.isIntroducedByAnAmendment());
    }

    @Test
    public void testGetTypeCounter() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        neighbour1.setType("foo");
        parent.addAmendableWidget(neighbour1);

        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        neighbour2.setType("foo");
        parent.addAmendableWidget(neighbour2);

        AmendableWidget neighbour3 = new AmendableWidgetImpl();
        neighbour3.setType("bar");
        parent.addAmendableWidget(neighbour3);

        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        parent.addAmendableWidget(amendableWidget);

        Assert.assertEquals(0, neighbour1.getTypeIndex());
        Assert.assertEquals(1, neighbour2.getTypeIndex());
        Assert.assertEquals(0, neighbour3.getTypeIndex());
        Assert.assertEquals(2, amendableWidget.getTypeIndex());
        amendableWidget.setType("bar");
        Assert.assertEquals(1, amendableWidget.getTypeIndex());
    }

    @Test
    public void testGetTypeIndexWithAmendableWidgetsIntroducedByAmendments() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        neighbour1.setType("foo");
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        neighbour2.setType("foo");
        neighbour2.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(neighbour2);
        AmendableWidget neighbour3 = new AmendableWidgetImpl();
        neighbour3.setType("bar");
        parent.addAmendableWidget(neighbour3);
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals(1, amendableWidget.getTypeIndex());
        Assert.assertEquals(1, amendableWidget.getTypeIndex(false));
        Assert.assertEquals(2, amendableWidget.getTypeIndex(true));
    }

    @Test
    public void testGetIndex() throws Exception {
        AmendableWidget parent = new AmendableWidgetImpl();
        AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);
        AmendableWidget neighbour3 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour3);
        AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals(0, neighbour1.getIndex());
        Assert.assertEquals(3, amendableWidget.getIndex());
    }

    @Test
    public void testHtml() throws Exception {
        final Element span = DOM.createSpan();
        span.setInnerHTML("<foo><bar>baz</bar></foo>");
        AmendableWidgetImpl amendableWidget = new AmendableWidgetImpl(span) {
            {
                amendmentHolderElement = new HTMLPanel("<span></span>");
            }
        };
        Assert.assertEquals("<foo><bar>baz</bar></foo>", amendableWidget.html());
    }

    @Test
    public void testWalk() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        final AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        final AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);

        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        final AmendableWidget child1 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child1);
        final AmendableWidget child2 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child2);

        final AmendableWidget neighbour3 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour3);

        final List<AmendableWidget> order = Arrays.asList(parent, neighbour1, neighbour2, amendableWidget, child1, child2, neighbour3);

        final Counter counter = new Counter(0);
        parent.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                Assert.assertEquals(order.get(counter.get()), visited);
                counter.increment();
                return true;
            }
        });

        final Counter counter2 = new Counter(0);
        parent.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                counter2.increment();
                // stop short
                return false;
            }
        });
        Assert.assertEquals(1, counter2.get());
    }

    @Test
    public void testWalkStop() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        final AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        final AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);

        final Counter counter = new Counter(0);
        parent.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                counter.increment();
                // stop short
                return false;
            }
        });
        Assert.assertEquals("Assert only the first parent node is visited.", 1, counter.get());
    }

    @Test
    public void testWalkStopExcludingChildrenOfASingleNode() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        final AmendableWidget neighbour1 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour1);
        final AmendableWidget neighbour2 = new AmendableWidgetImpl();
        parent.addAmendableWidget(neighbour2);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        final AmendableWidget child1 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child1);
        final AmendableWidget child2 = new AmendableWidgetImpl();
        amendableWidget.addAmendableWidget(child2);

        final AmendableWidget neighbour3 = new AmendableWidgetImpl();
        final AmendableWidget child3 = new AmendableWidgetImpl();
        neighbour3.addAmendableWidget(child3);
        final AmendableWidget child4 = new AmendableWidgetImpl();
        neighbour3.addAmendableWidget(child4);
        parent.addAmendableWidget(neighbour3);

        final Counter counter = new Counter(0);
        parent.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(AmendableWidget visited) {
                counter.increment();
                // stop short on amendable widget target
                final boolean keepWalking = amendableWidget != visited;
                // ensure children are not visited
                Assert.assertFalse(visited == child1 || visited == child2);
                return keepWalking;
            }
        });
        Assert.assertEquals("Assert all nodes and child nodes are visited, except for the child nodes 1 & 2", 7, counter.get());
    }
}
