/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import com.google.inject.Provider;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTAmendmentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDiffServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTDocumentServiceAsync;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;

/**
 * Date: 25/02/13 15:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class StaticServiceFactoryProvider implements Provider<ServiceFactory> {

    private static ServiceFactory INSTANCE;

    @Inject
    GWTServiceAsync gwtService;

    @Inject
    GWTAmendmentServiceAsync gwtAmendmentService;

    @Inject
    GWTDocumentServiceAsync gwtDocumentServiceAsync;

    @Inject
    GWTDiffServiceAsync gwtDiffService;

    @Override
    public ServiceFactory get() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceFactoryImpl(gwtService, gwtDiffService, gwtAmendmentService, gwtDocumentServiceAsync);
        }
        return INSTANCE;
    }
}
