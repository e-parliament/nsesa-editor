package org.nsesa.editor.app.xsd.model;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
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

    //freemarker configuration
    private Configuration configuration ;
    private String templateName;

    private List<CssOverlayStyle> styles;
    private Writer out;

    public CssOverlayClassProcessor(Properties properties, String templateName, Writer out) throws IOException {
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("UTF-8");
        this.templateName = templateName;
        this.out = out;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File dir = new File(classLoader.getResource(templateName).getFile()).getParentFile();
        configuration.setDirectoryForTemplateLoading(dir);

        initStyles(properties);
    }

    private void initStyles(Properties properties) throws IOException {
        styles = new ArrayList<CssOverlayStyle>();
        for (String name : properties.stringPropertyNames()) {
            String[] props = properties.getProperty(name).split(";");
            Map<String, String> values = new HashMap<String, String>();
            for(String value : props) {
                String[] css = value.split(":");
                values.put(css[0], css[1]);
            }
            styles.add(new CssOverlayStyle(name, values));
        }
    }


    @Override
    public boolean process(OverlayClass overlayClass) {
        final CssOverlayStyle overlayStyle = new CssOverlayStyle(overlayClass);
        try {
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put("overlayClass", overlayClass);
            rootMap.put("overlayStyleFactory", CssOverlayStyle.CssOverlayFactory.getInstance());
            rootMap.put("styles", styles);
            final Template template = configuration.getTemplate(templateName);
            final DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
            template.process(rootMap, out, wrapper);
        } catch (IOException e) {
            throw new RuntimeException("The overlay class can not be processed", e);
        } catch (TemplateException e) {
            throw new RuntimeException("The overlay css can not be generated", e);
        }
        return true;
    }

}
