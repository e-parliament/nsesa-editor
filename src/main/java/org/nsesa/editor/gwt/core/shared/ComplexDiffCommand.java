package org.nsesa.editor.gwt.core.shared;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComplexDiffCommand {

    private String original;
    private String modified;
    private String overrideModified;
    private ComplexDiffContext context;

    public ComplexDiffCommand() {
    }

    public ComplexDiffCommand(String original, String modified, String overrideModified) {
        this.original = original;
        this.modified = modified;
        this.overrideModified = overrideModified;
    }

    public ComplexDiffCommand(String original, String modified, String overrideModified, ComplexDiffContext context) {
        this.original = original;
        this.modified = modified;
        this.overrideModified = overrideModified;
        this.context = context;
    }

    public String getOriginal() {
        return original;
    }

    public String getModified() {
        return modified;
    }

    public String getOverrideModified() {
        return overrideModified;
    }

    public ComplexDiffContext getContext() {
        return context;
    }

}
