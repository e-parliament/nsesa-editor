package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

/**
 * Date: 17/10/12 21:31
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayStrategy implements OverlayStrategy {
    @Override
    public String getSource(Element element) {
        return null;
    }

    @Override
    public String getID(Element element) {
        return null;
    }

    @Override
    public Boolean isAmendable(Element element) {
        return null;
    }

    @Override
    public Boolean isImmutable(Element element) {
        return false;
    }

    @Override
    public String getIndex(Element element) {
        return null;
    }

    @Override
    public String getType(Element element) {
        return null;
    }

    @Override
    public NumberingType getNumberingType(Element element, int index) {
        return null;
    }

    @Override
    public Format getFormat(Element element) {
        return null;
    }

    @Override
    public String getContent(Element element) {
        return null;
    }

    @Override
    public Element[] getChildren(Element element) {
        return new Element[] {};
    }
}
