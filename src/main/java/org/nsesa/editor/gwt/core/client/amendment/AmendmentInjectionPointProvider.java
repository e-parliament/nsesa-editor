package org.nsesa.editor.gwt.core.client.amendment;

import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.editor.client.ui.document.DocumentController;

/**
 * This interface is responsible for providing the correct amendable widget to add the amendment to. Note that this
 * is not required to return the same {@link AmendableWidget} as the one that was provided by the {@link AmendmentInjectionPointFinder}.
 * <p/>
 * For example, if the amendment introduces a new Point 1 under a Paragraph B, then the implementation should create
 * a new Point element under the paragraph B, and return this newly created Point to attach the amendment on.
 *
 * Date: 30/11/12 11:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DefaultAmendmentInjectionPointProvider.class)
public interface AmendmentInjectionPointProvider {
    AmendableWidget provideInjectionPoint(AmendmentController amendmentController, AmendableWidget root, DocumentController documentController);
}
