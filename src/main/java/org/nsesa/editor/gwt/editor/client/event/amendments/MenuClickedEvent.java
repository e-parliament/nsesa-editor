package org.nsesa.editor.gwt.editor.client.event.amendments;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.Event;

/**
 * An event which occur when selecting a menu item.
 * User: groza
 * Date: 5/12/12
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
public class MenuClickedEvent extends GwtEvent<MenuClickedEventHandler> {
    public static final Type<MenuClickedEventHandler> TYPE = new Type<MenuClickedEventHandler>();

    public static enum MenuType {
        SELECTION,
        ACTION,
        FILTER
    }

    private String option;
    private MenuType menuType;

    public MenuClickedEvent(String option, MenuType menuType) {
        this.option = option;
        this.menuType = menuType;
    }

    @Override
    public Type<MenuClickedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MenuClickedEventHandler handler) {
        handler.onEvent(this);
    }
    public String getOption() {
        return option;
    }

    public MenuType getMenuType() {
        return menuType;
    }

}
