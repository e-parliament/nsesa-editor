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

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Interface responsible to allow movement of the overlay widget within the source text structure
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 10/04/13 14:56
 */
@ImplementedBy(DefaultMover.class)
public interface Mover {
    /**
     * Checks whether the given overlay widget can move up within the source text structure
     * @param widget The widget to be checked
     * @return True when the widget is allowed to move up.
     */
    abstract boolean canMoveUp(OverlayWidget widget);

    /**
     * Checks whether the given overlay widget can move down within the source text structure
     * @param widget The widget to be checked
     * @return True when the widget is allowed to move down.
     */
    abstract boolean canMoveDown(OverlayWidget widget);

    /**
     * Checks whether the given overlay widget can indent within the source text structure
     * @param widget The widget to be checked
     * @return True when the widget is allowed to indent.
     */
    abstract boolean canIndent(OverlayWidget widget);

    /**
     * Checks whether the given overlay widget can outdent within the source text structure
     * @param widget The widget to be checked
     * @return True when the widget is allowed to outdent.
     */
    abstract boolean canOutdent(OverlayWidget widget);

}
