package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.inject.ImplementedBy;

import java.util.Map;

/**
 * An overlay factory is responsible for translating an element into an amendable widget.
 * This widget will then be used in a separate tree to represent a high(er) level version
 * of the structure in the document.
 * <p/>
 * Date: 04/08/12 17:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultOverlayFactory.class)
public interface OverlayFactory {
    /**
     * Translates a given element into an {@link AmendableWidget}. If the overlay does not succeed (eg. because there
     * is no matching {@link AmendableWidget} found, then null is returned.
     *
     * @param element the element to get the overlaying element for.
     * @return the amendable widget, or null if it cannot be overlaid.
     */
    AmendableWidget getAmendableWidget(Element element, Map<String, String> namespaces);

    AmendableWidget getAmendableWidget(String tag, Map<String, String> namespaces);

    AmendableWidget toAmendableWidget(Element element, Map<String, String> namespaces);
}
