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
package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.DiffRequest;
import org.nsesa.editor.gwt.core.shared.DiffResult;

import java.util.ArrayList;

/**
 * Async interface for the {@link GWTDiffService}.
 * Date: 24/06/12 21:05
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface GWTDiffServiceAsync {

    /**
     * Batch processing of complex diff.
     *
     * @param commands the list of diff commands to execute
     * @return a list of results in the same order as the commands
     */
    void diff(ArrayList<DiffRequest> commands, AsyncCallback<ArrayList<DiffResult>> asyncCallback);

    /**
     * Returns the current version of this diffing service (so clients now when
     * to update their diffing).
     *
     * @return the version of this diffing service.
     */
    void getVersion(AsyncCallback<String> asyncCallback);

}
