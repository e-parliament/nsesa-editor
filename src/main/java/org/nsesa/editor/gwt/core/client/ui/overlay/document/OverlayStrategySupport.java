package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import org.nsesa.editor.gwt.core.client.ui.overlay.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date: 03/07/12 23:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayStrategySupport {

    private static final Logger LOG = Logger.getLogger(OverlayStrategySupport.class.getName());

    public static final String TAG_LITERAL_INDEX = "num";
    public static final String TAG_CONTENT = "content";

    public static final String ATTRIB_ID = "id";
    public static final String ATTRIB_EVOLVING_ID = "evolvingId";

    public static final String ATTRIB_AMENDABLE = "ep:amendable";
    public static final String ATTRIB_IMMUTABLE = "ep:immutable";
    public static final String ATTRIB_ASSIGNED_INDEX = "ep:assignedIndex";
    public static final String ATTRIB_ORIGINAL_INDEX = "ep:originalIndex";
    public static final String ATTRIB_FORMAT = "ep:format";
    public static final String ATTRIB_NUMBERING_TYPE = "ep:numberingType";
    public static final String ATTRIB_TRANSLATION_ID = "ep:sequence";
    public static final String ATTRIB_SOURCE = "ep:source";


    public static final String CLASS_OPERATION_PANEL = "ep:operationPanel";
    public static final String CLASS_AMENDMENTS_PANEL = "ep:amendmentsPanel";

    private HashSet<String> asProperties = new HashSet<String>();

    public void asProperties(Class<? extends AmendableWidget>... widgetClasses) {
        for (Class<? extends AmendableWidget> clazz : widgetClasses) {
            asProperties.add(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1).toUpperCase());
        }
    }

    public String getSource(Element element) {
        String sourceAttribute = element.getAttribute(getSourceAttributeName());
        return sourceAttribute == null || "".equals(sourceAttribute) ? null : sourceAttribute;
    }

    protected String getSourceAttributeName() {
        return ATTRIB_SOURCE;
    }

    public String getID(Element element) {
        return element.getId();
    }

    public Boolean isAmendable(Element element) {
        return true;
//        String amendableAttribute = element.getAttribute(getAmendableAttributeName());
//        return amendableAttribute == null || "".equals(amendableAttribute) ? null : "true".equalsIgnoreCase(amendableAttribute);
    }

    protected String getAmendableAttributeName() {
        return ATTRIB_AMENDABLE;
    }

    public Boolean isImmutable(Element element) {
        String immutable = element.getAttribute(getImmutableAttributeName());
        return immutable == null || "".equals(immutable) ? null : "true".equalsIgnoreCase(immutable);
    }

    protected String getImmutableAttributeName() {
        return ATTRIB_IMMUTABLE;
    }

    public String getType(Element element) {
        return element.getTagName();
    }

    /**
     * Returns the amendable content of an element.
     *
     * @return the amendable content.
     */
    public String getAmendableContent(Element element) {
        return element != null ? element.getInnerHTML() : null;
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
        final String type = element.getAttribute(ATTRIB_NUMBERING_TYPE);
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
        if (Arrays.binarySearch(Alphabet.GREEK_NUMBERING, c) != -1)
            return true;
        // perhaps in cyrillic
        if (Arrays.binarySearch(Alphabet.CYRILLIC_NUMBERING, c) != -1)
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
        final String format = element.getAttribute(ATTRIB_FORMAT);
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
            literalIndex = TextUtils.stripTags(literalIndex.trim());
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
        String assigned = element.getAttribute(ATTRIB_ASSIGNED_INDEX);
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
        Element el = getElementByTag(element, TAG_LITERAL_INDEX);
        if (el != null) {
            return TextUtils.stripTags(el.getInnerText().trim());
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
        String t = element.getAttribute(ATTRIB_TRANSLATION_ID);
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
        String originalIndex = element.getAttribute(ATTRIB_ORIGINAL_INDEX);
        if (originalIndex != null && !"".equals(originalIndex.trim()))
            return originalIndex;
        // if not, work with the literal index
        String literalIndex = getLiteralIndex(element);
        if (literalIndex != null && !"".equals(literalIndex)) {
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
                if (!asProperties.contains(el.getTagName().toUpperCase())) {
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
                if (el.getAttribute(attributeName).equalsIgnoreCase(attributeValue)) {
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
                if (el.getClassName().equalsIgnoreCase(className)) {
                    return el;
                }
            }
        }
        return null;
    }

    private void walk(Element elem, ElementVisitor visitor) {
        assert elem != null;
        assert visitor != null;

        final List<Element> stack = new ArrayList<Element>();
        stack.add(elem);

        while (!stack.isEmpty()) {
            Element nextElem = stack.remove(0);

            NodeList<Node> nodes = nextElem.getChildNodes();
            if (nodes != null) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.getItem(i);
                    if (node != null) {
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element el = Element.as(node);
                            if (visitor.visit(el)) {
                                stack.add(el);
                            } else {
                                break;
                            }
                        }
                    } else {
                        LOG.info("A 'null' node found -- that's very weird. Running in hosted mode under Chrome perhaps?");
                    }
                }
            } else {
                LOG.info("Nodes under nextElem are null -- that's very weird. Running in hosted mode under Chrome perhaps?");
            }
        }
    }

    private static interface ElementVisitor {
        boolean visit(Element visited);
    }
}
