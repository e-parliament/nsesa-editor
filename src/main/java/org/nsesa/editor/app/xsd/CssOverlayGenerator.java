package org.nsesa.editor.app.xsd;

import com.sun.xml.xsom.*;
import com.sun.xml.xsom.parser.XSOMParser;
import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Generates a hierarchy of css styles by parsing an XSD Schema
 *
 */
public class CssOverlayGenerator extends OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(CssOverlayGenerator.class);
    private String fileName;
    private Properties properties;
    private String templateName;

    public CssOverlayGenerator(Properties properties, String templateName, String fileName) {
        super();
        this.templateName = templateName;
        this.fileName = fileName;
        this.properties = properties;
    }

    public void print() {
        OverlayClassGenerator.OverlayRootClass root = overlayClassGenerator.getResult();
        Writer writer = null;
        try {
            writer = new FileWriter(fileName, false);
            OverlayClassProcessor processor = new CssOverlayClassProcessor(properties, templateName, writer);
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
        if (args.length != 1) {
            System.out.println("Usage java org.nsesa.editor.app.xsd.CssOverlayGenerator <<file_name>>");
            System.exit(1);
        }
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
            Properties props = new Properties();
            props.load(classLoader.getResourceAsStream("overlayCss.properties"));
            CssOverlayGenerator generator = new CssOverlayGenerator(props, "overlayCss.ftl", args[0]);
            final String[] xsds = {"xml.xsd", "akomantoso20.xsd"};
            generator.parse(xsds);
            generator.analyze();
            generator.print();
        } catch (IOException ioe) {
            LOG.error("IOException .", ioe);
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }
}
