/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

import java.util.ArrayList;

/**
 * A Data Transfer Object (DTO) for an Amendment entity.
 * Date: 24/06/12 21:51
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendmentContainerDTO implements IsSerializable {

    /**
     * The primary key that uniquely identifies an amendment container revision.
     */
    private String id;

    private String documentID;

    /**
     * A revision key that identifies all amendment revisions for a single, logical amendment.
     */
    private String revisionID;

    /**
     * The two letter ISO code of the (primary) language this amendment is created in.
     */
    private String languageISO;

    /**
     * The type of action of this amendment (modification, deletion, creation, move, ...)
     */
    private AmendmentAction amendmentAction;

    /**
     * The status of an amendment. The initial status of an amendment is 'candidate'. Left as a String for
     * easier extension.
     */
    private String amendmentContainerStatus = "candidate";

    /**
     * The serialized body/payload of this amendment. Can be XML or JSON, depending on what your backend provides.
     */
    private String body;

    /**
     * A transient overlay widget which is the result of the {@link #body} being transformed into an overlay tree
     * consisting of one or more {@link OverlayWidget} via an {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory}.
     */
    private transient OverlayWidget root;

    /**
     * A reference to the source of this this amendment (meaning, the place where the amendment should be injected upon)
     */
    private AmendableWidgetReference sourceReference;

    /**
     * A list of one or more target references - which will be impacted by this amendment. For example, if an amendment
     * is made on a &lt;DEFINITION&gt; element, the target references would be every widget where the redefined
     * element is used.
     * <p/>
     * TODO the target references are not yet supported
     */
    private ArrayList<AmendableWidgetReference> targetReferences = new ArrayList<AmendableWidgetReference>();

    public AmendmentContainerDTO() {
    }

    public AmendmentAction getAmendmentAction() {
        return amendmentAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmendmentContainerDTO that = (AmendmentContainerDTO) o;

        if (amendmentAction != that.amendmentAction) return false;
        if (!amendmentContainerStatus.equals(that.amendmentContainerStatus)) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (!id.equals(that.id)) return false;
        if (languageISO != null ? !languageISO.equals(that.languageISO) : that.languageISO != null)
            return false;
        if (!revisionID.equals(that.revisionID)) return false;
        if (sourceReference != null ? !sourceReference.equals(that.sourceReference) : that.sourceReference != null)
            return false;
        if (targetReferences != null ? !targetReferences.equals(that.targetReferences) : that.targetReferences != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (revisionID != null ? revisionID.hashCode() : 0);
        result = 31 * result + (languageISO != null ? languageISO.hashCode() : 0);
        result = 31 * result + (amendmentAction != null ? amendmentAction.hashCode() : 0);
        result = 31 * result + (amendmentContainerStatus != null ? amendmentContainerStatus.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (sourceReference != null ? sourceReference.hashCode() : 0);
        result = 31 * result + (targetReferences != null ? targetReferences.hashCode() : 0);
        return result;
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

    public OverlayWidget getRoot() {
        return root;
    }

    public void setRoot(OverlayWidget root) {
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
