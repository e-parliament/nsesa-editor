package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;

import java.util.ArrayList;

/**
 * Date: 24/06/12 21:51
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDTO implements IsSerializable {

    private AmendmentAction amendmentAction;
    private String amendmentContainerID;

    private ArrayList<AmendableWidgetReference> sourceReferences = new ArrayList<AmendableWidgetReference>();
    private ArrayList<AmendableWidgetReference> targetReferences = new ArrayList<AmendableWidgetReference>();

    public AmendmentContainerDTO() {
    }

    public String getAmendmentContainerID() {
        return amendmentContainerID;
    }

    public void setAmendmentContainerID(String amendmentContainerID) {
        this.amendmentContainerID = amendmentContainerID;
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    public void setAmendmentAction(AmendmentAction amendmentAction) {
        this.amendmentAction = amendmentAction;
    }

    public ArrayList<AmendableWidgetReference> getSourceReferences() {
        return sourceReferences;
    }

    public void setSourceReferences(ArrayList<AmendableWidgetReference> sourceReferences) {
        this.sourceReferences = sourceReferences;
    }

    public ArrayList<AmendableWidgetReference> getTargetReferences() {
        return targetReferences;
    }

    public void setTargetReferences(ArrayList<AmendableWidgetReference> targetReferences) {
        this.targetReferences = targetReferences;
    }
}
