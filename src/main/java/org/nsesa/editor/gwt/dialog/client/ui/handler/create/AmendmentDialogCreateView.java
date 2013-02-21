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
package org.nsesa.editor.gwt.dialog.client.ui.handler.create;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * View for the creation and editing of amendment bundles.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentDialogCreateViewImpl.class)
public interface AmendmentDialogCreateView extends IsWidget {

    void addBodyClass(String className);

    void resetBodyClass();

    String getAmendmentContent();

    void setAmendmentContent(String content);

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelLink();

    void setTitle(String title);

    RichTextEditor getRichTextEditor();

    void addView(IsWidget view, String title);
}
