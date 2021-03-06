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

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetSelector;
import org.nsesa.editor.gwt.core.client.util.OverlayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Numbering type enum to handle the different numbering types we encounter in XML documents.
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: NumberingType.java 4771 2012-01-20 13:24:50Z pluppens $
 */
public enum NumberingType {
    ROMANITO {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            // index + 1 because index it is zero based
            return RomanConvertor.int2roman(index + 1);
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            return RomanConvertor.roman2int(unformattedIndex);
        }
    },
    NUMBER {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            // index + 1 because it is zero based
            return Integer.toString(index + 1);
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            // - 1 because it is zero based
            return Integer.valueOf(unformattedIndex) - 1;
        }
    },
    LETTER {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            return Alphabet.getLiteralForNumber(index);
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            // todo: handle cases with alphabet overflows (> 26)
            return ((int) unformattedIndex.toLowerCase().toCharArray()[0]) - 97;
        }
    },
    COMBO {
        @Override
        public String get(int index, final OverlayWidget overlayWidget) {
            OverlayWidget reference = overlayWidget.getPreviousSibling(new OverlayWidgetSelector() {
                @Override
                public boolean select(OverlayWidget toSelect) {
                    return toSelect.getType().equalsIgnoreCase(overlayWidget.getType());
                }
            });
            if (reference == null) {
                reference = overlayWidget.getNextSibling(new OverlayWidgetSelector() {
                    @Override
                    public boolean select(OverlayWidget toSelect) {
                        return toSelect.getType().equalsIgnoreCase(overlayWidget.getType());
                    }
                });
            }
            if (reference == null) {
                return Integer.toString(index + 1);
            }

            String unformattedIndex = reference.getUnformattedIndex();
            if (unformattedIndex != null) {
                String relevantPart = TextUtils.findRelevantPart(unformattedIndex);
                String irrelevantPart = TextUtils.findIrrelevantPart(unformattedIndex);

                if (Character.isDigit(relevantPart.toCharArray()[0])) {
                    return irrelevantPart + (Integer.parseInt(relevantPart) + 1);
                } else {
                    // todo: handle cases with alphabet overflows (> 26)
                    return irrelevantPart + (char) ((int) relevantPart.toCharArray()[0] + 1);
                }
            } else {
                return Integer.toString(index + 1);
            }
        }

        @Override
        public Integer get(String unformattedIndex, final OverlayWidget overlayWidget) {

            OverlayWidget reference = overlayWidget.getPreviousSibling(new OverlayWidgetSelector() {
                @Override
                public boolean select(OverlayWidget toSelect) {
                    return toSelect.getType().equalsIgnoreCase(overlayWidget.getType());
                }
            });
            if (reference == null) {
                reference = overlayWidget.getNextSibling(new OverlayWidgetSelector() {
                    @Override
                    public boolean select(OverlayWidget toSelect) {
                        return toSelect.getType().equalsIgnoreCase(overlayWidget.getType());
                    }
                });
            }

            if (reference != null) {
                unformattedIndex = reference.getUnformattedIndex();
                if (unformattedIndex != null) {
                    String relevantSuffix = TextUtils.findRelevantPart(unformattedIndex);

                    if (Character.isDigit(relevantSuffix.toCharArray()[0])) {
                        return Integer.parseInt(relevantSuffix);
                    } else {
                        // todo: handle cases with alphabet overflows (> 26)
                        return (int) relevantSuffix.toCharArray()[0];
                    }
                }
            }
            return Integer.parseInt(unformattedIndex) - 1;
        }

    },
    NONE {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            return "";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            return null;
        }
    },
    INDENT {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            return "-";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            return null;
        }
    },
    BULLET {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            return "*";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            return null;
        }
    },
    ARROW {
        @Override
        public String get(int index, OverlayWidget overlayWidget) {
            return "->";
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public Integer get(String unformattedIndex, OverlayWidget overlayWidget) {
            return null;
        }
    };

    public abstract String get(int index, OverlayWidget overlayWidget);

    public abstract Integer get(String unformattedIndex, OverlayWidget overlayWidget);

    /**
     * Check if the numbering type is constant and does not differ from one element to another (eg. indents, arrows, etc)
     *
     * @return true if the location is constant, false if it changes.
     */
    public boolean isConstant() {
        return false;
    }

}
