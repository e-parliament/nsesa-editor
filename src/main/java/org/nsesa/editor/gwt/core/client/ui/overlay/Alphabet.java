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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import java.util.Arrays;

/**
 * Keeps track of non-latin alphabets such as Cyrillic and Greek. Used to guess the numbering type of a given
 * overlay widget.
 * <p/>
 * Date: 05/07/12 21:59
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Alphabet {

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

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Bulgarian_language#Alphabet">Bulgarian Alphabet</a>
     */
    private static final Character[] CYRILLIC_NUMBERING = new Character[]{
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч',
            'Ш', 'Щ', 'Ъ', 'Ь', 'Ю', 'Я', 'а', 'б',
            'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
            'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ь', 'ю', 'я'
    };

    public static Character[] getCyrillicNumbering() {
        return Arrays.asList(CYRILLIC_NUMBERING).toArray(new Character[CYRILLIC_NUMBERING.length]);
    }

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Greek_language#Greek_alphabet">Greek Alphabet</a>
     */
    private static final Character[] GREEK_NUMBERING = new Character[]{
            'Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ',
            'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π',
            'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω',
            'α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ',
            'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π',
            'ρ', 'σ', 'ς', 'τ', 'υ', 'φ', 'χ', 'ψ',
            'ω'
    };

    public static Character[] getGreekNumbering() {
        return Arrays.asList(GREEK_NUMBERING).toArray(new Character[GREEK_NUMBERING.length]);
    }
}
