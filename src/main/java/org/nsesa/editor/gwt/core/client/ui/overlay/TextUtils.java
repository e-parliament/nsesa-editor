package org.nsesa.editor.gwt.core.client.ui.overlay;


/**
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: TextUtils.java 5027 2012-02-17 14:01:17Z pluppens $
 */
public class TextUtils {
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

    public static String collapseWhiteSpace(String content) {
        return content.replaceAll("\\s+", " ");
    }

    public static String limit(String input, int max) {
        if (input.length() > max) {
            input = input.substring(0, max - 4) + " ...";
        }
        return input;
    }

    public static String capitalize(String input) {
        assert input != null;
        if ("".equals(input.trim()))
            return input;
        if (input != null)
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        return null;
    }

    /**
     * Strips all HTML tags
     *
     * @param {Mixed} value The text from which to strip tags
     * @return {String} The stripped text
     */
    public static String stripTags(String html) {
        return html.replaceAll("\\<.*?\\>", "");
    }

    private final static char[] alphabet;

    static {
        // create the alphabet - a to b
        alphabet = new char[26];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char) (i + (int) 'a');
        }
    }

    public static String getLiteralForNumber(int index) {
        if (index > alphabet.length * alphabet.length)
            throw new IllegalArgumentException("Cannot serve a number greater than " +
                    alphabet.length * alphabet.length);
        /*
       if the index is bigger than the number of chars in the alphabet,
       we'll add the the extra char equal to the number of times
       this index will overflow the alphabet.
        */
        if (index > alphabet.length) {
            return String.valueOf((alphabet[index / alphabet.length]) + alphabet[index % alphabet.length]);
        }
        return String.valueOf(alphabet[index]);
    }

    public static String markBoldItalic(String input) {
        if (input == null) return null;
        return "<span class='bold_italic'>" + input + "</span>";
    }

    public static String dump(StringBuilder sb, int level, Throwable e) {

        for (StackTraceElement traceElement : e.getStackTrace()) {
            sb.append(traceElement.getMethodName()).append(" in ").append(traceElement.getClassName()).append(" at line ").append(traceElement.getLineNumber()).append("\n");
        }
        if (e.getCause() != null) {
            sb.append("\n\n------------ UNDERLYING CAUSE-----------------\n\n");
            sb.append(dump(sb, ++level, e.getCause()));
        }
        return sb.toString();
    }


    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            // isWhitespace is not supported yet by gwt
            if (!Character.isSpace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a string starts with digits and might contains after digits only letters
     *
     * @param str
     * @return
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

    private static final String DOCUMENT_START_TAG = "<document>";
    private static final String DOCUMENT_END_TAG = "</document>";
    private static final String BOM_CHAR = "\uFEFF";

    /**
     * Strip start and end document tags
     *
     * @param html
     * @return
     */
    public static String stripDocumentTags(String html) {
        if (html == null) {
            return null;
        }
        html = html.trim();
        if (html.startsWith(BOM_CHAR)) {
            html = html.substring(1);
        }
        if (html.startsWith(DOCUMENT_START_TAG)) {
            html = html.substring(DOCUMENT_START_TAG.length());
        }
        if (html.endsWith(DOCUMENT_END_TAG)) {
            html = html.substring(0, html.length() - DOCUMENT_END_TAG.length());
        }
        return html;
    }
}
