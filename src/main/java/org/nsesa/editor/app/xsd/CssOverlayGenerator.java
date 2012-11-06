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
    private final List<OverlayClass> generatedClasses;

    public CssOverlayGenerator(Properties properties, String fileName) {
        super();
        this.fileName = fileName;
        this.properties = properties;
        this.generatedClasses = new ArrayList<OverlayClass>();
    }

    public void print() {
        generatedClasses.addAll(overlayClassGenerator.getResult(OverlayClass.DEFAULT_COMPARATOR));
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
}
