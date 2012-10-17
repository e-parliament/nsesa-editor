package org.nsesa.editor.gwt.editor.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.core.client.util.Checksum;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorView;

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
public abstract class Editor implements EntryPoint {
    ;

    public abstract Injector getInjector();

    private ClientFactory clientFactory;
    private ServiceFactory serviceFactory;


    @Override
    public void onModuleLoad() {
        // set up the uncaught exception handler before the actual initialization
        Log.setUncaughtExceptionHandler();
        clientFactory = getInjector().getClientFactory();

        serviceFactory = getInjector().getServiceFactory();

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
        final EditorController editorController = getInjector().getEditorController();
        // there seems to be no other way to inject this 'injector'
        editorController.setInjector(getInjector());

        final EditorView view = editorController.getView();
        RootLayoutPanel.get().add(view);

        registerEventListeners();

        // update the window title
        setInitialTitle();

        // process any changes to the layout
        doLayout();

        // retrieve the url parameters for the client context
        getParameters();

        // retrieve the user principal for the client context
        authenticate();

        try {
            Checksum.checksum("foo");
        } catch (Exception e) {
            Log.error("Checksum problem.", e);
        }
    }

    protected void setInitialTitle() {
        clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(clientFactory.getCoreMessages().windowTitleBootstrap()));
    }

    protected void registerEventListeners() {
        // register the basic event listeners
        final EventBus eventBus = clientFactory.getEventBus();

        // deal with critical errors
        eventBus.addHandler(CriticalErrorEvent.TYPE, new CriticalErrorEventHandler() {
            @Override
            public void onEvent(CriticalErrorEvent event) {
                handleError(clientFactory.getCoreMessages().errorTitleDefault(), event.getMessage(), event.getThrowable());
            }
        });

        // handle login & logout
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

        // handle updates to the window title
        eventBus.addHandler(SetWindowTitleEvent.TYPE, new SetWindowTitleEventHandler() {
            @Override
            public void onEvent(SetWindowTitleEvent event) {
                Log.debug("Setting window.title to " + event.getTitle());
                Window.setTitle(event.getTitle());
            }
        });

        // handle browser window resizing
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                clientFactory.getScheduler().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        eventBus.fireEvent(new org.nsesa.editor.gwt.core.client.event.ResizeEvent(Window.getClientHeight(), Window.getClientWidth()));
                    }
                });
            }
        });
    }

    protected void doLayout() {
        // remove the margin
        Window.setMargin("0px");
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
        final GWTServiceAsync gwtService = serviceFactory.getGwtService();
        final EventBus eventBus = clientFactory.getEventBus();

        gwtService.authenticate(clientFactory.getClientContext(), new AsyncCallback<ClientContext>() {
            @Override
            public void onFailure(Throwable caught) {
                eventBus.fireEvent(new CriticalErrorEvent(clientFactory.getCoreMessages().authenticationFailed(), caught));
            }

            @Override
            public void onSuccess(final ClientContext clientContext) {
                clientFactory.setClientContext(clientContext);
                eventBus.fireEvent(new AuthenticatedEvent(clientContext));
            }
        });
    }

    /**
     * Handle an error. By default, this means passing it on to the {@link ErrorController} and displaying it,
     * as well as logging it via gwt-log on error level.
     *
     * @param errorTitle   the title of the error message
     * @param errorMessage the content of the error message
     * @param throwable    the throwable, if it exists
     */
    protected void handleError(String errorTitle, String errorMessage, Throwable throwable) {
        final ErrorController errorController = getInjector().getErrorController();
        errorController.setError(errorTitle, errorMessage);
        errorController.center();
        Log.error(errorMessage, throwable);
    }
}
