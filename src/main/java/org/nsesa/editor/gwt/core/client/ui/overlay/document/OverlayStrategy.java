/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

/**
 * The overlay strategy is responsible for retrieving the correct properties from the DOM.
 * Date: 03/07/12 22:20
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultOverlayStrategy.class)
public interface OverlayStrategy {

    /**
     * Get the 'source' identifier for the given amendable element.
     * <p/>
     * Eg. 'commission', 'committee.imco', 'spanish-government:2011/02/233'
     *
     * @param element the amendable element.
     * @return the source, or <tt>null</tt> if it has not been declared.
     */
    String getSource(Element element);

    /**
     * Get the ID of the element.
     *
     * @param element the element.
     * @return the ID, should never return <tt>null</tt>
     */
    String getID(Element element);

    /**
     * Check if a given element is amendable.
     *
     * @param element the element to check
     * @return True or False if it is declared, <tt>null</tt> otherwise.
     */
    Boolean isAmendable(Element element);

    /**
     * Check if a given element is immutable.
     *
     * @param element the element to check.
     * @return True or False if it is declared, <tt>null</tt> otherwise.
     */
    Boolean isImmutable(Element element);

    /**
     * Get the index from the given <tt>element</tt>. This index is supposed to be unformatted.
     * eg. 1, a, A, 1.1, Article 34, -, ... (and not '1)', '(1.1)', '1.2.3.', ...)
     *
     * @param element the amendable element
     * @return the index, can be <tt>null</tt> if none is given.
     */
    String getIndex(Element element);

    /**
     * Return the type of the amendable element. The 'type' is not limited to any value.
     *
     * @param element the amendable element
     * @return the type, should never return null
     */
    String getType(Element element);

    /**
     * Return the namespace URI for this element.
     *
     * @param element the element to get the namespace for
     * @return
     */
    String getNamespaceURI(Element element);

    /**
     * Get the numbering type of the given amendable element.
     *
     * @param element the amendable element
     * @param index   the index of the element in its parent's collection
     * @return the {@link org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType}, should never return <tt>null</tt>
     */
    NumberingType getNumberingType(Element element, int index);

    /**
     * Get the format of the given amendable element.
     *
     * @param element the amendable element
     * @return the {@link org.nsesa.editor.gwt.core.client.ui.overlay.Format}, should never return <tt>null</tt>
     */
    Format getFormat(Element element);

    /**
     * Get the (amendable) content.
     *
     * @param element the element
     * @return the content, or <tt>null</tt> if nothing is available
     */
    String getInnerHTML(Element element);

    /**
     * Gather the children of a given <tt>element</tt>.
     *
     * @param element the element
     * @return the children of a given <tt>element</tt>
     */
    Element[] getChildren(Element element);

    /**
     * Retrieves the formatted index from a given <tt>element</tt>
     *
     * @param element the element
     * @return the formatted index
     */
    String getFormattedIndex(Element element);

    /**
     * Retries the unformatted index from a given <tt>element</tt>
     *
     * @param element the element
     * @return the unformatted index
     */
    String getUnFormattedIndex(Element element);
}
