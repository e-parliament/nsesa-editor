package org.nsesa.editor.gwt.core.server.service;

import org.nsesa.editor.gwt.core.client.service.GWTAmendmentService;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;

import java.util.ArrayList;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTAmendmentServiceImpl extends SpringRemoteServiceServlet implements GWTAmendmentService {
    @Override
    public AmendmentContainerDTO getAmendmentContainer(ClientContext clientContext, String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return null;
    }

    @Override
    public AmendmentContainerDTO[] getAmendmentContainers(ClientContext clientContext) throws UnsupportedOperationException {

        AmendmentContainerDTO amendment1 = new AmendmentContainerDTO();
        amendment1.setPosition("P");
        AmendmentContainerDTO amendment2 = new AmendmentContainerDTO();
        amendment2.setPosition("RECITAL");
        AmendmentContainerDTO amendment3 = new AmendmentContainerDTO();
        amendment3.setPosition("PREAMBLE");

        return new AmendmentContainerDTO[]{amendment1, amendment2, amendment3};
    }

    @Override
    public AmendmentContainerDTO[] getRevisions(ClientContext clientContext, String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] saveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, StaleResourceException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] deleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] tableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] withdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainerDTO[0];
    }
}
