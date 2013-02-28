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
package org.nsesa.editor.gwt.core.shared;

/**
 * The different amendment actions.
 * Date: 10/07/12 13:27
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public enum AmendmentAction {
    /**
     * Indicates the creation of a new element.
     */
    CREATION,

    /**
     * Indicates the deletion of an existing element.
     */
    DELETION,

    /**
     * Indicates the movement of an existing element to a new location.
     */
    MOVE,

    /**
     * Indicates a modification of an existing element's content.
     */
    MODIFICATION,

    /**
     * Action to bundle one or more standalone amendments in a so-called bundle amendment.
     */
    BUNDLE
}
