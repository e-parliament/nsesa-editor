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
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id: Format.java 4771 2012-01-20 13:24:50Z pluppens $
 */
public enum Format {
    POINT {
        @Override
        public final String format(String index) {
            return index + ".";
        }

        @Override
        public String unformat(String index) {
            return index.substring(0, index.length() - 1);
        }
    }, // eg. 1.
    BRACKET {
        @Override
        public final String format(String index) {
            return index + ")";
        }
        @Override
        public String unformat(String index) {
            return index.substring(0, index.length() - 1);
        }
    }, // eg. 1)
    DOUBLE_BRACKET {
        @Override
        public final String format(String index) {
            return "(" + index + ")";
        }
        @Override
        public String unformat(String index) {
            return index.substring(1, index.length() - 1);
        }
    }, // eg. (1)
    INDENT {
        @Override
        public final String format(String index) {
            return "–";
        }
        @Override
        public String unformat(String index) {
            return "";
        }
    }, // eg. –
    NONE {
        @Override
        public String format(String index) {
            return index;
        }
        @Override
        public String unformat(String index) {
            return index;
        }
    };

    public String format(String index) {
        throw new RuntimeException("Should have been overridden by subclass.");
    }

    public String unformat(String index) {
        throw new RuntimeException("Should have been overridden by subclass.");
    }
}