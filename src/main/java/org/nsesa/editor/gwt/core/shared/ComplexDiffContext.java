package org.nsesa.editor.gwt.core.shared;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComplexDiffContext {

    private String originalChangeTemplate, originalComplexChangeTemplate, complexInsertTemplate, complexDeleteTemplate,
            complexChangeTemplate;

    private DiffMethod diffMethod;

    public ComplexDiffContext() {
    }

    public ComplexDiffContext(String originalChangeTemplate, String originalComplexChangeTemplate,
                              String complexInsertTemplate, String complexDeleteTemplate, String complexChangeTemplate,
                              DiffMethod diffMethod) {
        super();
        this.originalChangeTemplate = originalChangeTemplate;
        this.originalComplexChangeTemplate = originalComplexChangeTemplate;
        this.complexInsertTemplate = complexInsertTemplate;
        this.complexDeleteTemplate = complexDeleteTemplate;
        this.complexChangeTemplate = complexChangeTemplate;
        this.diffMethod = diffMethod;
    }

    public String getOriginalChangeTemplate() {
        return originalChangeTemplate;
    }

    public ComplexDiffContext setOriginalChangeTemplate(String originalChangeTemplate) {
        this.originalChangeTemplate = originalChangeTemplate;
        return this;
    }

    public String getOriginalComplexChangeTemplate() {
        return originalComplexChangeTemplate;
    }

    public ComplexDiffContext setOriginalComplexChangeTemplate(String originalComplexChangeTemplate) {
        this.originalComplexChangeTemplate = originalComplexChangeTemplate;
        return this;
    }

    public String getComplexInsertTemplate() {
        return complexInsertTemplate;
    }

    public ComplexDiffContext setComplexInsertTemplate(String complexInsertTemplate) {
        this.complexInsertTemplate = complexInsertTemplate;
        return this;
    }

    public String getComplexDeleteTemplate() {
        return complexDeleteTemplate;
    }

    public ComplexDiffContext setComplexDeleteTemplate(String complexDeleteTemplate) {
        this.complexDeleteTemplate = complexDeleteTemplate;
        return this;
    }

    public String getComplexChangeTemplate() {
        return complexChangeTemplate;
    }

    public ComplexDiffContext setComplexChangeTemplate(String complexChangeTemplate) {
        this.complexChangeTemplate = complexChangeTemplate;
        return this;
    }

    public DiffMethod getDiffMethod() {
        return diffMethod;
    }

    public ComplexDiffContext setDiffMethod(DiffMethod diffMethod) {
        this.diffMethod = diffMethod;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexDiffContext that = (ComplexDiffContext) o;

        if (complexChangeTemplate != null ? !complexChangeTemplate.equals(that.complexChangeTemplate) : that.complexChangeTemplate != null)
            return false;
        if (complexDeleteTemplate != null ? !complexDeleteTemplate.equals(that.complexDeleteTemplate) : that.complexDeleteTemplate != null)
            return false;
        if (complexInsertTemplate != null ? !complexInsertTemplate.equals(that.complexInsertTemplate) : that.complexInsertTemplate != null)
            return false;
        if (diffMethod != that.diffMethod) return false;
        if (originalChangeTemplate != null ? !originalChangeTemplate.equals(that.originalChangeTemplate) : that.originalChangeTemplate != null)
            return false;
        if (originalComplexChangeTemplate != null ? !originalComplexChangeTemplate.equals(that.originalComplexChangeTemplate) : that.originalComplexChangeTemplate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalChangeTemplate != null ? originalChangeTemplate.hashCode() : 0;
        result = 31 * result + (originalComplexChangeTemplate != null ? originalComplexChangeTemplate.hashCode() : 0);
        result = 31 * result + (complexInsertTemplate != null ? complexInsertTemplate.hashCode() : 0);
        result = 31 * result + (complexDeleteTemplate != null ? complexDeleteTemplate.hashCode() : 0);
        result = 31 * result + (complexChangeTemplate != null ? complexChangeTemplate.hashCode() : 0);
        result = 31 * result + (diffMethod != null ? diffMethod.hashCode() : 0);
        return result;
    }
}
