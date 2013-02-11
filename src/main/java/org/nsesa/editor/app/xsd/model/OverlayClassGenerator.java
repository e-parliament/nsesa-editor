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
package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.*;

import java.util.Collection;

/**
 * Interface to generate objects of type {@link OverlayClass} from different xsd components
 * <p/>
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
     *
     * @return
     */
    OverlayRootClass getResult();

    /**
     * Start generation
     */
    void generate(Collection<XSSchema> schemas);

    /**
     * Generates overlay class from simple type component
     *
     * @param simpleType The simple type processed
     */
    void generate(XSSimpleType simpleType);

    /**
     * Generates overlay class from complex type component
     *
     * @param complexType The complex type processed
     */
    void generate(XSComplexType complexType);

    /**
     * Generates overlay class from attribute type component
     *
     * @param attribute The attribute type that will be processed
     */
    void generate(XSAttributeDecl attribute);

    /**
     * Generates overlay class from group type component
     *
     * @param modelGroup The xsd group processed
     */
    void generate(XSModelGroupDecl modelGroup);

    /**
     * Generates overlay class from attribute group type component
     *
     * @param attrGroup The xsd attribute group processed
     */
    void generate(XSAttGroupDecl attrGroup);

    /**
     * @param element
     * @return
     */
    void generate(XSElementDecl element);

}
