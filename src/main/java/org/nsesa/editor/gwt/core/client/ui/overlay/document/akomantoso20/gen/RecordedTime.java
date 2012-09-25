package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.*;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class RecordedTime extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private Language langAttribute;
    private TimeType typeAttribute;
    private NMTOKEN evolvingIdAttribute;
    private AnyURI alternativeToAttribute;
    private AnyURI refersToAttribute;
    private String styleAttribute;
    private String classAttribute;
    private String titleAttribute;
    private StatusType statusAttribute;
    private DateTime timeAttribute;
    private AnyURI periodAttribute;
    private java.util.List<RecordedTime> recordedTimeElements = new ArrayList<RecordedTime>();

// CONSTRUCTORS ------------------

    public RecordedTime(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public Language getLangAttribute() {
        return langAttribute;
    }

    public void setLangAttribute(final Language langAttribute) {
        this.langAttribute = langAttribute;
    }

    public TimeType getTypeAttribute() {
        return typeAttribute;
    }

    public void setTypeAttribute(final TimeType typeAttribute) {
        this.typeAttribute = typeAttribute;
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

    public DateTime getTimeAttribute() {
        return timeAttribute;
    }

    public void setTimeAttribute(final DateTime timeAttribute) {
        this.timeAttribute = timeAttribute;
    }

    public AnyURI getPeriodAttribute() {
        return periodAttribute;
    }

    public void setPeriodAttribute(final AnyURI periodAttribute) {
        this.periodAttribute = periodAttribute;
    }

    public java.util.List<RecordedTime> getRecordedTimeElement() {
        return recordedTimeElements;
    }

    public void setRecordedTimeElement(final java.util.List<RecordedTime> recordedTimeElements) {
        this.recordedTimeElements = recordedTimeElements;
    }

}

