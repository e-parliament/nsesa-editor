package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetImpl extends ComplexPanel implements AmendableWidget, HasWidgets {

    /**
     * The underlying DOM element.
     */
    private final Element amendableElement;

    /**
     * A listener for all the amending operations to call back on.
     */
    private AmendableWidgetListener listener;

    /**
     * The logical parent amendable widget. If the parent is null, this is considered a root widget.
     */
    private AmendableWidget parentAmendableWidget;

    /**
     * A list of logical children.
     */
    private List<AmendableWidget> childAmendableWidgets = new ArrayList<AmendableWidget>();

    /**
     * A list of all the amendments on this widget.
     */
    private List<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    /**
     * Flag to indicate whether or not this widget is amendable by the user (not, that does not mean there are no
     * amendments on this element). Note that this value, if set, cascades into its children.
     */
    private Boolean amendable;

    /**
     * Flag to indicate that this widget is immutable. We use this flag to set a single element as non-amendable
     * without having to set each of its children as amendable. Does <strong>not</strong> cascade into its children.
     */
    private Boolean immutable;

    /**
     * The assigned index: this is used to determine the location in case the index is not sufficient to uniquely
     * identify this widget (eg. in case of indents, bullet points ...)
     */
    private Integer assignedIndex;

    /**
     * The holder element for the amendments.
     */
    private Element amendmentHolderElement;

    public AmendableWidgetImpl(Element amendableElement) {
        this.amendableElement = amendableElement;
        setElement(amendableElement);
        // if we're not yet part of the DOM tree, try to attach
        if (!isAttached()) {
            onAttach();
        }
    }

    @Override
    public void addAmendableWidget(final AmendableWidget child) {
        if (!childAmendableWidgets.add(child)) {
            throw new RuntimeException("Child already exists: " + child);
        }
    }

    @Override
    public void removeAmendableWidget(final AmendableWidget child) {
        if (!childAmendableWidgets.remove(child)) {
            throw new RuntimeException("Child widget not found: " + child);
        }
    }

    @Override
    public void addAmendmentController(final AmendmentController amendmentController) {
        if (!amendmentControllers.add(amendmentController)) {
            throw new RuntimeException("Amendment already exists: " + amendmentController);
        }
        // physical attach
        add(amendmentController.getView().asWidget(), (com.google.gwt.user.client.Element) getAmendmentHolderElement());
    }

    @Override
    public void removeAmendmentController(final AmendmentController amendmentController) {
        if (!amendmentControllers.remove(amendmentController)) {
            throw new RuntimeException("Child widget not found: " + amendmentController);
        }
        // physical remove
        remove(amendmentController);
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
    public String getType() {
        return amendableElement.getTagName();
    }

    @Override
    public String getId() {
        return amendableElement.getId();
    }

    @Override
    public AmendableWidget[] getParentAmendableWidgets() {
        final ArrayList<AmendableWidget> parents = new ArrayList<AmendableWidget>();
        AmendableWidget parent = getParentAmendableWidget();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParentAmendableWidget();
        }
        Collections.reverse(parents);
        return parents.toArray(new AmendableWidget[parents.size()]);
    }

    @Override
    public AmendableWidget[] getChildAmendableWidgets() {
        return childAmendableWidgets.toArray(new AmendableWidget[childAmendableWidgets.size()]);
    }

    @Override
    public AmendableWidget getParentAmendableWidget() {
        return parentAmendableWidget;
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

    public Element getAmendmentHolderElement() {
        if (amendmentHolderElement == null) {
            amendmentHolderElement = DOM.createDiv();
            if (childAmendableWidgets.isEmpty()) {
                // add it at the end
                amendableElement.appendChild(amendmentHolderElement);
            } else {
                // add it just before the first child
                final AmendableWidget firstChild = childAmendableWidgets.get(0);
                Log.info("First child " + firstChild);
                final int childIndex = DOM.getChildIndex((com.google.gwt.user.client.Element) amendableElement.getParentElement(), firstChild.asWidget().getElement());
                Log.info("Insert at childIndex " + childIndex);
                DOM.insertChild((com.google.gwt.user.client.Element) amendableElement.getParentElement(),
                        (com.google.gwt.user.client.Element) amendmentHolderElement, childIndex - 1);
            }
        }
        return amendmentHolderElement;
    }
}
