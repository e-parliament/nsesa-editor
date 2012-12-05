package org.nsesa.editor.gwt.dialog.client.ui.handler;

import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * Date: 10/07/12 20:34
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendmentUIHandler {

    void setAmendmentAndWidget(AmendmentContainerDTO amendment, AmendableWidget amendableWidget);

    void setAmendmentAction(AmendmentAction amendmentAction);

    void setDocumentController(DocumentController documentController);

    IsWidget getView();
}
