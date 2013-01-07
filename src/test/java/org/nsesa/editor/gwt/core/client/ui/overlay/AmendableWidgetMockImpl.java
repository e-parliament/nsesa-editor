package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import org.nsesa.editor.gwt.core.client.ui.amendment.AmendmentController;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetUIListener;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.core.shared.AmendableWidgetOrigin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Date: 12/12/12 22:37
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetMockImpl implements AmendableWidget {

    public Boolean amendable, immutable;
    public boolean amended;
    public AmendableWidget parentAmendableWidget;
    public List<AmendableWidget> parentAmendableWidgets = new ArrayList<AmendableWidget>();
    public List<AmendableWidget> childAmendableWidgets = new ArrayList<AmendableWidget>();
    public AmendableWidget root;
    public List<AmendmentController> amendmentControllers = new ArrayList<AmendmentController>();

    public String type;
    public String id;
    public String innerHTML;
    public HTMLPanel amendmentHolderElement;
    public String[] allowedChildTypes;
    public NumberingType numberingType;
    public Format format;
    public Integer assignedNumber;
    public LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
    public AmendableWidgetOrigin origin;
    public boolean introducedByAnAmendment;
    public int typeIndex;
    public int index;
    public String namespaceURI;
    public OverlayStrategy overlayStrategy;
    public String unformattedIndex;
    public String formattedIndex;


    @Override
    public Boolean isAmendable() {
        return amendable;
    }

    @Override
    public boolean isAmended() {
        return amended;
    }

    @Override
    public void setAmendable(Boolean amendable) {
        this.amendable = amendable;
    }

    @Override
    public Boolean isImmutable() {
        return immutable;
    }

    @Override
    public void setImmutable(Boolean immutable) {
        this.immutable = immutable;
    }

    @Override
    public void setUIListener(AmendableWidgetUIListener UIListener) {
    }

    @Override
    public void setListener(AmendableWidgetListener listener) {
    }

    @Override
    public void setParentAmendableWidget(AmendableWidget parent) {
        this.parentAmendableWidget = parent;
    }

    @Override
    public List<AmendableWidget> getParentAmendableWidgets() {
        return parentAmendableWidgets;
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
        return root;
    }

    @Override
    public void addAmendableWidget(AmendableWidget child) {
        this.childAmendableWidgets.add(child);
    }

    @Override
    public void addAmendableWidget(AmendableWidget child, int index) {
        this.childAmendableWidgets.add(index, child);
    }

    @Override
    public void onAttach() {

    }

    @Override
    public boolean isAttached() {
        return true;
    }

    @Override
    public void onDetach() {
    }

    @Override
    public void addAmendableWidget(AmendableWidget child, int index, boolean skipValidation) {
        this.childAmendableWidgets.add(index, child);
    }

    @Override
    public void removeAmendableWidget(AmendableWidget child) {
        this.childAmendableWidgets.remove(child);
    }

    @Override
    public void addAmendmentController(AmendmentController amendmentController) {
        this.amendmentControllers.add(amendmentController);
    }

    @Override
    public void removeAmendmentController(AmendmentController amendmentController) {
        this.amendmentControllers.remove(amendmentController);
    }

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
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getInnerHTML() {
        return innerHTML;
    }

    @Override
    public void setInnerHTML(String amendableContent) {
        this.innerHTML = amendableContent;
    }

    @Override
    public HTMLPanel getAmendmentHolderElement() {
        return amendmentHolderElement;
    }

    @Override
    public String[] getAllowedChildTypes() {
        return allowedChildTypes;
    }

    @Override
    public NumberingType getNumberingType() {
        return numberingType;
    }

    @Override
    public void setNumberingType(NumberingType numberingType) {
        this.numberingType = numberingType;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public Integer getAssignedNumber() {
        return assignedNumber;
    }

    @Override
    public void setAssignedNumber(Integer assignedNumber) {
        this.assignedNumber = assignedNumber;
    }

    @Override
    public LinkedHashMap<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void setOrigin(AmendableWidgetOrigin origin) {
        this.origin = origin;
    }

    @Override
    public AmendableWidgetOrigin getOrigin() {
        return origin;
    }

    @Override
    public boolean isIntroducedByAnAmendment() {
        return introducedByAnAmendment;
    }

    @Override
    public Element getAmendableElement() {
        return null;
    }

    @Override
    public AmendmentController[] getAmendmentControllers() {
        return amendmentControllers.toArray(new AmendmentController[amendmentControllers.size()]);
    }

    @Override
    public int getTypeIndex() {
        return typeIndex;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getNamespaceURI() {
        return namespaceURI;
    }

    @Override
    public void setOverlayStrategy(OverlayStrategy overlayStrategy) {
        this.overlayStrategy = overlayStrategy;
    }

    @Override
    public String getUnformattedIndex() {
        return unformattedIndex;
    }

    @Override
    public String getFormattedIndex() {
        return formattedIndex;
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

    @Override
    public void add(Widget w) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Iterator<Widget> iterator() {
        return null;
    }

    @Override
    public boolean remove(Widget w) {
        return false;
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public String toString() {
        return "[AWMock " + getType() + ":" + getTypeIndex() + " #" + getId() + "]";
    }
}
