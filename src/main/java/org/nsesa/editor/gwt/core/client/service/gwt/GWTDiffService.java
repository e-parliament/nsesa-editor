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
package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.DiffRequest;
import org.nsesa.editor.gwt.core.shared.DiffResult;

import java.util.ArrayList;

/**
 * Service interface for diff RPC.
 * Date: 24/06/12 21:05
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtDiffService")
public interface GWTDiffService extends RemoteService {

    /**
     * Batch processing of diff requests.
     *
     * @param diffRequests the list of diff commands to execute
     * @return a list of results in the same order as the requests
     */
    ArrayList<DiffResult> diff(ArrayList<DiffRequest> diffRequests);

    /**
     * Returns the current version of this diffing service (so clients now when
     * to update their diffing).
     *
     * @return the version of this diffing service.
     */
    String getVersion();
}
