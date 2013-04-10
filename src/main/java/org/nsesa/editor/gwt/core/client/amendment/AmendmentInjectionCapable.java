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
package org.nsesa.editor.gwt.core.client.amendment;

import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

/**
 * An interface expressing the capability to inject one or more {@link org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController}s.
 *
 * Date: 07/07/12 23:21
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentInjectionCapable {

    /**
     * Requests an injection on a given <tt>root</tt> for a given <tt>documentController</tt>.
     * @param root                  the root overlay widget node
     * @param documentController    the containing document controller
     */
    void inject(OverlayWidget root, DocumentController documentController);

    /**
     * Requests the injection of a given amendment container object in to a given <tt>root</tt> overlay widget node.
     * @param amendment             the amendment to inject
     * @param root                  the root overlay widget node
     * @param documentController    the containing document controller
     */
    void injectSingleAmendment(AmendmentContainerDTO amendment, OverlayWidget root, DocumentController documentController);
}
