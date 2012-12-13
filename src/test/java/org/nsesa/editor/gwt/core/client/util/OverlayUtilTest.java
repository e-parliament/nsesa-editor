package org.nsesa.editor.gwt.core.client.util;

import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendableWidgetMockImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

import java.util.List;

/**
 * Date: 12/12/12 22:33
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayUtilTest {

    final AmendableWidgetMockImpl root = new AmendableWidgetMockImpl() {
        {
            type = "rootNode";
            id = "root";
        }
    };

    final AmendableWidgetMockImpl level1 = new AmendableWidgetMockImpl() {
        {
            type = "typeA";
            id = "id1";
        }
    };
    final AmendableWidgetMockImpl level2 = new AmendableWidgetMockImpl() {
        {
            type = "typeB";
            id = "id2";
        }
    };
    final AmendableWidgetMockImpl level3 = new AmendableWidgetMockImpl() {
        {
            type = "typeC";
            id = "id3";
        }
    };

    final AmendableWidgetMockImpl level31 = new AmendableWidgetMockImpl() {
        {
            type = "typeC";
            id = "id3-1";
        }
    };
    final AmendableWidgetMockImpl level32 = new AmendableWidgetMockImpl() {
        {
            type = "typeC";
            id = "id3-2";
            typeIndex = 1;
        }
    };
    final AmendableWidgetMockImpl level33 = new AmendableWidgetMockImpl() {
        {
            type = "typeD";
            id = "id3-3";
        }
    };
    final AmendableWidgetMockImpl level311 = new AmendableWidgetMockImpl() {
        {
            type = "typeC";
            id = "id3-1-1";
        }
    };

    {
        root.addAmendableWidget(level1);
        root.addAmendableWidget(level2);
        root.addAmendableWidget(level3);

        level3.addAmendableWidget(level31);
        level3.addAmendableWidget(level32);
        level3.addAmendableWidget(level33);

        level31.addAmendableWidget(level311);
    }


    @Test
    public void testXpathRootExpression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node can be found", root, xpathResult.get(0));
    }

    @Test
    public void testXpathRootExpressionWithTrailingSlash() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node can be found", root, xpathResult.get(0));
    }


    @Test
    public void testXpathLevel1Expression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/typeC[0]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level3, xpathResult.get(0));
    }

    @Test
    public void testXpathLevel2Expression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/typeC[0]/typeC[0]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level31, xpathResult.get(0));
    }

    @Test
    public void testXpathLevel3Expression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/typeC[0]/typeC[0]/typeC[0]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level311, xpathResult.get(0));
    }

    @Test
    public void testXpathLevel2ExpressionWithSecondNode() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/typeC[0]/typeC[1]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level32, xpathResult.get(0));
    }

    @Test
    public void testXpathIDExpression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("#id3-1-1", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level311, xpathResult.get(0));
    }

    @Test
    public void testXpathNothingExpression() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/typeF[0]", root);
        Assert.assertEquals("Ensure no node is found", 0, xpathResult.size());
    }

    @Test
    public void testSingleFind() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.find("typeB", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level2, xpathResult.get(0));
    }

    @Test
    public void testSingleFindNested() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.find("typeD", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level33, xpathResult.get(0));
    }

    @Test
    public void testMultipleFindOrderedNested() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.find("typeC", root);
        Assert.assertEquals("Ensure 4 nodes are found", 4, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level3, xpathResult.get(0));
        Assert.assertEquals("Ensure the correct node is found.", level31, xpathResult.get(1));
        Assert.assertEquals("Ensure the correct node is found.", level311, xpathResult.get(2));
        Assert.assertEquals("Ensure the correct node is found.", level32, xpathResult.get(3));
    }
}
