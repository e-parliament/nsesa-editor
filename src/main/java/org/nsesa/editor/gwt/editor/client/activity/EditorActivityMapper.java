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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

/**
 * Class to provide activity based on the given place
 * User: sgroza
 * Date: 26/10/12
 * Time: 12:20
 */
public class EditorActivityMapper implements ActivityMapper {
    private PlaceController placeController;

    @Inject
    public EditorActivityMapper(PlaceController placeController) {
        super();
        this.placeController = placeController;
    }


    @Override
    public Activity getActivity(Place place) {
        if (place instanceof EditorActivityPlace) {
            return ((EditorActivityPlace) place).getActivity();
        }
        return null;
    }
}
