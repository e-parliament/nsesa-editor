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
package org.nsesa.editor.gwt.core.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * An exception thrown if the resource requested is stale (used in combination with optimistic locking).
 * Date: 24/06/12 22:19
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class StaleResourceException extends Exception implements Serializable, IsSerializable {
    public StaleResourceException() {
        super();
    }

    public StaleResourceException(String s) {
        super(s);
    }

    public StaleResourceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public StaleResourceException(Throwable throwable) {
        super(throwable);
    }
}
