package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.AnyURI;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.NMTOKEN;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.StatusType;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.TextualMods;

/**
 * This file is generated.
 */
public class TextualMod extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private TextualMods typeAttribute;
    private Boolean incompleteAttribute;
    private AnyURI refersToAttribute;
    private NMTOKEN evolvingIdAttribute;
    private Boolean exclusionAttribute;
    private StatusType statusAttribute;
    private AnyURI periodAttribute;
    private TextualMod textualModElement;

// CONSTRUCTORS ------------------

    public TextualMod(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public TextualMods getTypeAttribute() {
        return typeAttribute;
    }

    public void setTypeAttribute(final TextualMods typeAttribute) {
        this.typeAttribute = typeAttribute;
    }

    public Boolean isIncompleteAttribute() {
        return incompleteAttribute;
    }

    public void setIncompleteAttribute(final Boolean incompleteAttribute) {
        this.incompleteAttribute = incompleteAttribute;
    }

    public AnyURI getRefersToAttribute() {
        return refersToAttribute;
    }

    public void setRefersToAttribute(final AnyURI refersToAttribute) {
        this.refersToAttribute = refersToAttribute;
    }

    public NMTOKEN getEvolvingIdAttribute() {
        return evolvingIdAttribute;
    }

    public void setEvolvingIdAttribute(final NMTOKEN evolvingIdAttribute) {
        this.evolvingIdAttribute = evolvingIdAttribute;
    }

    public Boolean isExclusionAttribute() {
        return exclusionAttribute;
    }

    public void setExclusionAttribute(final Boolean exclusionAttribute) {
        this.exclusionAttribute = exclusionAttribute;
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

    public TextualMod getTextualModElement() {
        return textualModElement;
    }

    public void setTextualModElement(final TextualMod textualModElement) {
        this.textualModElement = textualModElement;
    }

}

