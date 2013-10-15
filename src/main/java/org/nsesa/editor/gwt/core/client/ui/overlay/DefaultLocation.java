/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * Date: 11/07/13 17:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultLocation implements Location {

    boolean isNew, isSibling;
    String index, type, namespaceURI;

    public DefaultLocation() {
    }

    public DefaultLocation(String index, String type, String namespaceURI) {
        this.index = index;
        this.type = type;
        this.namespaceURI = namespaceURI;
    }

    public DefaultLocation(boolean aNew, boolean sibling, String index, String type, String namespaceURI) {
        isNew = aNew;
        isSibling = sibling;
        this.index = index;
        this.type = type;
        this.namespaceURI = namespaceURI;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setSibling(boolean sibling) {
        isSibling = sibling;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNamespaceURI(String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public boolean isSibling() {
        return isSibling;
    }

    @Override
    public String getIndex() {
        return index;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getNamespaceURI() {
        return namespaceURI;
    }
}
