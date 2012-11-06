package org.nsesa.editor.app.xsd.model;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Generate css style for the given node based on the pattern defined in properties files.
 * The css style will be inherited from the parent node if it is the case.
 * Otherwise a generic css style containing only the node name is generated
 * User: sgroza
 * Date: 30/10/12
 * Time: 14:11
 */
public class CssOverlayClassProcessor implements OverlayClassProcessor {

    // keep css definition for a overlay class
    private static class CssStyle {
        private OverlayClass aClass;
        private String name;
        private Map<String, String> values;
        CssStyle(OverlayClass aClass) {
            this.aClass = aClass;
            this.name = aClass.getName();
            this.values = new HashMap<String, String>();
        }

        public CssStyle(String name, Map<String, String> values) {
            this.name = name;
            this.values = values;

        }

        String getName() {
            return name;
        }
        Map<String, String> getValues() {
            return values;
        }

        public CssStyle cssProcess(String styleName, List<CssStyle> styles) {
            for(CssStyle cssStyle : styles) {
                if (styleName.equalsIgnoreCase(cssStyle.getName())) {
                    values.putAll(cssStyle.getValues());
                }
            }
            return this;
        };

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("/* " +
                    (aClass != null && aClass.getParent() != null ? aClass.getParent().getName() : "no base") +
                    " */").append("\n");
            sb.append(name + " {").append("\n");
            String delimiter = ";";
            for (Map.Entry<String,String> entry : values.entrySet()) {
                sb.append("\t" + entry.getKey() + ":" + entry.getValue()).append(delimiter).append("\n");
            }
            sb.append("}\n");
            return sb.toString();
        }
    }

    private List<CssStyle> styles;
    private Writer out;

    public CssOverlayClassProcessor(Properties properties, Writer out) throws IOException {
        this.out = out;
        initStyles(properties);
    }

    private void initStyles(Properties properties) throws IOException {
        styles = new ArrayList<CssStyle>();
        for (String name : properties.stringPropertyNames()) {
            String[] props = properties.getProperty(name).split(";");
            Map<String, String> values = new HashMap<String, String>();
            for(String value : props) {
                String[] css = value.split(":");
                values.put(css[0], css[1]);
            }
            styles.add(new CssStyle(name, values));
        }
    }


    @Override
    public boolean process(OverlayClass overlayClass) {
        if (canProcess(overlayClass)) {
            OverlayClass aClass = overlayClass;
            final CssStyle classStyle = new CssStyle(aClass);
            while(aClass != null) {
                classStyle.cssProcess(aClass.getName(), styles);
                aClass = aClass.getParent();
            }
            try {
                out.write(classStyle.toString());
            } catch (IOException e) {
                throw new RuntimeException("The overlay class can not be processed", e);
            }
            return true;
        }
        return false;
    }

    private boolean canProcess(OverlayClass overlayClass) {
        if (overlayClass.getNameSpace() == null) {
            return false;
        }
        if (!overlayClass.getNameSpace().equals("http://www.akomantoso.org/2.0")) {
            return false;
        }
        boolean skipped =  EnumSet.of(OverlayType.AttrGroup,
                            OverlayType.Attribute,
                            OverlayType.Group,
                            OverlayType.GroupDecl).contains(overlayClass.getOverlayType());

        return !skipped;
    }
}
