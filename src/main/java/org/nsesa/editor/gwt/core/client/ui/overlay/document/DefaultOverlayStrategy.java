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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getID(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean isAmendable(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean isImmutable(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIndex(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getType(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NumberingType getNumberingType(Element element, int index) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Format getFormat(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getContent(Element element) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Element[] getChildren(Element element) {
        return new Element[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
