package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import org.nsesa.editor.gwt.core.client.amendment.AmendableWidgetWalker;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 27/06/12 17:52
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidget extends IsWidget, HasWidgets, AmendableWidgetWalker {

    /**
     * Check if this amendable widget is amendable or not.
     *
     * @return true if it is amendable, or null if it is not defined.
     */
    Boolean isAmendable();

    /**
     * Check if this widget has any amendments.
     *
     * @return true if the widget has any amendments on it.
     */
    boolean isAmended();

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

    AmendableWidget getPreviousSibling();

    AmendableWidget getNextSibling();

    /**
     * Find the previous sibling that is not introduced by an amendment.
     *
     * @param sameType if true, only look for an amendable widget of the same type
     * @return the sibling, or null if it cannot be found
     */
    AmendableWidget getPreviousNonIntroducedAmendableWidget(boolean sameType);

    /**
     * Find the next sibling that is not introduced by an amendment.
     *
     * @param sameType if true, only look for an amendable widget of the same type
     * @return the sibling, or null if it cannot be found
     */
    AmendableWidget getNextNonIntroducedAmendableWidget(boolean sameType);

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
     * Add an amendable widget as a child at the given index.
     *
     * @param child the child to add.
     * @param index the index where to add the child (-1 means at the end)
     */
    void addAmendableWidget(AmendableWidget child, int index);

    void onAttach();

    boolean isAttached();

    void onDetach();

    /**
     * Add an amendable widget as a child at position <tt>index</tt>, but do not perform a runtime validation check.
     *
     * @param child          the child to add
     * @param index          the position to insert the widget at (-1 means it will be added at the end)
     * @param skipValidation <tt>true</tt> to skip validation.
     */
    void addAmendableWidget(AmendableWidget child, int index, boolean skipValidation);

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
     *
     * @return the type
     */
    String getType();

    /**
     * Set the type of this amendable widget. You are normally not supposed to call this method directly (it will be
     * set by the {@link OverlayFactory}.
     *
     * @param type the type of this amendable widget (eg. chapter, speech, b, ...)
     */
    void setType(String type);

    /**
     * Get the id of this amendable widget, if any.
     *
     * @return the id of this widget.
     */
    String getId();

    /**
     * Set the id of this amendable widget. Must be unique (we won't check for this, so it is your responsibility).
     *
     * @param id the id of this widget.
     */
    void setId(String id);

    /**
     * Get the child content (text + other elements) for this amendable widget.
     *
     * @return the child nodes serialized as a <tt>String</tt>.
     */
    String getInnerHTML();

    /**
     * Set the text content of this amendable widget. While this technically could contain tags, you are advised
     * to properly escape any content you wish to set.
     *
     * @param amendableContent the text content for this node.
     */
    void setInnerHTML(String amendableContent);

    /**
     * Get the HTMLPanel to attach the amendments on. If it does not yet exist, it will be created and attached
     * to the document.
     *
     * @return the HTML panel with the amendments, if any.
     */
    HTMLPanel getAmendmentHolderElement();

    /**
     * Get a map of the element node names that are allowed under this amendable widget (note, this also includes the
     * wildcard, if this was specified in the XSD). Because of their casing, make sure to do a case-insensitive comparison.
     *
     * @return the allowed child nodes
     */
    Map<AmendableWidget, Occurrence> getAllowedChildTypes();

    /**
     * Get the numbering type of this amendable widget. If it was not set using {@link #setNumberingType(org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType)},
     * then it will be guessed.
     *
     * @return the numbering type, if any.
     */
    NumberingType getNumberingType();

    /**
     * Set the numbering type on this amendable widget (eg. '1', 'A', '-', ...). Typically used in conjunction with
     * {@link #getFormat()} to format the literal index.
     *
     * @param numberingType the numbering type to use
     */
    void setNumberingType(NumberingType numberingType);

    /**
     * Get the format for this amendable widget. If it was not set using {@link #setFormat(org.nsesa.editor.gwt.core.client.ui.overlay.Format)},
     * then the format will be guessed.
     *
     * @return the format for this literal index, if any.
     */
    Format getFormat();

    /**
     * Set the format on this amendable widget (eg. ')', '()', '.', ...). The format is typically used in conjunction with the {@link #getNumberingType()}
     * to create the literal index.
     *
     * @param format the formatter to use for the literal index.
     */
    void setFormat(Format format);

    /**
     * Get the assigned number, or <tt>null</tt> if no number was assigned. Note that this is 1-based.
     *
     * @return the assigned number.
     */
    Integer getAssignedNumber();

    /**
     * Set the assigned number (1-based). This assigned number is then used for location determination in case no
     * num element is provided.
     *
     * @param assignedNumber the assigned number
     */
    void setAssignedNumber(Integer assignedNumber);

    /**
     * Get a map of all the attributes, where the key is the attribute name, and the value is the attribute's value.
     *
     * @return the map of attributes, should never return <tt>null</tt>
     */
    LinkedHashMap<String, String> getAttributes();

    /**
     * Set the origin of this widget.
     *
     * @param origin the origin of this widget.
     */
    void setOrigin(AmendableWidgetOrigin origin);

    /**
     * Returns the origin of this amendable widget, be it part of the document, or introduced by an amendment.
     *
     * @return the origin, cannot be <tt>null</tt>
     */
    AmendableWidgetOrigin getOrigin();

    /**
     * Check if this amendable widget is introduced by an amendment (meaning, its {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl#getOrigin()}
     * is equal to {@link AmendableWidgetOrigin#AMENDMENT}
     *
     * @return <tt>true</tt> if it was introduced by an amendment
     */
    boolean isIntroducedByAnAmendment();

    /**
     * Get the underlying element. Should never be <tt>null</tt>.
     *
     * @return the element.
     */
    Element getAmendableElement();

    /**
     * Returns all amendment controllers on this amendable widget.
     *
     * @return the amendment controllers.
     */
    AmendmentController[] getAmendmentControllers();

    /**
     * Get the index of this widget in its parent's widget collection, but only counting the same types (so, if there
     * is another widget of another type in between, it will not be counted) <b>and</b> widgets that have not been
     * introduced by amendments (if you wish to control this, use {@link #getTypeIndex(boolean)} instead.
     *
     * @return the index, filtered by type, or -1 if it cannot be found.
     */
    int getTypeIndex();

    /**
     * Get the index of this widget in its parent's widget collection, but only counting the same types (so, if there
     * is another widget of another type in between, it will not be counted).
     *
     * @param includeAmendments if true, widgets with the same type that have been introduced by amendments, will
     *                          also be taken into account.
     * @return the index, filtered by type, or -1 if it cannot be found.
     */
    int getTypeIndex(final boolean includeAmendments);

    /**
     * Get the index of this widget in its parent's widget collection, but only counting widgets that have not been
     * introduced by amendments.
     *
     * @return the index, or -1 if it cannot be found.
     */
    int getIndex();

    /**
     * Returns the namespace this amendable widget was generated for.
     *
     * @return the namespace URI.
     */
    String getNamespaceURI();

    /**
     * Set the overlay strategy (used for lazy loading)
     *
     * @param overlayStrategy the overlay strategy
     */
    void setOverlayStrategy(OverlayStrategy overlayStrategy);

    /**
     * Get the unformatted index, if any.
     *
     * @return the unformatted index.
     */
    String getUnformattedIndex();

    /**
     * Get the formatted index, if any.
     *
     * @return the formatted index.
     */
    String getFormattedIndex();
}
