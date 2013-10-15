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
package org.nsesa.editor.app.xsd;

import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.CssOverlayClassProcessor;
import org.nsesa.editor.app.xsd.model.OverlayClassGenerator;
import org.nsesa.editor.app.xsd.model.OverlayClassProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Get the result of XSD parsing and generate css class for each element defined in an XSD.
 * The program is using a predefined template properties file called <code>overlayCss.properties</code> where the you
 * can add custom css properties that will be generated for certain types of element (including their subclasses).
 * <p/>
 * The subclasses inherit the css style of the superclass unless they are overridden in the predefined template file.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 17/01/13 9:38
 */
public class CssOverlayGenerator extends OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(CssOverlayGenerator.class);

    /**
     * The filename to write the result to.
     */
    private String fileName;

    /**
     * The predefined list of properties from the overlayCss properties file.
     */
    private Properties properties;

    /**
     * The Freemarker template name to use.
     */
    private String templateName;

    /**
     * The final list with generated css properties.
     */
    private Map<String, Object> cssConfiguration = new HashMap<String, Object>();

    public CssOverlayGenerator(final Properties properties,
                               final String templateName,
                               final String fileName,
                               final boolean printEmptyCss
    ) {
        this.templateName = templateName;
        this.fileName = fileName;
        this.properties = properties;
        this.cssConfiguration.put("printEmptyCss", printEmptyCss);
    }

    public void export() {
        final OverlayClassGenerator.OverlayRootClass root = overlayClassGenerator.getResult();
        Writer writer = null;
        try {
            writer = new FileWriter(fileName, false);
            final OverlayClassProcessor processor = new CssOverlayClassProcessor(properties,
                    templateName, writer, cssConfiguration);

            LOG.info("Starting CSS processing...");
            root.process(processor);
            LOG.info("The file {} has been generated.", fileName);

        } catch (IOException e) {
            LOG.error("I/O Error while processing the CSS generation.", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error("Error while closing the writer", e);
                }
            }
        }

    }

    /**
     * The main method. The following arguments are required:
     * <ul>
     * <li>file_name: the filename to write the CSS generation to (eg. 'css/akomaNtoso20-all.css')</li>
     * <li>xsd_schema: the filename of the XSD (must be found in the classpath) (eg. 'css/akomaNtoso20.xsd')</li>
     * <li>css_template_file_name: the filename of the template to use for the CSS generation(eg. 'css/overlayCss.ftl')</li>
     * <li>print_empty_styles: a flag indicating whether or not to print empty CSS directives</li>
     * </ul>
     *
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java org.nsesa.editor.app.xsd.CssOverlayGenerator <<file_name>> <xsd_schema> <css_template_file_name> print_empty_styles(true|false)");
            System.exit(1);
        }
        // get the classloader to load resources from the classpath
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            // set up the log4j logging
            DOMConfigurator.configure(classLoader.getResource("log4j.xml"));

            // load the properties file with the predefined CSS settings
            final Properties props = new Properties();
            props.load(classLoader.getResourceAsStream("overlayCss.properties"));

            final String outputFileName = args[0];
            final String xsdFileName = args[1];
            final String templateName = args[2];
            final String printEmptyCSSDirectives = args[3];

            final CssOverlayGenerator generator = new CssOverlayGenerator(props, templateName, outputFileName, Boolean.valueOf(printEmptyCSSDirectives));

            generator.parse(xsdFileName);
            generator.analyze();
            generator.export();

        } catch (IOException ioe) {
            LOG.error("I/O exception while generating the CSS overlay file.", ioe);
        } catch (SAXException e) {
            LOG.error("SAX problem while inspecting the XSD.", e);
        }
    }
}
