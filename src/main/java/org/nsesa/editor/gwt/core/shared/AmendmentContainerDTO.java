package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.nsesa.editor.gwt.core.client.ui.overlay.AmendmentAction;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;

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
    private String amendmentContainerStatus;

    private String xmlContent;

    private transient AmendableWidget root;

    private AmendableWidgetReference sourceReference;
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

    public String getAmendmentContainerStatus() {
        return amendmentContainerStatus;
    }

    public void setAmendmentContainerStatus(String amendmentContainerStatus) {
        this.amendmentContainerStatus = amendmentContainerStatus;
    }

    public AmendableWidgetReference getSourceReference() {
        return sourceReference;
    }

    public void setSourceReference(AmendableWidgetReference sourceReference) {
        this.sourceReference = sourceReference;
    }

    public ArrayList<AmendableWidgetReference> getTargetReferences() {
        return targetReferences;
    }

    public void setTargetReferences(ArrayList<AmendableWidgetReference> targetReferences) {
        this.targetReferences = targetReferences;
    }

    public String getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public AmendableWidget getRoot() {
        return root;
    }

    public void setRoot(AmendableWidget root) {
        this.root = root;
    }
}
