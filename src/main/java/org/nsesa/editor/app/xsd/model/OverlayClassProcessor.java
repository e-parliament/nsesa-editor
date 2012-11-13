package org.nsesa.editor.app.xsd.model;

import java.util.EnumSet;

/**
 * Define how to process, if the case, an overlay class, when traversing it
 * User: sgroza
 * Date: 29/10/12
 * Time: 12:35
 */
public interface OverlayClassProcessor {
    /**
     * Process the overlay class
     * @param overlayClass The class to be processed
     * @return  True when the class was processed
     */
    public abstract boolean process(OverlayClass overlayClass);
}
