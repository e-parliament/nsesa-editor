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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.shared.OverlayWidgetOrigin;

/**
 * Date: 21/01/13 11:11
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DefaultLocatorTest extends GwtTest {

    private static final String ISO_ENGLISH = "EN";

    final DefaultLocator locator = new DefaultLocator();

    @Test
    public void testGetLocationNull() throws Exception {
        Assert.assertNull(locator.getLocation(null, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationWithoutType() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        Assert.assertEquals("?", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationWithSingleParentWithoutType() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – ? 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationRoot() throws Exception {
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("foo");
        Assert.assertEquals("foo", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParent() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithConstantNumberingTypeAndFormat() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        overlayWidget.setNumberingType(NumberingType.INDENT);
        overlayWidget.setFormat(Format.INDENT);
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));

        final Element amendableWidgetSpan = DOM.createSpan();
        final OverlayWidget overlayWidget2 = new OverlayWidgetImpl(amendableWidgetSpan);
        overlayWidget2.setType("bar");
        final Element span = DOM.createSpan();
        span.setAttribute("type", "num");
        span.setClassName("num");
        span.setInnerHTML("-");
        amendableWidgetSpan.appendChild(span);
        overlayWidget2.setOverlayStrategy(new DefaultOverlayStrategy());
        overlayWidget2.addOverlayWidget(new OverlayWidgetImpl(span));

        parent.addOverlayWidget(overlayWidget2);
        Assert.assertEquals("foo – bar 2", locator.getLocation(overlayWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithNonConstantNumberingTypeAndFormat() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final Element amendableWidgetSpan = DOM.createSpan();
        final OverlayWidget overlayWidget = new OverlayWidgetImpl(amendableWidgetSpan);
        overlayWidget.setType("bar");

        final Element span = DOM.createSpan();
        span.setAttribute("type", "num");
        span.setClassName("num");
        span.setInnerHTML("(a)");
        amendableWidgetSpan.appendChild(span);
        overlayWidget.setOverlayStrategy(new DefaultOverlayStrategy());
        overlayWidget.addOverlayWidget(new OverlayWidgetImpl(span));

        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar a", locator.getLocation(overlayWidget, ISO_ENGLISH, false));

        final Element amendableWidgetSpan2 = DOM.createSpan();
        final OverlayWidget overlayWidget2 = new OverlayWidgetImpl(amendableWidgetSpan2);
        overlayWidget2.setType("bar");

        final Element span2 = DOM.createSpan();
        span2.setAttribute("type", "num");
        span2.setClassName("num");
        span2.setInnerHTML("b)");
        amendableWidgetSpan2.appendChild(span2);
        overlayWidget2.setOverlayStrategy(new DefaultOverlayStrategy());
        overlayWidget2.addOverlayWidget(new OverlayWidgetImpl(span));

        overlayWidget2.addOverlayWidget(new OverlayWidgetImpl(span2));

        parent.addOverlayWidget(overlayWidget2);
        Assert.assertEquals("foo – bar b", locator.getLocation(overlayWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationMultipleParents() throws Exception {
        final OverlayWidget greatGrandParent = new OverlayWidgetImpl();
        greatGrandParent.setType("foo");
        final OverlayWidget grandParent = new OverlayWidgetImpl();
        grandParent.setType("bar");
        greatGrandParent.addOverlayWidget(grandParent);
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("baz");
        grandParent.addOverlayWidget(parent);
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("kk");
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar 1 – baz 1 – kk 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationMultipleParentsOfTheSameType() throws Exception {
        final OverlayWidget grandParent = new OverlayWidgetImpl();
        grandParent.setType("foo");
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("bar");
        grandParent.addOverlayWidget(parent);
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar 1 – subbar 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithSibling() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget sibling1 = new OverlayWidgetImpl();
        sibling1.setType("bar");
        parent.addOverlayWidget(sibling1);

        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("baz");
        parent.addOverlayWidget(overlayWidget);

        Assert.assertEquals("foo – baz 1", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithSiblingOfTheSameType() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget sibling1 = new OverlayWidgetImpl();
        sibling1.setType("bar");
        parent.addOverlayWidget(sibling1);

        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        parent.addOverlayWidget(overlayWidget);

        Assert.assertEquals("foo – bar 2", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetInAllNewCollection() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        overlayWidget.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget);

        Assert.assertEquals("foo – bar 1 (new)", locator.getLocation(overlayWidget, ISO_ENGLISH, false));

        final OverlayWidget overlayWidget2 = new OverlayWidgetImpl();
        overlayWidget2.setType("bar");
        overlayWidget2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget2);


        Assert.assertEquals("foo – bar 2 (new)", locator.getLocation(overlayWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetWithPreviousNonIntroducedAmendableWidget() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget previous = new OverlayWidgetImpl();
        previous.setType("bar");
        parent.addOverlayWidget(previous);

        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        overlayWidget.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget);
        Assert.assertEquals("foo – bar 1a (new)", locator.getLocation(overlayWidget, ISO_ENGLISH, false));

        final OverlayWidget overlayWidget2 = new OverlayWidgetImpl();
        overlayWidget2.setType("bar");
        overlayWidget2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget2);

        Assert.assertEquals("foo – bar 1b (new)", locator.getLocation(overlayWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetWithNextNonIntroducedAmendableWidget() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl();
        parent.setType("foo");
        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        overlayWidget.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget);

        final OverlayWidget overlayWidget2 = new OverlayWidgetImpl();
        overlayWidget2.setType("bar");
        overlayWidget2.setOrigin(OverlayWidgetOrigin.AMENDMENT);
        parent.addOverlayWidget(overlayWidget2);

        final OverlayWidget next = new OverlayWidgetImpl();
        next.setType("bar");
        parent.addOverlayWidget(next);

        Assert.assertEquals("foo – bar -1a (new)", locator.getLocation(overlayWidget, ISO_ENGLISH, false));
        Assert.assertEquals("foo – bar -1b (new)", locator.getLocation(overlayWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testParse() throws Exception {
        final OverlayWidget root = new OverlayWidgetImpl();
        root.setType("foo");

        final Location[] locations = locator.parse(root, "Foo 1", ISO_ENGLISH);
        Assert.assertTrue(locations.length == 1);
        Assert.assertEquals(root.getType(), locations[0].getType());

        final OverlayWidget overlayWidget = new OverlayWidgetImpl();
        overlayWidget.setType("bar");
        root.addOverlayWidget(overlayWidget);

        final Location[] locations2 = locator.parse(root, "Foo 1" + locator.getSplitter(ISO_ENGLISH) + "bar 1 a (new)", ISO_ENGLISH);
        Assert.assertTrue(locations2.length == 2);
        Assert.assertEquals(root.getType(), locations2[0].getType());
        Assert.assertEquals(overlayWidget.getType(), locations2[1].getType());
        Assert.assertTrue(locations2[1].isNew());
    }
}
