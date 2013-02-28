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
package org.nsesa.editor.gwt.core.client.ui.overlay;


import java.util.LinkedHashMap;

/**
 * Unsorted collection of utilities related to String and XML processing.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: TextUtils.java 5027 2012-02-17 14:01:17Z pluppens $
 */
public class TextUtils {

    // TODO: this seems hardly sufficient? What about other entities?
    // xml conversion: & - &amp; < - &lt; > - &gt; " - &quot; ' - &apos;
    private static final LinkedHashMap<Character, String> XML_ESCAPER = new LinkedHashMap<Character, String>();

    static {
        XML_ESCAPER.put('&', "&amp;");
        XML_ESCAPER.put('<', "&lt;");
        XML_ESCAPER.put('>', "&gt;");
        XML_ESCAPER.put('"', "&quot;");
        XML_ESCAPER.put('\'', "&apos;");
    }

    /**
     * Strips all tags from a given <tt>html</tt>, and optionally collapses all whitespace afterwards.
     *
     * @param html               the html to get the text for
     * @param collapseWhiteSpace <tt>true</tt> if you want the whitespace to be collapsed into a single whitespace
     * @return
     */
    public static String stripTags(String html, boolean collapseWhiteSpace) {
        if (html == null)
            return null;
        if ("".equals(html.trim()))
            return "";

        html = html.replaceAll("<.*?>", "");
        if (collapseWhiteSpace) {
            // collapse all the whitespace
            html = collapseWhiteSpace(html);
        }
        return html.trim();
    }

    /**
     * Collapse all whitespace in a given <tt>content</tt> into a single whitespace.
     *
     * @param content the content
     * @return the content with whitespace collapsed
     */
    public static String collapseWhiteSpace(String content) {
        return content.replaceAll("\\s+", " ");
    }

    /**
     * Limit a given <tt>input</tt> string into <tt>max</tt> characters with ellipsis at the end ('...').
     *
     * @param input the input string
     * @param max   the max. amount of characters
     * @return the string limited to <tt>max</tt>. characters, or the input if the length was not exceeded
     */
    public static String limit(String input, int max) {
        if (input.length() > max) {
            input = input.substring(0, max - 4) + " ...";
        }
        return input;
    }

    /**
     * Capitalize a given <tt>input</tt> string
     *
     * @param input the input to capitalize
     * @return the capitalized input, <tt>null</tt> if it was <tt>null</tt>, and an empty string ("") otherwise
     */
    public static String capitalize(String input) {
        if (input != null) {
            if ("".equals(input.trim()))
                return input;
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
        return null;
    }

    /**
     * Escape SOME xml special chars with their corresponding values as follows
     *
     * @param str The string to be escaped
     * @return The string escaped
     */
    public static String escapeXML(String str) {
        if (str == null) return null;
        StringBuilder sb = new StringBuilder(str.length() * 2);
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            String escape = XML_ESCAPER.get(ch);
            sb.append(escape == null ? ch.charValue() : escape);
        }
        return sb.toString();
    }

    /**
     * Checks whether a string starts with digits and might contains after the digits only letters
     *
     * @param str the string to check
     * @return true if the string starts with digits, and is followed by letters
     */
    public static boolean startsWithDigitsFollowedByLetters(String str) {
        int strLen = str.length();
        boolean startsWithDigits = false;

        int startForLetters = 0;
        for (int i = 0; i < strLen; i++) {
            if (Character.isDigit(str.charAt(i))) {
                startsWithDigits = true;
                continue;
            }
            ;
            startForLetters = i;
            break;
        }
        boolean followedByLetters = true;
        if (startsWithDigits && startForLetters > 0) {
            for (int i = startForLetters; i < strLen; i++) {
                if (!Character.isLetter(str.charAt(i))) {
                    followedByLetters = false;
                    break;
                }
                ;
            }
        }
        return startsWithDigits && followedByLetters;

    }

    /**
     * Creates a <tt>String</tt> of <tt>amount</tt> * <tt>indent</tt>.
     *
     * @param amount the number of times the indent string will be repeated.
     * @param indent the string part to repeat.
     * @return the repeated string.
     */
    public static String repeat(final int amount, final String indent) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be less than 0.");
        if (indent == null) return null;

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(indent);
        }
        return sb.toString();
    }
}
