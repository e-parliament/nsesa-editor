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
            put("extends", "extendz");
            put("content", "contentz");
            put("type", "typez");
        }
    };

    private String packageName;
    private final boolean collection;

    public OverlayProperty(OverlayType overlayType, String packageName,
                           String nameSpace, String className, String name, boolean collection) {
        super(name, nameSpace, overlayType);
        this.packageName = packageName;
        this.className = className;
        this.collection = collection;
    }

    public String getName() {
        String result = REPLACEMENT_NAMES.get(name);
        if (result == null) {
            result = name;
        }
        return result;
    }

    public boolean isCollection() {
        return collection;
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
}
