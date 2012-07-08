package org.nsesa.editor.gwt.core.client.ui.amendment;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.shared.AmendmentContainerDTO;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentController extends Composite {

    private final AmendmentView view;
    private final ClientFactory clientFactory;

    private AmendmentContainerDTO amendment;

    @Inject
    public AmendmentController(final ClientFactory clientFactory, final AmendmentView view) {
        this.clientFactory = clientFactory;
        this.view = view;

        registerListeners();
    }

    private void registerListeners() {

    }

    public AmendmentContainerDTO getAmendment() {
        return amendment;
    }

    public void setAmendment(AmendmentContainerDTO amendment) {
        this.amendment = amendment;
        setTitle(amendment.getPosition());
    }

    public AmendmentView getView() {
        return view;
    }

    public void setJustification(String justification) {
        this.view.setJustification(justification);
    }

    public void setTitle(String title) {
        this.view.setTitle(title);
    }
}
