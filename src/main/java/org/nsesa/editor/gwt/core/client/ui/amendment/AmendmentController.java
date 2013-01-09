package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

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

    AmendableWidget asAmendableWidget();

    void setAmendment(AmendmentContainerDTO amendment);

    DocumentController getDocumentController();

    void setDocumentController(DocumentController documentController);

    AmendmentView getView();

    AmendmentView getExtendedView();

    void setTitle(String title);

    void setAmendedAmendableWidget(AmendableWidget amendedAmendableWidget);

    AmendableWidget getAmendedAmendableWidget();

    int getOrder();

    void setOrder(int order);
}
