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
package org.nsesa.editor.gwt.inline.client.ui.inline;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditorPlugin;
import org.nsesa.editor.gwt.core.client.ui.rte.ckeditor.CKEditor;

/**
 * GIN module for the inline editing.
 * Date: 24/06/12 15:11
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditorModule extends AbstractGinModule {
    @Override
    public void configure() {
        bind(RichTextEditor.class).annotatedWith(Names.named("inlineRichTextEditor")).toProvider(InlineRichTextEditorProvider.class).in(Singleton.class);
    }

    public static class InlineRichTextEditorProvider implements Provider<RichTextEditor> {
        @Inject
        RichTextEditorPlugin plugin;

        @Inject
        RichTextEditorConfig config;

        @Override
        public RichTextEditor get() {
            config.setReadOnly(false);
            return new CKEditor(plugin, config, true);
        }
    }
}
