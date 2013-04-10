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
package org.nsesa.editor.gwt.core.client;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDiffServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;

/**
 * The {@link ServiceFactory} gives access to RPC services to communicate with the server side.
 * Date: 25/06/12 21:54
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(ServiceFactoryImpl.class)
public interface ServiceFactory {

    /**
     * Get a reference to the default GWT RPC service.
     * @return the default GWT service
     */
    GWTServiceAsync getGwtService();

    /**
     * Get a reference to the GWT Diff RPC service.
     * @return the Diff service
     */
    GWTDiffServiceAsync getGwtDiffService();

    /**
     * Get a reference to the GWT Amendment RPC service.
     * @return the amendment service
     */
    GWTAmendmentServiceAsync getGwtAmendmentService();

    /**
     * Get a reference to the GWT document RPC service.
     * @return the document service
     */
    GWTDocumentServiceAsync getGwtDocumentService();
}
