package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.place.shared.*;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A place for editor activity
 * User: sgroza
 * Date: 25/10/12
 * Time: 10:04
 */
public class EditorTabbedPlace extends EditorActivityPlace<EditorTabbedActivity> {
    @Inject
    public EditorTabbedPlace(EditorTabbedActivity tabbedActivity) {
        super(tabbedActivity);
    }

    @Override
    public EditorTabbedActivity getActivity() {
   		activity.init(this);
        return activity;
   	}

    @Prefix("tabbed")
   	public static class Tokenizer implements PlaceTokenizer<EditorTabbedPlace> {
        private final Provider<EditorTabbedPlace> placeProvider;

        @Inject
        public Tokenizer(Provider<EditorTabbedPlace> placeProvider) {
            this.placeProvider = placeProvider;
        }

   		@Override
   		public String getToken(EditorTabbedPlace place) {
   			return place.getPlaceName();
   		}

   		@Override
   		public EditorTabbedPlace getPlace(String token) {
            EditorTabbedPlace place = placeProvider.get();
            place.setPlaceName(token);
            return place;
   		}

   	}
}
