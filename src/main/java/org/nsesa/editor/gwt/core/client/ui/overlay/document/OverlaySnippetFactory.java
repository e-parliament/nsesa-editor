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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.inject.ImplementedBy;

/**
 * A factory to provide snippets for the overlay widgets.
 * An overlay snippet is a sort of template and is used when you create new overlay widgets
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 *         Date: 8/04/13 11:28
 */
@ImplementedBy(DefaultOverlaySnippetFactory.class)
public interface OverlaySnippetFactory {

    /**
     * Returns the snippet associated to the overlay widget
     * @param widget The widget for which we determine the associated {@link OverlaySnippet}
     * @return {@link OverlaySnippet}
     */
    abstract OverlaySnippet getSnippet(OverlayWidget widget);

}
