package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategySupport;

import java.util.List;

/**
 * Date: 03/07/12 22:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AkomaNtoso20OverlayStrategy implements OverlayStrategy {

    private OverlayStrategySupport overlayStrategySupport = new OverlayStrategySupport();

    @Override
    public String getSource(Element element) {
        return overlayStrategySupport.getSource(element);
    }

    @Override
    public String getID(Element element) {
        return overlayStrategySupport.getID(element);
    }

    @Override
    public Boolean isAmendable(Element element) {
        return overlayStrategySupport.isAmendable(element);
    }

    @Override
    public Boolean isImmutable(Element element) {
        return overlayStrategySupport.isImmutable(element);
    }

    @Override
    public String getType(Element element) {
        return overlayStrategySupport.getType(element);
    }

    @Override
    public String getIndex(Element element) {
        return overlayStrategySupport.getLiteralIndex(element);
    }

    @Override
    public NumberingType getNumberingType(Element element, int index) {
        return overlayStrategySupport.getNumberingType(element, index);
    }

    @Override
    public Format getFormat(Element element) {
        return overlayStrategySupport.getFormat(element);
    }

    @Override
    public String getHeading(Element element) {
        return overlayStrategySupport.getHeading(element);
    }

    @Override
    public String getContent(Element element) {
        return overlayStrategySupport.getAmendableContent(element);
    }

    @Override
    public Element[] getChildren(Element element) {
        final List<Element> children = overlayStrategySupport.getChildren(element);
        return children.toArray(new Element[children.size()]);
    }

}
