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
 * Generates a hierarchy of css styles by parsing an XSD Schema
 *
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

        } catch(IOException e ) {
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
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage java org.nsesa.editor.app.xsd.CssOverlayGenerator <<file_name>> <xsd_schema> <css_template_file_name> <predefined_css_file_name> print_empty_styles(true|false)");
            System.exit(1);
        }
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
            Properties props = new Properties();
            props.load(classLoader.getResourceAsStream(args[3]));
            CssOverlayGenerator generator = new CssOverlayGenerator(props, args[2], args[0], Boolean.valueOf(args[4]));
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
