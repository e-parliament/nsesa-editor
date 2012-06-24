package org.nsesa.editor.gwt.editor.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEvent;
import org.nsesa.editor.gwt.core.client.event.AuthenticatedEventHandler;
import org.nsesa.editor.gwt.core.client.event.BootstrapEvent;
import org.nsesa.editor.gwt.core.client.service.GWTServiceAsync;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

import java.util.List;
import java.util.Map;

/**
 * Main entry point for the Editor module.
 * <p/>
 * Date: 24/06/12 14:39
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class Editor implements EntryPoint {

    private final Injector injector = GWT.create(Injector.class);

    @Override
    public void onModuleLoad() {
        // set up the uncaught exception handler before the actual initialization
        Log.setUncaughtExceptionHandler();

        injector.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                Log.debug("Deferred Editor module loading started.");
                onModuleLoadDeferred();
                Log.debug("Deferred Editor module loading completed.");
            }
        });
    }

    protected void onModuleLoadDeferred() {
        // set up the main window
        final EditorController editorController = injector.getEditorController();
        RootPanel.get().add(editorController.getView());

        // start the bootstrap
        final ClientContext clientContext = injector.getClientContext();
        final EventBus eventBus = injector.getEventBus();

        eventBus.addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                Log.info("User authenticated as " + event.getPrincipal());
                clientContext.setPrincipal(event.getPrincipal());

                // we're authenticated, time for bootstrapping the rest of the application
                eventBus.fireEvent(new BootstrapEvent(clientContext));
            }
        });

        // retrieve the url parameters for the client context
        getParameters();

        // retrieve the user principal for the client context
        getPrincipal();
    }

    /**
     * Gathers the parameters as they have been added to the editor.html url.
     * This allows custom passing of parameters that can be used to identify
     * the requested document.
     * <p/>
     */
    protected void getParameters() {
        final ClientContext initialClient = injector.getClientContext();
        for (Map.Entry<String, List<String>> entry : Window.Location.getParameterMap().entrySet()) {
            initialClient.addParameter(entry.getKey(), entry.getValue());
        }
    }

    protected void getPrincipal() {
        final GWTServiceAsync gwtService = injector.getGWTService();
        final EventBus eventBus = injector.getEventBus();

        gwtService.getPrincipal(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Could not authenticate user.", caught);
            }

            @Override
            public void onSuccess(String result) {
                assert result != null : "Principal is null --BUG";
                eventBus.fireEvent(new AuthenticatedEvent(result));
            }
        });
    }
}
