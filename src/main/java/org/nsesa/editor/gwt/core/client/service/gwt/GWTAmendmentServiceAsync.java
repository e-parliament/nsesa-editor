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
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;

import java.util.ArrayList;

public interface GWTAmendmentServiceAsync {

    /**
     * Get a single amendment container identified by <tt>id</tt>.
     *
     * @param clientContext the client context
     * @param id            the identifier for a single amendment container
     */
    void getAmendmentContainer(ClientContext clientContext, String id, AsyncCallback<AmendmentContainerDTO> async)
    ;

    /**
     * Get all existing amendment containers that should be visible to this client.
     *
     * @param clientContext the client context
     */
    void getAmendmentContainers(ClientContext clientContext, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Retrieves all revisions for a given amendment container.
     *
     * @param clientContext the client context
     * @param id            the identifier of the amendment container
     */
    void getRevisions(ClientContext clientContext, String id, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Save a set of amendment containers to the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to save
     */
    void saveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Deletes a set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to delete
     */
    void deleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Tables a set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to table
     */
    void tableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Withdraws a set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to withdraw
     */
    void withdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Register a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to register
     * @return the registered amendment containers
     */
    void registerAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Check if it is possible to withdraws a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to withdraw
     * @return the array with <tt>true</tt> booleans for each amendment container that can be withdrawn
     */
    void canWithdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;

    /**
     * Check if it is possible to tables a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to table
     * @return the array with <tt>true</tt> booleans for each amendment container that can be tabled
     */
    void canTableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;

    /**
     * Check if it is possible to delete a given set of amendment containers at the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to delete
     * @return the array with <tt>true</tt> booleans for each amendment container that can be deleted
     */
    void canDeleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;

    /**
     * Check if it is possible to save a given set of amendment containers to the backend.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to save
     * @return the array with <tt>true</tt> booleans for each amendment container that can be saved
     */
    void canSaveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;

    /**
     * Check if it is possible to register a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to register
     * @return the array with <tt>true</tt> booleans for each amendment container that can be registered
     */
    void canRegisterAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;

    /**
     * Return a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to return
     * @return the returned amendment containers
     */
    void returnAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<AmendmentContainerDTO[]> async)
    ;

    /**
     * Check if it is possible to return a given set of amendment containers.
     *
     * @param clientContext       the client context
     * @param amendmentContainers the amendment container(s) to return
     * @return the array with <tt>true</tt> booleans for each amendment container that can be returned
     */
    void canReturnAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers, AsyncCallback<Boolean[]> async)
    ;
}
