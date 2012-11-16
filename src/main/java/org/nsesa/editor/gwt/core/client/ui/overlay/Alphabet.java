package org.nsesa.editor.gwt.core.client.ui.overlay;

import java.util.Arrays;

/**
 * Date: 05/07/12 21:59
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Alphabet {
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
