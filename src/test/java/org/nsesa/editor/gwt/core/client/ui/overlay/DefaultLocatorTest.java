package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayStrategy;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

/**
 * Date: 21/01/13 11:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class DefaultLocatorTest extends GwtTest {

    private static final String ISO_ENGLISH = "EN";

    final Locator locator = new DefaultLocator();

    @Test
    public void testGetLocationNull() throws Exception {
        Assert.assertNull(locator.getLocation(null, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationWithoutType() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        Assert.assertEquals("?", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationWithSingleParentWithoutType() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – ? 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationRoot() throws Exception {
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("foo");
        Assert.assertEquals("foo", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParent() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithConstantNumberingTypeAndFormat() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        amendableWidget.setNumberingType(NumberingType.INDENT);
        amendableWidget.setFormat(Format.INDENT);
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));

        final Element amendableWidgetSpan = DOM.createSpan();
        final AmendableWidget amendableWidget2 = new AmendableWidgetImpl(amendableWidgetSpan);
        amendableWidget2.setType("bar");
        final Element span = DOM.createSpan();
        span.setAttribute("type", "num");
        span.setClassName("num");
        span.setInnerHTML("-");
        amendableWidgetSpan.appendChild(span);
        amendableWidget2.setOverlayStrategy(new DefaultOverlayStrategy());
        amendableWidget2.addAmendableWidget(new AmendableWidgetImpl(span));

        parent.addAmendableWidget(amendableWidget2);
        Assert.assertEquals("foo – bar 2", locator.getLocation(amendableWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithNonConstantNumberingTypeAndFormat() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final Element amendableWidgetSpan = DOM.createSpan();
        final AmendableWidget amendableWidget = new AmendableWidgetImpl(amendableWidgetSpan);
        amendableWidget.setType("bar");

        final Element span = DOM.createSpan();
        span.setAttribute("type", "num");
        span.setClassName("num");
        span.setInnerHTML("(a)");
        amendableWidgetSpan.appendChild(span);
        amendableWidget.setOverlayStrategy(new DefaultOverlayStrategy());
        amendableWidget.addAmendableWidget(new AmendableWidgetImpl(span));

        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar a", locator.getLocation(amendableWidget, ISO_ENGLISH, false));

        final Element amendableWidgetSpan2 = DOM.createSpan();
        final AmendableWidget amendableWidget2 = new AmendableWidgetImpl(amendableWidgetSpan2);
        amendableWidget2.setType("bar");

        final Element span2 = DOM.createSpan();
        span2.setAttribute("type", "num");
        span2.setClassName("num");
        span2.setInnerHTML("b)");
        amendableWidgetSpan2.appendChild(span2);
        amendableWidget2.setOverlayStrategy(new DefaultOverlayStrategy());
        amendableWidget2.addAmendableWidget(new AmendableWidgetImpl(span));

        amendableWidget2.addAmendableWidget(new AmendableWidgetImpl(span2));

        parent.addAmendableWidget(amendableWidget2);
        Assert.assertEquals("foo – bar b", locator.getLocation(amendableWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationMultipleParents() throws Exception {
        final AmendableWidget greatGrandParent = new AmendableWidgetImpl();
        greatGrandParent.setType("foo");
        final AmendableWidget grandParent = new AmendableWidgetImpl();
        grandParent.setType("bar");
        greatGrandParent.addAmendableWidget(grandParent);
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("baz");
        grandParent.addAmendableWidget(parent);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("kk");
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar 1 – baz 1 – kk 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationMultipleParentsOfTheSameType() throws Exception {
        final AmendableWidget grandParent = new AmendableWidgetImpl();
        grandParent.setType("foo");
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("bar");
        grandParent.addAmendableWidget(parent);
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar 1 – subbar 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithSibling() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget sibling1 = new AmendableWidgetImpl();
        sibling1.setType("bar");
        parent.addAmendableWidget(sibling1);

        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("baz");
        parent.addAmendableWidget(amendableWidget);

        Assert.assertEquals("foo – baz 1", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithSiblingOfTheSameType() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget sibling1 = new AmendableWidgetImpl();
        sibling1.setType("bar");
        parent.addAmendableWidget(sibling1);

        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        parent.addAmendableWidget(amendableWidget);

        Assert.assertEquals("foo – bar 2", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetInAllNewCollection() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        amendableWidget.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget);

        Assert.assertEquals("foo – bar 1 (new)", locator.getLocation(amendableWidget, ISO_ENGLISH, false));

        final AmendableWidget amendableWidget2 = new AmendableWidgetImpl();
        amendableWidget2.setType("bar");
        amendableWidget2.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget2);


        Assert.assertEquals("foo – bar 2 (new)", locator.getLocation(amendableWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetWithPreviousNonIntroducedAmendableWidget() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget previous = new AmendableWidgetImpl();
        previous.setType("bar");
        parent.addAmendableWidget(previous);

        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        amendableWidget.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget);
        Assert.assertEquals("foo – bar 1a (new)", locator.getLocation(amendableWidget, ISO_ENGLISH, false));

        final AmendableWidget amendableWidget2 = new AmendableWidgetImpl();
        amendableWidget2.setType("bar");
        amendableWidget2.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget2);

        Assert.assertEquals("foo – bar 1b (new)", locator.getLocation(amendableWidget2, ISO_ENGLISH, false));
    }

    @Test
    public void testGetLocationSingleParentWithIntroducedAmendableWidgetWithNextNonIntroducedAmendableWidget() throws Exception {
        final AmendableWidget parent = new AmendableWidgetImpl();
        parent.setType("foo");
        final AmendableWidget amendableWidget = new AmendableWidgetImpl();
        amendableWidget.setType("bar");
        amendableWidget.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget);

        final AmendableWidget amendableWidget2 = new AmendableWidgetImpl();
        amendableWidget2.setType("bar");
        amendableWidget2.setOrigin(AmendableWidgetOrigin.AMENDMENT);
        parent.addAmendableWidget(amendableWidget2);

        final AmendableWidget next = new AmendableWidgetImpl();
        next.setType("bar");
        parent.addAmendableWidget(next);

        Assert.assertEquals("foo – bar -1a (new)", locator.getLocation(amendableWidget, ISO_ENGLISH, false));
        Assert.assertEquals("foo – bar -1b (new)", locator.getLocation(amendableWidget2, ISO_ENGLISH, false));
    }
}
