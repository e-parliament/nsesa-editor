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

import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.parser.XSOMParser;
import org.nsesa.editor.app.xsd.model.OverlayAnnotationParserFactory;
import org.nsesa.editor.app.xsd.model.OverlayClassGenerator;
import org.nsesa.editor.app.xsd.model.OverlayClassGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * An abstract class to to generate different representations based on XSD schema provided
 * Date: 03/08/12 19:25
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayGenerator.class);
    // XSOM parser
    private final XSOMParser parser;

    // the overlay generator used to parse and analyze xsd schemas
    protected OverlayClassGenerator overlayClassGenerator;
    protected String xsdSchema;

    public OverlayGenerator() {
        parser = new XSOMParser();
        parser.setErrorHandler(new LoggingErrorHandler());
        parser.setAnnotationParser(new OverlayAnnotationParserFactory());
        overlayClassGenerator = new OverlayClassGeneratorImpl();
    }

    /**
     * Parse the xsd schema
     *
     * @param xsd The xsd schema as string
     * @throws SAXException
     */
    public void parse(String xsd) throws SAXException {
        this.xsdSchema = xsd;
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        parser.parse(classLoader.getResource(xsd));
    }

    /**
     * Analyze the xsd schema and generate overlay classes
     *
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
