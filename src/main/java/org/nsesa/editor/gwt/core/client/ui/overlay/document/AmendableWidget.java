package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidget extends IsWidget, HasWidgets {

    /**
     * Check if this amendable widget is amendable or not.
     *
     * @return true if it is amendable, or null if it is not defined.
     */
    Boolean isAmendable();

    /**
     * Sets the flag whether or not this amendment is considered amendable (that is,
     * new amendments can be created by the user). Note that you can also
     *
     * @param amendable flag whether or not this widget is amendable - or null if it should inherit it from its
     *                  ancestor.
     */
    void setAmendable(Boolean amendable);

    /**
     * If a widget is immutable, it will not be possible amend this.
     *
     * @return true if this widget is immutable, or <tt>null</tt> if it has not been specified.
     */
    Boolean isImmutable();

    /**
     * Specify whether or not this widget is immutable.
     *
     * @param immutable true if this widget should be immutable, or <tt>null</tt> if it should inherit it from its
     *                  parent.
     */
    void setImmutable(Boolean immutable);

    /**
     * Sets a listener on this widget that will receive callbacks on various UI events suchs as clicks, hoovering, ...
     *
     * @param UIListener the listener to set.
     */
    void setUIListener(AmendableWidgetUIListener UIListener);

    void setListener(AmendableWidgetListener listener);

    /**
     * Sets the logical parent amendable widget - not that this is not necessarily the actual parent element!
     *
     * @param parent the parent to set.
     */
    void setParentAmendableWidget(AmendableWidget parent);

    /**
     * Returns the listing of all the parents amendable widgets, sorted descending by distance to this widget (so
     * first the root, then grandfather, father, ...).
     *
     * @return the list of parent amendable widgets - should not return <tt>null</tt>
     */
    List<AmendableWidget> getParentAmendableWidgets();

    /**
     * Returns the listing of all amendable children, sorted by their appearance.
     *
     * @return the list of amendable child widgets
     */
    List<AmendableWidget> getChildAmendableWidgets();

    /**
     * Get the direct parent amendable widget.
     *
     * @return the parent, or <tt>null</tt> if this is a root widget
     */
    AmendableWidget getParentAmendableWidget();

    /**
     * Returns the widget root - if this is not a root itself, it will reverse the tree until a root element is found.
     *
     * @return the root widget which contains this widget.
     */
    AmendableWidget getRoot();

    /**
     * Add an amendable widget as a child.
     *
     * @param child the child to add.
     */
    void addAmendableWidget(AmendableWidget child);

    /**
     * Remove an amendable child. Throws an exception if the passed widget is not an actual child (that is,
     * {@link #getChildAmendableWidgets()} contains the widget).
     *
     * @param child the child widget to remove.
     */
    void removeAmendableWidget(AmendableWidget child);

    /**
     * Add an amendment controller to this amendable widget.
     *
     * @param amendmentController the amendment controller to add.
     */
    void addAmendmentController(AmendmentController amendmentController);

    /**
     * Remove an amendment controller from this amendable widget.
     *
     * @param amendmentController the amendment controller to remove.
     */
    void removeAmendmentController(AmendmentController amendmentController);

    /**
     * Returns the type of the amendable widget (defaults to the local node name - so without prefix, if any).
     * @return  the type
     */
    String getType();

    void setType(String type);

    String getId();

    void setId(String id);

    String getContent();

    void setContent(String amendableContent);

    HTMLPanel getAmendmentHolderElement();

    String[] getAllowedChildTypes();

    NumberingType getNumberingType();

    void setNumberingType(NumberingType numberingType);

    Format getFormat();

    void setFormat(Format format);

    Integer getAssignedNumber();

    void setAssignedNumber(Integer assignedNumber);

    LinkedHashMap<String, String> getAttributes();

    void setOrigin(AmendableWidgetOrigin origin);

    AmendableWidgetOrigin getOrigin();

    boolean isIntroducedByAnAmendment();
}
