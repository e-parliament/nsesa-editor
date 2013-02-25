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
package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.dom.client.Element;
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;

import java.util.Comparator;

/**
 * Date: 09/01/13 16:46
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentController.class)
public interface AmendmentController {

    public static Comparator<AmendmentController> ORDER_COMPARATOR = new Comparator<AmendmentController>() {
        @Override
        public int compare(AmendmentController a, AmendmentController b) {
            return Integer.valueOf(a.getOrder()).compareTo(b.getOrder());
        }
    };

    AmendmentContainerDTO getModel();

    OverlayWidget asAmendableWidget(String source);

    OverlayWidget asAmendableWidget(Element element);

    void setAmendment(AmendmentContainerDTO amendment);

    DocumentController getDocumentController();

    void setDocumentController(DocumentController documentController);

    AmendmentView getView();

    AmendmentView getExtendedView();

    void setTitle(String title);

    void setStatus(String status);

    void setAmendedOverlayWidget(OverlayWidget amendedOverlayWidget);

    OverlayWidget getAmendedOverlayWidget();

    int getOrder();

    void setOrder(int order);
}
