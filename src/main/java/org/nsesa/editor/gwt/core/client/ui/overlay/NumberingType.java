package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: NumberingType.java 4771 2012-01-20 13:24:50Z pluppens $
 */
public enum NumberingType {
    ROMANITO {
        @Override
        public String get(int index) {
            // index + 1 because index it is zero based
            return RomanConvertor.int2roman(index + 1);
        }

        @Override
        public Integer get(String unformattedIndex) {
            return RomanConvertor.roman2int(unformattedIndex);
        }
    },
    NUMBER {
        @Override
        public String get(int index) {
            // index + 1 because it is zero based
            return Integer.toString(index + 1);
        }

        @Override
        public Integer get(String unformattedIndex) {
            return Integer.valueOf(unformattedIndex);
        }
    },
    LETTER {
        @Override
        public String get(int index) {
            return TextUtils.getLiteralForNumber(index);
        }

        @Override
        public Integer get(String unformattedIndex) {
            return (int)unformattedIndex.toCharArray()[0];
        }
    },
    COMBO {
        @Override
        public String get(int index) {
            throw new UnsupportedOperationException("A combination literal index (1A, A23, ...) cannot be used for new elements.");
        }

        @Override
        public Integer get(String unformattedIndex) {
            return null;
        }
    },
    NONE {
        @Override
        public String get(int index) {
            return "";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex) {
            return null;
        }
    },
    INDENT {
        @Override
        public String get(int index) {
            return "-";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex) {
            return null;
        }
    },
    BULLET {
        @Override
        public String get(int index) {
            return "*";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex) {
            return null;
        }
    },
    ARROW {
        @Override
        public String get(int index) {
            return "->";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex) {
            return null;
        }
    };

    public abstract String get(int index);

    public abstract Integer get(String unformattedIndex);

    /**
     * Check if the numbering type is constant and does not differ from one element to another (eg. indents, arrows, etc)
     *
     * @return true if the location is constant, false if it changes.
     */
    public boolean isConstant() {
        return false;
    }

}
