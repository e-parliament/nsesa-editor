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
package org.nsesa.editor.gwt.core.client.event.amendments;

import com.google.gwt.event.shared.GwtEvent;

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
