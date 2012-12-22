package org.nsesa.editor.gwt.core.client.service.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.nsesa.editor.gwt.core.shared.DiffRequest;
import org.nsesa.editor.gwt.core.shared.DiffResult;

import java.util.ArrayList;

/**
 * Date: 24/06/12 21:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface GWTDiffServiceAsync {

    /**
     * Batch processing of complex diff.
     *
     * @param commands the list of diff commands to execute
     * @return a list of results in the same order as the commands
     */
    void diff(ArrayList<DiffRequest> commands, AsyncCallback<ArrayList<DiffResult>> asyncCallback);

    /**
     * Returns the current version of this diffing service (so clients now when
     * to update their diffing).
     *
     * @return the version of this diffing service.
     */
    void getVersion(AsyncCallback<String> asyncCallback);

}