package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.allen_sauer.gwt.log.client.Log;
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
    private final AmendableElement amendableElement;

    private AmendableWidgetListener listener;

    public AmendableWidget parentAmendableWidget;

    public List<AmendableWidget> children;

    Boolean amendable;

    public AmendableWidgetImpl(AmendableElement amendableElement) {
        this.amendableElement = amendableElement;
        setElement(amendableElement);
    }

    @Override
    public void addAmendableWidget(AmendableWidget child) {
        if (children == null) children = new ArrayList<AmendableWidget>();
        if (!children.add(child)) {
            throw new RuntimeException("Child already exists: " + child);
        }
    }

    @Override
    public boolean isAmendable() {
        if (amendable != null) {
            return amendable;
        }
        if (amendableElement == null) throw new RuntimeException("AmendableElement not set --BUG");
        final Boolean isAmendable = amendableElement.isAmendable();
        amendable = isAmendable != null ? isAmendable : true;
        return amendable;
    }

    @Override
    public void setListener(AmendableWidgetListener listener) {
        this.listener = listener;
        try {
            sinkEvents(Event.ONCLICK | Event.ONDBLCLICK | Event.ONMOUSEMOVE);
        } catch (Exception e) {
            Log.error("Could not register listener.", e);
        }
    }

    @Override
    public void onBrowserEvent(Event event) {
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
    public void onAttach() {
        super.onAttach();
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
        return "[Element " + amendableElement.asString() + "]";
    }
}
