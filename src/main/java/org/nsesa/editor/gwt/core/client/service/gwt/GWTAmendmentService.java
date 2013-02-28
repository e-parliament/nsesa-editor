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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.PersonDTO;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;
import org.nsesa.editor.gwt.core.shared.exception.ValidationException;

import java.util.ArrayList;

/**
 * Service interface for the amendment RPC.
 * Date: 24/06/12 21:05
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtAmendmentService")
public interface GWTAmendmentService extends RemoteService {

    /**
     * Get a single amendment container identified by <tt>id</tt>.
     *
     * @param clientContext the client context
     * @param id            the identifier for a single amendment container
     * @return the amendment container, or <tt>null</tt> if it cannot be found.
     */
    AmendmentContainerDTO getAmendmentContainer(ClientContext clientContext, String id)
            throws UnsupportedOperationException, ResourceNotFoundException;


    /**
     * Get all existing amendment containers that should be visible to this client.
     *
     * @param clientContext the client context
     * @return the array of amendment containers
     */
    AmendmentContainerDTO[] getAmendmentContainers(ClientContext clientContext)
            throws UnsupportedOperationException;

    /**
     * Retrieves all revisions for a given amendment container.
     *
     * @param clientContext the client context
     * @param id            the identifier of the amendment container
     * @return the array of amendment container revisions
     */
    AmendmentContainerDTO[] getRevisions(ClientContext clientContext, String id)
            throws UnsupportedOperationException, ResourceNotFoundException;

    /**
     * Save a given set of amendment containers to the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to save
     * @return the array of amendment containers
     */
    AmendmentContainerDTO[] saveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, StaleResourceException, ValidationException;

    /**
     * Check if it is possible to save a given set of amendment containers to the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to save
     * @return the array with <tt>true</tt> booleans for each amendment container that can be saved
     */
    Boolean[] canSaveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, StaleResourceException;

    /**
     * Deletes a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to delete
     * @return the array of deleted amendment containers
     */
    AmendmentContainerDTO[] deleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Check if it is possible to delete a given set of amendment containers at the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to delete
     * @return the array with <tt>true</tt> booleans for each amendment container that can be deleted
     */
    Boolean[] canDeleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Tables a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to table
     * @return the array of tabled amendment containers
     */
    AmendmentContainerDTO[] tableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Check if it is possible to tables a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to table
     * @return the array with <tt>true</tt> booleans for each amendment container that can be tabled
     */
    Boolean[] canTableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Withdraws a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to withdraw
     * @return the withdrawn amendment containers
     */
    AmendmentContainerDTO[] withdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Check if it is possible to withdraw a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to withdraw
     * @return the array with <tt>true</tt> booleans for each amendment container that can be withdrawn
     */
    Boolean[] canWithdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Register a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to register
     * @return the registered amendment containers
     */
    AmendmentContainerDTO[] registerAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Check if it is possible to register a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to register
     * @return the array with <tt>true</tt> booleans for each amendment container that can be registered
     */
    Boolean[] canRegisterAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Return a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to return
     * @return the returned amendment containers
     */
    AmendmentContainerDTO[] returnAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Check if it is possible to return a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to return
     * @return the array with <tt>true</tt> booleans for each amendment container that can be returned
     */
    Boolean[] canReturnAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers)
            throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException;

    /**
     * Return a list of potential authors for an amendment based on a given query.
     * @param query the query to find available authors
     * @param limit the maximum amount of suggestions to return
     * @return  the list of available authors for a given query
     */
    ArrayList<PersonDTO> getAvailableAuthors(ClientContext clientContext, String query, int limit);
}
