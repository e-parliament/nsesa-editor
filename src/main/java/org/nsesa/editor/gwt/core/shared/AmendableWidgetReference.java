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
    private String path, type;
    private int offset;

    public AmendableWidgetReference() {
    }

    public AmendableWidgetReference(String path) {
        this.path = path;
    }

    public AmendableWidgetReference(boolean creation, boolean sibling, String path, String type, int offset) {
        this.creation = creation;
        this.sibling = sibling;
        this.path = path;
        this.type = type;
        this.offset = offset;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
