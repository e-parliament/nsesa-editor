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
package org.nsesa.editor.gwt.core.client.event.amendments;

import com.google.gwt.event.shared.EventHandler;

/**
 * An interface to handle <code>AmendmentControllerRemoveFromSelectionEvent</code> GWT event.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/11/12 13:15
 */
public interface AmendmentControllerRemoveFromSelectionEventHandler extends EventHandler {
    public void onEvent(AmendmentControllerRemoveFromSelectionEvent event);
}
