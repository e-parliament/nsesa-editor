package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

/**
 * Date: 15/10/12 21:53
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class AbstractOverlayStrategy implements OverlayStrategy {
    @Override
    public String getSource(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public String getID(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public Boolean isAmendable(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public Boolean isImmutable(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public String getIndex(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public String getType(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public String getNamespaceURI(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public NumberingType getNumberingType(Element element, int index) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public Format getFormat(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public String getContent(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }

    @Override
    public Element[] getChildren(Element element) {
        throw new UnsupportedOperationException("Should be overridden in Module!");
    }
}
