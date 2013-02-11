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

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
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
