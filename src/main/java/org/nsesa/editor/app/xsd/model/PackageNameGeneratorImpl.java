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
import java.util.Set;

/**
 * A default implementation of {@link PackageNameGenerator} interface converting the names space of
 * overlay datatypes into a package names.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 22/10/12 11:45
 */
public class PackageNameGeneratorImpl implements PackageNameGenerator {

    /**
     * a map of replacements in package name generation*
     */
    private static Map<String, String> REPLACEMENTS = new HashMap<String, String>() {
        {
            put("www.", "");
            put(".org", "");
            put("/", "");
            put(".", "");
        }
    };
    /**
     * an internal cache used to keep generated package names*
     */
    private Map<String, String> cache = new HashMap<String, String>();

    /**
     * keep the base package name *
     */
    private String basePackage;

    /**
     * Constructor
     *
     * @param basePackage The base package name as String
     */
    public PackageNameGeneratorImpl(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String getPackageName(OverlayNode overlayNode) {
        String source = overlayNode.getNameSpace();
        return getPackageName(source);
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
        if (nameSpace == null) {
            return null;
        }
        String packageName = cache.get(nameSpace);
        if (packageName != null) {
            return packageName;
        }
        int lastIndex = nameSpace.lastIndexOf("/");
        packageName = getEligiblePackageName(nameSpace.substring(lastIndex + 1));
        if (!Character.isJavaIdentifierStart(packageName.charAt(0))) {
            // we are trying second time
            int previousIndex = nameSpace.lastIndexOf("/", lastIndex - 1);
            packageName = getEligiblePackageName(nameSpace.substring(previousIndex + 1));
            if (!Character.isJavaIdentifierStart(packageName.charAt(0))) {
                packageName = "_" + packageName;
            }
            ;
        }
        packageName = basePackage + packageName;
        cache.put(nameSpace, packageName);

        return packageName;

    }

    private String getEligiblePackageName(String substr) {
        Set<Map.Entry<String, String>> entries = REPLACEMENTS.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            substr = substr.replace(entry.getKey(), entry.getValue());
        }
        substr = substr.toLowerCase();
        return substr;
    }
}
