package org.nsesa.editor.gwt.dialog.client.ui.handler.bundle;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the creation and editing of amendment bundles.
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(AmendmentBundleViewImpl.class)
public interface AmendmentBundleView extends IsWidget {

    HasClickHandlers getSaveButton();

    HasClickHandlers getCancelButton();
}
