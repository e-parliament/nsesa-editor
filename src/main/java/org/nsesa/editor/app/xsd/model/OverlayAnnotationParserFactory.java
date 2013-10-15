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

import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import com.sun.xml.xsom.parser.AnnotationParserFactory;
import org.xml.sax.*;

/**
 * An XSOM annotation parser implementation to read the documentation from XSD annotations.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 24/01/13 16:46
 */
public class OverlayAnnotationParserFactory implements AnnotationParserFactory {
    @Override
    public AnnotationParser create() {
        return new OverlayAnnotationParser();
    }

    private static class OverlayAnnotationParser extends AnnotationParser {
        private StringBuilder documentation = new StringBuilder();

        @Override
        public ContentHandler getContentHandler(AnnotationContext context,
                                                String parentElementName, ErrorHandler handler, EntityResolver resolver) {
            return new ContentHandler() {
                private boolean parsingDocumentation = false;

                @Override
                public void characters(char[] ch, int start, int length)
                        throws SAXException {
                    if (parsingDocumentation) {
                        documentation.append(ch, start, length);
                    }
                }

                @Override
                public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
                    //do nothing.
                }

                @Override
                public void processingInstruction(String target, String data) throws SAXException {
                    //do nothing.
                }

                @Override
                public void skippedEntity(String name) throws SAXException {
                    //do nothing.
                }

                @Override
                public void endElement(String uri, String localName, String name)
                        throws SAXException {
                    if (localName.equals("comment")) {
                        parsingDocumentation = false;
                    }
                }

                @Override
                public void setDocumentLocator(Locator locator) {
                    //do nothing.
                }

                @Override
                public void startDocument() throws SAXException {
                    //do nothing.
                }

                @Override
                public void endDocument() throws SAXException {
                    //do nothing.
                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {
                    //do nothing.
                }

                @Override
                public void endPrefixMapping(String prefix) throws SAXException {
                    //do nothing.
                }

                @Override
                public void startElement(String uri, String localName, String name,
                                         Attributes atts) throws SAXException {
                    if (localName.equals("comment")) {
                        parsingDocumentation = true;
                    }
                }
            };
        }

        @Override
        public Object getResult(Object existing) {
            return documentation.toString().trim();
        }
    }
}