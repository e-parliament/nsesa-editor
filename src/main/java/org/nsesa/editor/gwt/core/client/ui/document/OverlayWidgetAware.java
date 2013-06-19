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
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Date: 24/04/13 09:03
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface OverlayWidgetAware {
    void setOverlayWidget(OverlayWidget overlayWidget);

    OverlayWidget getOverlayWidget();

    void setOrder(int order);

    int getInjectionPosition();

    IsWidget getView();

    IsWidget getExtendedView();
}
