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

import java.util.HashMap;
import java.util.Map;

/**
 * A <code>OverlayProperty</code> is a layer between a <code>java property</code> and an <code>xsd node</code>.
 * It holds all the information required in order to generate a <code>Java property</code>
 * for a <code>java class</code> encapsulated into <code>OverlayClass</code>.
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 05/08/12 16:00
 */
public class OverlayProperty extends OverlayNode {

    /**
     * A map of replacements used when getting the name of the property to avoid
     * some collisions that might occur when the overlay class extends a base class
     */
    private static Map<String, String> REPLACEMENT_NAMES = new HashMap<String, String>() {
        {
            put("extends", "extend_");
            put("content", "content_");
            put("title", "title_");
        }
    };

    private String packageName;
    private boolean collection;
    private final boolean attribute;
    // the base class
    private OverlayClass baseClass;

    private Integer minOccurs = new Integer(0);
    private Integer maxOccurs = new Integer(-1);

    private boolean required;

    /**
     * Constructs a <code>OverlayProperty</code> with the given parameters
     * @param overlayType The <code></>
     * @param packageName The <code>java package name</code> as String
     * @param nameSpace The nameSpace as String
     * @param className The <code>java class name</code>as String
     * @param name The <code>xsd name</code> as String
     * @param collection True when it is <code>java collection</code>
     * @param attribute True when it is <code>xsd attribute</code>
     */
    public OverlayProperty(OverlayType overlayType,
                           String packageName,
                           String nameSpace,
                           String className,
                           String name,
                           boolean collection,
                           boolean attribute) {
        super(name, nameSpace, overlayType);
        this.packageName = packageName;
        this.className = className;
        this.collection = collection;
        this.attribute = attribute;
    }

    /**
     * Simple way to clone an overlay property object
     * @return a new instance of <code>OverlayProperty</code> class
     */
    public OverlayProperty copy() {
        OverlayProperty newProperty = new OverlayProperty(overlayType, packageName, nameSpace,
                className, name, collection, attribute);
        newProperty.setBaseClass(getBaseClass());
        newProperty.setMinOccurs(getMinOccurs());
        newProperty.setMaxOccurs(getMaxOccurs());
        newProperty.setRequired(isRequired());
        return newProperty;
    }

    /**
     * Returns the <code>allowed java property name</code>
     * @return Java name as String
     */
    public String getJavaName() {
        String propName = name + (isAttribute() ? "Attr" : "");
        String result = REPLACEMENT_NAMES.get(propName);
        if (result == null) {
            result = propName;
        }
        return result;
    }

    /**
     * Get <code>xsd name</code>
     * @return name as String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public boolean isCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     */
    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    /**
     *
     * @return
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "OverlayProperty{" +
                "packageName='" + packageName + '\'' +
                ", collection=" + collection +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OverlayProperty)) return false;
        OverlayProperty that = (OverlayProperty) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameSpace != null ? !nameSpace.equals(that.nameSpace) : that.nameSpace != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    /**
     * Returns True when it is <code>xsd attribute</code>
     * @return
     */
    public boolean isAttribute() {
        return attribute;
    }

    /**
     * Returns the base class of the property
     * @return
     */
    public OverlayClass getBaseClass() {
        return baseClass;
    }

    /**
     * Set <code>java class</code> of the property encapsulated in <code>OverlayClass</code>
     * @param baseClass
     */
    public void setBaseClass(OverlayClass baseClass) {
        this.baseClass = baseClass;
    }

    /**
     * Set <code>xsd min occurs value</code>
     * @param minOccurs
     */
    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    /**
     * Set <code>xsd max occurs value</code>
     * @param maxOccurs
     */
    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    /**
     * Returns <code>xsd min occurs value</code>
     * @return
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * Returns <code>xsd max occurs value</code>
     * @return
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    /**
     *  Check whether or not is <code>xsd required</code>
     * @return
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Set True when is <code>xsd required</code>
     * @param required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * An interface to filter  <code>OverlayProperty</code>
     */
    public static interface Filter {
        /**
         * Returns true when the <code>OverlayProperty</code> satisfy filter conditions
         * @param property The property to be checked
         * @return True when the property satisfy filter conditions
         */
        boolean apply(OverlayProperty property);
    }
}
