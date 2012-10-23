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
public class OverlayProperty {

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

    private final String name;
    private String packageName;
    private final String nameSpace;
    private final String className;
    private final boolean collection;
    private final OverlayType overlayType;

    public OverlayProperty(OverlayType overlayType, String packageName,
                           String nameSpace, String className, String name, boolean collection) {
        this.overlayType = overlayType;
        this.name = name;
        this.packageName = packageName;
        this.nameSpace = nameSpace;
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

    public String getNameSpace() {
        return nameSpace;
    }

    public String getClassName() {
        return className;
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

    public OverlayType getOverlayType() {
        return overlayType;
    }

}
