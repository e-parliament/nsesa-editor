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
 * A default implementation of the {@link PackageNameGenerator} interface converting the names space of
 * overlay datatypes into a package name.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 22/10/12 11:45
 */
public class PackageNameGeneratorImpl implements PackageNameGenerator {

    /**
     * A map of replacements in package name generation. Helps stripping out illegal characters for the Java package
     * name that gets generated.
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
     * An internal cache used to keep generated package names.
     */
    private Map<String, String> cache = new HashMap<String, String>();

    /**
     * Keeps track of the base package name.
     */
    private String basePackage;

    /**
     * Constructor.
     *
     * @param basePackage The base package name as String
     */
    public PackageNameGeneratorImpl(final String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String getPackageName(final OverlayNode overlayNode) {
        final String source = overlayNode.getNamespaceURI();
        return getPackageName(source);
    }

    @Override
    public String getPackageName(final OverlayClass overlayClass) {
        if (overlayClass.getPackageName() != null) {
            return overlayClass.getPackageName();
        }
        String source = overlayClass.getNamespaceURI();
        return getPackageName(source);
    }

    @Override
    public String getPackageName(final OverlayProperty overlayProperty) {
        if (overlayProperty.getPackageName() != null) {
            return overlayProperty.getPackageName();
        }
        String source = overlayProperty.getNamespaceURI();
        return getPackageName(source);
    }

    @Override
    public String getPackageName(final String namespaceURI) {
        if (namespaceURI == null) {
            return null;
        }
        String packageName = cache.get(namespaceURI);
        if (packageName != null) {
            return packageName;
        }
        final int lastIndex = namespaceURI.lastIndexOf("/");
        packageName = getEligiblePackageName(namespaceURI.substring(lastIndex + 1));
        if (!Character.isJavaIdentifierStart(packageName.charAt(0))) {
            // we are trying second time
            final int previousIndex = namespaceURI.lastIndexOf("/", lastIndex - 1);
            packageName = getEligiblePackageName(namespaceURI.substring(previousIndex + 1));
            if (!Character.isJavaIdentifierStart(packageName.charAt(0))) {
                packageName = "_" + packageName;
            }
        }
        packageName = basePackage + packageName;
        cache.put(namespaceURI, packageName);

        return packageName;

    }

    /**
     * Transforms the given <tt>substr</tt> into a String that can be used as part of a package name in Java.
     *
     * @param substr the substring to cleanup
     * @return the cleaned version of the given <tt>substr</tt>
     */
    private String getEligiblePackageName(String substr) {
        final Set<Map.Entry<String, String>> entries = REPLACEMENTS.entrySet();
        for (final Map.Entry<String, String> entry : entries) {
            substr = substr.replace(entry.getKey(), entry.getValue());
        }
        substr = substr.toLowerCase();
        return substr;
    }
}
