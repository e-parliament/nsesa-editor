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
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 05/08/12 13:36
 */
public class OverlayClass extends OverlayNode {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayClass.class);
    /**
     * Compare 2 overlay classes by their name
     */
    public static final Comparator<OverlayClass> DEFAULT_COMPARATOR = new Comparator<OverlayClass>() {
        @Override
        public int compare(OverlayClass o1, OverlayClass o2) {
            int result = o1.name.compareTo(o2.name);
            return result;
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
     * @param name
     * @param nameSpace
     * @param overlayType
     */
    public OverlayClass(String name, String nameSpace, OverlayType overlayType) {
        super(name, nameSpace, overlayType);
        this.properties = new ArrayList<OverlayProperty>();
        this.children = new ArrayList<OverlayClass>();
        this.flatProperties = new ArrayList<OverlayProperty>();
    }

    /**
     * Returns the possible overlay subclasses as a list. An overlay subclass is a <code>java subclass</code> of
     * <code>java class</code> linked to this overlay class
     * @return
     */
    public List<OverlayClass> getChildren() {
        return children;
    }

    /**
     * Returns a list of ordered children by using <code>DEFAULT_COMPARATOR</code>
     * @return
     */
    public List<OverlayClass> getOrderedChildren() {
        Collections.sort(children, OverlayClass.DEFAULT_COMPARATOR);
        return children;
    }

    /**
     * Set a list of overlay subclasses
     * @param children
     */
    public void setChildren(List<OverlayClass> children) {
        this.children = children;
    }

    /**
     * Returns <code>SimpleTypeRestriction</code> if the xsd representation is a simple type restriction
     * @return
     */
    public SimpleTypeRestriction getRestriction() {
        return restriction;
    }

    /**
     * Returns a list of <code>OverlayProperty</code>. An <code>OverlayProperty</code> works like a layer between
     * <code>java property</code> and <code>xsd attributes/xsd elements</code>
     * @return
     */
    public List<OverlayProperty> getProperties() {
        return properties;
    }

    /**
     * Set the list of <code>OverlayProperty</code>
     * @param properties
     */
    public void setProperties(List<OverlayProperty> properties) {
        this.properties = properties;
    }

    /**
     * Returns the parent of <code>OverlayClass</code>. The parent is defined as the <code>OverlayClass</code> linked to
     * <code>java superclass</code>
     * @return
     */
    public OverlayClass getParent() {
        return parent;
    }

    /**
     * Set the parent of <code>OverlayClass</code>
     * @param parent
     */
    public void setParent(OverlayClass parent) {
        this.parent = parent;
    }

    /**
     * Checks to see whether or not this overlay class is descendent of given parent
     * @param parentName Parent Name as String
     * @return True when the overlay class is descendent of given parent
     */
    public boolean isDescendentOf(String parentName) {
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
     * utility method to generate a list of <code>java import</code>statements based on
     * the its parent and properties
     * @param packageNameGenerator
     * @return An array of String
     */
    public String[] getImports(PackageNameGenerator packageNameGenerator) {
        Set<String> imports = new LinkedHashSet<String>();
        if (parent != null && (parent.isComplex() || parent.isElement() || parent.isSimple())) {
            String packageName = packageNameGenerator.getPackageName(parent);
            imports.add(packageName + "." + StringUtils.capitalize(parent.getClassName()));
        }
        for (OverlayProperty property : getFlatProperties()) {
            String packageName = packageNameGenerator.getPackageName(property);
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
            for (OverlayProperty property : getParent().getAllFlatAttributesProperties()) {
                String packageName = packageNameGenerator.getPackageName(property);
                if ("java.lang".equals(packageName)) {
                    continue;
                }
                imports.add(packageName + "." + StringUtils.capitalize(property.getClassName()));
            }
        }


        return imports.toArray(new String[imports.size()]);
    }

    /**
     * Returns possible interfaces of <code>java class</code> linked to <code>OverlayClass</code>
     * @return
     */
    public List<String> getInterfaces() {
        return interfaces;
    }

    /**
     * Set the possible java interfaces of this <code>OverlayClass</>
     * @param interfaces
     */
    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * Returns the package name of <code>java class</code> linked to this <code>OverlayClass</code>
     * @return
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Set the package name
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Process <code>OverlayClass</code> by passing it as parameter to the <code>OverlayClassProcessor</code>
     * @param processor
     */
    public void process(OverlayClassProcessor processor) {
        processor.process(this);
    }

    /**
     * Set XSd restriction if the case
     * @param typeRestriction
     */
    public void setRestriction(SimpleTypeRestriction typeRestriction) {
        this.restriction = typeRestriction;
    }

    /**
     * Check to see whether or not <code>java class</code> associated to <code>OverlayClass</code> can be treated as
     * <code>java enum</code>
     * @return
     */
    public boolean isEnumeration() {
        return isSimple() && restriction != null &&
                restriction.getEnumeration() != null &&
                restriction.getEnumeration().size() > 0;
    }

    /**
     * Returns true when it contains wild card properties
     *
     * @return
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

    public Set<String> getAllowedSubTypes() {
        Set<String> set = new HashSet<String>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            for (OverlayProperty property : aClass.getProperties()) {
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

    public List<OverlayProperty> getFlatProperties() {
        return flatProperties;
    }

    public void setFlatProperties(List<OverlayProperty> flatProperties) {
        this.flatProperties = flatProperties;
    }

    /**
     * Returns a list with all properties marked as <code>attribute</code> from <code>OverlayClass</code>
     * and its parents
     * @return A List of <code>OverlayProperty</code>
     */
    public List<OverlayProperty> getAllFlatAttributesProperties() {
        OverlayProperty.Filter filter = new OverlayProperty.Filter() {
            @Override
            public boolean apply(OverlayProperty property) {
                return property.isAttribute();
            }
        };
        return getAllFilteredProperties(filter);
    }

    /**
     * Returns a list with all properties marked as <code>attribute</code> from <code>OverlayClass</code>
     * and its parents
     * @return A List of <code>OverlayProperty</code>
     */
    public List<OverlayProperty> getAllStructureProperties() {
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            for (OverlayProperty property : aClass.getProperties()) {
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
     * Filter the <code>OverlayProperty</code> properties by using the given filter
     * @param filter The filter to be used
     * @return A List of filtered properties
     */
    private List<OverlayProperty> getAllFilteredProperties(OverlayProperty.Filter filter) {
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        OverlayClass aClass = this;
        while (aClass != null && (aClass.isComplex() || aClass.isSimple() || aClass.isElement())) {
            for (OverlayProperty property : aClass.getFlatProperties()) {
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
        if (nameSpace != null ? !nameSpace.equals(that.nameSpace) : that.nameSpace != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
