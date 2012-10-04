package org.nsesa.editor.gwt.core.server.service.gwt;

import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentService;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;

import java.util.ArrayList;
import java.util.Arrays;

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
        AmendableWidgetReference reference1 = new AmendableWidgetReference("rec1");
        amendment1.setSourceReference(reference1);
        AmendmentContainerDTO amendment2 = new AmendmentContainerDTO();
        AmendableWidgetReference reference2 = new AmendableWidgetReference("rec2");
        AmendableWidgetReference reference2a = new AmendableWidgetReference("rec3");
        AmendableWidgetReference reference2b = new AmendableWidgetReference("rec1");
        amendment2.setTargetReferences(new ArrayList<AmendableWidgetReference>(Arrays.asList(reference2a, reference2b)));
        amendment2.setSourceReference(reference2);
        AmendmentContainerDTO amendment3 = new AmendmentContainerDTO();
        AmendableWidgetReference reference3 = new AmendableWidgetReference("art2-pnta");
        amendment3.setSourceReference(reference3);

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
