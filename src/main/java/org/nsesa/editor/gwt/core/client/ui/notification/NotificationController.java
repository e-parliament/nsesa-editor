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
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(EDITOR)
public class NotificationController {

    private final static List<NotificationController> INSTANCES = new ArrayList<NotificationController>();
    private static boolean PAUSE_UPDATE = false;

    private final static int HEIGHT_IN_PX = 30;
    private final static int WIDTH_IN_PX = 600;

    private PopupPanel popupPanel = new PopupPanel(false, false);

    private final NotificationView view;
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            hide();
        }
    };

    private Animation animation = new Animation() {
        @Override
        protected void onUpdate(double progress) {
            setOpacity(1 - interpolate(progress));
        }
    };


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

    private void setOpacity(double opacity) {
        final Style style = popupPanel.getElement().getStyle();
        style.setOpacity(opacity);
        if (opacity != 0.0 && opacity < 0.3) {
            hide();
        }
    }

    public NotificationView getView() {
        return view;
    }

    public void setMessage(final String message) {
        view.setMessage(message);
    }

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

    private static void updatePositions() {
        if (!PAUSE_UPDATE) {
            final int left = (Window.getClientWidth() / 2) - (WIDTH_IN_PX / 2);
            int counter = 0;
            for (final NotificationController notificationController : INSTANCES) {
                notificationController.popupPanel.setPopupPosition(left, counter++ * HEIGHT_IN_PX);
            }
        }
    }

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
