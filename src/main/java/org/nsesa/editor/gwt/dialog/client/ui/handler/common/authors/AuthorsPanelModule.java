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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.authors;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

/**
 * GIN module for the common author component.
 * Date: 24/06/12 15:11
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AuthorsPanelModule extends AbstractGinModule {
    @Override
    protected void configure() {

    }

    @Inject
    @Provides
    AuthorsPanelViewCss createStyle(final Resources resources) {
        AuthorsPanelViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
