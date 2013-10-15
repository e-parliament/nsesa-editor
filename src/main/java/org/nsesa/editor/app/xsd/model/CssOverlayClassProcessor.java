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
import java.net.URL;
import java.util.*;

/**
 * Generate a list of css selectors for elements which belong to an xsd file.
 * The css selectors are based on predefined list of styles stored in properties file.
 * The css style will be inherited from the parent node if it is the case.
 * Otherwise a generic css style containing only the node name is generated
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 30/10/12 13:38
 */
public class CssOverlayClassProcessor implements OverlayClassProcessor {
    /**
     * Freemarker configuration object
     */
    private Configuration configuration;
    /**
     * The Freemarker template name
     */
    private String templateName;
    /**
     * The list of predefined CSS styles.
     */
    private List<CssOverlayStyle> styles;
    /**
     * The writer to write the processed template to.
     */
    private Writer out;
    /**
     * The CSS configuration properties.
     */
    private Map<String, Object> cssConfiguration;

    /**
     * Constructor
     *
     * @param properties   The predefined list of css styles
     * @param templateName The freemarker template used for css generation
     * @param out          The location where the output is saved
     * @throws IOException
     */
    public CssOverlayClassProcessor(final Properties properties,
                                    final String templateName,
                                    final Writer out,
                                    final Map<String, Object> cssConfiguration) throws IOException {
        this.cssConfiguration = cssConfiguration;
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("UTF-8");
        this.templateName = templateName;
        this.out = out;

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource(templateName);
        if (resource == null) throw new RuntimeException("Could not load resource " + templateName +
                " from the classpath. Is it available?");
        final File dir = new File(resource.getFile()).getParentFile();
        configuration.setDirectoryForTemplateLoading(dir);

        // process the CSS properties specified in the overlay css file
        initStyles(properties);
    }

    /**
     * Create a list of predefined styles
     *
     * @param properties The properties which store the list of predefined styles
     * @throws IOException
     */
    private void initStyles(final Properties properties) throws IOException {
        styles = new ArrayList<CssOverlayStyle>();
        for (final String name : properties.stringPropertyNames()) {
            final String[] props = properties.getProperty(name).split(";");
            final Map<String, String> values = new HashMap<String, String>();
            for (String value : props) {
                final String[] css = value.split(":");
                if (css.length != 2)
                    throw new RuntimeException("Could not process css directive " + value + " - missing ':' separator.");
                values.put(css[0], css[1]);
            }
            styles.add(new CssOverlayStyle(name, values));
        }
    }


    /**
     * Process a given overlay class to generate.
     *
     * @param overlayClass The class to be processed
     * @return true if the class was successfully processed.
     */
    @Override
    public boolean process(final OverlayClass overlayClass) {
        try {
            // create the context map for usage inside the freemarker template
            final Map<String, Object> context = new HashMap<String, Object>();
            context.put("overlayClass", overlayClass);
            context.put("overlayStyleFactory", CssOverlayStyle.CssOverlayFactory.getInstance());
            context.put("styles", styles);
            context.put("cssConfiguration", cssConfiguration);
            context.put("colorGenerator", CssColorGenerator.getInstance());
            final Template template = configuration.getTemplate(templateName);
            final DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
            template.process(context, out, wrapper);
        } catch (IOException e) {
            throw new RuntimeException("The overlay class can not be processed.", e);
        } catch (TemplateException e) {
            throw new RuntimeException("The overlay css can not be generated.", e);
        }
        return true;
    }

}
