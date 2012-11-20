package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

import java.util.*;
import java.util.logging.Logger;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetImpl extends ComplexPanel implements AmendableWidget, HasWidgets {

    private static final Logger LOG = Logger.getLogger(AmendableWidgetImpl.class.getName());

    public static final boolean DEFAULT_ROOT_WIDGET_AMENDABLE = true;

    /**
     * The underlying DOM element.
     */
    private transient Element amendableElement;

    /**
     * A listener for all the UI operations to call back on.
     */
    private AmendableWidgetUIListener UIListener;

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

    private String type;

    /**
     * Flag to indicate that this widget is immutable. We use this flag to set a single element as non-amendable
     * without having to set each of its children as amendable. Does <strong>not</strong> cascade into its children.
     */
    private Boolean immutable;

    private String amendableContent;

    private Format format;

    private NumberingType numberingType;

    /**
     * The assigned index: this is used to determine the location in case the index is not sufficient to uniquely
     * identify this widget (eg. in case of indents, bullet points ...)
     * Note: this number is <strong>1-based</strong>!!
     */
    private Integer assignedNumber;

    /**
     * The holder element for the amendments.
     */
    private HTMLPanel amendmentHolderElement;

    public AmendableWidgetImpl() {
    }

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
        if (child == null) throw new NullPointerException("Cannot add null child!");
        boolean vetoed = false;
        if (listener != null)
            vetoed = listener.beforeAmendableWidgetAdded(this, child);

        if (!vetoed) {
            // see if there is a wildcard
            if (Arrays.binarySearch(getAllowedChildTypes(), "*") == -1) {
                // no wildcard - see if the type is supported as a child widget
                boolean canAdd = false;
                for (String type : getAllowedChildTypes()) {
                    if (type.equalsIgnoreCase(child.getType())) {
                        canAdd = true;
                    }
                }
                if (!canAdd) {
                    LOG.warning(getType() + " does not support child type:" + child);
                }
            }

            if (!childAmendableWidgets.add(child)) {
                throw new RuntimeException("Child already exists: " + child.getType());
            }
            child.setParentAmendableWidget(this);
            // inform the listener
            if (listener != null) listener.afterAmendableWidgetAdded(this, child);
        } else {
            LOG.info("AmendableWidget listener veto'ed the adding of the amendable widget.");
        }
    }

    @Override
    public void removeAmendableWidget(final AmendableWidget child) {
        if (child == null) throw new NullPointerException("Cannot remove null child!");

        boolean vetoed = false;
        if (listener != null)
            vetoed = listener.beforeAmendableWidgetAdded(this, child);

        if (!vetoed) {
            if (!childAmendableWidgets.remove(child)) {
                throw new RuntimeException("Child widget not found: " + child);
            }
            child.setParentAmendableWidget(null);
            // inform the listener
            if (listener != null) listener.afterAmendableWidgetRemoved(this, child);
        } else {
            LOG.info("AmendableWidget listener veto'ed the removal of the amendable widget.");
        }
    }

    @Override
    public void addAmendmentController(final AmendmentController amendmentController) {
        if (amendmentController == null) throw new NullPointerException("Cannot add null amendment controller!");

        boolean vetoed = false;
        if (listener != null)
            vetoed = listener.beforeAmendmentControllerAdded(this, amendmentController);

        if (!vetoed) {
            if (!amendmentControllers.add(amendmentController)) {
                throw new RuntimeException("Amendment already exists: " + amendmentController);
            }

            // physical attach
            final HTMLPanel holderElement = getAmendmentHolderElement();
            if (holderElement != null) {
                holderElement.add(amendmentController.getView());
                // set up a reference to this widget
                amendmentController.setParentAmendableWidget(this);
                // inform the listener
                if (listener != null) listener.afterAmendmentControllerAdded(this, amendmentController);
            } else {
                LOG.severe("No amendment holder panel could be added for this widget " + this);
            }
        } else {
            LOG.info("AmendableWidget listener veto'ed the adding of the amendment controller.");
        }
    }

    @Override
    public void removeAmendmentController(final AmendmentController amendmentController) {
        if (amendmentController == null) throw new NullPointerException("Cannot remove null amendment controller!");

        boolean vetoed = false;
        if (listener != null)
            vetoed = listener.beforeAmendmentControllerRemoved(this, amendmentController);

        if (!vetoed) {
            if (!amendmentControllers.remove(amendmentController)) {
                throw new RuntimeException("Amendment controller not found: " + amendmentController);
            }

            // physical remove
            remove(amendmentController.getView());
            // clear reference to this widget
            amendmentController.setParentAmendableWidget(null);

            // inform the listener
            if (listener != null) listener.afterAmendmentControllerRemoved(this, amendmentController);
        } else {
            LOG.info("AmendableWidget listener veto'ed the removal of the amendment controller.");
        }
    }

    @Override
    public void setUIListener(final AmendableWidgetUIListener UIListener) {
        this.UIListener = UIListener;
        if (UIListener != null) {
            // register a listener for the browser events
            sinkEvents(Event.ONKEYDOWN | Event.ONCLICK | Event.ONDBLCLICK | Event.ONMOUSEMOVE);
        }
    }

    @Override
    public void setListener(AmendableWidgetListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBrowserEvent(final Event event) {
        // don't let events bubble up or you'd get parent widgets being invoked as well
        event.stopPropagation();
        if (UIListener != null) {
            switch (DOM.eventGetType(event)) {
                case Event.ONCLICK:
                    UIListener.onClick(this);
                    break;
                case Event.ONDBLCLICK:
                    UIListener.onDblClick(this);
                    break;
                case Event.ONMOUSEMOVE:
                    UIListener.onMouseOver(this);
                    break;
                case Event.ONMOUSEOUT:
                    UIListener.onMouseOut(this);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown event.");
            }
        }
    }

    @Override
    public void setParentAmendableWidget(AmendableWidget parent) {
        this.parentAmendableWidget = parent;
    }

    /**
     * Returns the type (its local name, meaning the element without any prefix) in lowercase.
     * @return
     */
    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getId() {
        return amendableElement.getId();
    }

    @Override
    public String getContent() {
        return amendableContent;
    }

    @Override
    public void setContent(String amendableContent) {
        this.amendableContent = amendableContent;
    }

    @Override
    public List<AmendableWidget> getParentAmendableWidgets() {
        final List<AmendableWidget> parents = new ArrayList<AmendableWidget>();
        AmendableWidget parent = getParentAmendableWidget();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParentAmendableWidget();
        }
        Collections.reverse(parents);
        return parents;
    }

    @Override
    public List<AmendableWidget> getChildAmendableWidgets() {
        return childAmendableWidgets;
    }

    @Override
    public AmendableWidget getParentAmendableWidget() {
        return parentAmendableWidget;
    }

    @Override
    public AmendableWidget getRoot() {
        return getParentAmendableWidget() != null ? getParentAmendableWidget() : this;
    }

    @Override
    public String toString() {
        return "[Element " + type + "]";
    }

    /**
     * Check if this widget is amendable or not in the editor. If it has not been specified
     * (when {@code amendable == null}), then we will go up in the tree until the root widget has been found,
     * in which case the default {@link #DEFAULT_ROOT_WIDGET_AMENDABLE} is returned.
     *
     * @return true if the amendment is amendable, false otherwise. Should never return <tt>null</tt>.
     */
    @Override
    public Boolean isAmendable() {
        // has been set explicitly
        if (amendable != null) return amendable;
        // walk the parents until we find one that has been set, or default to DEFAULT_ROOT_WIDGET_AMENDABLE for the root
        return parentAmendableWidget != null ? parentAmendableWidget.isAmendable() : DEFAULT_ROOT_WIDGET_AMENDABLE;
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

    @Override
    public HTMLPanel getAmendmentHolderElement() {
        if (amendmentHolderElement == null) {
            if (getElement().getParentElement() != null) {
                amendmentHolderElement = new HTMLPanel("Amendments<hr/>");
                getElement().insertFirst(amendmentHolderElement.getElement());
                adopt(amendmentHolderElement);
                assert amendmentHolderElement.isAttached() : "Amendment Holder element is not attached.";
            }
        }
        return amendmentHolderElement;
    }

    /**
     * Returns an array of the node names that are allowed to be nested.
     * Note: this can include wildcards (*).
     * The default implementation throws an exception, since this method is supposed to be overridden.
     * @return the list of allowed child types in lower case. Should never return <tt>null</tt> (an empty array is ok though).
     */
    @Override
    public String[] getAllowedChildTypes() {
        throw new UnsupportedOperationException("Should have been overridden in the subclasses.");
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(final Format format) {
        this.format = format;
    }

    public NumberingType getNumberingType() {
        return numberingType;
    }

    public void setNumberingType(final NumberingType numberingType) {
        this.numberingType = numberingType;
    }

    public Integer getAssignedNumber() {
        return assignedNumber;
    }

    public void setAssignedNumber(final Integer assignedNumber) {
        this.assignedNumber = assignedNumber;
    }

    @Override
    public LinkedHashMap<String, String> getAttributes() {
        return new LinkedHashMap<String, String>();
    }
}
