package org.nsesa.editor.app.xsd.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates a style on the given overlay class
 * User: sgroza
 * Date: 06/11/12
 * Time: 12:46
 */
 public class CssOverlayStyle {
    // a factory to create styles for the given class
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

        private boolean canProcess(OverlayClass overlayClass) {
            if (overlayClass.getNameSpace() == null) {
                return false;
            }
            if (!overlayClass.getNameSpace().equals("http://www.akomantoso.org/2.0")) {
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
                    values.putAll(cssStyle.getValues());
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