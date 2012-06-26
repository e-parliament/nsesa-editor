package org.nsesa.editor.gwt.editor.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.client.service.GWTServiceAsync;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

import java.util.Arrays;
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
    private ClientFactory clientFactory;

    @Override
    public void onModuleLoad() {
        // set up the uncaught exception handler before the actual initialization
        Log.setUncaughtExceptionHandler();
        clientFactory = injector.getClientFactory();

        clientFactory.getScheduler().scheduleDeferred(new Command() {
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

        registerEventListeners();

        // retrieve the url parameters for the client context
        getParameters();

        // retrieve the user principal for the client context
        authenticate();
    }

    private void registerEventListeners() {
        // start the bootstrap
        final EventBus eventBus = clientFactory.getEventBus();
        eventBus.addHandler(CriticalErrorEvent.TYPE, new CriticalErrorEventHandler() {
            @Override
            public void onEvent(CriticalErrorEvent event) {
                Log.error(event.getMessage(), event.getThrowable());
            }
        });
        eventBus.addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                final ClientContext clientContext = event.getClientContext();
                clientFactory.setClientContext(clientContext);

                Log.info("User authenticated as " + clientContext.getPrincipal()
                        + " with roles: " + (clientContext.getRoles() != null ? Arrays.asList(clientContext.getRoles()) : "[NONE]"));

                // we're authenticated, time for bootstrapping the rest of the application
                eventBus.fireEvent(new BootstrapEvent(clientContext));
            }
        });
    }

    /**
     * Gathers the parameters as they have been added to the editor.html url.
     * This allows custom passing of parameters that can be used to identify
     * the requested document.
     * <p/>
     */
    protected void getParameters() {
        final ClientContext initialClient = clientFactory.getClientContext();
        for (Map.Entry<String, List<String>> entry : Window.Location.getParameterMap().entrySet()) {
            initialClient.addParameter(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
        }
    }

    /**
     * Get the principal and fire the {@link AuthenticatedEvent} when ready.
     */
    protected void authenticate() {
        final GWTServiceAsync gwtService = injector.getGWTService();
        final EventBus eventBus = clientFactory.getEventBus();

        gwtService.authenticate(clientFactory.getClientContext(), new AsyncCallback<ClientContext>() {
            @Override
            public void onFailure(Throwable caught) {
                eventBus.fireEvent(new CriticalErrorEvent(clientFactory.getCoreMessages().authenticationFailed(), caught));
            }

            @Override
            public void onSuccess(final ClientContext clientContext) {
                eventBus.fireEvent(new AuthenticatedEvent(clientContext));
            }
        });
    }
}
