package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.AnyURI;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.Language;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.NMTOKEN;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.StatusType;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class Legislature extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private Language langAttribute;
    private NMTOKEN evolvingIdAttribute;
    private AnyURI alternativeToAttribute;
    private AnyURI refersToAttribute;
    private String styleAttribute;
    private String classAttribute;
    private String valueAttribute;
    private String titleAttribute;
    private StatusType statusAttribute;
    private AnyURI periodAttribute;
    private java.util.List<Legislature> legislatureElements = new ArrayList<Legislature>();

// CONSTRUCTORS ------------------

    public Legislature(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public Language getLangAttribute() {
        return langAttribute;
    }

    public void setLangAttribute(final Language langAttribute) {
        this.langAttribute = langAttribute;
    }

    public NMTOKEN getEvolvingIdAttribute() {
        return evolvingIdAttribute;
    }

    public void setEvolvingIdAttribute(final NMTOKEN evolvingIdAttribute) {
        this.evolvingIdAttribute = evolvingIdAttribute;
    }

    public AnyURI getAlternativeToAttribute() {
        return alternativeToAttribute;
    }

    public void setAlternativeToAttribute(final AnyURI alternativeToAttribute) {
        this.alternativeToAttribute = alternativeToAttribute;
    }

    public AnyURI getRefersToAttribute() {
        return refersToAttribute;
    }

    public void setRefersToAttribute(final AnyURI refersToAttribute) {
        this.refersToAttribute = refersToAttribute;
    }

    public String getStyleAttribute() {
        return styleAttribute;
    }

    public void setStyleAttribute(final String styleAttribute) {
        this.styleAttribute = styleAttribute;
    }

    public String getClassAttribute() {
        return classAttribute;
    }

    public void setClassAttribute(final String classAttribute) {
        this.classAttribute = classAttribute;
    }

    public String getValueAttribute() {
        return valueAttribute;
    }

    public void setValueAttribute(final String valueAttribute) {
        this.valueAttribute = valueAttribute;
    }

    public String getTitleAttribute() {
        return titleAttribute;
    }

    public void setTitleAttribute(final String titleAttribute) {
        this.titleAttribute = titleAttribute;
    }

    public StatusType getStatusAttribute() {
        return statusAttribute;
    }

    public void setStatusAttribute(final StatusType statusAttribute) {
        this.statusAttribute = statusAttribute;
    }

    public AnyURI getPeriodAttribute() {
        return periodAttribute;
    }

    public void setPeriodAttribute(final AnyURI periodAttribute) {
        this.periodAttribute = periodAttribute;
    }

    public java.util.List<Legislature> getLegislatureElement() {
        return legislatureElements;
    }

    public void setLegislatureElement(final java.util.List<Legislature> legislatureElements) {
        this.legislatureElements = legislatureElements;
    }

}

