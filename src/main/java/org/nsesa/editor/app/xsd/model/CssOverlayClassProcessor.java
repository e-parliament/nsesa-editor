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

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Generate a list of css selectors for elements which belong to an xsd file.
 * The css selectors are based on predefined list of styles stored in properties file.
 * The css style will be inherited from the parent node if it is the case.
 * Otherwise a generic css style containing only the node name is generated
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 30/10/12 13:38
 */
public class CssOverlayClassProcessor implements OverlayClassProcessor {
    private boolean emptyCssStyles;
    //freemarker configuration
    private Configuration configuration;
    private String templateName;
    // keep the list of predefined css styles
    private List<CssOverlayStyle> styles;
    private Writer out;
    private Map<String, Object> cssConfiguration;

    /**
     * Constructor
     * @param properties   The predefined list of css styles
     * @param templateName The freemarker template used for css generation
     * @param out          The location where the output is saved
     * @throws IOException
     */
    public CssOverlayClassProcessor(Properties properties,
                                    String templateName,
                                    Writer out,
                                    Map<String, Object> cssConfiguration) throws IOException {
        this.cssConfiguration = cssConfiguration;
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("UTF-8");
        this.templateName = templateName;
        this.out = out;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File dir = new File(classLoader.getResource(templateName).getFile()).getParentFile();
        configuration.setDirectoryForTemplateLoading(dir);

        initStyles(properties);
    }

    /**
     * Create a list of predefined styles
     *
     * @param properties The properties whcih store the list of predefined styles
     * @throws IOException
     */
    private void initStyles(Properties properties) throws IOException {
        styles = new ArrayList<CssOverlayStyle>();
        for (String name : properties.stringPropertyNames()) {
            String[] props = properties.getProperty(name).split(";");
            Map<String, String> values = new HashMap<String, String>();
            for (String value : props) {
                String[] css = value.split(":");
                values.put(css[0], css[1]);
            }
            styles.add(new CssOverlayStyle(name, values));
        }
    }


    @Override
    public boolean process(OverlayClass overlayClass) {
        try {
            Map<String, Object> rootMap = new HashMap<String, Object>();
            rootMap.put("overlayClass", overlayClass);
            rootMap.put("overlayStyleFactory", CssOverlayStyle.CssOverlayFactory.getInstance());
            rootMap.put("styles", styles);
            rootMap.put("cssConfiguration", cssConfiguration);
            rootMap.put("colorGenerator", CssColorGenerator.getInstance());
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
