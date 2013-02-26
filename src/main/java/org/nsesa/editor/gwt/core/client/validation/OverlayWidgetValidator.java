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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A more complex validation of a given overlay widget. Allows for validation of leaves and branches of a tree.
 * This validation currently check the given min/max occurrences of both the children and the current overlay widget.
 * Date: 19/02/13 14:03
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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
                    final Map<OverlayWidget, Occurrence> allowedChildTypes = parent.getAllowedChildTypes();
                    boolean typeAllowed = false;

                    // check the type to make sure it is allowed as a child
                    for (Map.Entry<OverlayWidget, Occurrence> entry : allowedChildTypes.entrySet()) {
                        if (entry.getKey().getType().equalsIgnoreCase(visited.getType())) {
                            typeAllowed = true;
                            if (!entry.getValue().isUnbounded() && entry.getValue().getMaxOccurs() < visited.getTypeIndex() + 1) {
                                validationResults[0] = new ValidationResultImpl(false, "Max occurrence of " + entry.getValue().getMaxOccurs() + " not honored for " + entry.getKey().getType() + " under " + parent.getType());
                            }
                            break;
                        }
                    }

                    if (!typeAllowed) {
                        validationResults[0] = new ValidationResultImpl(false, "Type " + visited.getType() + " not allowed under " + parent.getType());
                    }
                }
                if (validationResults[0] == null) {
                    // validate the min occurrences goes via the children
                    Map<String, Integer> children = new LinkedHashMap<String, Integer>();
                    for (final OverlayWidget child : visited.getChildOverlayWidgets()) {
                        final String key = child.getNamespaceURI() + ":" + child.getType();
                        if (children.containsKey(key)) {
                            children.put(key, children.get(key) + 1);
                        } else {
                            children.put(key, 1);
                        }
                    }
                    // validate the map
                    for (Map.Entry<OverlayWidget, Occurrence> entry : visited.getAllowedChildTypes().entrySet()) {

                        Integer count = children.get(entry.getKey().getNamespaceURI() + ":" + entry.getKey().getType());
                        if (count == null) count = 0;
                        if (entry.getValue().getMinOccurs() > 0) {
                            // validate min occurrences
                            if (count < entry.getValue().getMinOccurs()) {
                                validationResults[0] = new ValidationResultImpl(false, "Min occurrence of " + entry.getValue().getMinOccurs() + " not honored for " + entry.getKey().getType() + " under " + visited.getType());
                            }
                        }

                    }
                }
                return true;
            }
        });
        return validationResults[0] != null ? validationResults[0] : new ValidationResultImpl(true, null);
    }
}
