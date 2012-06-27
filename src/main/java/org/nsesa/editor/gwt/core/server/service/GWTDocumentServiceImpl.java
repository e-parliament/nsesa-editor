package org.nsesa.editor.gwt.core.server.service;

import com.google.common.io.Files;
import org.nsesa.editor.gwt.core.client.service.GWTDocumentService;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Date: 24/06/12 19:57
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GWTDocumentServiceImpl extends SpringRemoteServiceServlet implements GWTDocumentService {

    private Resource documentResource;

    @Override
    public HashMap<String, String> getMetaInformation(final ClientContext clientContext, final String documentID) {
        return null;
    }

    @Override
    public DocumentDTO getDocument(final ClientContext clientContext, final String documentID) {
        final DocumentDTO document = new DocumentDTO();
        document.setDocumentID(documentID);
        document.setAmendable(true);
        document.setName("Document " + documentID);
        document.setLanguageIso("EN");
        return document;
    }

    @Override
    public String getDocumentFragment(final ClientContext clientContext, final String documentID, final String elementID) {
        return null;
    }

    @Override
    public String[] getAvailableTranslations(final ClientContext clientContext, final String documentID) {
        return null;
    }

    @Override
    public String getDocumentContent(ClientContext clientContext, String documentID) {
        try {
            return Files.toString(documentResource.getFile(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("Could not read file.");
        }
    }

    // Spring setters ----------------------

    public void setDocumentResource(Resource documentResource) {
        this.documentResource = documentResource;
    }
}
