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
package org.nsesa.editor.gwt.core.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event indicating a critical error within the system.
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class CriticalErrorEvent extends GwtEvent<CriticalErrorEventHandler> {

    public static final Type<CriticalErrorEventHandler> TYPE = new Type<CriticalErrorEventHandler>();

    private final String message;
    private Throwable throwable;

    public CriticalErrorEvent(String message) {
        this.message = message;
    }

    public CriticalErrorEvent(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    @Override
    public Type<CriticalErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CriticalErrorEventHandler handler) {
        handler.onEvent(this);
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
