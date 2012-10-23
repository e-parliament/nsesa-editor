package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.*;

/**
 * Interface to generate objects of type {@link OverlayClass} from different xsd components
 *
 * User: sgroza
 * Date: 18/10/12
 * Time: 15:24
 */
public interface OverlayClassGenerator {
    /**
     * Generates overlay class from simple type component
     * @param simpleType The simple type processed
     * @return An overlay class mapped on simple xsd type
     */
    OverlayClass generate(XSSimpleType simpleType);

    /**
     * Generates overlay class from complex type component
     * @param complexType The complex type processed
     * @return An overlay class mapped on complex xsd type
     */
    OverlayClass generate(XSComplexType complexType);

    /**
     * Generates overlay class from attribute type component
     * @param attribute The attribute type that will be processed
     * @return An overlay class mapped on attribute xsd type
     */
    OverlayClass generate(XSAttributeDecl attribute);

    /**
     * Generates overlay class from group type component
     * @param modelGroup The xsd group processed
     * @return An overlay class mapped on group xsd type
     */
    OverlayClass generate(XSModelGroupDecl modelGroup);

    /**
     * Generates overlay class from attribute group type component
     * @param attrGroup The xsd attribute group processed
     * @return An overlay class mapped on attribute group xsd type
     */
    OverlayClass generate(XSAttGroupDecl attrGroup);

    /**
     *
     * @param element
     * @return
     */
    OverlayClass generate(XSElementDecl element);
}
