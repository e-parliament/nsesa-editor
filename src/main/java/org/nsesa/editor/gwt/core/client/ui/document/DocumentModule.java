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
package org.nsesa.editor.gwt.core.client.ui.document;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.*;
import org.nsesa.editor.gwt.core.client.ui.deadline.DeadlineModule;
import org.nsesa.editor.gwt.core.client.ui.document.header.DocumentHeaderModule;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.SourceFileController;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarModule;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.content.ContentModule;
import org.nsesa.editor.gwt.core.client.ui.document.sourcefile.marker.MarkerModule;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetUIListener;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModule extends AbstractGinModule {

    @Override
    protected void configure() {
        install(new ContentModule());
        install(new MarkerModule());
        install(new DocumentHeaderModule());
        install(new DeadlineModule());
        install(new ActionBarModule());

        bind(OverlayWidgetUIListener.class).to(SourceFileController.class);
    }

    @Inject
    @Provides
    DocumentViewCss createStyle(final Resources resources) {
        DocumentViewCss style = resources.style();
        style.ensureInjected();
        return style;
    }
}
