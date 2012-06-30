package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: RomanConvertor.java 4771 2012-01-20 13:24:50Z pluppens $
 */
public class RomanConvertor {
    final static RomanValue[] ROMAN_VALUE_TABLE = {
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
        //============================================================== fields
        //... No need to make this fields private because they are
        //    used only in this private value class.
        int intVal;     // Integer value.
        String romVal;     // Equivalent roman numeral.

        //========================================================= constructor
        RomanValue(int dec, String rom) {
            this.intVal = dec;
            this.romVal = rom;
        }
    }

    public static String int2roman(int n) {
        if (n >= 4000 || n < 1) {
            throw new NumberFormatException("Numbers must be in range 1-3999");
        }
        StringBuilder result = new StringBuilder();

        //... Start with largest value, and work toward smallest.
        for (RomanValue equiv : ROMAN_VALUE_TABLE) {
            //... Remove as many of this value as possible (maybe none).
            while (n >= equiv.intVal) {
                n -= equiv.intVal;            // Subtract value.
                result.append(equiv.romVal);  // Add roman equivalent.
            }
        }
        return result.toString().toLowerCase();
    }

}
