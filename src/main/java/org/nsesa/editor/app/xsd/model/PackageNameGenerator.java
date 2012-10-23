package org.nsesa.editor.app.xsd.model;

/**
 * An interface to generate package name from overlay classes, overlay properties or simple strings
 * User: sgroza
 * Date: 22/10/12
 * Time: 11:44
 */
public interface PackageNameGenerator {
    /**
     * Generates a package name from {@link OverlayClass} object
     * @param overlayClass The overlay class processed
     * @return The package name as String
     */
    abstract String getPackageName(OverlayClass overlayClass);

    /**
     * Generates a package name from {@link OverlayProperty} object
     * @param overlayProperty The overlay property processed
     * @return The package name as String
     */
    abstract String getPackageName(OverlayProperty overlayProperty);

    /**
     * Generates a package name from String
     * @param source The string processed
     * @return The package name as String
     */
    abstract String getPackageName(String source);
}
