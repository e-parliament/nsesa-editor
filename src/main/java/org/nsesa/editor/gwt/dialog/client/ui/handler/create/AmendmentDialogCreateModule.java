package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce.YATinyEditor;
import org.nsesa.editor.gwt.dialog.client.ui.rte.tinymce.YATinyEditorListener;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentDialogCreateModule extends AbstractGinModule {
    @Override
    public void configure() {
        bind(AmendmentDialogCreateController.class).in(Singleton.class);
        bind(RichTextEditor.class).annotatedWith(Names.named("newText")).toProvider(NewTextProvider.class);
    }

    public static class NewTextProvider implements Provider<RichTextEditor> {

        @Inject
        ClientFactory clientFactory;

        @Override
        public RichTextEditor get() {
            return new YATinyEditor(false, clientFactory, new YATinyEditorListener() {
                @Override
                public void onInitialized(YATinyEditor editor) {
                    editor.addStyleName("tiny-editor");
                    editor.addStyleName("editor-content");
                }

                @Override
                public void onAttached() {
                    // do nothing
                }

                @Override
                public void onDetached() {
                    // do nothing
                }

                @Override
                public boolean executeCommand(YATinyEditor editor, String command, Object value) {
                    return true;
                }
            });
        }
    }
}
