package org.nsesa.editor.gwt.core.server.service.gwt;

import com.google.common.io.Files;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentService;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetReference;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.exception.ResourceNotFoundException;
import org.nsesa.editor.gwt.core.shared.exception.StaleResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTAmendmentServiceImpl extends SpringRemoteServiceServlet implements GWTAmendmentService {

    private static final Logger LOG = LoggerFactory.getLogger(GWTAmendmentServiceImpl.class);

    private Map<String, Resource> documents;
    private Resource documentTemplate;

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

        byte[] bytes = Files.toByteArray(documents.get(id).getFile());
        return toHTML(bytes);

    }

    private String toHTML(byte[] bytes) {
        try {
            final InputSource inputSource = new InputSource(new ByteArrayInputStream(bytes));
            final NodeModel model = NodeModel.parse(inputSource);
            final Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(documentTemplate.getFile().getParentFile());
            final StringWriter sw = new StringWriter();
            Map<String, Object> root = new HashMap<String, Object>();
            root.put("doc", model);
            configuration.getTemplate(documentTemplate.getFile().getName()).process(root, sw);
            return sw.toString();

        } catch (IOException e) {
            throw new RuntimeException("Could not read file.", e);
        } catch (SAXException e) {
            throw new RuntimeException("Could not parse file.", e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Could not parse file.", e);
        } catch (TemplateException e) {
            throw new RuntimeException("Could not load template.", e);
        }
    }

    @Override
    public AmendmentContainerDTO[] getRevisions(final ClientContext clientContext, final String id) throws UnsupportedOperationException, ResourceNotFoundException {
        return new AmendmentContainerDTO[0];
    }

    @Override
    public AmendmentContainerDTO[] saveAmendmentContainers(final ClientContext clientContext, final ArrayList<AmendmentContainerDTO> amendmentContainers) throws UnsupportedOperationException, StaleResourceException {
        List<AmendmentContainerDTO> amendmentContainerDTOs = new ArrayList<AmendmentContainerDTO>();
        for (AmendmentContainerDTO data : amendmentContainers) {
            /*try {
                Files.write(data.getXmlContent(), new File(new File("R:/"), "amendment.xml"), Charset.forName("UTF-8"));
            } catch (IOException e) {
                LOG.error("Could not write file.", e);
            }*/
            try {
                data.setXmlContent(toHTML(data.getXmlContent().getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                LOG.error("Could not get encoding.", e);
            }
            amendmentContainerDTOs.add(data);
        }
        return amendmentContainerDTOs.toArray(new AmendmentContainerDTO[amendmentContainerDTOs.size()]);
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

    public void setDocumentTemplate(Resource documentTemplate) {
        this.documentTemplate = documentTemplate;
    }
}
