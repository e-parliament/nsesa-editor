package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 26/06/12 19:15
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentDTO implements IsSerializable, Serializable {

    private String documentID;
    private String name;
    private String languageIso;
    private boolean amendable;

    private Date deadline;

    public DocumentDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentDTO that = (DocumentDTO) o;

        if (documentID != null ? !documentID.equals(that.documentID) : that.documentID != null) return false;
        if (languageIso != null ? !languageIso.equals(that.languageIso) : that.languageIso != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentID != null ? documentID.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (languageIso != null ? languageIso.hashCode() : 0);
        return result;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getLanguageIso() {
        return languageIso;
    }

    public void setLanguageIso(String languageIso) {
        this.languageIso = languageIso;
    }

    public boolean isAmendable() {
        return amendable;
    }

    public void setAmendable(boolean amendable) {
        this.amendable = amendable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline != null ? new Date(deadline.getTime()) : null;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
