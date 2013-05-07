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
package org.nsesa.editor.gwt.core.client.ref;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * A simple default reference handler for local {@link OverlayWidget}s in the current document.
 * Date: 28/03/13 14:43
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultReferenceHandler implements ReferenceHandler<OverlayWidget> {

    private final LocalOverlayWidgetReferenceResolver localOverlayWidgetReferenceResolver;

    @Inject
    public DefaultReferenceHandler(LocalOverlayWidgetReferenceResolver localOverlayWidgetReferenceResolver) {
        this.localOverlayWidgetReferenceResolver = localOverlayWidgetReferenceResolver;
    }

    @Override
    public void resolve(final String attributeName, final String attributeValue, final OverlayWidget overlayWidget,
                        final AsyncCallback<OverlayWidget> callback) {

        if (attributeName.equalsIgnoreCase("href") && attributeValue.startsWith("#")) {
            localOverlayWidgetReferenceResolver.resolve(attributeValue, overlayWidget, new AsyncCallback<OverlayWidget>() {
                @Override
                public void onFailure(Throwable caught) {
                    callback.onFailure(caught);
                }

                @Override
                public void onSuccess(OverlayWidget result) {
                    callback.onSuccess(result);
                }
            });
        }
    }
}
