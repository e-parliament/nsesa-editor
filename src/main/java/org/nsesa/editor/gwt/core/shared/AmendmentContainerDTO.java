package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;

/**
 * Date: 24/06/12 21:51
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDTO implements IsSerializable {

    private AmendmentAction amendmentAction;
    private String amendmentContainerID;
    private String position;

    public String getAmendmentContainerID() {
        return amendmentContainerID;
    }

    public void setAmendmentContainerID(String amendmentContainerID) {
        this.amendmentContainerID = amendmentContainerID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }
}
