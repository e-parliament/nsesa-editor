/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.keyboard;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.nsesa.editor.gwt.core.client.event.KeyComboEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple implementation of a keyboard combo listener to handle key presses in the document. Works via a
 * {@link com.google.gwt.user.client.Event.NativePreviewHandler} rather than adding a DomHandler on the
 * {@link com.google.gwt.user.client.ui.RootLayoutPanel}, since that one doesn't always seem to fire.
 * <p/>
 * Date: 25/03/13 11:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class KeyboardListener {

    private static final Logger LOG = Logger.getLogger(KeyboardListener.class.getName());

    private final EventBus eventBus;

    private final List<KeyCombo> keyCombos = new ArrayList<KeyCombo>();
    private static final String KEY_UP_CONSTANT = "keyup";

    private HandlerRegistration handlerRegistration;

    @Inject
    public KeyboardListener(final EventBus eventBus) {
        this.eventBus = eventBus;

    }

    public void install() {
        handlerRegistration = Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                if (event.getNativeEvent().getKeyCode() > 0 && KEY_UP_CONSTANT.equalsIgnoreCase(event.getNativeEvent().getType())) {
                    filter(event);
                }
            }
        });
    }

    public void remove() {
        handlerRegistration.removeHandler();
    }

    public void register(KeyCombo... toAdd) {
        for (final KeyCombo keyCombo : toAdd) {
            keyCombos.add(keyCombo);
            LOG.info("Registered keyboard shortcut: " + keyCombo);
        }
    }

    public void filter(final Event.NativePreviewEvent event) {
        KeyCombo toMatch = new KeyCombo(event.getNativeEvent().getShiftKey(), event.getNativeEvent().getAltKey(),
                event.getNativeEvent().getCtrlKey(), event.getNativeEvent().getKeyCode());
        if (LOG.isLoggable(Level.FINEST))
            LOG.finest("Looking for key combo --> " + toMatch);
        //LOG.info("Looking for key combo --> " + toMatch);
        if (keyCombos.contains(toMatch)) {
            //LOG.info("Matching key combo --> " + toMatch);
            event.getNativeEvent().preventDefault();
            event.getNativeEvent().stopPropagation();
            eventBus.fireEvent(new KeyComboEvent(toMatch, event.getNativeEvent()));
        }
    }

    @Override
    public String toString() {
        return "KeyboardListener for: " + keyCombos;
    }

    /**
     * Key combo representation, supporting [Shift] [Alt] and [Ctrl] modifier keys, and general key codes.
     */
    public static class KeyCombo {
        public final boolean shift, control, alt;
        public final int charCode;

        public KeyCombo(boolean shift, boolean alt, boolean control, int charCode) {
            this.shift = shift;
            this.alt = alt;
            this.control = control;
            this.charCode = charCode;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof KeyCombo) {
                KeyCombo toMatch = (KeyCombo) o;
                return toMatch.control == control && toMatch.shift == shift && toMatch.charCode == charCode && toMatch.alt == alt;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = (shift ? 1 : 0);
            result = 31 * result + (control ? 1 : 0);
            result = 31 * result + (alt ? 1 : 0);
            result = 31 * result + charCode;
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (control) sb.append("[Ctrl]");
            if (shift) sb.append("[Shift]");
            if (alt) sb.append("[Alt]");
            if (charCode > 0) sb.append("<").append(charCode).append(">");
            return sb.toString();
        }
    }
}
