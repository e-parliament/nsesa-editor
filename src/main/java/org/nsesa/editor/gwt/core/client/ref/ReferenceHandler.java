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
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * A reference handler is responsible for the handling of a reference attribute for a given <tt>overlayWidget</tt>.
 * It passes the <tt>attributeName</tt> and the its value (which has already been resolved by one for more
 * {@link ReferenceResolver}s.
 *
 * Date: 28/03/13 11:03
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultReferenceHandler.class)
public interface ReferenceHandler<T> {

    /**
     * Resolve a given attribute.
     * @param attributeName     the name of the ref attribute
     * @param attributeValue    the value of the ref attribute
     * @param overlayWidget     the overlay widget containing the attribute
     * @param callback          the callback with type T
     */
    void resolve(String attributeName, String attributeValue, OverlayWidget overlayWidget, AsyncCallback<T> callback);

}
