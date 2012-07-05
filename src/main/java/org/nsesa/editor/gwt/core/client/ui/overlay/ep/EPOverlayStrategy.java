package org.nsesa.editor.gwt.core.client.ui.overlay.ep;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;
import org.nsesa.editor.gwt.core.client.ui.overlay.OverlayStrategy;
import org.nsesa.editor.gwt.core.client.ui.overlay.OverlayStrategySupport;

import java.util.List;

/**
 * Date: 03/07/12 22:54
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EPOverlayStrategy implements OverlayStrategy {

    public static final String ATTRIB_AMENDABLE = "ep:amendable";
    public static final String ATTRIB_IMMUTABLE = "ep:immutable";
    public static final String ATTRIB_ASSIGNED_INDEX = "ep:assignedIndex";
    public static final String ATTRIB_ORIGINAL_INDEX = "ep:originalIndex";
    public static final String ATTRIB_FORMAT = "ep:format";
    public static final String ATTRIB_NUMBERING_TYPE = "ep:numberingType";
    public static final String ATTRIB_TRANSLATION_ID = "ep:sequence";
    public static final String ATTRIB_SOURCE = "ep:source";


    public static final String CLASS_OPERATION_PANEL = "ep:operationPanel";
    public static final String CLASS_AMENDMENTS_PANEL = "ep:amendmentsPanel";

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
