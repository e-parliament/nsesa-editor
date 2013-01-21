package org.nsesa.editor.gwt.core.client.util;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;

import java.util.List;

/**
 * Date: 12/12/12 22:33
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class OverlayUtilTest extends GwtTest {

    final AmendableWidgetImpl root = new AmendableWidgetImpl() {
        {
            setType("rootNode");
            setId("root");
        }
    };

    final AmendableWidgetImpl level1 = new AmendableWidgetImpl() {
        {
            setType("typeA");
            setId("id1");
        }
    };
    final AmendableWidgetImpl level2 = new AmendableWidgetImpl() {
        {
            setType("typeB");
            setId("id2");
        }
    };
    final AmendableWidgetImpl level3 = new AmendableWidgetImpl() {
        {
            setType("typeC");
            setId("id3");
        }
    };

    final AmendableWidgetImpl level31 = new AmendableWidgetImpl() {
        {
            setType("typeC");
            setId("id3-1");
        }
    };
    final AmendableWidgetImpl level32 = new AmendableWidgetImpl() {
        {
            setType("typeC");
            setId("id3-2");
        }
    };
    final AmendableWidgetImpl level33 = new AmendableWidgetImpl() {
        {
            setType("typeD");
            setId("id3-3");
        }
    };
    final AmendableWidgetImpl level311 = new AmendableWidgetImpl() {
        {
            setType("typeC");
            setId("id3-1-1");
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
    public void testXpathLevel2ExpressionWithWildCard() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/*[0]/typeC[1]", root);
        Assert.assertEquals("Ensure one node is found", 1, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level32, xpathResult.get(0));
    }

    @Test
    public void testXpathMultipleReturnValuesWithWildCard() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/*[0]/*", root);
        Assert.assertEquals("Ensure three nodes are found", 3, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level31, xpathResult.get(0));
        Assert.assertEquals("Ensure the correct node is found.", level32, xpathResult.get(1));
        Assert.assertEquals("Ensure the correct node is found.", level33, xpathResult.get(2));
    }

    @Test
    public void testXpathMultipleReturnValuesWitType() throws Exception {
        final List<AmendableWidget> xpathResult = OverlayUtil.xpath("//rootNode[0]/*[0]/typeC", root);
        Assert.assertEquals("Ensure two nodes are found", 2, xpathResult.size());
        Assert.assertEquals("Ensure the correct node is found.", level31, xpathResult.get(0));
        Assert.assertEquals("Ensure the correct node is found.", level32, xpathResult.get(1));
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
