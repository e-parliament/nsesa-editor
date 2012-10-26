package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.inject.ImplementedBy;

/**
 * Interface responsible with the creation of places and tokenizers
 * User: sgroza
 * Date: 26/10/12
 * Time: 14:44
 */
@ImplementedBy(DefaultEditorPlaceFactory.class)
public interface EditorPlaceFactory {
    Place getDefaultPlace();
    EditorTabbedPlace.Tokenizer getEditorTabbedPlaceTokenizer();
}
