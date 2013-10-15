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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * A <code>OverlayClass</code> is a layer between a <code>java class</code> and an <code>xsd node</code>.
 * It holds all the information required in order to generate a <code>Java</code>class.
 * Besides providing access to the hierarchy it also provides some convenience methods for accessing
 * certain sets of information.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 05/08/12 13:36
 */
public class OverlayClass extends OverlayNode {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayClass.class);
    /**
     * Compare 2 overlay classes by their name
     */
    public static final Comparator<OverlayClass> DEFAULT_COMPARATOR = new Comparator<OverlayClass>() {
        @Override
        public int compare(OverlayClass o1, OverlayClass o2) {
            return o1.name.compareTo(o2.name);
        }
    };


    private String packageName;
    private List<String> interfaces;
    private SimpleTypeRestriction restriction;

    private OverlayClass parent;

    private List<OverlayClass> children;
    private List<OverlayProperty> properties;
    private List<OverlayProperty> flatProperties;


    /**
     * Constructs an empty <code>OverlayClass</code>
     */
    public OverlayClass() {
        super();
        this.properties = new ArrayList<OverlayProperty>();
        this.children = new ArrayList<OverlayClass>();
        this.flatProperties = new ArrayList<OverlayProperty>();
    }

    /**
     * Constructs an <code>OverlayClass</code> with the given name, namespace and overlaytype
     *
     * @param name         the local node name
     * @param namespaceURI the namespace
     * @param overlayType  the type of overlay
     */
    public OverlayClass(String name, String namespaceURI, OverlayType overlayType) {
        super(name, namespaceURI, overlayType);
        this.properties = new ArrayList<OverlayProperty>();
        this.children = new ArrayList<OverlayClass>();
        this.flatProperties = new ArrayList<OverlayProperty>();
    }

    /**
     * Returns the possible overlay subclasses as a list. An overlay subclass is a <code>java subclass</code> of
     * <code>java class</code> linked to this overlay class
     *
     * @return the child overlay classes
     */
    public List<OverlayClass> getChildren() {
        return children;
    }

    /**
     * Returns a list of ordered children by using {@link OverlayClass#DEFAULT_COMPARATOR}.
     *
     * @return the list of children, order
     */
    public List<OverlayClass> getOrderedChildren() {
        Collections.sort(children, OverlayClass.DEFAULT_COMPARATOR);
        return children;
    }

    /**
     * Set a list of overlay subclasses
     *
     * @param children the children.
     */
    public void setChildren(List<OverlayClass> children) {
        this.children = children;
    }

    /**
     * Returns <code>SimpleTypeRestriction</code> if the xsd representation is a simple type restriction
     *
     * @return the restriction.
     */
    public SimpleTypeRestriction getRestriction() {
        return restriction;
    }

    /**
     * Returns a list of <code>OverlayProperty</code>. An <code>OverlayProperty</code> works like a layer between
     * <code>java property</code> and <code>xsd attributes/xsd elements</code>
     *
     * @return the properties
     */
    public List<OverlayProperty> getProperties() {
        return properties;
    }

    /**
     * Set the list of <code>OverlayProperty</code>
     *
     * @param properties the properties
     */
    public void setProperties(List<OverlayProperty> properties) {
        this.properties = properties;
    }

    /**
     * Returns the parent of <code>OverlayClass</code>. The parent is defined as the <code>OverlayClass</code> linked to
     * <code>java superclass</code>.
     *
     * @return the parent class.
     */
    public OverlayClass getParent() {
        return parent;
    }

    /**
     * Set the parent of <code>OverlayClass</code>
     *
     * @param parent the parent overlay class.
     */
    public void setParent(OverlayClass parent) {
        this.parent = parent;
    }

    /**
     * Checks to see whether or not this overlay class is descendant of the given parent. Used in the
     * <tt>overlayCssVisual.ftl</tt> freemarker file.
     *
     * @param parentName the parent's name as a String
     * @return <tt>true</tt> when the overlay class is a descendant of the given parent
     */
    public boolean isDescendantOf(String parentName) {
        boolean result = false;
        OverlayClass nodeParent = this;
        while (nodeParent != null) {
            if (parentName.equalsIgnoreCase(nodeParent.getName())) {
                result = true;
                break;
            }
            nodeParent = nodeParent.getParent();
        }
        return result;
    }

    /**
     * Utility method to generate a list of <code>java import</code> statements based on
     * its parent and properties.
     *
     * @param packageNameGenerator the name generator for the package
     * @return An array of String
     */
    public String[] getImports(final PackageNameGenerator packageNameGenerator) {
        final Set<String> imports = new LinkedHashSet<String>();
        if (parent != null && (parent.isComplex() || parent.isElement() || parent.isSimple())) {
            final String packageName = packageNameGenerator.getPackageName(parent);
            imports.add(packageName + "." + StringUtils.capitalize(parent.getClassName()));
        }
        for (OverlayProperty property : getFlatProperties()) {
            final String packageName = packageNameGenerator.getPackageName(property);
            if ("java.lang".equals(packageName)) {
                continue;
            }
            if (property.isAllIndicator() || property.isChoiceIndicator() || property.isSequenceIndicator()) {
                continue;
            }
            imports.add(packageName + "." + StringUtils.capitalize(property.getClassName()));
        }
        // add imports for parent properties
        if (getParent() != null) {
            for (final OverlayProperty property : getParent().getAllFlatAttributesProperties()) {
                final String packageName = packageNameGenerator.getPackageName(property);
                if ("java.lang".equals(packageName)) {
                    continue;
                }
                imports.add(packageName + "." + StringUtils.capitalize(property.getClassName()));
            }
        }
        return imports.toArray(new String[imports.size()]);
    }

    /**
     * Returns possible interfaces of <code>java class</code> linked to <code>OverlayClass</code>.
     *
     * @return the list of interface names
     */
    public List<String> getInterfaces() {
        return interfaces;
    }

    /**
     * Set the possible java interfaces of this <code>OverlayClass</>.
     *
     * @param interfaces the interface names
     */
    public void setInterfaces(final List<String> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * Returns the package name of <code>java class</code> linked to this <code>OverlayClass</code>.
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

    /**
     * Process <code>OverlayClass</code> by passing it as parameter to the <code>OverlayClassProcessor</code>
     *
     * @param processor the overlay class processor
     */
    public void process(final OverlayClassProcessor processor) {
        processor.process(this);
    }

    /**
     * Set XSD restriction if specified
     *
     * @param typeRestriction the simple type restriction
     */
    public void setRestriction(final SimpleTypeRestriction typeRestriction) {
        this.restriction = typeRestriction;
    }

    /**
     * Check to see whether or not <code>java class</code> associated to <code>OverlayClass</code> can be treated as
     * <code>java enum</code>
     *
     * @return <tt>true</tt> if it is an enumeration.
     */
    public boolean isEnumeration() {
        return isSimple() && restriction != null &&
                restriction.getEnumeration() != null &&
                restriction.getEnumeration().size() > 0;
    }

    /**
     * Returns true when the property contains a wild card.
     *
     * @return <tt>true</tt> if at least one property of the overlay class specifies a wildcard
     */
    public boolean hasWildCardProperties() {
        boolean result = false;
        for (OverlayProperty property : properties) {
            if (property.isWildCard()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the list of allowed sub types for this overlay class.
     *
     * @return the list of subtype element names that are allowed to be nested.
     */
    public Set<String> getAllowedSubTypes() {
        final Set<String> set = new HashSet<String>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            for (final OverlayProperty property : aClass.getProperties()) {
                if (property.isAttribute()) {
                    continue;
                }
                if (property.isWildCard()) {
                    set.add("*");
                } else {
                    set.add(property.getClassName());
                }
            }
            aClass = aClass.getParent();
        }
        return set;
    }

    /**
     * Returns the simple properties for this overlay class.
     *
     * @return the properties
     */
    public List<OverlayProperty> getFlatProperties() {
        return flatProperties;
    }

    /**
     * Set the properties on this overlay class.
     *
     * @param flatProperties the properties
     */
    public void setFlatProperties(final List<OverlayProperty> flatProperties) {
        this.flatProperties = flatProperties;
    }

    /**
     * Returns a list with all properties marked as <code>attribute</code> from <code>OverlayClass</code>
     * and its parents
     *
     * @return A List of <code>OverlayProperty</code>
     */
    public List<OverlayProperty> getAllFlatAttributesProperties() {
        final OverlayProperty.Filter filter = new OverlayProperty.Filter() {
            @Override
            public boolean apply(OverlayProperty property) {
                return property.isAttribute();
            }
        };
        return getAllFilteredProperties(filter);
    }

    /**
     * Returns a list with all properties marked as <code>attribute</code> from this <code>OverlayClass</code>
     * and its parents.
     *
     * @return the list of <code>OverlayProperty</code>s that are not supposed to be treated as attributes, but rather
     */
    public List<OverlayProperty> getAllStructureProperties() {
        final List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            for (final OverlayProperty property : aClass.getProperties()) {
                if (!property.isAttribute()) {
                    result.add(property);
                }
            }
            aClass = aClass.getParent();
        }
        return result;
    }

    /**
     * Returns a list with all properties marked as <code>non attribute</code> from <code>OverlayClass</code>
     * and its parents
     *
     * @return A List of <code>OverlayProperty</code>
     */
    public List<OverlayProperty> getAllNonAttributesProperties() {
        OverlayProperty.Filter filter = new OverlayProperty.Filter() {
            @Override
            public boolean apply(OverlayProperty property) {
                return !property.isAttribute();
            }
        };
        return getAllFilteredProperties(filter);
    }

    /**
     * Filter the <code>OverlayProperty</code> properties by using the given {@link OverlayProperty.Filter}.
     *
     * @param filter The filter to be used
     * @return A List of filtered properties
     */
    private List<OverlayProperty> getAllFilteredProperties(OverlayProperty.Filter filter) {
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            final List<OverlayProperty> flatProperties = aClass.getFlatProperties();
            Collections.sort(flatProperties, new Comparator<OverlayProperty>() {
                @Override
                public int compare(OverlayProperty overlayProperty, OverlayProperty overlayProperty2) {

                    assert overlayProperty2 != null;
                    assert overlayProperty2.getName() != null;

                    return overlayProperty.getName().compareTo(overlayProperty2.getName());
                }
            });
            for (OverlayProperty property : flatProperties) {
                if (filter.apply(property)) {
                    result.add(property);
                }
            }
            aClass = aClass.getParent();
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + '\'' +
                "OverlayClass{" +
                "packageName='" + packageName + '\'' +
                ", interfaces=" + (interfaces == null ? null : Arrays.asList(interfaces)) +
                ", restriction=" + restriction +
                ", parent=" + parent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OverlayClass)) return false;
        OverlayClass that = (OverlayClass) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (namespaceURI != null ? !namespaceURI.equals(that.namespaceURI) : that.namespaceURI != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }
}
