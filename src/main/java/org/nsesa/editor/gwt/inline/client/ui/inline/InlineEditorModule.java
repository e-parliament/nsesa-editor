package org.nsesa.editor.gwt.inline.client.ui.inline;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditorPlugin;
import org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CKEditorEnterKeyPlugin;
import org.nsesa.editor.gwt.dialog.client.ui.rte.ckeditor.CkEditorCompositePlugin;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class InlineEditorModule extends AbstractGinModule {
    @Override
    public void configure() {
        bind(RichTextEditor.class).annotatedWith(Names.named("inlineRichTextEditor")).toProvider(InlineRichTextEditorProvider.class);
    }

    public static class InlineRichTextEditorProvider implements Provider<RichTextEditor> {

        @Inject
        ClientFactory clientFactory;

        @Inject(optional = true)
        @Named("richTextEditorCss")
        String cssPath;

        @Inject(optional = true)
        @Named("richTextEditorExtraPlugins")
        String extraPlugins;

        @Inject(optional = true)
        @Named("richTextEditorBodyClass")
        String bodyClass;

        @Inject
        RichTextEditorPlugin plugin;

        @Override
        public RichTextEditor get() {
            return new CKEditor(plugin, cssPath, extraPlugins, bodyClass, false);
        }
    }
}
