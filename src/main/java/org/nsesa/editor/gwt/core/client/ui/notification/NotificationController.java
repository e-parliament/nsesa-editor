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
package org.nsesa.editor.gwt.core.client.ui.notification;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.ArrayList;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Component for showing a (temporary) notification on the screen of the end user.
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(EDITOR)
public class NotificationController {

    /**
     * To keep track of all instances (since multiple instances kan appear at the same time).
     */
    private final static List<NotificationController> INSTANCES = new ArrayList<NotificationController>();

    /**
     * A flag to keep track of whether or not we should pause the animation (eg. during hovering)
     */
    private static boolean PAUSE_UPDATE = false;

    /**
     * The default height in pixels of a single notification.
     */
    private final static int HEIGHT_IN_PX = 30;

    /**
     * The default width in pixels of a single notification.
     */
    private final static int WIDTH_IN_PX = 600;

    /**
     * The underlying popup panel for a single notification.
     */
    private PopupPanel popupPanel = new PopupPanel(false, false);

    /**
     * The view.
     */
    private final NotificationView view;

    /**
     * An animation timer.
     */
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * An animation to alter the opacity.
     */
    private Animation animation = new Animation() {
        @Override
        protected void onUpdate(double progress) {
            setOpacity(1 - interpolate(progress));
        }
    };

    /**
     * The default duration of 5 seconds.
     */
    private int duration = 5;

    @Inject
    public NotificationController(final NotificationView view) {
        this.view = view;
        view.asWidget().setWidth(WIDTH_IN_PX + "px");
        view.asWidget().setHeight(HEIGHT_IN_PX + "px");
        popupPanel.setWidget(view);
        popupPanel.getElement().getStyle().setZIndex(10);
        registerListeners();
    }

    private void registerListeners() {
        view.getCloseButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        view.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                timer.cancel();
                animation.cancel();
                setOpacity(1.0);
                PAUSE_UPDATE = true;
            }
        });
        view.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                timer.schedule(duration * 1000);
                animation.run(duration * 1000);
                PAUSE_UPDATE = false;
            }
        });
    }

    /**
     * Set the opacity on this view.
     *
     * @param opacity
     */
    private void setOpacity(double opacity) {
        final Style style = popupPanel.getElement().getStyle();
        style.setOpacity(opacity);
        if (opacity != 0.0 && opacity < 0.3) {
            hide();
        }
    }

    /**
     * Get the view associated with this notification.
     *
     * @return the view
     */
    public NotificationView getView() {
        return view;
    }

    /**
     * Set the message to be displayed in the notification.
     *
     * @param message the message to show
     */
    public void setMessage(final String message) {
        view.setMessage(message);
    }

    /**
     * Show the notification, and keep track of this notification's position.
     */
    public void show() {
        timer.cancel();

        if (duration > 0) {
            timer.schedule(duration * 1000);
            animation.run(duration * 1000);
        }
        final int left = (Window.getClientWidth() / 2) - (WIDTH_IN_PX / 2);
        popupPanel.setPopupPosition(left, 5 + (INSTANCES.size() * HEIGHT_IN_PX));
        popupPanel.show();

        INSTANCES.add(this);
        updatePositions();
    }

    /**
     * Update the position to make sure they are not overlapping
     */
    private static void updatePositions() {
        if (!PAUSE_UPDATE) {
            final int left = (Window.getClientWidth() / 2) - (WIDTH_IN_PX / 2);
            int counter = 0;
            for (final NotificationController notificationController : INSTANCES) {
                notificationController.popupPanel.setPopupPosition(left, counter++ * HEIGHT_IN_PX);
            }
        }
    }

    /**
     * Hide the notification.
     */
    public void hide() {
        popupPanel.hide();
        timer.cancel();
        animation.cancel();

        INSTANCES.remove(this);
        updatePositions();
    }

    /**
     * Sets the duration of the notification. A value less than <tt>1</tt> means no timeout, causing the notification
     * to be closed manually.
     *
     * @param duration the duration in seconds.
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
