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
package org.nsesa.editor.app.xsd.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A <code>OverlayProperty</code> is a layer between a <code>java property</code> and an <code>xsd node</code>.
 * It holds all the information required in order to generate a <code>Java property</code>
 * for a <code>java class</code> encapsulated into <code>OverlayClass</code>.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 05/08/12 16:00
 */
public class OverlayProperty extends OverlayNode {

    /**
     * A map of replacements used when getting the name of the property to avoid
     * some collisions that might occur when the overlay class extends a base class.
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

    private Integer minOccurs = 0;
    private Integer maxOccurs = -1;

    private boolean required;

    /**
     * Constructs a <code>OverlayProperty</code> with the given parameters.
     *
     * @param overlayType  The the property type
     * @param packageName  The <code>java package name</code> as String
     * @param namespaceURI The namespaceURI as String
     * @param className    The <code>java class name</code>as String
     * @param name         The <code>xsd name</code> as String
     * @param collection   True when it is <code>java collection</code>
     * @param attribute    True when it is <code>xsd attribute</code>
     */
    public OverlayProperty(final OverlayType overlayType,
                           final String packageName,
                           final String namespaceURI,
                           final String className,
                           final String name,
                           final boolean collection,
                           final boolean attribute) {
        super(name, namespaceURI, overlayType);
        this.packageName = packageName;
        this.className = className;
        this.collection = collection;
        this.attribute = attribute;
    }

    /**
     * Simple way to clone an overlay property object.
     *
     * @return a new instance of <code>OverlayProperty</code> class
     */
    public OverlayProperty copy() {
        final OverlayProperty newProperty = new OverlayProperty(overlayType, packageName, namespaceURI,
                className, name, collection, attribute);
        newProperty.setBaseClass(getBaseClass());
        newProperty.setMinOccurs(getMinOccurs());
        newProperty.setMaxOccurs(getMaxOccurs());
        newProperty.setRequired(isRequired());
        return newProperty;
    }

    /**
     * Returns the <code>allowed java property name</code>.
     *
     * @return Java name as String
     */
    public String getJavaName() {
        final String propName = name + (isAttribute() ? "Attr" : "");
        String result = REPLACEMENT_NAMES.get(propName);
        if (result == null) {
            result = propName;
        }
        return result;
    }

    /**
     * Check if the property represents a collection or not.
     *
     * @return <tt>true</tt> if the property represents a collection.
     */
    public boolean isCollection() {
        return collection;
    }

    /**
     * Set the flag if this property represents a collection.
     *
     * @param isCollection <tt>true</tt> if this is a collection
     */
    public void setCollection(final boolean isCollection) {
        this.collection = isCollection;
    }

    /**
     * Return the package name.
     *
     * @return the package name
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Set the package name.
     *
     * @param packageName the package name
     */
    public void setPackageName(final String packageName) {
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
        if (namespaceURI != null ? !namespaceURI.equals(that.namespaceURI) : that.namespaceURI != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }

    /**
     * Check if this is an <code>xsd attribute</code>.
     *
     * @return <tt>true</tt> if this property represents an attribute
     */
    public boolean isAttribute() {
        return attribute;
    }

    /**
     * Returns the base class of the property.
     *
     * @return the base class of this property
     */
    public OverlayClass getBaseClass() {
        return baseClass;
    }

    /**
     * Set <code>java class</code> of the property encapsulated in <code>OverlayClass</code>.
     *
     * @param baseClass the base class
     */
    public void setBaseClass(final OverlayClass baseClass) {
        this.baseClass = baseClass;
    }

    /**
     * Set <code>xsd min occurs value</code>.
     *
     * @param minOccurs the min occurs value
     */
    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    /**
     * Set <code>xsd max occurs value</code>.
     *
     * @param maxOccurs the max occurs value
     */
    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    /**
     * Returns <code>xsd min occurs value</code>.
     *
     * @return the min occurs value
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * Returns <code>xsd max occurs value</code>.
     *
     * @return the max occurs value
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * Check whether or not is <code>xsd required</code>.
     *
     * @return <tt>true</tt> if this property is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Check if this property represents a sequence or group.
     *
     * @return <tt>true</tt> if it represents a group of sequence
     */
    public boolean isSequenceIndicator() {
        return overlayType.equals(OverlayType.GroupSequence);
    }

    /**
     * Check if this property represents all possibilities (nor group not choice)
     *
     * @return <tt>true</tt> if it represents all
     */
    public boolean isAllIndicator() {
        return overlayType.equals(OverlayType.GroupAll);
    }

    /**
     * Check if this property represents a choice.
     *
     * @return <tt>true</tt> if it represents a choice
     */
    public boolean isChoiceIndicator() {
        return overlayType.equals(OverlayType.GroupChoice);
    }

    /**
     * Check if this property represents a group.
     *
     * @return <tt>true</tt> if it represents a group
     */
    public boolean isGroupIndicator() {
        return overlayType.equals(OverlayType.Group) || overlayType.equals(OverlayType.GroupDecl)
                || overlayType.equals(OverlayType.AttrGroup);
    }

    /**
     * Set the flag if this property is <code>xsd required</code>
     *
     * @param required <tt>true</tt> if it is required
     */
    public void setRequired(final boolean required) {
        this.required = required;
    }

    /**
     * An interface to filter <code>OverlayProperty</code>s.
     */
    public static interface Filter {
        /**
         * Returns true when the <code>OverlayProperty</code> satisfy filter conditions
         *
         * @param property The property to be checked
         * @return True when the property satisfy filter conditions
         */
        boolean apply(OverlayProperty property);
    }

}
