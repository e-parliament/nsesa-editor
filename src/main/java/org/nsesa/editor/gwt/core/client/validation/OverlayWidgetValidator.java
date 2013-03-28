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
package org.nsesa.editor.gwt.core.client.validation;

import org.nsesa.editor.gwt.core.client.amendment.OverlayWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.StructureIndicator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A more complex validation of a given overlay widget. Allows for validation of leaves and branches of a tree.
 * This validation currently check the given min/max occurrences of both the children and the current overlay widget.
 * Date: 19/02/13 14:03
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayWidgetValidator implements Validator<OverlayWidget> {

    /**
     * Validate a given {@link OverlayWidget} <tt>toValidate</tt> for the min/max occurrences.
     * @param toValidate the instance to validate
     * @return the validation result
     */
    @Override
    public ValidationResult validate(final OverlayWidget toValidate) {

        final ValidationResultImpl[] validationResults = new ValidationResultImpl[1];

        toValidate.walk(new OverlayWidgetWalker.OverlayWidgetVisitor() {
            @Override
            public boolean visit(final OverlayWidget visited) {

                // short circuit if we already have a validation result
                if (validationResults[0] != null) return false;

                // do a validation on the allowed child type(s)
                OverlayWidget parent = visited.getParentOverlayWidget();
                if (parent != null) {
                    List<StructureIndicator.Element> allowedChildren = findAllowedChildTypes(parent);
                    boolean typeAllowed = false;
                    for (StructureIndicator.Element structElem : allowedChildren) {
                        OverlayWidget allowedChild = structElem.asWidget();
                        if (allowedChild != null) {
                            if (allowedChild.getType().equals(visited.getType())
                                    && allowedChild.getNamespaceURI().equals(visited.getNamespaceURI())) {
                                typeAllowed = true;
                                int maxOccurence = structElem.getMaxOccurs();
                                if (maxOccurence != StructureIndicator.UNBOUNDED && maxOccurence < visited.getTypeIndex() + 1) {
                                    validationResults[0] = new ValidationResultImpl(false, "Max occurrence of " + maxOccurence + " not honored for " + visited.getType() + " under " + parent.getType(), visited);
                                }
                                break;
                            }
                        }
                    }
                    if (!typeAllowed) {
                        validationResults[0] = new ValidationResultImpl(false, "Type " + visited.getType() + " not allowed under " + parent.getType(), visited);
                    }
                }
                if (validationResults[0] == null) {
                    // validate the min occurrences goes via the children
                    Map<String, Integer> children = new LinkedHashMap<String, Integer>();
                    for (final OverlayWidget child : visited.getChildOverlayWidgets()) {
                        final String key = child.getType()  + ":" + child.getNamespaceURI();
                        if (children.containsKey(key)) {
                            children.put(key, children.get(key) + 1);
                        } else {
                            children.put(key, 1);
                        }
                    }
                    List<StructureIndicator.Element> allowedChildren = findAllowedChildTypes(visited);
                    for (final StructureIndicator.Element structElem : allowedChildren) {
                        OverlayWidget allowedChild = structElem.asWidget();
                        if (allowedChild != null) {
                            Integer count = children.get(allowedChild.getType() + ":" + allowedChild.getNamespaceURI());
                            if (count == null) count = 0;
                            int minOccurrence = structElem.getMinOccurs();
                            if (count < minOccurrence) {
                                validationResults[0] = new ValidationResultImpl(false, "Min occurrence of " + minOccurrence + " not honored for " + allowedChild + " under " + visited.getType(), visited);
                                break;
                            }
                        }
                    }

                }
                return true;
            }
        });
        return validationResults[0] != null ? validationResults[0] : new ValidationResultImpl(true, null, null);
    }

    /**
     * Find the children occurrences for the given overlayWidget
     * @param overlayWidget {@link OverlayWidget}
     * @return A List of elements as StructureIndicator
     */
    private List<StructureIndicator.Element> findAllowedChildTypes(OverlayWidget overlayWidget) {
        List<StructureIndicator.Element> result = new ArrayList<StructureIndicator.Element>();
        if (overlayWidget == null) {
            return result;
        }
        List<StructureIndicator> stack = new ArrayList<StructureIndicator>();
        List<StructureIndicator> stackOccurrence = new ArrayList<StructureIndicator>();

        StructureIndicator indicator = overlayWidget.getStructureIndicator();
        stack.add(indicator);
        stackOccurrence.add(new StructureIndicator.DefaultStructureIndicator(indicator.getMinOccurs(),
                indicator.getMaxOccurs()));

        while (!stack.isEmpty()) {
            StructureIndicator structureIndicator = stack.remove(0);
            StructureIndicator occurrenceIndicator = stackOccurrence.remove(0);

            if (structureIndicator instanceof StructureIndicator.Element) {
                StructureIndicator.Element elemIndicator = (StructureIndicator.Element) structureIndicator;
                int minOccurs = minOfMinim(structureIndicator.getMinOccurs(), occurrenceIndicator.getMinOccurs());
                int maxOccurs = maxOfMax(structureIndicator.getMaxOccurs(), occurrenceIndicator.getMaxOccurs());
                result.add(new StructureIndicator.DefaultElement(minOccurs, maxOccurs, elemIndicator.asWidget()));
            } else {
                if (structureIndicator.getIndicators() != null ) {
                    for(StructureIndicator ind : structureIndicator.getIndicators()) {
                        int minOccurs = minOfMinim(occurrenceIndicator.getMinOccurs(), ind.getMinOccurs());
                        if (ind instanceof StructureIndicator.Choice) {
                            minOccurs = 0;
                        }
                        int maxOccurs = maxOfMax(occurrenceIndicator.getMaxOccurs(), ind.getMaxOccurs());
                        stack.add(ind);
                        stackOccurrence.add(new StructureIndicator.DefaultStructureIndicator(minOccurs,maxOccurs));
                    }
                }
            }
        }
        return result;
    }

    /**
     * return the minimum
     * @param newMinim
     * @return Minimum as int
     */
    private int minOfMinim(int oldMinim, int newMinim) {
        return Math.min(oldMinim, newMinim);
    }

    /**
     * return UNBOUNDED value if one of the two values is unbounded otherwise the maximum
     * @param newMax
     * @return Maximum as Int
     */
    public int maxOfMax(int oldMax, int newMax) {
        if (oldMax == StructureIndicator.UNBOUNDED || newMax == StructureIndicator.UNBOUNDED ) {
            return StructureIndicator.UNBOUNDED;
        }
        return Math.max(oldMax, newMax);
    }
 }
