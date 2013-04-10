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
package org.nsesa.editor.app.xsd.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>OverlayNode</code> class is the primary datatype when parsing xsd schema.
 * It represents a single node in the xsd structure and keep basic information about xsd node like name,
 * namespace, classname, comments and type.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 29/10/12 10:05
 */
public class OverlayNode {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayNode.class);

    protected String name;
    protected String nameSpace;
    protected String className;
    protected OverlayType overlayType;
    protected String comments;

    /**
     * Constructs an empty <code>OverlayNode</code>
     */
    public OverlayNode() {
    }

    /**
     * Constructs an <code>OverlayNode</code> with the given name, namespace abd type
     * @param name The node name as String
     * @param nameSpace The namespace as String
     * @param overlayType  The overlayType as Enum
     */
    public OverlayNode(String name, String nameSpace, OverlayType overlayType) {
        this.name = name;
        this.nameSpace = nameSpace;
        this.overlayType = overlayType;
    }

    /**
     * Return the <code>OverlayType</code> of the <code>OverlayNode</code>
     * @return
     */
    public OverlayType getOverlayType() {
        return overlayType;
    }

    /**
     * Set the <code>overlayType</code> of the node
     * @param overlayType
     */
    public void setOverlayType(OverlayType overlayType) {
        this.overlayType = overlayType;
    }

    /**
     * Returns the node name
     * @return the node name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the node name
     * @param name as String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the node namespace
     * @return The namespace as String
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Set the namespace
     * @param nameSpace as String
     */
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    /**
     * Returns the node classname
     * @return Classname as String
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the node class name
     * @param className As String
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * True when the <code>OverlayNode</code> is simple type
     * @return True for simple type
     */
    public boolean isSimple() {
        return OverlayType.SimpleType.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is complex type
     * @return True for complex type
     */
    public boolean isComplex() {
        return OverlayType.ComplexType.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is xsd element
     * @return True for xsd element
     */
    public boolean isElement() {
        return OverlayType.Element.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is wildcard type
     * @return True for wildcard type
     */
    public boolean isWildCard() {
        return OverlayType.WildcardType.equals(overlayType);
    }

    /**
     * Casts this object to OverlayClass if possible, otherwise returns null.
     */
    public OverlayClass asOverlayClass() {
        if (this instanceof OverlayClass) {
            return (OverlayClass) this;
        }
        return null;
    }

    /**
     * Casts this object to OverlayProperty if possible, otherwise returns null.
     */
    public OverlayProperty asOverlayProperty() {
        if (this instanceof OverlayProperty) {
            return (OverlayProperty) this;
        }
        return null;
    }

    @Override
    public String toString() {
        return "OverlayNode{" +
                "name='" + name + '\'' +
                ",nameSpace='" + nameSpace + '\'' +
                ",overlayType=" + overlayType +
                ", className='" + className + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OverlayNode that = (OverlayNode) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameSpace != null ? !nameSpace.equals(that.nameSpace) : that.nameSpace != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameSpace != null ? nameSpace.hashCode() : 0);
        result = 31 * result + (overlayType != null ? overlayType.hashCode() : 0);
        return result;
    }

    /**
     * Returns the comments associated to the node
     * @return comments as String
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the comments to the node
     * @param comments as String
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
