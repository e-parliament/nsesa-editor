package org.nsesa.editor.gwt.core.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.ClientContext;

import java.util.HashMap;

public interface GWTDocumentServiceAsync {
    /**
     * Retrieves the meta information about a certain document. This can include things like the author,
     * rapporteur, document identification, procedure identification, ...
     *
     * @param clientContext the client context
     * @return a Map with key/value pairs.
     */
    void getMetaInformation(ClientContext clientContext, AsyncCallback<HashMap<String, String>> async);

    /**
     * Retrieves the full document content for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @return the full document content (XML, HTML, ...)
     */
    void getDocument(ClientContext clientContext, AsyncCallback<String> async);

    /**
     * Retrieves a list of the available translations for the given document.
     *
     * @param clientContext the client context
     * @return the array with the iso codes of the translations that are available.
     */
    void getAvailableTranslations(ClientContext clientContext, AsyncCallback<String[]> async);

    /**
     * Retrieves a fragment of a document (usually a tree under the element identified by <tt>elementID</tt>).
     *
     * @param clientContext the client context
     * @param elementID     the id attribute of the element for which the fragment is requested.
     * @return the fragment content (XML, HTML, JSON, ...)
     */
    void getDocumentFragment(ClientContext clientContext, String elementID, AsyncCallback<String> async);
}
