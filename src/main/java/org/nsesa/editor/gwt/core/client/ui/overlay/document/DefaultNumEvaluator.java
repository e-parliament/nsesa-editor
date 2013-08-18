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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

/**
 * An evaluator for num that will compute the num based on the existing widgets.
 * <p/>
 * Date: 16/08/13 18:35
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class DefaultNumEvaluator implements OverlaySnippetEvaluator.Evaluator {

    private ClientFactory clientFactory;
    private OverlayWidgetInjectionStrategy widgetInjectionStrategy;
    private Locator locator;
    private OverlayWidget overlayWidget;
    private OverlayWidget parent;
    private OverlayWidget reference;

    @Inject
    public DefaultNumEvaluator(ClientFactory clientFactory,
                               OverlayWidgetInjectionStrategy widgetInjectionStrategy,
                               Locator locator,
                               OverlayWidget overlayWidget,
                               OverlayWidget parent,
                               OverlayWidget reference) {
        this.clientFactory = clientFactory;
        this.widgetInjectionStrategy = widgetInjectionStrategy;
        this.locator = locator;
        this.overlayWidget = overlayWidget;
        this.parent = parent;
        this.reference = reference;
    }

    @Override
    public String getPlaceHolder() {
        return "${widget.num}";
    }

    @Override
    public String evaluate() {
        if (overlayWidget.getNumberingType() == null) {
            // if there is a sibling of the same type, use that one
            OverlayWidget sibling = overlayWidget.next(new OverlayWidgetSelector() {
                @Override
                public boolean select(OverlayWidget toSelect) {
                    return true;
                }
            });
            if (sibling != null) {
                overlayWidget.setNumberingType(sibling.getNumberingType());
            } else {
                // take the parent's numbering, but use a different one
                // TODO
                overlayWidget.setNumberingType(NumberingType.ROMANITO);
            }
        }
        //add the overlay widget in the parent children collection to compute the num
        final int injectionPosition = widgetInjectionStrategy.getProposedInjectionPosition(parent, reference, overlayWidget);

        parent.addOverlayWidget(overlayWidget, injectionPosition);
        String num = locator.getNum(overlayWidget, clientFactory.getClientContext().getDocumentTranslationLanguageCode(), true);
        parent.removeOverlayWidget(overlayWidget);
        return num == null ? "" : num;
    }
}
