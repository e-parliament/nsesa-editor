package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.util.Filter;
import org.nsesa.editor.gwt.core.client.util.FilterResponse;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

import java.util.List;

/**
 * Date: 09/01/13 17:16
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentManager.class)
public interface AmendmentManager extends AmendmentInjectionCapable {
    void setAmendmentContainerDTOs(AmendmentContainerDTO[] amendmentContainerDTOs);

    List<AmendmentController> getAmendmentControllers();

    FilterResponse<AmendmentController> getAmendmentControllers(Filter<AmendmentController> filter);

    void setDocumentController(DocumentController documentController);
}
