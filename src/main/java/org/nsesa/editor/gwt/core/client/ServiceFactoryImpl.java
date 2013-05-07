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
package org.nsesa.editor.gwt.core.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDiffServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Default implementation of the {@link ServiceFactory}.
 * Date: 26/06/12 17:18
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class ServiceFactoryImpl implements ServiceFactory {

    private final GWTServiceAsync gwtService;
    private final GWTDiffServiceAsync gwtDiffService;
    private final GWTAmendmentServiceAsync gwtAmendmentService;
    private final GWTDocumentServiceAsync gwtDocumentService;

    @Inject
    public ServiceFactoryImpl(final GWTServiceAsync gwtService,
                              final GWTDiffServiceAsync gwtDiffService,
                              final GWTAmendmentServiceAsync gwtAmendmentService,
                              final GWTDocumentServiceAsync gwtDocumentService) {
        this.gwtService = gwtService;
        this.gwtDiffService = gwtDiffService;
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

    @Override
    public GWTDiffServiceAsync getGwtDiffService() {
        return gwtDiffService;
    }
}
