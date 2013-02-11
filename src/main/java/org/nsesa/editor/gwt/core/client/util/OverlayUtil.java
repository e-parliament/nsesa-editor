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
package org.nsesa.editor.gwt.core.client.util;

import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.util.select.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 12/12/12 13:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayUtil {
    public static List<AmendableWidget> xpath(final String path, final AmendableWidget root) {
        final List<AmendableWidget> injectionPoints = new ArrayList<AmendableWidget>();
        final AmendableWidgetWalker.AmendableVisitor visitor;
        if (path.startsWith("//")) {
            // xpath-like expression ...
            Selector selector = new Selector();
            injectionPoints.addAll(selector.select(path.substring(2), root));
        } else {
            if (path.startsWith("#")) {
                visitor = new AmendableWidgetWalker.AmendableVisitor() {
                    @Override
                    public boolean visit(final AmendableWidget visited) {
                        if (visited != null) {
                            if (path.substring(1).equalsIgnoreCase(visited.getId())) {
                                injectionPoints.add(visited);
                            }
                        }
                        return true;
                    }
                };
            } else {
                visitor = new AmendableWidgetWalker.AmendableVisitor() {
                    @Override
                    public boolean visit(final AmendableWidget visited) {
                        if (visited != null) {
                            if (path.equalsIgnoreCase(visited.getId())) {
                                injectionPoints.add(visited);
                            }
                        }
                        return true;
                    }
                };
            }
            root.walk(visitor);
        }
        return injectionPoints;
    }

    public static List<AmendableWidget> find(final String expression, final AmendableWidget root) {
        final List<AmendableWidget> matches = new ArrayList<AmendableWidget>();
        root.walk(new AmendableWidgetWalker.AmendableVisitor() {
            @Override
            public boolean visit(final AmendableWidget visited) {
                // only simple tag names atm
                if (visited.getType().equalsIgnoreCase(expression)) {
                    matches.add(visited);
                }
                return true;
            }
        });
        return matches;
    }
}
