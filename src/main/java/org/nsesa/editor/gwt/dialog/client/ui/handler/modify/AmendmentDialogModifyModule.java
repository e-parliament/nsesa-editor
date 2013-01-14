package org.nsesa.editor.gwt.dialog.client.ui.handler.modify;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorConfig;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;
import org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditor;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogModifyModule extends AbstractGinModule {
    @Override
    public void configure() {
        bind(RichTextEditor.class).annotatedWith(Names.named("amendmentText")).toProvider(AmendmentTextProvider.class);
    }

    public static class AmendmentTextProvider implements Provider<RichTextEditor> {
        @Inject
        RichTextEditorPlugin plugin;

        @Inject
        RichTextEditorConfig config;

        @Override
        public RichTextEditor get() {
            config.setReadOnly(false);
            return new CKEditor(plugin, config);
        }
    }
}
