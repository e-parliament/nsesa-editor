package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.AnyURI;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class Notes extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private AnyURI sourceAttribute;
    private java.util.List<Notes> notesElements = new ArrayList<Notes>();

// CONSTRUCTORS ------------------

    public Notes(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public AnyURI getSourceAttribute() {
        return sourceAttribute;
    }

    public void setSourceAttribute(final AnyURI sourceAttribute) {
        this.sourceAttribute = sourceAttribute;
    }

    public java.util.List<Notes> getNotesElement() {
        return notesElements;
    }

    public void setNotesElement(final java.util.List<Notes> notesElements) {
        this.notesElements = notesElements;
    }

}

