package org.nsesa.editor.gwt.editor.client.ui.header.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Date: 03/08/12 15:37
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Resources extends ClientBundle {

    @Source("amendments.gif")
    ImageResource viewAmendments();

    @Source("amendments-selected.gif")
    ImageResource viewAmendmentsSelected();

    @Source("info.gif")
    ImageResource viewInfo();

    @Source("info-selected.gif")
    ImageResource viewInfoSelected();

    @Source("document.gif")
    ImageResource viewDocument();

    @Source("document-selected.gif")
    ImageResource viewDocumentSelected();
}
