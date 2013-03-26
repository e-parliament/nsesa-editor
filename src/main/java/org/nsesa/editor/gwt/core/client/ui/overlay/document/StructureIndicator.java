/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import java.util.Arrays;
import java.util.List;
/**
 * This interface emulates the structure of an overlay widget as it comes from xsd definition
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 22/03/13 12:20
 */
public interface StructureIndicator {

    public static int UNBOUNDED = -1;

    /**
     * The minimum number of times an element can occur
     * @return as int
     */
    abstract int getMaxOccurs();

    /**
     * The maximum number of times an element can occur
     * @return as int
     */
    abstract int getMinOccurs();

    /**
     * Returns the list of the indicators when the root acts as composite
     * @return as List
     */
    abstract List<StructureIndicator> getIndicators();

    /**
     * Emulates "All" xsd indicator
     */
    public static interface All extends StructureIndicator {
    }

    /**
     * Emulates "sequence" xsd indicator
     */
    public static interface Sequence extends StructureIndicator {
    }

    /**
     * Emulates "choice" xsd indicator
     */
    public static interface Choice extends StructureIndicator {
    }

    /**
     * Emulates "group" xsd indicator
     */
    public static interface Group extends StructureIndicator {
    }

    /**
     * Emulates "element" xsd indicator
     */
    public static interface Element extends StructureIndicator {
        abstract OverlayWidget asWidget();
    }

    /**
     * Emulates "wildcard" xsd indicator
     */
    public static interface Wildcard extends Element {
        abstract void setWidget(OverlayWidget widget);
    }

    /**
     * Default implementation of <code>StructureIndicator</code> interface
     */
    public static class DefaultStructureIndicator implements StructureIndicator {
        private int minOccurs;
        private int maxOccurs;
        private List<StructureIndicator> indicators;

        public DefaultStructureIndicator(int minOccurs, int maxOccurs) {
            this.minOccurs = minOccurs;
            this.maxOccurs = maxOccurs;
        }

        public DefaultStructureIndicator(int minOccurs, int maxOccurs, StructureIndicator... indicators) {
            this(minOccurs,maxOccurs);
            this.indicators = Arrays.asList(indicators);
        }

        @Override
        public int getMaxOccurs() {
            return maxOccurs;
        }

        @Override
        public int getMinOccurs() {
            return minOccurs;
        }
       @Override
        public List<StructureIndicator> getIndicators() {
            return indicators;
        }
    }


    /**
     * Default implementation of <code>Group</code> structure indicator interface
     */
    public static class DefaultGroup extends DefaultStructureIndicator implements All {
        public DefaultGroup(int minOccurs, int maxOccurs, StructureIndicator... indicators) {
            super(minOccurs, maxOccurs, indicators);
        }
    }
    /**
     * Default implementation of <code>All</code> structure indicator interface
     */
    public static class DefaultAll extends DefaultStructureIndicator implements All {
        public DefaultAll(int minOccurs, int maxOccurs, StructureIndicator... indicators) {
            super(minOccurs, maxOccurs, indicators);
        }
    }

    /**
     * Default implementation of <code>Seq</code> structure indicator interface
     */
    public static class DefaultSequence extends DefaultStructureIndicator implements Sequence {
        public DefaultSequence(int minOccurs, int maxOccurs, StructureIndicator... indicators) {
            super(minOccurs, maxOccurs, indicators);
        }
    }

    /**
     * Default implementation of <code>Choice</code> structure indicator interface
     */
    public static class DefaultChoice extends DefaultStructureIndicator implements Choice {
        public DefaultChoice(int minOccurs, int maxOccurs, StructureIndicator... indicators) {
            super(minOccurs, maxOccurs, indicators);
        }
    }

    /**
     * Default implementation of <code>Element</code> structure indicator interface
     */
    public static class DefaultElement extends DefaultStructureIndicator implements Element {
        protected OverlayWidget widget;

        public DefaultElement(int minOccurs, int maxOccurs) {
            super(minOccurs, maxOccurs);
        }

        public DefaultElement(int minOccurs, int maxOccurs, OverlayWidget widget) {
            super(minOccurs, maxOccurs);
            this.widget = widget;
        }

        @Override
        public OverlayWidget asWidget() {
            return widget;
        }
    }
    /**
     * Default implementation of <code>Wildcard</code> structure indicator interface
     */
    public static class DefaultWildcard extends DefaultElement implements Wildcard {
        public DefaultWildcard(int minOccurs, int maxOccurs) {
            super(minOccurs, maxOccurs);
        }
        @Override
        public void setWidget(OverlayWidget widget) {
            this.widget = widget;
        }
    }

}
