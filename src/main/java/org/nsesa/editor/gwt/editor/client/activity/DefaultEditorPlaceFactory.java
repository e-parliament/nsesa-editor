package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.util.Scope;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Basic editor place factory. All the places factories will extends this class
 * User: sgroza
 * Date: 26/10/12
 * Time: 12:25
 */
@Singleton
@Scope(EDITOR)
public class DefaultEditorPlaceFactory implements EditorPlaceFactory {

    @Inject
    EditorTabbedPlace.Tokenizer editorTabbedPlaceTokenizer;

    @Inject
    Provider<EditorTabbedPlace> editorTabbedPlaceProvider;

    @Override
    public final Place getDefaultPlace() {
        return editorTabbedPlaceProvider.get();
    }

    @Override
    public final EditorTabbedPlace.Tokenizer getEditorTabbedPlaceTokenizer() {
        return editorTabbedPlaceTokenizer;
    }
}
