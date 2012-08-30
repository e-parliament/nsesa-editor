package org.nsesa.editor.app.xsd.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Date: 05/08/12 13:36
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayClass {
    private String packageName;
    private String name;
    private String superClassName;
    private Class<?>[] interfaces;

    private List<OverlayProperty> properties = new ArrayList<OverlayProperty>();

    public OverlayClass() {
    }

    public String getFullyQualifiedClassName() {
        return packageName + "." + name;
    }

    public String[] getImports() {
        Set<String> imports = new LinkedHashSet<String>();
        for (OverlayProperty property : properties) {
            if (!"java.lang".equals(property.getPackageName())) {
                imports.add(property.getPackageName());
            }
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
}
