package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Interface to generate objects of type {@link OverlayClass} from different xsd components
 *
 * User: sgroza
 * Date: 18/10/12
 * Time: 15:24
 */
public interface OverlayClassGenerator {
    public static class OverlayRootClass extends OverlayClass {
        public OverlayRootClass() {
            super("XS Root class", null, null);
        }
    }

    public static class OverlaySchemaClass extends OverlayClass {
        public OverlaySchemaClass(String nameSpace) {
            super("XS Schema class", nameSpace, null);
        }
    }

    /**
     * Return tree result of overlay classes
     * @return
     */
    OverlayRootClass getTreeResult();

    /**
     * Returns a flat list of overlay classes
     * @return
     */
    List<OverlayClass> getResult();

    /**
     * Returns a list of overlay classes sorted by comparator
     * @param comparator
     * @return
     */
    List<OverlayClass> getResult(Comparator<OverlayClass> comparator);

    /**
     * Start generation
     */
    void generate(Collection<XSSchema> schemas);

    /**
     * Generates overlay class from simple type component
     * @param simpleType The simple type processed
     */
    void generate(XSSimpleType simpleType);

    /**
     * Generates overlay class from complex type component
     * @param complexType The complex type processed
     */
    void generate(XSComplexType complexType);

    /**
     * Generates overlay class from attribute type component
     * @param attribute The attribute type that will be processed
     */
    void generate(XSAttributeDecl attribute);

    /**
     * Generates overlay class from group type component
     * @param modelGroup The xsd group processed
     */
    void generate(XSModelGroupDecl modelGroup);

    /**
     * Generates overlay class from attribute group type component
     * @param attrGroup The xsd attribute group processed
     */
    void generate(XSAttGroupDecl attrGroup);
    /**
     *
     * @param element
     * @return
     */
    void generate(XSElementDecl element);

}
