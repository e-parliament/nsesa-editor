package org.nsesa.editor.gwt.core.client.util.select;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

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

    public List<AmendableWidget> select(final String path, final AmendableWidget root) {
        List<AmendableWidget> amendableWidgets = new ArrayList<AmendableWidget>();
        final String[] split = path.split("/");
        select(split, root, amendableWidgets);
        return amendableWidgets;
    }

    public void select(final String[] split, final AmendableWidget root, final List<AmendableWidget> found) {
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
                        for (final AmendableWidget child : root.getChildAmendableWidgets()) {
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
        public boolean matches(String expression, AmendableWidget amendableWidget) {
            return "*".equals(expression);
        }

        @Override
        public boolean applicable(String expression) {
            return true;
        }
    }

    private static class AttributeMatcher implements Matcher {
        @Override
        public boolean matches(String expression, AmendableWidget amendableWidget) {
            final String attributeName = expression.substring(0, expression.indexOf("="));
            final String attributeValue = expression.substring(expression.indexOf("=") + 1, expression.length());
            final String attribute;
            if ("id".equalsIgnoreCase(attributeName)) {
                return attributeValue.equalsIgnoreCase(amendableWidget.getId());
            } else
                attribute = amendableWidget.getAttributes().get(attributeName);
            return attribute != null && attribute.equals(attributeValue);
        }

        @Override
        public boolean applicable(String expression) {
            return expression.contains("[") && expression.contains("=") && expression.contains("]");
        }
    }

    private static class NodeNameMatcher implements Matcher {
        @Override
        public boolean matches(String expression, AmendableWidget amendableWidget) {
            return expression.equalsIgnoreCase(amendableWidget.getType());
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
        public boolean matches(String expression, AmendableWidget amendableWidget) {
            final String type = expression.substring(0, expression.indexOf("["));
            final String index = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
            boolean matchOnType = wildcardMatcher.matches(type, amendableWidget) || nodeNameMatcher.matches(type, amendableWidget);
            return matchOnType && (wildcardMatcher.matches(index, amendableWidget) || indexMatcher.matches(index, amendableWidget));
        }

        @Override
        public boolean applicable(String expression) {
            return nodeNameMatcher.applicable(expression) && indexMatcher.applicable(expression);
        }
    }

    private static class IndexMatcher implements Matcher {
        @Override
        public boolean matches(String expression, AmendableWidget amendableWidget) {
            try {
                return amendableWidget.getTypeIndex() == Integer.parseInt(expression);
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
