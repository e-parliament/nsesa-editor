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
package org.nsesa.editor.gwt.amendment.client.ui.document.resources;

/**
 * Date: 11/06/13 10:09
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

    @Key("amendment.action")
    String amendmentAction(String action, String widget);

    @Key("amendment.action.delete")
    String amendmentActionDelete();

    @Key("amendment.action.creation")
    String amendmentActionCreation();

    @Key("amendment.action.modification")
    String amendmentActionModification();

    @Key("amendment.action.move")
    String amendmentActionMove();

    @Key("amendment.action.bundle")
    String amendmentActionBundle();
}