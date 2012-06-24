package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.CoreModule;
import org.nsesa.editor.gwt.core.client.service.GWTServiceAsync;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.editor.client.ui.header.HeaderModule;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorModule;

/**
 * Date: 24/06/12 15:56
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GinModules({CoreModule.class, EditorModule.class, HeaderModule.class})
public interface Injector extends Ginjector {

    Scheduler getScheduler();

    EditorController getEditorController();

    EventBus getEventBus();

    PlaceController getPlaceController();

    ClientContext getClientContext();

    GWTServiceAsync getGWTService();
}
