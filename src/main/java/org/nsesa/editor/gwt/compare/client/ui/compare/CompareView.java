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
package org.nsesa.editor.gwt.compare.client.ui.compare;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.shared.RevisionDTO;

import java.util.List;

/**
 * View for the confirmation component.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(CompareViewImpl.class)
public interface CompareView extends IsWidget {

    Button getRollbackButton();

    Anchor getCancelAnchor();

    void setAvailableRevisions(List<RevisionDTO> revisions);

    void destroy();

    void setRevisionA(String revisionContent);

    void setRevisionB(String revisionContent);

    ListBox getRevisionsA();

    ListBox getRevisionsB();
}
