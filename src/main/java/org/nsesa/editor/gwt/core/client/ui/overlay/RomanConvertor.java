package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * @author Fred Swartz - 2006-12-29 - Placed in public domain (MIT License).
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a> (small modifications)
 * @see <a href="http://www.leepoint.net/notes-java/examples/components/romanNumerals/romanNumeral.html">Origin</a>
 */
public class RomanConvertor {
    private final static RomanValue[] ROMAN_VALUE_TABLE = {
            new RomanValue(1000, "M"),
            new RomanValue(900, "CM"),
            new RomanValue(500, "D"),
            new RomanValue(400, "CD"),
            new RomanValue(100, "C"),
            new RomanValue(90, "XC"),
            new RomanValue(50, "L"),
            new RomanValue(40, "XL"),
            new RomanValue(10, "X"),
            new RomanValue(9, "IX"),
            new RomanValue(5, "V"),
            new RomanValue(4, "IV"),
            new RomanValue(1, "I")
    };

    private static class RomanValue {
        final int intVal;
        final String romVal;

        RomanValue(final int dec, final String rom) {
            this.intVal = dec;
            this.romVal = rom;
        }
    }

    public static String int2roman(int n) {
        if (n >= 4000 || n < 1) {
            throw new NumberFormatException("Numbers must be in range 1-3999");
        }
        final StringBuilder result = new StringBuilder();

        // ... Start with largest value, and work toward smallest
        for (RomanValue equiv : ROMAN_VALUE_TABLE) {
            // ... Remove as many of this value as possible (maybe none)
            while (n >= equiv.intVal) {
                // Subtract value
                n -= equiv.intVal;
                // Add roman equivalent
                result.append(equiv.romVal);
            }
        }
        return result.toString().toLowerCase();
    }

    public static Integer roman2int(String roman) {
        return null;
    }

}
