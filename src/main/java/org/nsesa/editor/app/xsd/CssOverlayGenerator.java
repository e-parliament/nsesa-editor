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
public class CssOverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(CssOverlayGenerator.class);
    private String fileName;
    private Properties properties;

    // XSOM parser
    private final XSOMParser parser;

    private final List<OverlayClass> generatedClasses;

    public CssOverlayGenerator(Properties properties, String fileName) {
        this.fileName = fileName;
        this.properties = properties;
        this.generatedClasses = new ArrayList<OverlayClass>();
        this.parser = new XSOMParser();
        this.parser.setErrorHandler(new LoggingErrorHandler());
    }

    /**
     * Parse the xsd schema
     * @param xsds List of xsd as String
     * @throws SAXException
     */
    public void parse(final String[] xsds) throws SAXException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        for (String xsd : xsds) {
            parser.parse(classLoader.getResource(xsd));
        }

    }

    /**
     * Analyze the xsd schema and generate overlayclasses
     * @throws SAXException
     */
    public void analyze() throws SAXException {
        final XSSchemaSet set = parser.getResult();
        OverlayClassGenerator classGenerator = new OverlayClassGeneratorImpl(set.getSchemas());
        generatedClasses.addAll(classGenerator.getResult(OverlayClass.DEFAULT_COMPARATOR));
    }

    public void print() {
        Writer writer = null;
        try {
            writer = new FileWriter(fileName, false);
            OverlayClassProcessor processor = new CssOverlayClassProcessor(properties, writer);
            LOG.info("Start css processing...");
            for (OverlayClass aClass : generatedClasses) {
                aClass.process(processor);
            }
            LOG.info("The file {} has been generated.", fileName);

        } catch(IOException e ) {
            LOG.error("Error in printing ", e);
        } finally {
             if (writer != null) {
                 try {
                     writer.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }

    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        DOMConfigurator.configure(classLoader.getResource("log4j.xml"));

        Properties props = new Properties();
        props.setProperty("container", "display:block");
        props.setProperty("block", "display:block");
        props.setProperty("inline", "display:inline");

        CssOverlayGenerator generator = new CssOverlayGenerator(props, "r:/test.css");
        try {
            final String[] xsds = {"xml.xsd", "akomantoso20.xsd"};
            generator.parse(xsds);
            generator.analyze();
            generator.print();
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }

    private static class LoggingErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException e) throws SAXException {
            LOG.info("Warning: " + e.getMessage(), e);
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            LOG.warn("Error: " + e.getMessage(), e);
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            LOG.error("Fatal: " + e.getMessage(), e);
        }
    }
}
