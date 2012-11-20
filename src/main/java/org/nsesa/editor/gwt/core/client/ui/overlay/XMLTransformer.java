package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

/**
 * An interface to transform <code>AmendableWidget<code/> object into its XML representation
 * User: groza
 * Date: 20/11/12
 * Time: 10:59
 */
@ImplementedBy(DefaultXMLTransformer.class)
public interface XMLTransformer {
    /**
     * Transforms an amendable widget into XML representation
     * @param widget The amendable widget that will be XML-ized.
     * @return XML representation as String
     */
    abstract String toXML(AmendableWidget widget);
}
