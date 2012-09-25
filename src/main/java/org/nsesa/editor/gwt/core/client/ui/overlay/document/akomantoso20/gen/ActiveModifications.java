package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class ActiveModifications extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private java.util.List<ActiveModifications> activeModificationsElements = new ArrayList<ActiveModifications>();
    private java.util.List<PassiveModifications> passiveModificationsElements = new ArrayList<PassiveModifications>();

// CONSTRUCTORS ------------------

    public ActiveModifications(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public java.util.List<ActiveModifications> getActiveModificationsElement() {
        return activeModificationsElements;
    }

    public void setActiveModificationsElement(final java.util.List<ActiveModifications> activeModificationsElements) {
        this.activeModificationsElements = activeModificationsElements;
    }

    public java.util.List<PassiveModifications> getPassiveModificationsElement() {
        return passiveModificationsElements;
    }

    public void setPassiveModificationsElement(final java.util.List<PassiveModifications> passiveModificationsElements) {
        this.passiveModificationsElements = passiveModificationsElements;
    }

}

