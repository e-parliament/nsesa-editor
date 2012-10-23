package org.nsesa.editor.app.xsd.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of {@link PackageNameGenerator} interface converting the names spaces into a package
 * User: sgroza
 * Date: 22/10/12
 * Time: 11:45
 */
public class PackageNameGeneratorImpl implements PackageNameGenerator {

    /** a map of replacements in package name generation**/
    private static Map<String, String> REPLACEMENTS = new HashMap<String, String>() {
        {
            put("www.", "");
            put(".org", "");
            put("/", "_");
            put(".", "_");
        }
    };
    /** an internal cache used to keep generated package names**/
    private Map<String, String> cache = new HashMap<String, String>();

    /** keep the base package name **/
    private String basePackage;

    /**
     * Constructor
     * @param basePackage The base package name as String
     */
    public PackageNameGeneratorImpl(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String getPackageName(OverlayClass overlayClass) {
        if (overlayClass.getPackageName() != null) {
            return overlayClass.getPackageName();
        }
        String source = overlayClass.getNameSpace();

        return getPackageName(source);
    }

    @Override
    public String getPackageName(OverlayProperty overlayProperty) {
        if (overlayProperty.getPackageName() != null) {
            return overlayProperty.getPackageName();
        }
        String source = overlayProperty.getNameSpace();
        return getPackageName(source);
    }

    @Override
    public String getPackageName(String nameSpace) {
        String packageName = cache.get(nameSpace);
        if (packageName != null) {
            return packageName;
        }

        int lastIndex = nameSpace.lastIndexOf("/");
        // get previous one
        int previousIndex = nameSpace.lastIndexOf("/", lastIndex - 1);
        packageName = nameSpace.substring(previousIndex + 1);
        Set<Map.Entry<String, String>> entries = REPLACEMENTS.entrySet();
        for(Map.Entry<String, String> entry : entries) {
            packageName = packageName.replace(entry.getKey(), entry.getValue());
        }
        packageName = packageName.toLowerCase();

        if (Character.isDigit(packageName.charAt(0))) {
            packageName = "_" + packageName;
        };
        packageName = basePackage + packageName;
        cache.put(nameSpace, packageName);

        return packageName;

    }
}
