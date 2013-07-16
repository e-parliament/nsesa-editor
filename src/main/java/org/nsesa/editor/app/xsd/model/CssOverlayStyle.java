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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keeps track of the style information for a given overlay class.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 06/11/12 12:46
 */
public class CssOverlayStyle {
    /**
     * Factory to create styles for the given class
     */
    public static class CssOverlayFactory {

        private static CssOverlayFactory FACTORY_INSTANCE = new CssOverlayFactory();

        public static CssOverlayFactory getInstance() {
            return FACTORY_INSTANCE;
        }

        public CssOverlayStyle create(final OverlayClass aClass, final List<CssOverlayStyle> styles) {
            // if we cannot or should not process the class, just generate an empty CSS overlay style.
            if (!canProcess(aClass)) {
                return new CssOverlayStyle();
            }
            final CssOverlayStyle overlayStyle = new CssOverlayStyle(aClass);
            overlayStyle.cssProcess(styles);
            return overlayStyle;
        }

        /**
         * Check whether or not a css style can be generated for a given overlay class.
         * Exclude the ones which are not relevant for generation.
         *
         * @param overlayClass the overlay class to check.
         * @return <tt>true</tt> if the processing is possible for this overlay class.
         */
        private boolean canProcess(OverlayClass overlayClass) {
            if (overlayClass.getNamespaceURI() == null) {
                return false;
            }
            // TODO Stelian (not sure why we're not processing non-AN elements here? - Phil)
            if (!"http://www.akomantoso.org/2.0".equalsIgnoreCase(overlayClass.getNamespaceURI())) {
                return false;
            }
            if (overlayClass instanceof OverlayClassGenerator.OverlayRootClass) {
                return false;
            }
            if (overlayClass instanceof OverlayClassGenerator.OverlaySchemaClass) {
                return false;
            }
            final boolean skipped = EnumSet.of(OverlayType.AttrGroup,
                    OverlayType.Attribute,
                    OverlayType.Group,
                    OverlayType.SimpleType,
                    OverlayType.GroupDecl).contains(overlayClass.getOverlayType());

            return !skipped;
        }
    }

    /**
     * The overlay class we're generating the CSS style for.
     */
    private OverlayClass overlayClass;

    /**
     * The CSS directive name.
     */
    private String name;

    /**
     * The CSS properties for the CSS directive.
     */
    private Map<String, String> values;

    /**
     * Creates an empty overlay style class.
     */
    public CssOverlayStyle() {
    }

    /**
     * Create an default overlay style for the given class, and initialize the CSS properties.
     *
     * @param aClass the overlay class
     */
    public CssOverlayStyle(OverlayClass aClass) {
        this.overlayClass = aClass;
        this.name = aClass.getName();
        this.values = new HashMap<String, String>();
    }

    /**
     * Used in combination with the CSS properties file to override the default (empty) CSS settings.
     *
     * @param name   the CSS class name
     * @param values the CSS properties
     */
    public CssOverlayStyle(String name, Map<String, String> values) {
        this.name = name;
        this.values = values;
    }

    /**
     * Return the CSS directive name (eg. 'paragraph', 'recital', ...).
     *
     * @return the (class) name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the CSS properties for a given {@link #getName()}.
     *
     * @return the CSS properties
     */
    public Map<String, String> getValues() {
        return values;
    }

    /**
     * Generates css values including the ones from the parent class (by walking the parent class).
     *
     * @param styles the accumulator of CSS overlay styles.
     */
    public void cssProcess(final List<CssOverlayStyle> styles) {
        OverlayClass aClass = overlayClass;
        while (aClass != null) {
            for (CssOverlayStyle cssStyle : styles) {
                if (aClass.getName() != null && aClass.getName().equalsIgnoreCase(cssStyle.getName())) {
                    // if the key already exist do not override it
                    for (final Map.Entry<String, String> entry : cssStyle.values.entrySet()) {
                        if (!values.containsKey(entry.getKey())) {
                            values.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
            aClass = aClass.getParent();
        }
    }

    /**
     * Generates a CSS valid String representation for this CSS overlay style instance.
     *
     * @return the CSS representation.
     */
    public String toString() {
        final String delimiter = ";";
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" {").append("\n");
        if (values != null) {
            for (final Map.Entry<String, String> entry : values.entrySet()) {
                sb.append("\t").append(entry.getKey()).append(":").append(entry.getValue()).append(delimiter).append("\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

}