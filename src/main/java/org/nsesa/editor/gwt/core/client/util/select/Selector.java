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
package org.nsesa.editor.gwt.core.client.util.select;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: 07/01/13 14:23
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Selector {

    private static final Matcher[] MATCHERS = new Matcher[]{
            new AttributeMatcher(), new NodeNameIndexMatcher(), new IndexMatcher(), new NodeNameMatcher(), new WildcardMatcher()
    };

    public List<OverlayWidget> select(final String path, final OverlayWidget root) {
        List<OverlayWidget> overlayWidgets = new ArrayList<OverlayWidget>();
        final String[] split = path.split("/");
        select(split, root, overlayWidgets);
        return overlayWidgets;
    }

    public void select(final String[] split, final OverlayWidget root, final List<OverlayWidget> found) {
        if (split != null && split.length > 0) {
            final String expression = split[0];
            for (final Matcher matcher : MATCHERS) {
                if (matcher.applicable(expression)) {
                    if (matcher.matches(expression, root)) {
                        if (split.length == 1) {
                            // we're at the last part of the expression ...
                            found.add(root);
                        }
                        // go deeper
                        for (final OverlayWidget child : root.getChildOverlayWidgets()) {
                            // does not work in GWT ...
                            // final String[] tail = Arrays.copyOfRange(split, 1, split.length);
                            final List<String> copy = Arrays.asList(split).subList(1, split.length);
                            final String[] tail = copy.toArray(new String[copy.size()]);
                            select(tail, child, found);
                        }
                        break;
                    }
                }
            }
        }
    }

    private static class WildcardMatcher implements Matcher {
        @Override
        public boolean matches(String expression, OverlayWidget overlayWidget) {
            return "*".equals(expression);
        }

        @Override
        public boolean applicable(String expression) {
            return true;
        }
    }

    private static class AttributeMatcher implements Matcher {
        @Override
        public boolean matches(String expression, OverlayWidget overlayWidget) {
            final String attributeName = expression.substring(0, expression.indexOf("="));
            final String attributeValue = expression.substring(expression.indexOf("=") + 1, expression.length());
            final String attribute;
            if ("id".equalsIgnoreCase(attributeName)) {
                return attributeValue.equalsIgnoreCase(overlayWidget.getId());
            } else
                attribute = overlayWidget.getAttributes().get(attributeName);
            return attribute != null && attribute.equals(attributeValue);
        }

        @Override
        public boolean applicable(String expression) {
            return expression.contains("[") && expression.contains("=") && expression.contains("]");
        }
    }

    private static class NodeNameMatcher implements Matcher {
        @Override
        public boolean matches(String expression, OverlayWidget overlayWidget) {
            return expression.equalsIgnoreCase(overlayWidget.getType());
        }

        @Override
        public boolean applicable(String expression) {
            return true;
        }
    }

    private static class NodeNameIndexMatcher implements Matcher {

        private NodeNameMatcher nodeNameMatcher = new NodeNameMatcher();
        private WildcardMatcher wildcardMatcher = new WildcardMatcher();
        private IndexMatcher indexMatcher = new IndexMatcher();

        @Override
        public boolean matches(String expression, OverlayWidget overlayWidget) {
            final String type = expression.substring(0, expression.indexOf("["));
            final String index = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
            boolean matchOnType = wildcardMatcher.matches(type, overlayWidget) || nodeNameMatcher.matches(type, overlayWidget);
            return matchOnType && (wildcardMatcher.matches(index, overlayWidget) || indexMatcher.matches(index, overlayWidget));
        }

        @Override
        public boolean applicable(String expression) {
            return nodeNameMatcher.applicable(expression) && indexMatcher.applicable(expression);
        }
    }

    private static class IndexMatcher implements Matcher {
        @Override
        public boolean matches(String expression, OverlayWidget overlayWidget) {
            try {
                return overlayWidget.getTypeIndex() == Integer.parseInt(expression);
            } catch (NumberFormatException e) {
                // ignore
                return false;
            }
        }

        @Override
        public boolean applicable(String expression) {
            return expression.contains("[") && expression.contains("]");
        }
    }

}
