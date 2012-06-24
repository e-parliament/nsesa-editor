package org.nsesa.editor.gwt.core.server.service;

import org.nsesa.editor.gwt.core.client.service.GWTAmendmentService;
import org.nsesa.editor.gwt.core.shared.AmendmentContainer;
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
    public AmendmentContainer getAmendmentContainer(ClientContext clientContext, String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return null;
    }

    @Override
    public AmendmentContainer[] getAmendmentContainers(ClientContext clientContext) throws UnsupportedOperationException {
        return new AmendmentContainer[0];
    }

    @Override
    public AmendmentContainer[] getRevisions(ClientContext clientContext, String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return new AmendmentContainer[0];
    }

    @Override
    public AmendmentContainer[] saveAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainer> amendmentContainers) throws UnsupportedOperationException, StaleResourceException {
        return new AmendmentContainer[0];
    }

    @Override
    public AmendmentContainer[] deleteAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainer> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainer[0];
    }

    @Override
    public AmendmentContainer[] tableAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainer> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainer[0];
    }

    @Override
    public AmendmentContainer[] withdrawAmendmentContainers(ClientContext clientContext, ArrayList<AmendmentContainer> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return new AmendmentContainer[0];
    }
}
