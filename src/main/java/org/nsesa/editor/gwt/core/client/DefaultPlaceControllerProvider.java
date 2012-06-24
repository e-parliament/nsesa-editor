package org.nsesa.editor.gwt.core.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Date: 24/06/12 18:02
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultPlaceControllerProvider implements Provider<PlaceController> {

    @Inject
    private EventBus eventBus;

    @Override
    public PlaceController get() {
        return new PlaceController(eventBus);
    }
}
