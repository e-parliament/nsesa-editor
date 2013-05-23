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
package org.nsesa.editor.gwt.amendment.client.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;

import java.util.List;

/**
 * The {@link AmendmentInjectionPointFinder} is responsible for find the correct amendable widget in the tree to which
 * the given <tt>amendmentController</tt> applies. Note that it is the responsibility of the {@link AmendmentInjectionPointProvider}
 * to actually give the correct amendable widget to add the amendment to, which might be different from the amendable
 * widget the amendment applies to (for example, in the case of a new element).
 * <p/>
 * At the same time, this allows us to get the expression to find a unique {@link OverlayWidget} in the tree.
 * <p/>
 * Date: 30/11/12 11:10
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentInjectionPointFinder.class)
public interface AmendmentInjectionPointFinder {

    /**
     * Finds the injection points where an <tt>amendmentController</tt> would need to be injected in. Note that we do
     * not actually inject the amendment.
     *
     * @param path               the path to find the inject points.
     * @param root               the root overlay widget node
     * @param documentController the containing document controller
     * @return the list of {@link OverlayWidget}s where to which the amendment controller applies, and should be
     *         injected in.
     */
    List<OverlayWidget> findInjectionPoints(String path, OverlayWidget root, DocumentController documentController);

    /**
     * Returns an expression that can be used to uniquely identify the given <tt>overlayWidget</tt> within its tree
     * (note that the overlay widget is inherently part of a tree). The expression can then subsequently be used
     * by passing it to {@link org.nsesa.editor.gwt.core.client.util.OverlayUtil}.
     *
     * @param parent
     * @param reference
     * @param child
     * @return the reference that can be used to find this <tt>overlayWidget</tt>
     */
    AmendableWidgetReference getInjectionPoint(OverlayWidget parent, OverlayWidget reference, OverlayWidget child);
}
