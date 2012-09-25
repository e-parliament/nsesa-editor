package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.AnyURI;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class TemporalData extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private AnyURI sourceAttribute;
    private java.util.List<TemporalData> temporalDataElements = new ArrayList<TemporalData>();

// CONSTRUCTORS ------------------

    public TemporalData(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public AnyURI getSourceAttribute() {
        return sourceAttribute;
    }

    public void setSourceAttribute(final AnyURI sourceAttribute) {
        this.sourceAttribute = sourceAttribute;
    }

    public java.util.List<TemporalData> getTemporalDataElement() {
        return temporalDataElements;
    }

    public void setTemporalDataElement(final java.util.List<TemporalData> temporalDataElements) {
        this.temporalDataElements = temporalDataElements;
    }

}

