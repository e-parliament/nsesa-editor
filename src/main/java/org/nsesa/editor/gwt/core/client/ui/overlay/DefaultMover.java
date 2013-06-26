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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Default implementation of {@link Mover} interface by allowing all the operations by default
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 10/04/13 15:07
 */
public class DefaultMover implements Mover {
    @Override
    public boolean canMoveUp(OverlayWidget widget) {
        return true;
    }

    @Override
    public boolean canMoveDown(OverlayWidget widget) {
        return true;
    }

    @Override
    public boolean canIndent(OverlayWidget widget) {
        return true;
    }

    @Override
    public boolean canOutdent(OverlayWidget widget) {
        return true;
    }
}
