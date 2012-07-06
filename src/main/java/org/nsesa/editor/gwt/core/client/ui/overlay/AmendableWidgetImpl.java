package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetImpl extends ComplexPanel implements AmendableWidget, HasWidgets {

    private final Element amendableElement;

    private AmendableWidgetListener listener;

    private AmendableWidget parentAmendableWidget;

    private List<AmendableWidget> children = new ArrayList<AmendableWidget>();

    private Boolean amendable, immutable;

    private Integer assignedIndex;

    private Element actionBarHolderElement, amendmentHolderElement;

    public AmendableWidgetImpl(Element amendableElement) {
        this.amendableElement = amendableElement;
        setElement(amendableElement);
        // if we're not yet part of the DOM tree, try to attach
        if (!isAttached()) {
            onAttach();
        }
    }

    @Override
    public void addAmendableWidget(AmendableWidget child) {
        if (!children.add(child)) {
            throw new RuntimeException("Child already exists: " + child);
        }
    }

    @Override
    public void setListener(AmendableWidgetListener listener) {
        this.listener = listener;
        // register a listener for the browser events
        sinkEvents(Event.ONCLICK | Event.ONDBLCLICK | Event.ONMOUSEMOVE);
    }

    @Override
    public void onBrowserEvent(Event event) {
        // don't let events bubble up or you'd get parent widgets being invoked as well
        event.stopPropagation();
        if (listener != null) {
            switch (DOM.eventGetType(event)) {
                case Event.ONCLICK:
                    listener.onClick(this);
                    break;
                case Event.ONDBLCLICK:
                    listener.onDblClick(this);
                    break;
                case Event.ONMOUSEMOVE:
                    listener.onMouseOver(this);
                    break;
                case Event.ONMOUSEOUT:
                    listener.onMouseOut(this);
                    break;
            }
        }
    }

    @Override
    public void setParentAmendableWidget(AmendableWidget parent) {
        this.parentAmendableWidget = parent;
    }

    @Override
    public void postProcess() {

    }

    @Override
    public String toString() {
        return "[Element " + amendableElement.getNodeName() + "]";
    }

    @Override
    public Boolean isAmendable() {
        // has been set explicitly
        if (amendable != null) return amendable;
        // walk the parents until we find one that has been set, or default to true for the root
        return parentAmendableWidget != null ? parentAmendableWidget.isAmendable() : true;
    }

    @Override
    public void setAmendable(Boolean amendable) {
        this.amendable = amendable;
    }

    @Override
    public Boolean isImmutable() {
        // has been set explicitly
        if (immutable != null) return immutable;
        // walk the parents until we find one that has been set, or default to false for the root
        return parentAmendableWidget != null ? parentAmendableWidget.isImmutable() : false;
    }

    @Override
    public void setImmutable(Boolean immutable) {
        this.immutable = immutable;
    }
}
