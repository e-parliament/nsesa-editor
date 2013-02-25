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
 * Bean to keep basic information about properties of the overlay class
 * Date: 05/08/12 16:00
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
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

    // simple way to clone an overlay property
    public OverlayProperty copy() {
        OverlayProperty newProperty = new OverlayProperty(overlayType, packageName, nameSpace, className, name, collection, attribute);
        newProperty.setBaseClass(getBaseClass());
        newProperty.setMinOccurs(getMinOccurs());
        newProperty.setMaxOccurs(getMaxOccurs());
        newProperty.setRequired(isRequired());
        return newProperty;
    }

    public String getJavaName() {
        String propName = name + (isAttribute() ? "Attr" : "");
        String result = REPLACEMENT_NAMES.get(propName);
        if (result == null) {
            result = propName;
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public String getPackageName() {
        return packageName;
    }

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

    public boolean isAttribute() {
        return attribute;
    }

    public OverlayClass getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(OverlayClass baseClass) {
        this.baseClass = baseClass;
    }

    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }


    public Integer getMinOccurs() {
        return minOccurs;
    }

    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public static interface Filter {
        boolean apply(OverlayProperty property);
    }
}
