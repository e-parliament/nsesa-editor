package org.nsesa.editor.gwt.core.client.event.amendment;

import com.google.gwt.event.shared.GwtEvent;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.shared.DiffMethod;

/**
 * Date: 24/06/12 20:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDiffEvent extends GwtEvent<AmendmentContainerDiffEventHandler> {

    public static final Type<AmendmentContainerDiffEventHandler> TYPE = new Type<AmendmentContainerDiffEventHandler>();

    private final String type;
    private final DiffMethod diffMethod;
    private final AmendmentController[] amendmentControllers;

    public AmendmentContainerDiffEvent(String type, DiffMethod diffMethod, AmendmentController amendmentController) {
        this.type = type;
        this.diffMethod = diffMethod;
        this.amendmentControllers = new AmendmentController[]{amendmentController};
    }

    public AmendmentContainerDiffEvent(String type, DiffMethod diffMethod, AmendmentController[] amendmentControllers) {
        this.type = type;
        this.diffMethod = diffMethod;
        this.amendmentControllers = amendmentControllers;
    }

    @Override
    public Type<AmendmentContainerDiffEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AmendmentContainerDiffEventHandler handler) {
        handler.onEvent(this);
    }

    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers;
    }

    public String getType() {
        return type;
    }

    public DiffMethod getDiffMethod() {
        return diffMethod;
    }
}
