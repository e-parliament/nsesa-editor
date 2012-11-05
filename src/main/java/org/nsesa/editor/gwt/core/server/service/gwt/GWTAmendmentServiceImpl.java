package org.nsesa.editor.gwt.core.server.service.gwt;

import com.google.common.io.Files;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentService;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTAmendmentServiceImpl extends SpringRemoteServiceServlet implements GWTAmendmentService {

    private static final Logger LOG = LoggerFactory.getLogger(GWTAmendmentServiceImpl.class);

    private Map<String, Resource> documents;

    @Override
    public AmendmentContainerDTO getAmendmentContainer(final ClientContext clientContext, final String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return null;
    }

    @Override
    public AmendmentContainerDTO[] getAmendmentContainers(final ClientContext clientContext) throws UnsupportedOperationException {

        try {
            final AmendmentContainerDTO amendment1 = new AmendmentContainerDTO();
            amendment1.setAmendmentContainerID("1");
            final AmendableWidgetReference reference1 = new AmendableWidgetReference("rec1");
            amendment1.setSourceReference(reference1);
            amendment1.setXmlContent(getAmendmentDocument("amendment-1"));

            final AmendmentContainerDTO amendment2 = new AmendmentContainerDTO();
            amendment2.setAmendmentContainerID("2");
            final AmendableWidgetReference reference2 = new AmendableWidgetReference("rec2");
            final AmendableWidgetReference reference2a = new AmendableWidgetReference("rec3");
            final AmendableWidgetReference reference2b = new AmendableWidgetReference("rec1");
            amendment2.setTargetReferences(new ArrayList<AmendableWidgetReference>(Arrays.asList(reference2a, reference2b)));
            amendment2.setSourceReference(reference2);
            amendment2.setXmlContent(getAmendmentDocument("amendment-2"));

            final AmendmentContainerDTO amendment3 = new AmendmentContainerDTO();
            amendment3.setAmendmentContainerID("3");
            final AmendableWidgetReference reference3 = new AmendableWidgetReference("art2-pnta");
            amendment3.setSourceReference(reference3);
            amendment3.setXmlContent(getAmendmentDocument("amendment-3"));

            return new AmendmentContainerDTO[]{amendment1, amendment2, amendment3};

        } catch (IOException e) {
            throw new RuntimeException("Could not retrieve amendment content.", e);
        }
    }

    private String getAmendmentDocument(String id) throws IOException {
        return Files.toString(documents.get(id).getFile(), Charset.forName("UTF-8"));
    }

    @Override
    public AmendmentContainerDTO[] getRevisions(final ClientContext clientContext, final String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] saveAmendmentContainers(final ClientContext clientContext, final ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, StaleResourceException {
        return amendmentContainers.toArray(new AmendmentContainerDTO[amendmentContainers.size()]);
    }

    @Override
    public AmendmentContainerDTO[] deleteAmendmentContainers(final ClientContext clientContext, final ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return amendmentContainers.toArray(new AmendmentContainerDTO[amendmentContainers.size()]);
    }

    @Override
    public AmendmentContainerDTO[] tableAmendmentContainers(final ClientContext clientContext, final ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return amendmentContainers.toArray(new AmendmentContainerDTO[amendmentContainers.size()]);
    }

    @Override
    public AmendmentContainerDTO[] withdrawAmendmentContainers(final ClientContext clientContext, final ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, ResourceNotFoundException, StaleResourceException {
        return amendmentContainers.toArray(new AmendmentContainerDTO[amendmentContainers.size()]);
    }

    // SPRING SETTERS -------------------------------------------


    public void setDocuments(Map<String, Resource> documents) {
        this.documents = documents;
    }
}
