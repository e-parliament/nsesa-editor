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
package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.List;

/**
 * The {@link AmendmentInjectionPointFinder} is responsible for find the correct amendable widget in the tree to which
 * the given <tt>amendmentController</tt> applies. Note that it is the responsibility of the {@link AmendmentInjectionPointProvider}
 * to actually give the correct amendable widget to add the amendment to, which might be different from the amendable
 * widget the amendment applies to (for example, in the case of a new element).
 * <p/>
 * Date: 30/11/12 11:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentInjectionPointFinder.class)
public interface AmendmentInjectionPointFinder {
    List<AmendableWidget> findInjectionPoints(AmendmentController amendmentController, final AmendableWidget root, final DocumentController documentController);

    String getInjectionPoint(AmendableWidget amendableWidget);
}
