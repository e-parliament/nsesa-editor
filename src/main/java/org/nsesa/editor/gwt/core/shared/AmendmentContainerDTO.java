/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
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

    private String id;
    private String revisionID;

    private String languageISO;

    private AmendmentAction amendmentAction;
    private String amendmentContainerStatus = "candidate";

    private String body;

    private transient AmendableWidget root;

    private AmendableWidgetReference sourceReference;
    private ArrayList<AmendableWidgetReference> targetReferences = new ArrayList<AmendableWidgetReference>();

    public AmendmentContainerDTO() {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AmendableWidget getRoot() {
        return root;
    }

    public void setRoot(AmendableWidget root) {
        this.root = root;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguageISO() {
        return languageISO;
    }

    public void setLanguageISO(String languageISO) {
        this.languageISO = languageISO;
    }

    public String getRevisionID() {
        return revisionID;
    }

    public void setRevisionID(String revisionID) {
        this.revisionID = revisionID;
    }
}
