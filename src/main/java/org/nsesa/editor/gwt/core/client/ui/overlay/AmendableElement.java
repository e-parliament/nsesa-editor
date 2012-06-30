package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Overlay class for amendable elements. This uses a special technique ('overlay')
 * to provide an easier to use layer for working with DOM elements.
 * <p/>
 * We can then use this element to map it on the DOM tree, and use
 * DOM operations to retrieve data out of the element.
 * <p/>
 * By using this technique, we can reuse the existing DOM tree,
 * render it immediately rather than trying to parse -> build datamodel -> output, which
 * was limiting the previous approach. This approach is similar to what JQuery would
 * do (walk the tree, attach listeners), except this approach is a lot more maintainable
 * and extensible. Plus, overlay types make sure that the developer doesn't have to
 * worry about the underlying elements.
 * <p/>
 * I tried to keep this as flexible as possible to be able to handle (small) future changes:
 * all child elements that are important are being found by a treewalk rather than indices (but limited).
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: AmendableElement.java 5257 2012-03-08 16:09:15Z pluppens $
 * @link http://code.google.com/p/google-web-toolkit/wiki/OverlayTypes
 * @link http://www.youtube.com/watch?v=vv2MnqP8Bmk
 */
public class AmendableElement extends Element {

    public static final String TAG_LITERAL_INDEX = "num";
    public static final String TAG_CONTENT = "content";
    public static final String TAG_HEADING = "heading";
    public static final String TAG_INTRODUCTORY = "introductory";

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

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Bulgarian_language#Alphabet">Bulgarian Alphabet</a>
     */
    private static final char[] CYRILLIC_NUMBERING = new char[]{
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч',
            'Ш', 'Щ', 'Ъ', 'Ь', 'Ю', 'Я', 'а', 'б',
            'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
            'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ь', 'ю', 'я'
    };

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Greek_language#Greek_alphabet">Greek Alphabet</a>
     */
    private static final char[] GREEK_NUMBERING = new char[]{
            'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ',
            'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π',
            'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω',
            'α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ',
            'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π',
            'ρ', 'σ', 'ς', 'τ', 'υ', 'φ', 'χ', 'ψ',
            'ω'
    };

    /*


     */

    /**
     * Required protected default constructor.
     */
    protected AmendableElement() {
    }

    /**
     * Assert that the given {@link com.google.gwt.dom.client.Element} is compatible with this class and
     * automatically typecast it.
     * <p/>
     * Note that this is simply a convenience method to use overlay types.
     *
     * @param elem the element to cast
     * @return the AmendableElement
     */
    public static AmendableElement as(Element elem) {
        return (AmendableElement) elem;
    }

    /**
     * Returns the amendable content of an element.
     *
     * @return the amendable content.
     */
    public final String getAmendableContent() {
        Element el = getAmendableContentElement();
        return el != null ? el.getInnerHTML() : null;
    }

    /**
     * Returns the amendable element content holder of an element.
     *
     * @return the amendable content holder element.
     */
    private Element getAmendableContentElement() {
        return getElementByTag(TAG_CONTENT);
    }

    /**
     * Check whether or not this element declares if it's amendable.
     *
     * @return true or false, or null if it hasn't been specified.
     */
    public final Boolean isAmendable() {
        String amendableAttribute = getAttribute(ATTRIB_AMENDABLE);
        return amendableAttribute == null || "".equals(amendableAttribute) ? null : "true".equalsIgnoreCase(amendableAttribute);
    }

    /**
     * Check whether or not this element declares if it's immutable.
     *
     * @return true or false, or null if it hasn't been specified.
     */
    public final Boolean isImmutable() {
        String immutable = getAttribute(ATTRIB_IMMUTABLE);
        return immutable == null || "".equals(immutable) ? null : "true".equalsIgnoreCase(immutable);
    }

    /**
     * Returns the source of a given element. This is used to determine what
     * the amendment is on, eg. Text from Commission, Text from Spanish Parliament, ..
     *
     * @return the source string, or null
     */
    public final String getSource() {
        String sourceAttribute = getAttribute(ATTRIB_SOURCE);
        return sourceAttribute == null || "".equals(sourceAttribute) ? null : sourceAttribute;
    }

    /**
     * Returns the type of this amendable element.
     *
     * @return the type
     */
    public final String getType() {
        return getTagName();
    }

    /**
     * Returns the type of the amended document e.g. 'Proposal for a decision', 'Motion for resolution' etc
     *
     * @return the type of amended document
     */
    public final String getAmendedDocument() {
        //return getOwnerDocument().getDocumentElement().getElementsByTagName();
        throw new UnsupportedOperationException("Not yet implemented.");
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
    public final NumberingType getNumberingType(int assignedIndex) {
        final String type = getAttribute(ATTRIB_NUMBERING_TYPE);
        if (type == null || "".equals(type.trim())) {
            // not defined, so try to guess it
            return guessNumberingType(assignedIndex);
        }
        return NumberingType.valueOf(type.toUpperCase());
    }

    // TODO: add support for indexes like bullet points, arrows, snowmen, etc ..
    private NumberingType guessNumberingType(int assignedIndex) {
        String literalIndex = getLiteralIndex();
        if (literalIndex != null) {
            final String unformattedIndex = getUnformattedIndex();
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
        if (Arrays.binarySearch(GREEK_NUMBERING, c) != -1)
            return true;
        // perhaps in cyrillic
        if (Arrays.binarySearch(CYRILLIC_NUMBERING, c) != -1)
            return true;
        // nope, guess it's ascii then ..
        return Character.isLetter(c);
    }

    /**
     * Returns the {@link Format}
     * of this amendable element.
     *
     * @return the type
     * @see Format
     */
    public final Format getFormat() {
        final String format = getAttribute(ATTRIB_FORMAT);
        if (format == null || "".equals(format.trim())) {
            // guess
            return guessFormat();
        }
        return Format.valueOf(format);
    }

    /**
     * Guesses the {@link Format}
     * based on the literal index provided for this element.
     *
     * @return the type
     */
    private Format guessFormat() {
        String literalIndex = getLiteralIndex();
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
    public final Integer getAssignedIndex() {
        String assigned = getAttribute(ATTRIB_ASSIGNED_INDEX);
        if (assigned != null && !"".equals(assigned)) {
            Integer assignedIndex = null;
            try {
                assignedIndex = Integer.parseInt(assigned);
            } catch (NumberFormatException e) {
                Log.error("The assigned index '" + assigned + "' on " + asString() + " is not a number -- ignoring.");
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
    public final String getLiteralIndex() {
        Element el = getElementByTag(TAG_LITERAL_INDEX);
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
    public final String getTranslationId() {
        String t = getAttribute(ATTRIB_TRANSLATION_ID);
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
    public final String getUnformattedIndex() {
        // see if we have an original index attribute specified
        String originalIndex = getAttribute(ATTRIB_ORIGINAL_INDEX);
        if (originalIndex != null && !"".equals(originalIndex.trim()))
            return originalIndex;
        // if not, work with the literal index
        String literalIndex = getLiteralIndex();
        if (literalIndex != null && !"".equals(literalIndex)) {
            Format f = getFormat();
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
    public final Element getOperationPanelHolderElement() {
        Element element = getElementByClassName(CLASS_OPERATION_PANEL);
        if (element == null) {
            // create it
            final com.google.gwt.user.client.Element div = DOM.createDiv();
            div.setClassName(CLASS_OPERATION_PANEL);
            getParentElement().insertBefore(div, this);
            element = div.cast();
        }
        return element;
    }

    /**
     * Returns the element to place the (new) child elements on.
     *
     * @return the children holder element.
     */
    public final Element getChildrenHolderElement() {
        throw new UnsupportedOperationException("No longer exists in Akoma Ntoso.");
    }

    /**
     * Returns the element to place the amendments on.
     *
     * @return the amendment holder element.
     */
    public final Element getAmendmentHolderElement() {
        Element element = getElementByClassName(CLASS_AMENDMENTS_PANEL);
        if (element == null) {
            // create it
            final com.google.gwt.user.client.Element div = DOM.createDiv();
            div.setClassName(CLASS_AMENDMENTS_PANEL);
            // TODO find the correct place to inject this panel
            appendChild(div);
            element = div.cast();
        }
        return element;
    }

    /**
     * Returns a list of all the amendable elements under this element by
     * enumerating the elements under the 'children' element of this element.
     * <p/>
     * If there is no children element, an empty list will be returned.
     *
     * @return the list of children.
     */
    public final List<AmendableElement> getChildren() {

        NodeList<Node> nodes = getChildNodes();
        List<AmendableElement> amendableElements = new ArrayList<AmendableElement>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.getItem(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = nodes.getItem(i).cast();
                // note: the only way we can detect 'children' is because they have an id specified ... :(
                if (!"num".equalsIgnoreCase(el.getNodeName()) && !"content".equalsIgnoreCase(el.getNodeName())) {
                    final AmendableElement ae = AmendableElement.as(el);
                    assert ae != null : "Could not convert " + DOM.toString(el.<com.google.gwt.user.client.Element>cast());
                    if ("NUM".equalsIgnoreCase(ae.getNodeName())) {
                        throw new RuntimeException("Shouldn't happen: " + el.getId());
                    }
                    amendableElements.add(ae);
                }
            }
        }
        return amendableElements;
    }

    public final Element getElementByTag(final String tag) {
        for (int i = 0; i < getChildCount(); i++) {
            Node node = getChild(i);
            if (node.getNodeName().equals(tag)) {
                return node.cast();
            }
        }
        return null;
    }

    public final Element getElementByAttribute(final String attributeName, final String attributeValue) {
        for (int i = 0; i < getChildCount(); i++) {
            Node node = getChild(i);
            if (node.getNodeType() == ELEMENT_NODE) {
                Element element = node.cast();
                if (element.getAttribute(attributeName).equalsIgnoreCase(attributeValue)) {
                    return element;
                }
            }
        }
        return null;
    }

    public final Element getElementByClassName(final String className) {
        for (int i = 0; i < getChildCount(); i++) {
            Node node = getChild(i);
            if (node.getNodeType() == ELEMENT_NODE) {
                Element element = node.cast();
                if (element.getClassName().equalsIgnoreCase(className)) {
                    return element;
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
                        Log.warn("A 'null' node found -- that's very weird. Running in hosted mode under Chrome perhaps?");
                    }
                }
            } else {
                Log.warn("Nodes under nextElem are null -- that's very weird. Running in hosted mode under Chrome perhaps?");
            }
        }
    }

    private static interface ElementVisitor {
        boolean visit(Element visited);
    }

    public final String asString() {
        return "<" + getTagName() + " id=" + getId() + " class=" + getClassName() + " type=" + getType() + ">";
    }
}