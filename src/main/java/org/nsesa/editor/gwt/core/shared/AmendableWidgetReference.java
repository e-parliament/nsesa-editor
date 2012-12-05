package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Date: 10/07/12 22:34
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetReference implements IsSerializable {
    private boolean creation, sibling;
    private String element, type;
    private int offset;

    public AmendableWidgetReference() {
    }

    public AmendableWidgetReference(String element) {
        this.element = element;
    }

    public AmendableWidgetReference(boolean creation, boolean sibling, String element, String type, int offset) {
        this.creation = creation;
        this.sibling = sibling;
        this.element = element;
        this.type = type;
        this.offset = offset;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public boolean isCreation() {
        return creation;
    }

    public void setCreation(boolean creation) {
        this.creation = creation;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isSibling() {
        return sibling;
    }

    public void setSibling(boolean sibling) {
        this.sibling = sibling;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
