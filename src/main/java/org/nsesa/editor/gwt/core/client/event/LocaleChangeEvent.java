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
 * An event used to indicate that a request for changing the locale has been performed.
 * Date: 24/06/12 18:14
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class LocaleChangeEvent extends GwtEvent<LocaleChangeEventHandler> {

    public static final Type<LocaleChangeEventHandler> TYPE = new Type<LocaleChangeEventHandler>();

    private final String locale;

    public LocaleChangeEvent(String locale) {
        this.locale = locale;
    }

    @Override
    public Type<LocaleChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LocaleChangeEventHandler handler) {
        handler.onEvent(this);
    }

    public String getLocale() {
        return locale;
    }
}
