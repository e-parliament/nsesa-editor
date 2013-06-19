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

import java.util.Random;

/**
 * A generator to compute randomly color codes in hexadecimal format based on the given label.
 * The generated hexadecimal color code remains constant over the recomputation with the same label.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 21/01/13 13:38
 */
public class CssColorGenerator {

    private static CssColorGenerator INSTANCE = new CssColorGenerator();

    /**
     * Returns one instance of this generator
     * @return
     */
    public static CssColorGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Disallows creation of other instances
     */
    private CssColorGenerator() {
    }

    /**
     * Generates a black/white color code based on the given label
     * @param label The label that will be processed
     * @return
     */
    public String getTextColor(final String label) {
        String color = getColor(label);
        return matchTextColor(color);
    }

    /**
     * Returns "000000" or "FFFFFF" depending on the lightness of the background color
     * @param color The background color code in hexadecimal format
     * @return The text color in hexadecimal
     */
    public String matchTextColor(String color) {
        if (color.startsWith("#")) color = color.substring(1);
        if (color.length() != 6) return "000000";

        int r = Integer.parseInt(color.substring(0, 2), 16);
        int g = Integer.parseInt(color.substring(2, 4), 16);
        int b = Integer.parseInt(color.substring(4, 6), 16);
        int brightness = (int) (Math.sqrt((r * r * 0.241) + (g * g * 0.691) + (b * b * 0.068)));
        return (brightness <= 128 ? "FFFFFF" : "000000");
    }

    /**
     *  Generate a color based on provided label.
     *  The color remains constant during recomputation with the same label
     * @param label
     * @return
     */
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
