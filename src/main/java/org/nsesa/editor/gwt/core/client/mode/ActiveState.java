package org.nsesa.editor.gwt.core.client.mode;

/**
 * Date: 26/11/12 11:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ActiveState implements DocumentState {

    boolean active;

    public ActiveState(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
