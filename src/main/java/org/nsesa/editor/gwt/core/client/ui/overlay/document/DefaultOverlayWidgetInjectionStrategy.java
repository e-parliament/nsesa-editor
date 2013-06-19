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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.user.client.DOM;

import java.util.logging.Logger;

/**
 * Date: 21/05/13 15:51
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayWidgetInjectionStrategy implements OverlayWidgetInjectionStrategy {

    private static final Logger LOG = Logger.getLogger(DefaultOverlayWidgetInjectionStrategy.class.getName());

    @Override
    public int getProposedInjectionPosition(OverlayWidget parent, OverlayWidget reference, final OverlayWidget child) {
        // by default inject children at the last place
        boolean sibling = parent != reference;
        if (sibling) {
            final OverlayWidget nextSibling = reference.getNextSibling(new OverlayWidgetSelector() {
                @Override
                public boolean select(OverlayWidget toSelect) {
                    return !toSelect.isIntroducedByAnAmendment();
                }
            });
            if (nextSibling != null)
                return reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(nextSibling);
            else
                return reference.getParentOverlayWidget().getChildOverlayWidgets().size();
        } else {
            return reference.getChildOverlayWidgets().size();
        }
    }

    @Override
    public void injectAsSibling(OverlayWidget reference, OverlayWidget sibling) {
        assert sibling.isIntroducedByAnAmendment();

        final int injectionPosition = getProposedInjectionPosition(reference.getParentOverlayWidget(), reference, sibling);
        final int offset = injectionPosition - reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(reference);
        assert offset != 0 : "Offset cannot be null.";
        if (offset > 0) {
            // this implies that the reference is a 'previous'

            // find all overlay widgets between our reference and the 'next' non-introduced overlay widget
            int indexOfReferenceInParent = reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(reference);
            assert indexOfReferenceInParent != -1;

            // find the next non-introduced widget
            OverlayWidget next = null;
            int counter = 0;
            for (OverlayWidget child : reference.getParentOverlayWidget().getChildOverlayWidgets()) {
                if (counter > indexOfReferenceInParent) {
                    if (!child.isIntroducedByAnAmendment()) {
                        next = child;
                        break;
                    }
                }
                counter++;
            }

            int until = next != null ? next.getParentOverlayWidget().getChildOverlayWidgets().indexOf(next) : reference.getParentOverlayWidget().getChildOverlayWidgets().size();

            // now find all introduced overlay widgets in between
            int actualInjectionPosition = until;
            OverlayWidget before = null;
            for (int i = indexOfReferenceInParent + 1; i < until; i++) {
                OverlayWidget mustHaveBeenIntroduced = reference.getParentOverlayWidget().getChildOverlayWidgets().get(i);
                if (mustHaveBeenIntroduced.isIntroducedByAnAmendment()) {
                    if (!mustHaveBeenIntroduced.getOverlayWidgetAwareList().isEmpty()) {
                        int injectionPositionOfIntroduced = mustHaveBeenIntroduced.getOverlayWidgetAwareList().get(0).getInjectionPosition();
                        if (injectionPositionOfIntroduced > offset) {
                            // we're too far, so we're going to inject the sibling at this position
                            actualInjectionPosition = reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(mustHaveBeenIntroduced);
                            before = mustHaveBeenIntroduced;
                            break;
                        }
                    }
                }
            }
            // logical insert
            reference.getParentOverlayWidget().addOverlayWidget(sibling, actualInjectionPosition);

            if (before != null) {
                // DOM insert
                DOM.insertBefore(reference.getParentOverlayWidget().asWidget().getElement(), sibling.asWidget().getElement(), before.asWidget().getElement());
            } else {
                // no other introduced widgets
                if (next != null) {
                    DOM.insertBefore(reference.getParentOverlayWidget().asWidget().getElement(), sibling.asWidget().getElement(), next.asWidget().getElement());
                } else {
                    DOM.appendChild(reference.getParentOverlayWidget().asWidget().getElement(), sibling.asWidget().getElement());
                }
            }

        } else {
            // implies that the reference is a 'next'
            int indexOfReferenceInParent = reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(reference);
            assert indexOfReferenceInParent != -1;

            int from = 0; // is the logical upper boundary (since otherwise we would have gotten another reference)

            // now find all introduced overlay widgets in between
            int actualInjectionPosition = 0;
            OverlayWidget before = null;
            for (int i = from; i < indexOfReferenceInParent; i++) {
                OverlayWidget mustHaveBeenIntroduced = reference.getParentOverlayWidget().getChildOverlayWidgets().get(i);
                assert mustHaveBeenIntroduced.isIntroducedByAnAmendment();

                int injectionPositionOfIntroduced = mustHaveBeenIntroduced.getOverlayWidgetAwareList().get(0).getInjectionPosition();
                if (injectionPositionOfIntroduced < injectionPosition) {
                    // we're too far, so we're going to inject the sibling at this position
                    actualInjectionPosition = reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(mustHaveBeenIntroduced);
                    before = mustHaveBeenIntroduced;
                    break;
                }
            }

            // logical insert
            reference.getParentOverlayWidget().addOverlayWidget(sibling, actualInjectionPosition);

            if (before != null) {
                // DOM insert
                DOM.insertBefore(reference.getParentOverlayWidget().asWidget().getElement(), sibling.asWidget().getElement(), before.asWidget().getElement());
            } else {
                // no other introduced widgets
                DOM.insertChild(reference.getParentOverlayWidget().asWidget().getElement(), sibling.asWidget().getElement(), 0);
            }
        }
    }

    @Override
    public void injectAsChild(OverlayWidget parent, OverlayWidget child) {
        com.google.gwt.user.client.Element parentElement = parent.getOverlayElement().cast();
        com.google.gwt.user.client.Element childElement = child.getOverlayElement().cast();

        int injectionPosition = getProposedInjectionPosition(parent, parent, child);
        parent.addOverlayWidget(child, injectionPosition);
        DOM.insertChild(parentElement, childElement, injectionPosition);
    }
}
