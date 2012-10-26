package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
            return ((EditorActivityPlace)place).getActivity();
        }
        return null;
    }
}
