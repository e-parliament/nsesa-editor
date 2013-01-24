package org.nsesa.editor.app.xsd.model;

import java.util.Random;

/**
 * Generates a color code in hexadecimal.
 * User: groza
 * Date: 21/01/13
 * Time: 13:05
 */
public class CssColorGenerator {

    public static CssColorGenerator INSTANCE = new CssColorGenerator();

    public static CssColorGenerator getInstance() {
        return INSTANCE;
    }

    private CssColorGenerator() {

    }

    public String getTextColor(final String label) {
        String color =  getColor(label);
        return matchTextColor(color);
    }

    public String matchTextColor(String color) {
        if (color.startsWith("#")) color = color.substring(1);
        if (color.length() != 6 ) return "000000";

        int r = Integer.parseInt(color.substring(0, 2), 16);
        int g = Integer.parseInt(color.substring(2, 4), 16);
        int b = Integer.parseInt(color.substring(4,6), 16);
        int brightness = (int)(Math.sqrt((r * r * 0.241) + (g * g * 0.691) + (b * b * 0.068)));
        return (brightness <=128 ? "FFFFFF" : "000000");
    }

    public String getColor(final String label) {
        final Random random = new Random(label.hashCode());
        String code = hexColor(random);
        return code;
    }

    private String hexColor(final Random random) {
        StringBuffer result = new StringBuffer();
        String sb = Integer.toHexString(random.nextInt(255));
        if (sb.length() < 2) {
            sb = "0" + sb;
        }
        result.append(sb);
        sb = Integer.toHexString(random.nextInt(255));
        if (sb.length() < 2) {
            sb = "0" + sb;
        }
        result.append(sb);
        sb = Integer.toHexString(random.nextInt(255));
        if (sb.length() < 2) {
            sb = "0" + sb;
        }
        result.append(sb);
        return result.toString();
    }
}
