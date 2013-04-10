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
package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event fired when the browser window has been resized.
 * Date: 24/06/12 20:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ResizeEvent extends GwtEvent<ResizeEventHandler> {

    public static final Type<ResizeEventHandler> TYPE = new Type<ResizeEventHandler>();

    final int height;
    final int width;

    public ResizeEvent(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Type<ResizeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResizeEventHandler handler) {
        handler.onEvent(this);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
