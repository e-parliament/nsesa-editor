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

import com.google.inject.ImplementedBy;

/**
 * Date: 21/05/13 15:49
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultOverlayWidgetInjectionStrategy.class)
public interface OverlayWidgetInjectionStrategy {

    int getProposedInjectionPosition(OverlayWidget parent, OverlayWidget reference, OverlayWidget child);

    void injectAsSibling(OverlayWidget reference, OverlayWidget sibling);

    void injectAsChild(OverlayWidget parent, OverlayWidget child);
}
