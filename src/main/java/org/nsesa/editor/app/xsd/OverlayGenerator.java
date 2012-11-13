package org.nsesa.editor.app.xsd;

import com.sun.xml.xsom.*;
import com.sun.xml.xsom.parser.XSOMParser;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * An abstract class to to generate different representations based on XSD schema provided
 * Date: 03/08/12 19:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayGenerator.class);
    // XSOM parser
    private final XSOMParser parser;

    // the overlay generator used to parse and analyze xsd schemas
    protected OverlayClassGenerator overlayClassGenerator;

    public OverlayGenerator() {
        parser = new XSOMParser();
        parser.setErrorHandler(new LoggingErrorHandler());
        overlayClassGenerator = new OverlayClassGeneratorImpl();
    }

    /**
     * Parse the xsd schema
     * @param xsds The xsd schemas as array of string
     * @throws SAXException
     */
    public void parse(final String[] xsds) throws SAXException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        for (String xsd : xsds) {
            parser.parse(classLoader.getResource(xsd));
        }
    }

    /**
     * Analyze the xsd schema and generate overlay classes
     * @throws SAXException
     */
    public void analyze() throws SAXException {
        final XSSchemaSet set = parser.getResult();
        overlayClassGenerator.generate(set.getSchemas());
    }
    /**
     * Print overlay classes in different formats
     */
    public abstract void print();

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
