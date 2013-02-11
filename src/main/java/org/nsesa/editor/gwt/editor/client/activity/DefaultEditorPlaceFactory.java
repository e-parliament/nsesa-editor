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
