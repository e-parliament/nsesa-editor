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
package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.event.*;
import org.nsesa.editor.gwt.core.client.service.gwt.GWTServiceAsync;
import org.nsesa.editor.gwt.core.client.ui.confirmation.ConfirmationController;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.core.client.ui.notification.NotificationController;
import org.nsesa.editor.gwt.core.shared.ClientContext;
import org.nsesa.editor.gwt.editor.client.activity.EditorPlaceFactory;
import org.nsesa.editor.gwt.editor.client.activity.EditorPlaceHistoryMapper;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base entry point for the Editor module. Designed for easy subclassing.
 * <p/>
 * Date: 24/06/12 14:39
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public abstract class Editor implements EntryPoint {

    private static final Logger LOG = Logger.getLogger(Editor.class.getName());

    private ClientFactory clientFactory;
    private ServiceFactory serviceFactory;
    private SimpleLayoutPanel appWidget = new SimpleLayoutPanel();

    @Override
    public void onModuleLoad() {
        // set up the uncaught exception handler before the actual initialization
        installUncaughtExceptionHandler();
        clientFactory = getInjector().getClientFactory();

        serviceFactory = getInjector().getServiceFactory();

        clientFactory.getScheduler().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                LOG.info("Deferred Editor module loading started.");
                onModuleLoadDeferred();
                LOG.info("Deferred Editor module loading completed.");
            }
        });
    }

    /**
     * Abstract call that must be implemented to get a (subclass of) the {@link Injector}, which
     * will be used to set up all dependencies in the correct scope.
     *
     * @return the {@link Injector}
     */
    public abstract Injector getInjector();

    /**
     * Installs an exception handler that catches exceptions that would otherwise bubble up and result in a
     * exception in the browser window.
     * By handling this via the java.util.Logger, we are able to pass this exception on to the server side, and log
     * it over there.
     */
    protected void installUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable e) {
                LOG.log(Level.SEVERE, "Uncaught exception: " + e, e);
            }
        });
        LOG.info("Installed uncaught exception handler.");
    }

    /**
     * Deferred loading of the module, to ensure that the uncaught exception handler has been installed. At this point,
     * the client and service factory have been set.
     */
    protected void onModuleLoadDeferred() {

        // build the UI
        initializeUI();

        // register event listeners
        registerEventListeners();

        // update the window title
        setInitialTitle();

        // process any changes to the layout
        doLayout();

        // retrieve the url parameters for the client context
        getParameters();

        // retrieve the user principal for the client context
        authenticate();
    }

    /**
     * Initialize the main UI component(s) and add them to the {@link RootLayoutPanel}.
     * We're currently also setting up the {@link ActivityManager} and {@link EditorPlaceFactory}, but they are
     * unused until we handle multiple document controllers.
     */
    protected void initializeUI() {
        // set up the main window
        final EditorController editorController = getInjector().getEditorController();
        // there seems to be no other way to inject this 'injector'
        editorController.setInjector(getInjector());

        PlaceController placeController = clientFactory.getPlaceController();
        EventBus eventBus = clientFactory.getEventBus();

        final ActivityManager activityManager = getActivityManager(eventBus);
        activityManager.setDisplay(new AcceptsOneWidget() {
            @Override
            public void setWidget(IsWidget activityWidget) {
                Widget widget = Widget.asWidgetOrNull(activityWidget);
                appWidget.setVisible(widget != null);
                appWidget.setWidget(widget);
            }
        });

        RootLayoutPanel.get().add(appWidget);

        final EditorPlaceFactory placeFactory = getInjector().getPlaceFactory();
        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        final EditorPlaceHistoryMapper historyMapper = GWT.create(EditorPlaceHistoryMapper.class);
        historyMapper.setFactory(placeFactory);
        Place defaultPlace = placeFactory.getDefaultPlace();
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }

    /**
     * Retrieve the {@link ActivityManager}.
     *
     * @param eventBus the event bus to use
     * @return the activity manager
     */
    protected ActivityManager getActivityManager(final EventBus eventBus) {
        final ActivityMapper activityMapper = getInjector().getActivityMapper();
        return new ActivityManager(activityMapper, eventBus);
    }

    /**
     * Sets the initial title for the bootstrap.
     */
    protected void setInitialTitle() {
        clientFactory.getEventBus().fireEvent(new SetWindowTitleEvent(clientFactory.getCoreMessages().windowTitleBootstrap()));
    }

    /**
     * Registers the listeners.
     */
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

        // deal with confirmations
        eventBus.addHandler(ConfirmationEvent.TYPE, new ConfirmationEventHandler() {
            @Override
            public void onEvent(ConfirmationEvent event) {
                handleConfirmation(event.getTitle(), event.getMessage(), event.getConfirmationButtonText(),
                        event.getConfirmationHandler(), event.getCancelButtonText(), event.getCancelHandler());
            }
        });

        // handle login & logout
        eventBus.addHandler(AuthenticatedEvent.TYPE, new AuthenticatedEventHandler() {
            @Override
            public void onEvent(AuthenticatedEvent event) {
                final ClientContext clientContext = event.getClientContext();
                clientFactory.setClientContext(clientContext);

                LOG.info("User authenticated as " + clientContext.getLoggedInPerson().getUsername()
                        + " with roles: " + (clientContext.getRoles() != null ? Arrays.asList(clientContext.getRoles()) : "[NONE]"));

                // we're authenticated, time for bootstrapping the rest of the application
                eventBus.fireEvent(new BootstrapEvent(clientContext));
            }
        });

        // handle updates to the window title
        eventBus.addHandler(SetWindowTitleEvent.TYPE, new SetWindowTitleEventHandler() {
            @Override
            public void onEvent(SetWindowTitleEvent event) {
                LOG.info("Setting window.title to " + event.getTitle());
                Window.setTitle(event.getTitle());
            }
        });

        // add notification events
        eventBus.addHandler(NotificationEvent.TYPE, new NotificationEventHandler() {
            @Override
            public void onEvent(NotificationEvent event) {
                LOG.info("Showing notification: '" + event.getMessage() + "' for " + event.getDuration() + " seconds.");
                handleNotification(event.getMessage(), event.getDuration());
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

        // handle locale change requests
        eventBus.addHandler(LocaleChangeEvent.TYPE, new LocaleChangeEventHandler() {
            @Override
            public void onEvent(LocaleChangeEvent event) {
                LOG.info("Changing UI language to " + event.getLocale());
                Window.Location.assign(Window.Location.createUrlBuilder().setParameter(LocaleInfo.getLocaleQueryParam(), event.getLocale()).buildString());
            }
        });
    }

    /**
     * Perform the basic layout calls after the initialization.
     * In this case, we simply remove the margin, but this can be overridden by subclasses.
     */
    protected void doLayout() {
        // remove the margin
        Window.setMargin("0px");
    }

    /**
     * Gathers the parameters as they have been added to the editor.html url.
     * This allows custom passing of parameters that can be used to identify
     * the requested document.
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
                LOG.info("Authentication failed for " + clientFactory.getClientContext());
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
    protected void handleError(final String errorTitle, final String errorMessage, final Throwable throwable) {
        final ErrorController errorController = getInjector().getErrorController();
        errorController.setError(errorTitle, errorMessage);
        errorController.center();
        LOG.log(Level.SEVERE, errorMessage, throwable);
    }

    /**
     * Handle a confirmation. Contains callbacks to handle confirmations and cancel operations.
     *
     * @param confirmationTitle      the title of the confirmation panel
     * @param confirmationMessage    the message of the confirmation panel
     * @param confirmationButtonText the confirmation button's text
     * @param confirmationHandler    the confirmation handler, will be invoked when the user confirms
     * @param cancelButtonText       the cancel button's text
     * @param cancelHandler          the cancel handler, will be invoked when the user cancels
     */
    protected void handleConfirmation(final String confirmationTitle, final String confirmationMessage,
                                      final String confirmationButtonText, final ClickHandler confirmationHandler,
                                      final String cancelButtonText, final ClickHandler cancelHandler) {
        ConfirmationController confirmationController = getInjector().getConfirmationController();
        confirmationController.setConfirmation(confirmationTitle, confirmationMessage, confirmationButtonText,
                confirmationHandler, cancelButtonText, cancelHandler);
        confirmationController.center();
    }

    /**
     * Handle a notification. Notifications are displayed by default in the top center of the screen. They can be closed,
     * and in fact must be, when a <tt>duration</tt> of less than 1 second is specified.
     *
     * @param message  the message of the notification
     * @param duration the duration in seconds the notification should be shown before hiding itself
     */
    protected void handleNotification(final String message, final int duration) {
        final NotificationController notificationController = getInjector().getNotificationController();
        notificationController.setMessage(message);
        notificationController.setDuration(duration);
        notificationController.show();
    }
}
