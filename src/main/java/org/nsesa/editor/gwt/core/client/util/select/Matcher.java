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
package org.nsesa.editor.gwt.core.client.util.select;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Simple matcher interface. Subject to change.
 * Date: 07/01/13 14:24
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
interface Matcher {
    /**
     * Check if a certain <tt>expression</tt> matches a given <tt>overlayWidget</tt>.
     * @param expression        the expression
     * @param overlayWidget     the overlay widget to match
     * @return <tt>true</tt> if the overlay widget matches the given <tt>expression</tt>
     */
    boolean matches(String expression, OverlayWidget overlayWidget);

    /**
     * Check if the expression is applicable to this matcher.
     * @param expression    the expression
     * @return <tt>true</tt> if the matcher can handle this <tt>expression</tt>
     */
    boolean applicable(String expression);
}
