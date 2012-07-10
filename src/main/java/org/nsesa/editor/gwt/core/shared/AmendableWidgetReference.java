package org.nsesa.editor.gwt.core.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Date: 10/07/12 22:34
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class AmendableWidgetReference implements IsSerializable {
    private String element;

    public AmendableWidgetReference() {
    }

    public AmendableWidgetReference(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
