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

/**
 * An interface to generate the <code>java</code> package name from overlay datatypes.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 22/10/12 11:44
 */
public interface PackageNameGenerator {

    /**
     * Generates a package name from {@link OverlayNode} object
     *
     * @param overlayNode The overlay node processed
     * @return The package name as String
     */
    String getPackageName(OverlayNode overlayNode);

    /**
     * Generates a package name from {@link OverlayClass} object
     *
     * @param overlayClass The overlay class processed
     * @return The package name as String
     */
    String getPackageName(OverlayClass overlayClass);

    /**
     * Generates a package name from {@link OverlayProperty} object
     *
     * @param overlayProperty The overlay property processed
     * @return The package name as String
     */
    String getPackageName(OverlayProperty overlayProperty);

    /**
     * Generates a package name from String
     *
     * @param source The string processed
     * @return The package name as String
     */
    String getPackageName(String source);

}
