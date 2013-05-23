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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;
import org.nsesa.editor.gwt.core.client.util.Counter;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 18/01/13 14:11
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class AmendableWidgetImplTest extends GwtTest {

    @Test
    public void testAddAmendableWidget() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        Assert.assertTrue(overlayWidget.getChildOverlayWidgets().contains(child));
    }

    @Test
    public void testAddAmendableWidgetWithListener() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        overlayWidget.setListener(new OverlayWidgetListenerMock() {
            @Override
            public boolean beforeOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[0] = true;
                return false;
            }

            @Override
            public void afterOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[1] = true;
            }
        });
        final OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
    }

    @Test
    public void testAddAmendableWidgetWithVetoListener() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        overlayWidget.setListener(new OverlayWidgetListenerMock() {
            @Override
            public boolean beforeOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[0] = true;
                return true;
            }

            @Override
            public void afterOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[1] = true;
            }
        });
        final OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertFalse("Make sure the child is not added.", overlayWidget.getChildOverlayWidgets().contains(child));
    }

    @Test
    public void testRemoveAmendableWidget() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        Assert.assertTrue(overlayWidget.getChildOverlayWidgets().contains(child));
        overlayWidget.removeOverlayWidget(child);
        Assert.assertFalse(overlayWidget.getChildOverlayWidgets().contains(child));
    }

    @Test
    public void testRemoveAmendableWidgetWithListener() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        overlayWidget.setListener(new OverlayWidgetListenerMock() {
            @Override
            public boolean beforeOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[0] = true;
                return false;
            }

            @Override
            public void afterOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[1] = true;
            }
        });
        final OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        overlayWidget.removeOverlayWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertTrue("Make sure the after method is hit", hits[1]);
    }

    @Test
    public void testRemoveAmendableWidgetWithVetoListener() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        final Boolean[] hits = new Boolean[]{false, false};
        overlayWidget.setListener(new OverlayWidgetListenerMock() {
            @Override
            public boolean beforeOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[0] = true;
                return true;
            }

            @Override
            public void afterOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
                hits[1] = true;
            }
        });
        final OverlayWidget child = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child);
        overlayWidget.removeOverlayWidget(child);
        Assert.assertTrue("Make sure the before method is hit", hits[0]);
        Assert.assertFalse("Make sure the after method is not hit", hits[1]);
        Assert.assertTrue("Make sure the child is not removed.", overlayWidget.getChildOverlayWidgets().contains(child));
    }


    @Test
    public void testOnBrowserEvent() throws Exception {

    }

    @Test
    public void testGetType() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        Assert.assertEquals("foo", overlayWidget.getType());
    }

    @Test
    public void testGetId() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setId("foo");
        Assert.assertEquals("foo", overlayWidget.getId());
    }

    @Test
    public void testGetInnerHTML() throws Exception {
        final Element span = DOM.createSpan();
        span.setInnerHTML("<foo><bar>baz</bar></foo>");
        OverlayWidget overlayWidget = new OverlayWidgetImpl(span) {
            {
                amendmentControllersHolderElement = new HTMLPanel("<span></span>");
            }
        };
        Assert.assertEquals("<foo><bar>baz</bar></foo>", overlayWidget.getInnerHTML());
    }

    @Test
    public void testGetParentAmendableWidgets() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        OverlayWidget parent = new OverlayWidgetImpl();
        overlayWidget.setParentOverlayWidget(parent);
        OverlayWidget grandParent = new OverlayWidgetImpl();
        parent.setParentOverlayWidget(grandParent);
        OverlayWidget greatGrandParent = new OverlayWidgetImpl();
        grandParent.setParentOverlayWidget(greatGrandParent);

        final List<OverlayWidget> parentOverlayWidgets = overlayWidget.getParentOverlayWidgets();
        Assert.assertEquals("Make sure only the three parents are returned", 3, parentOverlayWidgets.size());
        Assert.assertEquals("Make sure only the order is correct", parent, parentOverlayWidgets.get(2));
        Assert.assertEquals("Make sure only the order is correct", grandParent, parentOverlayWidgets.get(1));
        Assert.assertEquals("Make sure only the order is correct", greatGrandParent, parentOverlayWidgets.get(0));
    }

    @Test
    public void testGetChildAmendableWidgets() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        OverlayWidget child1 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child1);
        OverlayWidget child2 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child2);
        OverlayWidget child3 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child3);

        final List<OverlayWidget> childOverlayWidgets = overlayWidget.getChildOverlayWidgets();

        Assert.assertEquals("Make sure only the three children are returned", 3, childOverlayWidgets.size());
        Assert.assertEquals("Make sure only the order is correct", child1, childOverlayWidgets.get(0));
        Assert.assertEquals("Make sure only the order is correct", child2, childOverlayWidgets.get(1));
        Assert.assertEquals("Make sure only the order is correct", child3, childOverlayWidgets.get(2));
        for (OverlayWidget child : childOverlayWidgets) {
            Assert.assertEquals("Make sure the children have been set the correct parent", overlayWidget, child.getParentOverlayWidget());
        }
    }

    @Test
    public void testGetParentAmendableWidget() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        OverlayWidget parent = new OverlayWidgetImpl();
        overlayWidget.setParentOverlayWidget(parent);
        Assert.assertEquals(parent, overlayWidget.getParentOverlayWidget());
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidget() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        final OverlayWidget previousNonIntroducedOverlayWidget = overlayWidget.getPreviousNonIntroducedOverlayWidget(false);
        Assert.assertEquals(previousNonIntroducedOverlayWidget, neighbour2);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetSameType() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        neighbour1.setType("foo");
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);
        neighbour2.setType("bar");
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        overlayWidget.setType("foo");
        final OverlayWidget previousNonIntroducedOverlayWidget = overlayWidget.getPreviousNonIntroducedOverlayWidget(true);
        Assert.assertEquals(previousNonIntroducedOverlayWidget, neighbour1);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetSkipIntroducedOne() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);

        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        neighbour2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(neighbour2);

        OverlayWidget neighbour3 = new OverlayWidgetImpl();
        neighbour3.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(neighbour3);

        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        final OverlayWidget previousNonIntroducedOverlayWidget = overlayWidget.getPreviousNonIntroducedOverlayWidget(false);
        Assert.assertEquals(previousNonIntroducedOverlayWidget, neighbour1);
    }

    @Test
    public void testGetPreviousNonIntroducedAmendableWidgetFirstInCollection() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);

        final OverlayWidget previousNonIntroducedOverlayWidget = overlayWidget.getPreviousNonIntroducedOverlayWidget(false);
        Assert.assertNull(previousNonIntroducedOverlayWidget);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidget() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);

        final OverlayWidget nextNonIntroducedOverlayWidget = overlayWidget.getNextNonIntroducedOverlayWidget(false);
        Assert.assertEquals(nextNonIntroducedOverlayWidget, neighbour1);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetSameType() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        parent.addOverlayWidget(overlayWidget);

        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        neighbour1.setType("bar");

        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);
        neighbour2.setType("foo");

        final OverlayWidget nextNonIntroducedOverlayWidget = overlayWidget.getNextNonIntroducedOverlayWidget(true);
        Assert.assertEquals(nextNonIntroducedOverlayWidget, neighbour2);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetSkipIntroducedOne() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        parent.addOverlayWidget(overlayWidget);

        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        neighbour1.setType("foo");
        neighbour1.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(neighbour1);

        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        neighbour2.setType("foo");
        parent.addOverlayWidget(neighbour2);
        neighbour2.setOrigin(OverlayWidgetOrigin.AMENDMENT);

        OverlayWidget neighbour3 = new OverlayWidgetImpl();
        neighbour3.setType("foo");
        parent.addOverlayWidget(neighbour3);

        final OverlayWidget nextNonIntroducedOverlayWidget = overlayWidget.getNextNonIntroducedOverlayWidget(false);
        Assert.assertEquals(nextNonIntroducedOverlayWidget, neighbour3);
    }

    @Test
    public void testGetNextNonIntroducedAmendableWidgetLastInCollection() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();

        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);

        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);

        final OverlayWidget nextNonIntroducedOverlayWidget = overlayWidget.getNextNonIntroducedOverlayWidget(false);
        Assert.assertNull(nextNonIntroducedOverlayWidget);
    }

    @Test
    public void testIsAmendable() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setAmendable(true);
        Assert.assertTrue(overlayWidget.isAmendable());
    }

    @Test
    public void testIsAmendableParent() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setAmendable(true);
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        Assert.assertTrue(overlayWidget.isAmendable());
    }

    @Test
    public void testIsAmended() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        Assert.assertFalse(overlayWidget.isAmended());
        OverlayWidgetAware amendment = new OverlayWidgetAware() {

            OverlayWidget target;

            @Override
            public void setOverlayWidget(OverlayWidget overlayWidget) {
                this.target = overlayWidget;
            }

            @Override
            public OverlayWidget getOverlayWidget() {
                return target;
            }

            @Override
            public void setOrder(int order) {
                // ignore
            }

            @Override
            public int getInjectionPosition() {
                return 0;
            }

            @Override
            public IsWidget getView() {
                return new HTML();
            }

            @Override
            public IsWidget getExtendedView() {
                return new HTML();
            }
        };
        overlayWidget.addOverlayWidgetAware(amendment);
        Assert.assertTrue(overlayWidget.isAmended());
    }

    @Test
    public void testIsImmutable() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setImmutable(Boolean.TRUE);
        Assert.assertTrue(overlayWidget.isImmutable());
    }

    @Test
    public void testIsImmutableParent() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setImmutable(true);
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        Assert.assertTrue(overlayWidget.isImmutable());
    }

    @Test
    public void testGetOrigin() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        Assert.assertEquals(OverlayWidgetOrigin.AMENDMENT, overlayWidget.getOrigin());
        Assert.assertNull("Assert default is null", new OverlayWidgetImpl().getOrigin());
    }

    @Test
    public void testGetFormattedIndex() throws Exception {
        OverlayWidgetImpl amendableWidget = new OverlayWidgetImpl();
        amendableWidget.setFormattedIndex("(15)");
        Assert.assertEquals("(15)", amendableWidget.getFormattedIndex());
    }

    @Test
    public void testGetAssignedNumber() throws Exception {
        OverlayWidgetImpl parent = new OverlayWidgetImpl();
        OverlayWidgetImpl amendableWidget = new OverlayWidgetImpl();
        amendableWidget.setType("foo");
        parent.addOverlayWidget(amendableWidget);

        OverlayWidgetImpl amendableWidget2 = new OverlayWidgetImpl();
        amendableWidget2.setType("foo");
        parent.addOverlayWidget(amendableWidget2);

        OverlayWidgetImpl amendableWidget3 = new OverlayWidgetImpl();
        amendableWidget3.setType("foo");
        amendableWidget3.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(amendableWidget3);

        OverlayWidgetImpl amendableWidget4 = new OverlayWidgetImpl();
        amendableWidget4.setType("foo");
        parent.addOverlayWidget(amendableWidget4);

        OverlayWidgetImpl amendableWidget5 = new OverlayWidgetImpl();
        amendableWidget5.setType("bar");
        parent.addOverlayWidget(amendableWidget5);

        Assert.assertEquals(1, (int) amendableWidget.getAssignedNumber());
        Assert.assertEquals(2, (int) amendableWidget2.getAssignedNumber());
        Assert.assertEquals(3, (int) amendableWidget3.getAssignedNumber());
        Assert.assertEquals(3, (int) amendableWidget4.getAssignedNumber());
        Assert.assertEquals(1, (int) amendableWidget5.getAssignedNumber());
    }

    @Test
    public void testGetAssignedNumberSet() throws Exception {
        OverlayWidgetImpl amendableWidget = new OverlayWidgetImpl();
        amendableWidget.setAssignedNumber(1);
        Assert.assertEquals(1, (int) amendableWidget.getAssignedNumber());
    }

    @Test
    public void testGetUnformattedIndex() throws Exception {
        OverlayWidgetImpl amendableWidget = new OverlayWidgetImpl();
        amendableWidget.setUnformattedIndex("15");
        Assert.assertEquals("15", amendableWidget.getUnformattedIndex());
    }

    @Test
    public void testIsIntroducedByAnAmendment() throws Exception {
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        Assert.assertTrue(overlayWidget.isIntroducedByAnAmendment());
    }

    @Test
    public void getTypeIndex() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        neighbour1.setType("foo");
        parent.addOverlayWidget(neighbour1);

        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        neighbour2.setType("foo");
        parent.addOverlayWidget(neighbour2);

        OverlayWidget neighbour3 = new OverlayWidgetImpl();
        neighbour3.setType("bar");
        parent.addOverlayWidget(neighbour3);

        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        parent.addOverlayWidget(overlayWidget);

        Assert.assertEquals(0, neighbour1.getTypeIndex());
        Assert.assertEquals(1, neighbour2.getTypeIndex());
        Assert.assertEquals(0, neighbour3.getTypeIndex());
        Assert.assertEquals(2, overlayWidget.getTypeIndex());
        overlayWidget.setType("bar");
        Assert.assertEquals(1, overlayWidget.getTypeIndex());
    }

    @Test
    public void testGetTypeIndexWithAmendableWidgetsIntroducedByAmendments() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        neighbour1.setType("foo");
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        neighbour2.setType("foo");
        neighbour2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(neighbour2);
        OverlayWidget neighbour3 = new OverlayWidgetImpl();
        neighbour3.setType("bar");
        parent.addOverlayWidget(neighbour3);
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals(1, overlayWidget.getTypeIndex());
        Assert.assertEquals(1, overlayWidget.getTypeIndex(false));
        Assert.assertEquals(2, overlayWidget.getTypeIndex(true));
    }

    @Test
    public void testGetIndex() throws Exception {
        OverlayWidget parent = new OverlayWidgetImpl();
        OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);
        OverlayWidget neighbour3 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour3);
        OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals(0, neighbour1.getIndex());
        Assert.assertEquals(3, overlayWidget.getIndex());
    }

    @Test
    public void testHtml() throws Exception {
        final Element span = DOM.createSpan();
        span.setInnerHTML("<foo><bar>baz</bar></foo>");
        OverlayWidgetImpl amendableWidget = new OverlayWidgetImpl(span) {
            {
                amendmentControllersHolderElement = new HTMLPanel("<span></span>");
            }
        };
        Assert.assertEquals("<foo><bar>baz</bar></foo>", amendableWidget.html());
    }

    @Test
    public void testWalk() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        final OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        final OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);

        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        final OverlayWidget child1 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child1);
        final OverlayWidget child2 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child2);

        final OverlayWidget neighbour3 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour3);

        final List<OverlayWidget> order = Arrays.asList(parent, neighbour1, neighbour2, overlayWidget, child1, child2, neighbour3);

        final Counter counter = new Counter(0);
        parent.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                Assert.assertEquals(order.get(counter.get()), visited);
                counter.increment();
                return true;
            }
        });

        final Counter counter2 = new Counter(0);
        parent.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                counter2.increment();
                // stop short
                return false;
            }
        });
        Assert.assertEquals(1, counter2.get());
    }

    @Test
    public void testWalkStop() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        final OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        final OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);

        final Counter counter = new Counter(0);
        parent.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                counter.increment();
                // stop short
                return false;
            }
        });
        Assert.assertEquals("Assert only the first parent node is visited.", 1, counter.get());
    }

    @Test
    public void testWalkStopExcludingChildrenOfASingleNode() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        final OverlayWidget neighbour1 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour1);
        final OverlayWidget neighbour2 = new OverlayWidgetImpl();
        parent.addOverlayWidget(neighbour2);
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        final OverlayWidget child1 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child1);
        final OverlayWidget child2 = new OverlayWidgetImpl();
        overlayWidget.addOverlayWidget(child2);

        final OverlayWidget neighbour3 = new OverlayWidgetImpl();
        final OverlayWidget child3 = new OverlayWidgetImpl();
        neighbour3.addOverlayWidget(child3);
        final OverlayWidget child4 = new OverlayWidgetImpl();
        neighbour3.addOverlayWidget(child4);
        parent.addOverlayWidget(neighbour3);

        final Counter counter = new Counter(0);
        parent.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(OverlayWidget visited) {
                counter.increment();
                // stop short on amendable widget target
                final boolean keepWalking = overlayWidget != visited;
                // ensure children are not visited
                Assert.assertFalse(visited == child1 || visited == child2);
                return keepWalking;
            }
        });
        Assert.assertEquals("Assert all nodes and child nodes are visited, except for the child nodes 1 & 2", 7, counter.get());
    }
}
