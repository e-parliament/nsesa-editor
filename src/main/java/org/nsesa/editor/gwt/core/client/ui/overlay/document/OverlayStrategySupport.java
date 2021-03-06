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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.overlay.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Support class to assist an {@link OverlayStrategy}.
 * Date: 03/07/12 23:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayStrategySupport {

    private static final Logger LOG = Logger.getLogger(OverlayStrategySupport.class.getName());

    private static final String TYPE_ATTRIBUTE = "data-type";
    private static final String NAMESPACE_ATTRIBUTE = "data-ns";
    private static final String ORIGIN_ATTRIBUTE = "data-origin";

    public static final String TAG_LITERAL_INDEX = "num";
    public static final String ATTRIB_AMENDABLE = "data-amendable";
    public static final String ATTRIB_IMMUTABLE = "data-immutable";
    public static final String ATTRIB_ASSIGNED_INDEX = "data-assignedIndex";
    public static final String ATTRIB_ORIGINAL_INDEX = "data-originalIndex";
    public static final String ATTRIB_FORMAT = "data-format";
    public static final String ATTRIB_NUMBERING_TYPE = "data-numberingType";
    public static final String ATTRIB_TRANSLATION_ID = "data-sequence";


    public static final String ATTRIB_SOURCE = "data-source";
    public static final String CLASS_OPERATION_PANEL = "data-operationPanel";

    public static final String CLASS_AMENDMENTS_PANEL = "data-amendmentsPanel";

    private Set<String> asProperties = new HashSet<String>();

    public void asProperties(Class<? extends OverlayWidget>... widgetClasses) {
        for (final Class<? extends OverlayWidget> clazz : widgetClasses) {
            asProperties.add(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1).toUpperCase());
        }
    }

    public String getSource(Element element) {
        String sourceAttribute = getAttribute(element, getSourceAttributeName());
        return sourceAttribute == null || "".equals(sourceAttribute) ? null : sourceAttribute;
    }

    protected String getSourceAttributeName() {
        return ATTRIB_SOURCE;
    }

    public String getID(Element element) {
        return element.getId();
    }

    public Boolean isAmendable(Element element) {
        String amendableAttribute = getAttribute(element, getAmendableAttributeName());
        return amendableAttribute == null || "".equals(amendableAttribute) ? null : "true".equalsIgnoreCase(amendableAttribute);
    }

    protected String getAmendableAttributeName() {
        return ATTRIB_AMENDABLE;
    }

    public Boolean isImmutable(Element element) {
        String immutable = getAttribute(element, getImmutableAttributeName());
        return immutable == null || "".equals(immutable) ? null : "true".equalsIgnoreCase(immutable);
    }

    private String getAttribute(final Element element, final String attributeName) {
        String attribute = null;
        try {
            attribute = element.hasAttribute(attributeName) ? element.getAttribute(attributeName) : null;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Could not retrieve attribute " + attributeName + " from " + element.getTagName());
            return null;
        }
        return attribute;
    }

    protected String getImmutableAttributeName() {
        return ATTRIB_IMMUTABLE;
    }

    public String getType(Element element) {
        return element.hasAttribute(TYPE_ATTRIBUTE) ? element.getAttribute(TYPE_ATTRIBUTE) : null;
    }

    /**
     * Returns the amendable content of an element.
     *
     * @return the amendable content.
     */
    public String getInnerHTML(Element element) {
        if (element == null) return null;
        return element.getInnerHTML();
    }


    /**
     * Get the numbering type for this element. A numbering type can
     * be something like a number, a letter, an indent, a roman number, etc ..
     * TODO: if this is NOT specified on the element definition in the XML, this MIGHT/WILL be wrong. Be safe, and declare it in the XML instead.
     *
     * @param assignedIndex the assigned index for this element. Necessary to do optimal comparison against things
     *                      like roman numberingtypes.
     * @return the numbering type for this element
     */
    public final NumberingType getNumberingType(Element element, int assignedIndex) {
        final String type = getAttribute(element, ATTRIB_NUMBERING_TYPE);
        if (type == null || "".equals(type.trim())) {
            // not defined, so try to guess it
            return guessNumberingType(element, assignedIndex);
        }
        return NumberingType.valueOf(type.toUpperCase());
    }

    // TODO: add support for indexes like bullet points, arrows, snowmen, etc ..
    private NumberingType guessNumberingType(Element element, int assignedIndex) {
        String literalIndex = getLiteralIndex(element);
        if (literalIndex != null) {
            final String unformattedIndex = getUnformattedIndex(element);
            if (unformattedIndex != null) {

                // compare with indents
                if (literalIndex.equalsIgnoreCase("&ndash;")
                        || literalIndex.equalsIgnoreCase("—")
                        || literalIndex.equalsIgnoreCase("–")
                        || literalIndex.equalsIgnoreCase("-"))
                    return NumberingType.INDENT;

                // compare with the roman equivalent
                String romanResult = RomanConvertor.int2roman(assignedIndex);
                if (romanResult.equalsIgnoreCase(unformattedIndex)) {
                    return NumberingType.ROMANITO;
                }

                // take a very simple guess
                boolean allNumbers = true;
                for (char c : unformattedIndex.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        allNumbers = false;
                        break;
                    }
                }
                if (allNumbers)
                    return NumberingType.NUMBER;
                boolean allLetters = true;
                for (char c : unformattedIndex.toCharArray()) {
                    if (!isLetter(c)) {
                        allLetters = false;
                        break;
                    }
                }
                if (allLetters)
                    return NumberingType.LETTER;
                char startChar = unformattedIndex.toCharArray()[0];
                char endChar = unformattedIndex.toCharArray()[unformattedIndex.toCharArray().length - 1];
                // see if it starts with a char and ends with a digit (eg. A1, B4, etc ..)
                // or if it starts with a digit and ends with a char (eg. 1A, B$, ...)
                // bugfix: and detect cases like 1.1, A.A, etc ..
                if ((isLetter(startChar) && Character.isDigit(endChar))
                        || ((isLetter(endChar) && Character.isDigit(startChar)))
                        || ((isLetter(endChar) && Character.isLetter(startChar)))
                        || ((Character.isDigit(endChar) && Character.isDigit(startChar)))) {
                    return NumberingType.COMBO;
                }
            }
        }
        return NumberingType.NONE;
    }

    private static boolean isLetter(char c) {
        // there's a bug in GWT where non-ascii letters are not recognized as such
        // but we rely on this method to detect letters in eg. Bulgarian numbering schemes.

        // see if it's in the greek alphabet
        if (Arrays.binarySearch(Alphabet.getGreekNumbering(), c) != -1)
            return true;
        // perhaps in cyrillic
        if (Arrays.binarySearch(Alphabet.getCyrillicNumbering(), c) != -1)
            return true;
        // nope, guess it's ascii then ..
        return Character.isLetter(c);
    }

    /**
     * Returns the {@link org.nsesa.editor.gwt.core.client.ui.overlay.Format}
     * of this amendable element.
     *
     * @return the type
     * @see org.nsesa.editor.gwt.core.client.ui.overlay.Format
     */
    public final Format getFormat(Element element) {
        final String format = getAttribute(element, ATTRIB_FORMAT);
        if (format == null || "".equals(format.trim())) {
            // guess
            return guessFormat(element);
        }
        return Format.valueOf(format);
    }

    /**
     * Guesses the {@link Format}
     * based on the literal index provided for this element.
     *
     * @return the type
     */
    private Format guessFormat(Element element) {
        String literalIndex = getLiteralIndex(element);
        if (literalIndex != null) {
            literalIndex = TextUtils.stripTags(literalIndex.trim(), false);
            // see if we start with the same name as the element
            // TODO: dynamic lookup of the translated name - because this alone won't be enough
            // eg. in Dutch 'Artikel' won't match the type name
            final String type = getType(element);
            if (type != null && literalIndex.toLowerCase().startsWith(type)) {
                return Format.ELEMENT;
            }
            if (literalIndex.startsWith("("))
                return Format.DOUBLE_BRACKET;
            if (literalIndex.endsWith("."))
                return Format.POINT;
            if (literalIndex.endsWith(")"))
                return Format.BRACKET;
        }
        return Format.NONE;
    }

    /**
     * Returns the assigned index, if specified. This can be used to
     * override the location determination of the amendable element.
     *
     * @return the assigned index, or null if it hasn't been specified.
     */
    public final Integer getAssignedIndex(Element element) {
        String assigned = getAttribute(element, ATTRIB_ASSIGNED_INDEX);
        if (assigned != null && !"".equals(assigned)) {
            Integer assignedIndex = null;
            try {
                assignedIndex = Integer.parseInt(assigned);
            } catch (NumberFormatException e) {
                LOG.log(Level.SEVERE, "The assigned index '" + assigned + "' on " + element + " is not a number -- ignoring.");
            }
            return assignedIndex;
        }
        return null;
    }

    /**
     * Returns the literal index of the element, if specified. The literal index
     * is the index as it should be (and is) displayed. For example, (a), iv., 22), ..
     *
     * @return the literal index or null if it hasn't been specified.
     */
    public final String getLiteralIndex(Element element) {
        Element el = getElementByClassName(element, TAG_LITERAL_INDEX);
        if (el != null) {
            return TextUtils.stripTags(el.getInnerText().trim(), false);
        } else {
            return null;
        }
    }

    /**
     * Returns the translation id. This id is shared amongst all
     * translations of this particular amendable element.
     * <p/>
     * French:
     * <pre>
     *  <div id="100" trid="300"> ..
     * </pre>
     * English:
     * <pre>
     *  <div id="101" trid="300"> ..
     * </pre>
     * As you can see, while the id differs from translation to translation (the id
     * is (or should be) unique for all documents in the EP), the trid (=translation id)
     * is the same. This allows us to find the translation for each and every element.
     *
     * @return the translation id or null if it hasn't been specified.
     */
    public final String getTranslationId(Element element) {
        String t = getAttribute(element, ATTRIB_TRANSLATION_ID);
        if (t == null || "".equals(t.trim()))
            return null;
        return t;
    }

    /**
     * Retrieves the index from the literal index.
     * Used to find the index to use for new elements that require the literal index of a nearby element.
     * <p/>
     * Eg. the literal index of an element is '(a)' - then the original index is 'a',
     * whereas 'iv.' will return 'iv' as the original index.
     *
     * @return the original index (literal index minus the .. stuff)
     */
    public final String getUnformattedIndex(Element element) {
        // see if we have an original index attribute specified
        String originalIndex = getAttribute(element, ATTRIB_ORIGINAL_INDEX);
        if (originalIndex != null && !"".equals(originalIndex.trim()))
            return originalIndex;
        // if not, work with the literal index
        String literalIndex = getLiteralIndex(element);
        if (literalIndex != null && !"".equals(literalIndex)) {
            // check if there is a space - drop everything in front of the space
            // note: not sure if this is a good way, but it helps us in getting rid of the <num> tags
            // that have too much information in it, such as 'Article I' or 'Capo II'
            if (literalIndex.contains(" ")) {
                literalIndex = literalIndex.substring(literalIndex.lastIndexOf(" ") + 1);
            }
            Format f = getFormat(element);
            switch (f) {
                // in case of a point (11.)or bracket (11)), just strip off the last char
                case POINT:
                case BRACKET:
                    return literalIndex.substring(0, literalIndex.length() - 1);
                // in case of a double bracket (eg. (11)), strip off the first and last char
                case DOUBLE_BRACKET:
                    return literalIndex.substring(1, literalIndex.length() - 1);
                case NONE:
                    return literalIndex;
            }
            return literalIndex;
        }
        return null;
    }

    /**
     * Returns the element to place the operational panel on.
     *
     * @return the place holder element.
     */
    public final Element getOperationPanelHolderElement(Element element) {
        Element holder = getElementByClassName(element, CLASS_OPERATION_PANEL);
        if (holder == null) {
            // create it
            final com.google.gwt.user.client.Element div = DOM.createDiv();
            div.setClassName(CLASS_OPERATION_PANEL);
            element.getParentElement().insertBefore(div, element);
            holder = div.cast();
        }
        return holder;
    }

    /**
     * Returns the element to place the amendments on.
     *
     * @return the amendment holder element.
     */
    public final Element getAmendmentHolderElement(Element element) {
        Element holder = getElementByClassName(element, CLASS_AMENDMENTS_PANEL);
        if (holder == null) {
            // create it
            final com.google.gwt.user.client.Element div = DOM.createDiv();
            div.setClassName(CLASS_AMENDMENTS_PANEL);
            // TODO find the correct place to inject this panel
            element.appendChild(div);
            holder = div.cast();
        }
        return holder;
    }

    /**
     * Returns a list of all the amendable elements under this element by
     * enumerating the elements under the 'children' element of this element.
     * <p/>
     * If there is no children element, an empty list will be returned.
     *
     * @return the list of children.
     */
    public final List<Element> getChildren(Element element) {
        NodeList<Node> nodes = element.getChildNodes();
        List<Element> amendableElements = new ArrayList<Element>();
        int length = 0;
        try {
            length = nodes.getLength();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Caught exception (probably under Chrome): " + e, e);
        }
        for (int i = 0; i < length; i++) {
            if (nodes.getItem(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = nodes.getItem(i).cast();

                // do not include any of the 'property' tags
                // these elements are not included in our tree, but can be searched by their parent later on, to
                // get certain properties such as content, num, ...
                if (!asProperties.contains(el.getAttribute("type").toUpperCase())) {
                    amendableElements.add(el);
                }
            }
        }
        return amendableElements;
    }

    public final Element getElementByTag(Element element, final String tag) {
        for (int i = 0; i < element.getChildCount(); i++) {
            final Node node = element.getChild(i);
            if (node.getNodeName().equalsIgnoreCase(tag)) {
                return node.cast();
            }
        }
        return null;
    }

    public final Element getElementByAttribute(Element element, final String attributeName, final String attributeValue) {
        for (int i = 0; i < element.getChildCount(); i++) {
            final Node node = element.getChild(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element el = node.cast();
                if (getAttribute(el, attributeName).equalsIgnoreCase(attributeValue)) {
                    return el;
                }
            }
        }
        return null;
    }

    public final Element getElementByClassName(Element element, final String className) {
        for (int i = 0; i < element.getChildCount(); i++) {
            final Node node = element.getChild(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element el = node.cast();
                final String classNames = el.getClassName();
                if (classNames != null) {
                    final String clean = TextUtils.collapseWhiteSpace(classNames);
                    if (Arrays.asList(clean.split(" ")).contains(className))
                        return el;
                }
            }
        }
        return null;
    }

    public String getNamespaceURI(Element element) {
        return element.hasAttribute(NAMESPACE_ATTRIBUTE) ? element.getAttribute(NAMESPACE_ATTRIBUTE) : null;
    }

    public String getOrigin(Element element) {
        return element.hasAttribute(ORIGIN_ATTRIBUTE) ? element.getAttribute(ORIGIN_ATTRIBUTE) : null;
    }
    
    public void setOrigin(Element element, String origin) {
        element.setAttribute(ORIGIN_ATTRIBUTE, origin);
    }

    private static interface ElementVisitor {
        boolean visit(Element visited);
    }
}
