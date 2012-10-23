package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.ArrayList;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidget extends IsWidget, HasWidgets {

    Boolean isAmendable();

    void setAmendable(Boolean amendable);

    Boolean isImmutable();

    void setImmutable(Boolean immutable);

    void setListener(AmendableWidgetListener listener);

    void setParentAmendableWidget(AmendableWidget parent);

    ArrayList<AmendableWidget> getParentAmendableWidgets();

    ArrayList<AmendableWidget> getChildAmendableWidgets();

    AmendableWidget getParentAmendableWidget();

    AmendableWidget getRoot();

    void addAmendableWidget(AmendableWidget child);

    void removeAmendableWidget(AmendableWidget child);

    void addAmendmentController(AmendmentController amendmentController);

    void removeAmendmentController(AmendmentController amendmentController);

    void postProcess();

    String getType();

    String getId();

    String getContent();

    void setContent(String amendableContent);

    HTMLPanel getAmendmentHolderElement();
}
