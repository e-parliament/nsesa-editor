package org.nsesa.editor.gwt.core.client.diffing;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.DiffMethod;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentEventBus;

import java.util.logging.Logger;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DOCUMENT;

/**
 * Date: 08/07/12 13:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DOCUMENT)
public class DefaultDiffingManager implements DiffingManager {

    private static final Logger LOG = Logger.getLogger(DefaultDiffingManager.class.getName());

    protected final ServiceFactory serviceFactory;

    protected final ClientFactory clientFactory;

    protected final DocumentEventBus documentEventBus;

    @Inject
    public DefaultDiffingManager(final ServiceFactory serviceFactory,
                                 final ClientFactory clientFactory,
                                 final DocumentEventBus documentEventBus) {
        this.serviceFactory = serviceFactory;
        this.clientFactory = clientFactory;
        this.documentEventBus = documentEventBus;
    }

    public void diff(final DiffMethod method, final AmendmentController... amendmentControllers) {
        // default is to not do any diffing at all ..
    }
}
