package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.LocatorUtil;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

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
     * A listener for all the UI operations to call back on.
     */
    private AmendableWidgetUIListener UIListener;

    /**
     * A listener for all the amending operations to call back on.
     */
    private AmendableWidgetListener listener;

    /**
     * Supports lazy retrieval of properties
     */
    private OverlayStrategy overlayStrategy;

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

    private String id;

    private AmendableWidgetOrigin origin;

    /**
     * Flag to indicate that this widget is immutable. We use this flag to set a single element as non-amendable
     * without having to set each of its children as amendable. Does <strong>not</strong> cascade into its children.
     */
    private Boolean immutable;

    private Format format;

    private NumberingType numberingType;

    /**
     * The assigned index: this is used to determine the location in case the index is not sufficient to uniquely
     * identify this widget (eg. in case of indents, bullet points ...)
     * Note: this number is <strong>1-based</strong>!!
     */
    private Integer assignedNumber;

    /**
     * Contains the unformatted index of this amendable widget (eg. 'a', '2', '-', ...)
     */
    private String unformattedIndex;

    /**
     * Contains the formatted, original index of this amendable widget (eg. 'a)', '(2)', '-', '', ...)
     */
    private String formattedIndex;

    /**
     * The holder element for the amendments.
     */
    private HTMLPanel amendmentHolderElement;

    public AmendableWidgetImpl() {
    }

    public AmendableWidgetImpl(Element amendableElement) {
        setElement(amendableElement);
        // if we're not yet part of the DOM tree, try to attach
        if (!isAttached()) {
            onAttach();
        }
    }

    @Override
    public void setOverlayStrategy(OverlayStrategy overlayStrategy) {
        this.overlayStrategy = overlayStrategy;
    }

    @Override
    public void addAmendableWidget(final AmendableWidget child) {
        addAmendableWidget(child, -1);
    }

    @Override
    public void addAmendableWidget(AmendableWidget child, int index) {
        addAmendableWidget(child, index, false);
    }

    @Override
    public void onAttach() {
        super.onAttach();
    }

    @Override
    public void onDetach() {

        if (childAmendableWidgets != null) {
            for (final AmendableWidget child : childAmendableWidgets) {
                if (child.isAttached()) {
                    child.onDetach();
                }
            }
        }
        if (isAttached()) {
            super.onDetach();
            this.listener = null;
            this.overlayStrategy = null;
            this.origin = null;
            this.amendable = null;
            this.amendmentControllers = null;
            this.assignedNumber = null;
            this.amendmentHolderElement = null;
            this.childAmendableWidgets = null;
            this.format = null;
            this.id = null;
            this.immutable = null;
            this.type = null;
            this.UIListener = null;
        }
    }

    @Override
    public void addAmendableWidget(final AmendableWidget child, int index, boolean skipValidation) {
        if (child == null) throw new NullPointerException("Cannot add null child!");
        boolean vetoed = false;
        if (listener != null)
            vetoed = listener.beforeAmendableWidgetAdded(this, child);

        if (!vetoed) {
            if (!skipValidation) {
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

                // if our child was somehow still connected to another widget, then clear this reference first
                if (child.getParentAmendableWidget() != null) {
                    if (child.getParentAmendableWidget().getChildAmendableWidgets().contains(child)) {
                        child.getParentAmendableWidget().removeAmendableWidget(child);
                    }
                }
            }
            if (index == -1) {
                if (!childAmendableWidgets.add(child)) {
                    throw new RuntimeException("Child already exists: " + child.getType());
                }
            } else {
                if (index > childAmendableWidgets.size()) {
                    throw new RuntimeException("Could not insert child amendable widget at index " + index +
                            " because the size of the amendable children is only " + childAmendableWidgets.size());
                }
                childAmendableWidgets.add(index, child);
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
            if (amendmentControllers.contains(amendmentController)) {
                throw new RuntimeException("Amendment already exists: " + amendmentController);
            }
            if (!amendmentControllers.add(amendmentController)) {
                throw new RuntimeException("Could not add amendment controller: " + amendmentController);
            }

            // physical attach
            final HTMLPanel holderElement = getAmendmentHolderElement();
            if (holderElement != null) {
                holderElement.add(amendmentController.getView());
                // set up a reference to this widget
                amendmentController.setAmendedAmendableWidget(this);
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
            if (!amendmentControllers.contains(amendmentController)) {
                throw new RuntimeException("Amendment controller not found: " + amendmentController);
            }
            if (!amendmentControllers.remove(amendmentController)) {
                throw new RuntimeException("Could not remove amendment controller: " + amendmentController);
            }

            // physical remove
            amendmentController.getView().asWidget().removeFromParent();
            amendmentController.getExtendedView().asWidget().removeFromParent();

            // clear reference to this widget
            amendmentController.setAmendedAmendableWidget(null);

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
     *
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
        if (overlayStrategy == null) return id;
        if (id == null) {
            id = overlayStrategy.getID(getElement());
        }
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getInnerHTML() {
        final NodeList<Node> childNodes = getElement().getChildNodes();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node node = childNodes.getItem(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = node.cast();
                if (element != getAmendmentHolderElement().getElement()) {
                    sb.append(DOM.toString((com.google.gwt.user.client.Element) element));
                }
            } else if (Node.TEXT_NODE == node.getNodeType()) {
                sb.append(node.getNodeValue());
            }
        }
        return sb.toString();
    }

    @Override
    public void setInnerHTML(String amendableContent) {
        // TODO make sure to restore the amendments if this is called
        if (isAmended()) {
            throw new RuntimeException("[TODO] We're not yet restoring amendments when the inner HTML is set.");
        }
        getElement().setInnerHTML(amendableContent);
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

    public AmendableWidget getPreviousNonIntroducedAmendableWidget(final boolean sameType) {
        // short circuit if we're already the top element
        if (getIndex() == 0) return null;
        for (final AmendableWidget amendableWidget : parentAmendableWidget.getChildAmendableWidgets()) {
            if (!amendableWidget.isIntroducedByAnAmendment()) {
                if (sameType && amendableWidget.getTypeIndex() == getTypeIndex() - 1) {
                    return amendableWidget;
                } else if (!sameType && amendableWidget.getIndex() == getIndex() - 1) {
                    return amendableWidget;
                }
            }
        }
        return null;
    }

    public AmendableWidget getNextNonIntroducedAmendableWidget(final boolean sameType) {
        // short circuit if we're already the last element
        if (getIndex() == parentAmendableWidget.getChildAmendableWidgets().size() - 1) return null;
        for (final AmendableWidget amendableWidget : parentAmendableWidget.getChildAmendableWidgets()) {
            if (!amendableWidget.isIntroducedByAnAmendment()) {
                if (sameType && amendableWidget.getTypeIndex() == getTypeIndex() + 1) {
                    return amendableWidget;
                } else if (!sameType && amendableWidget.getIndex() == getIndex() + 1) {
                    return amendableWidget;
                }
            }
        }
        return null;
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
    public boolean isAmended() {
        return !amendmentControllers.isEmpty();
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
                // TODO: this holder should not be necessary ... should be added directly to to the element.
                amendmentHolderElement = new HTMLPanel("");
                amendmentHolderElement.getElement().setClassName("amendments");
                getElement().insertBefore(amendmentHolderElement.getElement(), null);
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
     *
     * @return the list of allowed child types in lower case. Should never return <tt>null</tt> (an empty array is ok though).
     */
    @Override
    public String[] getAllowedChildTypes() {
        throw new UnsupportedOperationException("Should have been overridden in the subclasses.");
    }

    @Override
    public Format getFormat() {
        if (overlayStrategy == null) return format;
        if (format == null) {
            format = overlayStrategy.getFormat(getElement());
        }
        return format;
    }

    @Override
    public void setFormat(final Format format) {
        this.format = format;
    }

    @Override
    public NumberingType getNumberingType() {
        if (overlayStrategy == null) return numberingType;
        if (numberingType == null) {
            numberingType = overlayStrategy.getNumberingType(getElement(), getAssignedNumber());
        }
        return numberingType;
    }

    @Override
    public void setNumberingType(final NumberingType numberingType) {
        this.numberingType = numberingType;
    }

    @Override
    public Integer getAssignedNumber() {
        if (assignedNumber == null) {
            assignedNumber = LocatorUtil.getAssignedNumber(this);
        }
        return assignedNumber;
    }

    @Override
    public void setAssignedNumber(final Integer assignedNumber) {
        this.assignedNumber = assignedNumber;
    }

    @Override
    public LinkedHashMap<String, String> getAttributes() {
        return new LinkedHashMap<String, String>();
    }

    @Override
    public void setOrigin(AmendableWidgetOrigin origin) {
        this.origin = origin;
    }

    @Override
    public AmendableWidgetOrigin getOrigin() {
        return origin;
    }

    public String getFormattedIndex() {
        if (overlayStrategy == null) return formattedIndex;
        if (formattedIndex == null) {
            formattedIndex = overlayStrategy.getFormattedIndex(getElement());
        }
        return formattedIndex;
    }

    public void setFormattedIndex(String formattedIndex) {
        this.formattedIndex = formattedIndex;
    }

    public String getUnformattedIndex() {
        if (overlayStrategy == null) return unformattedIndex;
        if (unformattedIndex == null) {
            unformattedIndex = overlayStrategy.getUnFormattedIndex(getElement());
        }
        return unformattedIndex;
    }

    public void setUnformattedIndex(String unformattedIndex) {
        this.unformattedIndex = unformattedIndex;
    }

    /**
     * Check if this amendable widget was created by an amendment. Will traverse upwards if the origin was not specified.
     *
     * @return <tt>true</tt> if this widget was introduced by an amendment.
     */
    public boolean isIntroducedByAnAmendment() {
        return origin != null ? origin == AmendableWidgetOrigin.AMENDMENT : getParentAmendableWidget() != null && getParentAmendableWidget().isIntroducedByAnAmendment();
    }

    @Override
    public Element getAmendableElement() {
        return getElement();
    }

    @Override
    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers.toArray(new AmendmentController[amendmentControllers.size()]);
    }

    @Override
    public int getTypeIndex() {
        if (getParentAmendableWidget() != null) {
            final Iterator<AmendableWidget> iterator = getParentAmendableWidget().getChildAmendableWidgets().iterator();
            int count = 0;
            while (iterator.hasNext()) {
                final AmendableWidget aw = iterator.next();
                if (aw != null) {

                    if (aw == this) {
                        break;
                    }
                    if (!aw.isIntroducedByAnAmendment() && aw.getType().equalsIgnoreCase(getType())) {
                        count++;
                    }
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public int getIndex() {
        if (getParentAmendableWidget() != null) {
            final Iterator<AmendableWidget> iterator = getParentAmendableWidget().getChildAmendableWidgets().iterator();
            int count = 0;
            while (iterator.hasNext()) {
                final AmendableWidget aw = iterator.next();
                if (aw != null) {

                    if (aw == this) {
                        break;
                    }
                    if (!aw.isIntroducedByAnAmendment()) {
                        count++;
                    }
                }
            }
            return count;
        }
        return -1;
    }

    // DSL Way
    public String html() {
        return getInnerHTML();
    }

    public AmendableWidgetImpl html(String s) {
        setInnerHTML(s);
        return this;
    }

    /**
     * Returns the namespace URI of this amendable widget.
     */
    @Override
    public String getNamespaceURI() {
        throw new NullPointerException("Should be overridden by subclass.");
    }

    @Override
    public void walk(AmendableVisitor visitor) {
        walk(this, visitor);
    }

    @Override
    public void walk(AmendableWidget toVisit, AmendableVisitor visitor) {
        if (visitor.visit(toVisit)) {
            if (toVisit != null) {
                for (final AmendableWidget child : toVisit.getChildAmendableWidgets()) {
                    walk(child, visitor);
                }
            }
        }
    }
}
