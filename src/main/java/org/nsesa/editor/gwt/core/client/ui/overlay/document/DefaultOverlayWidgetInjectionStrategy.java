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
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultOverlayWidgetInjectionStrategy implements OverlayWidgetInjectionStrategy {

    private static final Logger LOG = Logger.getLogger(DefaultOverlayWidgetInjectionStrategy.class.getName());

    @Override
    public int getInjectionPosition(OverlayWidget parent, OverlayWidget reference, OverlayWidget child) {
        // by default inject children at the last place
        return reference.getChildOverlayWidgets().size();
    }

    @Override
    public void injectAsSibling(OverlayWidget reference, OverlayWidget sibling) {
        assert sibling.isIntroducedByAnAmendment();

        final int injectionPosition = getInjectionPosition(reference.getParentOverlayWidget(), reference, sibling);
        if (injectionPosition > 0) {
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

            int until = next != null ? next.getParentOverlayWidget().getChildOverlayWidgets().indexOf(next) - 1 : reference.getParentOverlayWidget().getChildOverlayWidgets().size() - 1;

            // now find all introduced overlay widgets in between
            int actualInjectionPosition = 0;
            OverlayWidget before = null;
            for (int i = indexOfReferenceInParent; i < until; i++) {
                OverlayWidget mustHaveBeenIntroduced = reference.getParentOverlayWidget().getChildOverlayWidgets().get(i);
                assert mustHaveBeenIntroduced.isIntroducedByAnAmendment();
                if (!mustHaveBeenIntroduced.getOverlayWidgetAwareList().isEmpty()) {
                    int injectionPositionOfIntroduced = mustHaveBeenIntroduced.getOverlayWidgetAwareList().get(0).getInjectionPosition();
                    if (injectionPositionOfIntroduced > injectionPosition) {
                        // we're too far, so we're going to inject the sibling at this position
                        actualInjectionPosition = reference.getParentOverlayWidget().getChildOverlayWidgets().indexOf(mustHaveBeenIntroduced);
                        before = mustHaveBeenIntroduced;
                        break;
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
        int index = getInjectionPosition(parent, parent, child);

        com.google.gwt.user.client.Element parentElement = parent.getOverlayElement().cast();
        com.google.gwt.user.client.Element childElement = child.getOverlayElement().cast();

        if (parent.getChildOverlayWidgets().isEmpty()) {
            // ok, insert as the last child
            DOM.insertChild(parentElement, childElement, parentElement.getChildCount());
            parent.addOverlayWidget(child, -1, false);

            LOG.info("Added new " + child + " as the last child to " + parent);
        } else {
            // insert before the first child amendable widget
            final OverlayWidget overlayWidget = parent.getChildOverlayWidgets().get(index);

            com.google.gwt.user.client.Element beforeElement = overlayWidget.getOverlayElement().cast();
            DOM.insertBefore(parentElement, childElement, beforeElement);
            // logical
            parent.addOverlayWidget(child, index, true);
            LOG.info("Added new " + child + " as the first child to " + parent + " at position " + index);
        }
    }
}
