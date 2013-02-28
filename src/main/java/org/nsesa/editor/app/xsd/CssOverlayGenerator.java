/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
 * Get the result of XSD parsing and generate css class for each element defined in XSD schema.
 * The program is usind a predefined template properties <code>overlayCss.properties</code> whereas the user can add
 * custom css properties that will be generated for certain type of elements. If there are css properties defined in
 * superclasses they will be carried on also in subclasses unless they are overridden in the template file.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 17/01/13 9:38
 */
public class CssOverlayGenerator extends OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(CssOverlayGenerator.class);
    private String fileName;
    private Properties properties;
    private String templateName;
    private Map<String, Object> cssConfiguration = new HashMap<String, Object>();

    public CssOverlayGenerator(Properties properties,
                               String templateName,
                               String fileName,
                               boolean printEmptyCss
    ) {
        super();
        this.templateName = templateName;
        this.fileName = fileName;
        this.properties = properties;
        this.cssConfiguration.put("printEmptyCss", printEmptyCss);
    }

    public void print() {
        OverlayClassGenerator.OverlayRootClass root = overlayClassGenerator.getResult();
        Writer writer = null;
        try {
            writer = new FileWriter(fileName, false);
            OverlayClassProcessor processor = new CssOverlayClassProcessor(properties, templateName, writer, cssConfiguration);
            LOG.info("Start css processing...");
            root.process(processor);
            LOG.info("The file {} has been generated.", fileName);

        } catch (IOException e) {
            LOG.error("Error in printing ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error("Error in closing the writer", e);
                }
            }
        }

    }

    /**
     * The main method
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage java org.nsesa.editor.app.xsd.CssOverlayGenerator <<file_name>> <xsd_schema> <css_template_file_name> print_empty_styles(true|false)");
            System.exit(1);
        }
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
            Properties props = new Properties();
            props.load(classLoader.getResourceAsStream("overlayCss.properties"));
            CssOverlayGenerator generator = new CssOverlayGenerator(props, args[2], args[0], Boolean.valueOf(args[3]));
            final String xsd = args[1];
            generator.parse(xsd);
            generator.analyze();
            generator.print();
        } catch (IOException ioe) {
            LOG.error("IOException .", ioe);
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }
}
