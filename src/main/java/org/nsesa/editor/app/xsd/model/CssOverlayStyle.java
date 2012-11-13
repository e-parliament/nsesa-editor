package org.nsesa.editor.app.xsd.model;

import java.util.*;

/**
 * Keeps style information for a given overlay class
 * User: sgroza
 * Date: 06/11/12
 * Time: 12:46
 */
 public class CssOverlayStyle {
    /**
     *   Factory to create styles for the given class
     */
    public static class CssOverlayFactory {
        private static CssOverlayFactory instance = new CssOverlayFactory();
        public static CssOverlayFactory getInstance() {
            return instance;
        }
        public CssOverlayStyle create(OverlayClass aClass, List<CssOverlayStyle> styles) {
            if (!canProcess(aClass)) {
                return new CssOverlayStyle(null, null);
            }
            CssOverlayStyle overlayStyle = new CssOverlayStyle(aClass);
            overlayStyle.cssProcess(styles);
            return overlayStyle;
        }

        /**
         * Check whether or not a css style can be generated for a given overlay class.
         * Exclude the ones which are not relevant for generation.
         * @param overlayClass
         * @return
         */
        private boolean canProcess(OverlayClass overlayClass) {
            if (overlayClass.getNameSpace() == null) {
                return false;
            }
            if (!"http://www.akomantoso.org/2.0".equalsIgnoreCase(overlayClass.getNameSpace())) {
                return false;
            }
            if (overlayClass instanceof OverlayClassGenerator.OverlayRootClass) {
                return false;
            }
            if (overlayClass instanceof OverlayClassGenerator.OverlaySchemaClass) {
                return false;
            }
            boolean skipped =  EnumSet.of(OverlayType.AttrGroup,
                    OverlayType.Attribute,
                    OverlayType.Group,
                    OverlayType.SimpleType,
                    OverlayType.GroupDecl).contains(overlayClass.getOverlayType());

            return !skipped;
        }

    }


    private OverlayClass overlayClass;
    private String name;
    private Map<String, String> values;

    CssOverlayStyle(OverlayClass aClass) {
        this.overlayClass = aClass;
        this.name = aClass.getName();
        this.values = new HashMap<String, String>();
    }

    public CssOverlayStyle(String name, Map<String, String> values) {
        this.name = name;
        this.values = values;

    }

    public String getName() {
        return name;
    }

    public Map<String, String> getValues() {
        return values;
    }

    /**
     * Generates css values including the ones from parent class
     * @param styles
     */
    public void cssProcess(List<CssOverlayStyle> styles) {
        OverlayClass aClass = overlayClass;
        while(aClass != null) {
            for(CssOverlayStyle cssStyle : styles) {
                if (aClass.getName() != null && aClass.getName().equalsIgnoreCase(cssStyle.getName())) {
                    // if the key already exist do not override it
                    for (Map.Entry<String, String> entry : cssStyle.values.entrySet()) {
                        if (!values.containsKey(entry.getKey())) {
                            values.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
            aClass = aClass.getParent();
        }
    };

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + " {").append("\n");
        String delimiter = ";";
        if (values != null) {
            for (Map.Entry<String,String> entry : values.entrySet()) {
                sb.append("\t" + entry.getKey() + ":" + entry.getValue()).append(delimiter).append("\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

}