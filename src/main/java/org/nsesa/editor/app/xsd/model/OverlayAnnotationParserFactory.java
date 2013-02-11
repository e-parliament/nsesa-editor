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
package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import com.sun.xml.xsom.parser.AnnotationParserFactory;
import org.xml.sax.*;

/**
 * An annotation parser implementation to read the documentation from annotations
 * User: groza
 * Date: 24/01/13
 * Time: 16:00
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
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void processingInstruction(String target, String data) throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void skippedEntity(String name) throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
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
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void startDocument() throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void endDocument() throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void endPrefixMapping(String prefix) throws SAXException {
                    //To change body of implemented methods use File | Settings | File Templates.
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