package org.nsesa.editor.gwt.core.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.nsesa.editor.gwt.core.shared.ClientContext;

import java.util.HashMap;

/**
 * Date: 24/06/12 21:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@RemoteServiceRelativePath("gwtDocumentService")
public interface GWTDocumentService extends RemoteService {

    /**
     * Retrieves the meta information about a certain document. This can include things like the author,
     * rapporteur, document identification, procedure identification, ...
     *
     * @param clientContext the client context
     * @return a Map with key/value pairs.
     */
    HashMap<String, String> getMetaInformation(ClientContext clientContext);

    /**
     * Retrieves the full document content for a given document identified by the client context.
     *
     * @param clientContext the client context
     * @return the full document content (XML, HTML, JSON, ...)
     */
    String getDocument(ClientContext clientContext);

    /**
     * Retrieves a fragment of a document (usually a tree under the element identified by <tt>elementID</tt>).
     *
     * @param clientContext the client context
     * @param elementID     the id attribute of the element for which the fragment is requested.
     * @return the fragment content (XML, HTML, JSON, ...)
     */
    String getDocumentFragment(ClientContext clientContext, String elementID);

    /**
     * Retrieves a list of the available translations for the given document.
     *
     * @param clientContext the client context
     * @return the array with the iso codes of the translations that are available.
     */
    String[] getAvailableTranslations(ClientContext clientContext);
}
