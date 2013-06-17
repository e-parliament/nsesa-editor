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
package org.nsesa.editor.gwt.core.client.util;

/**
 * A simple boolean flag class implementation to work around the ugliness when dealing with anonymous inner classes.
 * Date: 03/12/12 11:06
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public final class Flag {
    final boolean initialValue;
    boolean value;

    public Flag() {
        this.initialValue = false;
    }

    public Flag(boolean initialValue) {
        this.initialValue = initialValue;
        this.value = initialValue;
    }

    public void reset() {
        this.value = this.initialValue;
    }

    public void flip() {
        this.value = !value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }
}
