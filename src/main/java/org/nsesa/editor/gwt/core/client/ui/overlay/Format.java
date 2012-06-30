package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * Index format for enumerations in the source text.
 * Possible supported formats:
 * <ul>
 * <li>POINT (eg. '1.')</li>
 * <li>BRACKET (eg. '1)')</li>
 * <li>DOUBLE BRACKET (eg. '(1)')</li>
 * <li>NONE (eg. '')</li>
 * <li>INDENT (eg. '-')</li>
 * </ul>
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: Format.java 4771 2012-01-20 13:24:50Z pluppens $
 */
public enum Format {
    POINT {
        @Override
        public final String format(String index) {
            return index + ".";
        }
    }, // eg. 1.
    BRACKET {
        @Override
        public final String format(String index) {
            return index + ")";
        }
    }, // eg. 1)
    DOUBLE_BRACKET {
        @Override
        public final String format(String index) {
            return "(" + index + ")";
        }
    }, // eg. (1)
    INDENT {
        @Override
        public final String format(String index) {
            return "–";
        }
    }, // eg. –
    NONE {
        @Override
        public String format(String index) {
            return index;
        }
    };

    public String format(String index) {
        throw new RuntimeException("Should have been overridden by subclass.");
    }
}