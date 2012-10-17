package org.nsesa.editor.gwt.core.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;

/**
 * Date: 26/06/12 17:18
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class ServiceFactoryImpl implements ServiceFactory {

    private final GWTServiceAsync gwtService;
    private final GWTAmendmentServiceAsync gwtAmendmentService;
    private final GWTDocumentServiceAsync gwtDocumentService;

    @Inject
    public ServiceFactoryImpl(GWTServiceAsync gwtService, GWTAmendmentServiceAsync gwtAmendmentService, GWTDocumentServiceAsync gwtDocumentService) {
        this.gwtService = gwtService;
        this.gwtAmendmentService = gwtAmendmentService;
        this.gwtDocumentService = gwtDocumentService;
    }

    @Override
    public GWTServiceAsync getGwtService() {
        return gwtService;
    }

    @Override
    public GWTAmendmentServiceAsync getGwtAmendmentService() {
        return gwtAmendmentService;
    }

    @Override
    public GWTDocumentServiceAsync getGwtDocumentService() {
        return gwtDocumentService;
    }
}
