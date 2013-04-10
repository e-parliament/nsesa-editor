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
package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Service interface for document related RPC.
 * Date: 24/06/12 21:05
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtDocumentService")
public interface GWTDocumentService extends RemoteService {

    /**
     * Retrieves the meta information about a certain document. This can include things like the author,
     * rapporteur, document identification, procedure identification, ...
     *
     * @param clientContext the client context
     * @param documentID    the id of the document or revision
     * @return a Map with key/value pairs.
     */
    HashMap<String, String> getMetaInformation(ClientContext clientContext, String documentID);

    /**
     * Retrieves the document object for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @param documentID    the identifier of the document to load (or revision)
     * @return the full document content (XML, HTML, JSON, ...)
     */
    DocumentDTO getDocument(ClientContext clientContext, String documentID);

    /**
     * Retrieves the document object for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @param documentID    the identifier of the document to load (or revision)
     * @return the full document content (XML, HTML, JSON, ...)
     */
    String getDocumentContent(ClientContext clientContext, String documentID);

    /**
     * Retrieves a fragment of a document (usually a tree under the element identified by <tt>elementID</tt>).
     *
     * @param clientContext the client context
     * @param documentID    the id of the document or revision
     * @param elementID     the id attribute of the element for which the fragment is requested.
     * @return the fragment content (XML, HTML, JSON, ...)
     */
    String getDocumentFragment(ClientContext clientContext, String documentID, String elementID);

    /**
     * Retrieves a list of the available translations for the given document.
     *
     * @param clientContext the client context
     * @param documentID    the id of the document or revision
     * @return the array with the translations that are available.
     */
    ArrayList<DocumentDTO> getAvailableTranslations(ClientContext clientContext, String documentID);

    /**
     * Retrieves the list of related documents for a given document.
     *
     * @param clientContext the client context
     * @param documentID    the id of the document or revision
     * @return the array with the related documents (if any)
     */
    ArrayList<DocumentDTO> getRelatedDocuments(ClientContext clientContext, String documentID);
}
