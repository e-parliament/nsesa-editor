package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.core.shared.DocumentDTO;

import java.util.ArrayList;
import java.util.HashMap;

public interface GWTDocumentServiceAsync {
    /**
     * Retrieves the meta information about a certain document. This can include things like the author,
     * rapporteur, document identification, procedure identification, ...
     *
     * @param clientContext the client context
     * @param documentID    the document identifier
     * @param async         the callback
     */
    void getMetaInformation(ClientContext clientContext, String documentID, AsyncCallback<HashMap<String, String>> async);

    /**
     * Retrieves a list of the available translations for the given document.
     *
     * @param clientContext the client context
     * @param documentID    the document identifier
     * @param async         the callback
     */
    void getAvailableTranslations(ClientContext clientContext, String documentID, AsyncCallback<ArrayList<DocumentDTO>> async);

    /**
     * Retrieves a fragment of a document (usually a tree under the element identified by <tt>elementID</tt>).
     *
     * @param clientContext the client context
     * @param documentID    the document identifier
     * @param elementID     the id attribute of the element for which the fragment is requested.
     * @param async         the callback
     */
    void getDocumentFragment(ClientContext clientContext, String documentID, String elementID, AsyncCallback<String> async);

    /**
     * Retrieves the document object for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @param documentID    the document identifier
     * @param async         the callback
     */
    void getDocument(ClientContext clientContext, String documentID, AsyncCallback<DocumentDTO> async);

    /**
     * Retrieves the full document content for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @param documentID    the document identifier
     * @param async         the callback
     */
    void getDocumentContent(ClientContext clientContext, String documentID, AsyncCallback<String> async);

}
