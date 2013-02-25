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
package org.nsesa.editor.gwt.editor.client.ui.main;

import com.google.gwt.inject.client.AbstractGinModule;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentModule;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentModule;
import org.nsesa.editor.gwt.core.client.ui.document.amendments.AmendmentsPanelModule;
import org.nsesa.editor.gwt.core.client.ui.document.info.InfoPanelModule;
import org.nsesa.editor.gwt.editor.client.ui.footer.FooterModule;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderModule;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class EditorModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new CoreModule());
        install(new DocumentModule());
        install(new HeaderModule());
        install(new FooterModule());
        install(new AmendmentModule());
        install(new AmendmentsPanelModule());
        install(new InfoPanelModule());
    }
}
