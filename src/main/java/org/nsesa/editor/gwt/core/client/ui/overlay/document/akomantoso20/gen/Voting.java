package org.nsesa.editor.gwt.core.client.ui.overlay.document.akomantoso20.gen;

import com.google.gwt.dom.client.Element;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.AnyURI;
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.NMTOKEN;

import java.util.ArrayList;

/**
 * This file is generated.
 */
public class Voting extends org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl {

// FIELDS ------------------

    private NMTOKEN evolvingIdAttribute;
    private AnyURI refersToAttribute;
    private AnyURI outcomeAttribute;
    private AnyURI hrefAttribute;
    private java.util.List<QuorumVerification> quorumVerificationElements = new ArrayList<QuorumVerification>();
    private java.util.List<Voting> votingElements = new ArrayList<Voting>();
    private java.util.List<Recount> recountElements = new ArrayList<Recount>();

// CONSTRUCTORS ------------------

    public Voting(final Element amendableElement) {
        super(amendableElement);
    }

// ACCESSORS ------------------

    public NMTOKEN getEvolvingIdAttribute() {
        return evolvingIdAttribute;
    }

    public void setEvolvingIdAttribute(final NMTOKEN evolvingIdAttribute) {
        this.evolvingIdAttribute = evolvingIdAttribute;
    }

    public AnyURI getRefersToAttribute() {
        return refersToAttribute;
    }

    public void setRefersToAttribute(final AnyURI refersToAttribute) {
        this.refersToAttribute = refersToAttribute;
    }

    public AnyURI getOutcomeAttribute() {
        return outcomeAttribute;
    }

    public void setOutcomeAttribute(final AnyURI outcomeAttribute) {
        this.outcomeAttribute = outcomeAttribute;
    }

    public AnyURI getHrefAttribute() {
        return hrefAttribute;
    }

    public void setHrefAttribute(final AnyURI hrefAttribute) {
        this.hrefAttribute = hrefAttribute;
    }

    public java.util.List<QuorumVerification> getQuorumVerificationElement() {
        return quorumVerificationElements;
    }

    public void setQuorumVerificationElement(final java.util.List<QuorumVerification> quorumVerificationElements) {
        this.quorumVerificationElements = quorumVerificationElements;
    }

    public java.util.List<Voting> getVotingElement() {
        return votingElements;
    }

    public void setVotingElement(final java.util.List<Voting> votingElements) {
        this.votingElements = votingElements;
    }

    public java.util.List<Recount> getRecountElement() {
        return recountElements;
    }

    public void setRecountElement(final java.util.List<Recount> recountElements) {
        this.recountElements = recountElements;
    }

}

