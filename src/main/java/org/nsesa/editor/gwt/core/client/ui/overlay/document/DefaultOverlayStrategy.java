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

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of the {@link OverlayStrategy}.
 * Date: 17/10/12 21:31
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayStrategy implements OverlayStrategy {

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
        if (!Arrays.asList("num", "p", "b", "i", "span", "content").contains(element.getAttribute("type").toLowerCase()))
            return overlayStrategySupport.isAmendable(element);
        return false;
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
    public String getNamespaceURI(Element element) {
        return overlayStrategySupport.getNamespaceURI(element);
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
    public String getInnerHTML(Element element) {
        return overlayStrategySupport.getInnerHTML(element);
    }

    @Override
    public Element[] getChildren(Element element) {
        final List<Element> children = overlayStrategySupport.getChildren(element);
        return children.toArray(new Element[children.size()]);
    }

    @Override
    public String getFormattedIndex(Element element) {
        return overlayStrategySupport.getLiteralIndex(element);
    }

    @Override
    public String getUnFormattedIndex(Element element) {
        return overlayStrategySupport.getUnformattedIndex(element);
    }
}
