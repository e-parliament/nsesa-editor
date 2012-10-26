package org.nsesa.editor.gwt.editor.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

/**
 * Abstract class to map an activity to a place
 * User: sgroza
 * Date: 26/10/12
 * Time: 12:17
 */
public class EditorActivityPlace<T extends Activity> extends Place {
    protected T activity;
    private String placeName;

   	public EditorActivityPlace(T activity) {
   		this.activity = activity;
   		setPlaceName("");
   	}

   	public T getActivity() {
   		return activity;
   	}

   	public void setPlaceName(String token) {
   		this.placeName = token;
   	}

   	public String getPlaceName() {
   		return placeName;
   	}

}
