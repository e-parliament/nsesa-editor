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
package org.nsesa.editor.gwt.compare.client.ui.compare;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.RevisionDTO;

import java.util.List;

/**
 * Class responsible for retrieving and rolling back revisions.
 * Date: 14/05/13 14:06
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface ComparisonProvider {

    /**
     * Get a certain revision's content.
     *
     * @param revisionID    the revision ID
     * @param asyncCallback the callback (since this happens asynchronously)
     */
    void getRevisionContent(String revisionID, AsyncCallback<String> asyncCallback);

    /**
     * Get the list of all revisions that will be handled by this provider.
     *
     * @param asyncCallback the callback
     */
    void getRevisions(AsyncCallback<List<RevisionDTO>> asyncCallback);

    /**
     * Rollback to a certain revision.
     *
     * @param revisionID the revisionID to roll back to.
     */
    void rollback(String revisionID);
}
