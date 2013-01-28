package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;

import java.util.ArrayList;

/**
 * Date: 24/06/12 21:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
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
            throws UnsupportedOperationException, StaleResourceException;

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
}
