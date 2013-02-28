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
package org.nsesa.editor.gwt.core.client.mode;

/**
 * A document mode is mode that can be changed by applying a different {@link DocumentState}, and that can be
 * queried for its current {@link DocumentState}.
 * Date: 23/11/12 23:05
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface DocumentMode<S extends DocumentState> {
    /**
     * Applies a new {@link DocumentState}.
     * @param state the state to apply
     * @return <tt>true</tt> if it was applied successfully, <tt>false</tt> otherwise.
     */
    boolean apply(S state);

    /**
     * Returns the current state this {@link DocumentMode} is in.
     * @return
     */
    S getState();
}
