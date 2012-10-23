package org.nsesa.editor.app.xsd.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean to keep basic information about class.
 * Date: 05/08/12 13:36
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayClass {

    private String packageName;
    private String nameSpace;
    private String name;
    private String superClassName;

    private String superNameSpace;
    private Class<?>[] interfaces;

    private OverlayType overlayType;

    private List<OverlayProperty> properties = new ArrayList<OverlayProperty>();

    private SimpleTypeRestriction restriction;

    public OverlayClass() {
        this(OverlayType.Unknown);
    }

    public OverlayClass(OverlayType overlayType) {
        this.overlayType = overlayType;
    }

    public SimpleTypeRestriction getRestriction() {
        return restriction;
    }

    public String[] getImports(PackageNameGenerator packageNameGenerator) {
        Set<String> imports = new LinkedHashSet<String>();
        if (superClassName != null) {
            String packageName = packageNameGenerator.getPackageName(superNameSpace);
            imports.add(packageName + "." + StringUtils.capitalize(superClassName));
        }
        for (OverlayProperty property : properties) {
            String packageName = packageNameGenerator.getPackageName(property);
            if ("java.lang".equals(packageName)) {
                continue;
            }
            imports.add(packageName + "." + StringUtils.capitalize(property.getClassName()));
        }
        return imports.toArray(new String[imports.size()]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class<?>[] interfaces) {
        this.interfaces = interfaces;
    }

    public List<OverlayProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<OverlayProperty> properties) {
        this.properties = properties;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setRestriction(SimpleTypeRestriction typeRestriction) {
        this.restriction = typeRestriction;
    }

    public OverlayType getOverlayType() {
        return overlayType;
    }
    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setSuperNameSpace(String superNameSpace) {
        this.superNameSpace = superNameSpace;
    }
    public String getSuperNameSpace() {
        return superNameSpace;
    }

    public boolean isSimple() {
        return OverlayType.SimpleType.equals(overlayType);
    }
    public boolean isComplex() {
        return OverlayType.ComplexType.equals(overlayType);
    }
    public boolean isElement() {
        return OverlayType.Element.equals(overlayType);
    }

    public boolean isEnumeration() {
        return isSimple() && restriction != null &&
                restriction.getEnumeration() != null &&
                restriction.getEnumeration().length > 0;
    }

}
