package org.nsesa.editor.app.xsd.model;

/**
 * Date: 05/08/12 16:00
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayProperty {
    private final String name;
    private final String packageName;
    private final String className;
    private final boolean collection;

    public OverlayProperty(String packageName, String className, String name, boolean collection) {
        this.name = name;
        this.packageName = packageName;
        this.className = className;
        this.collection = collection;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public boolean isCollection() {
        return collection;
    }
}
