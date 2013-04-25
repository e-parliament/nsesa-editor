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
package org.nsesa.editor.gwt.amendment.client.ui.document.amendments;

import com.google.gwt.inject.client.AbstractGinModule;
import org.nsesa.editor.gwt.amendment.client.ui.document.amendments.header.AmendmentsHeaderModule;

/**
 * The GIN module for amendments panel functionality
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 26/11/12 11:50
 */
public class AmendmentsPanelModule extends AbstractGinModule {
    /**
     * Configure <code>AmendmentsPanelModule</code>
     */
    @Override
    protected void configure() {
        install(new AmendmentsHeaderModule());
    }
}
