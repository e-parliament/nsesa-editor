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
 * The <code>OverlayNode</code> class is the primary data type when parsing an xsd.
 * It represents a single node in the xsd structure and keeps basic information about the node like its name,
 * namespace, classname, comments and type. We'll then use this information to generate various files, such
 * as overlay classes, CSS properties, ...
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 29/10/12 10:05
 */
public class OverlayNode {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayNode.class);

    /**
     * The local node name.
     */
    protected String name;
    /**
     * The namespace URI.
     */
    protected String namespaceURI;

    /**
     * The overlay class name.
     */
    protected String className;
    /**
     * The type of overlay.
     */
    protected OverlayType overlayType;
    /**
     * Any further comments that have been specified.
     */
    protected String comments;

    /**
     * Constructs an empty <code>OverlayNode</code>
     */
    public OverlayNode() {
    }

    /**
     * Constructs an <code>OverlayNode</code> with the given name, namespace and type.
     *
     * @param name         The node name as String
     * @param namespaceURI The namespace as String
     * @param overlayType  The overlayType as Enum
     */
    public OverlayNode(final String name, final String namespaceURI, final OverlayType overlayType) {
        this.name = name;
        this.namespaceURI = namespaceURI;
        this.overlayType = overlayType;
    }

    /**
     * Return the <code>OverlayType</code> of the <code>OverlayNode</code>.
     *
     * @return the overlay type
     */
    public OverlayType getOverlayType() {
        return overlayType;
    }

    /**
     * Set the <code>overlayType</code> of the node.
     *
     * @param overlayType the overlay type
     */
    public void setOverlayType(final OverlayType overlayType) {
        this.overlayType = overlayType;
    }

    /**
     * Returns the node name.
     *
     * @return the node name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the node name.
     *
     * @param name as String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the node namespace.
     *
     * @return The namespace as a String
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * Set the namespace URI.
     *
     * @param namespaceURI as a String
     */
    public void setNamespaceURI(final String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }

    /**
     * Returns the node's class name (for further generation).
     *
     * @return the classname as a String
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the node class name.
     *
     * @param className the classname as a String
     */
    public void setClassName(final String className) {
        this.className = className;
    }

    /**
     * True when the <code>OverlayNode</code> is simple type
     *
     * @return True for simple type
     */
    public boolean isSimple() {
        return OverlayType.SimpleType.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is complex type
     *
     * @return <tt>true</tt> for complex type
     */
    public boolean isComplex() {
        return OverlayType.ComplexType.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is xsd element
     *
     * @return True for xsd element
     */
    public boolean isElement() {
        return OverlayType.Element.equals(overlayType);
    }

    /**
     * True when the <code>OverlayNode</code> is wildcard type
     *
     * @return True for wildcard type
     */
    public boolean isWildCard() {
        return OverlayType.WildcardType.equals(overlayType);
    }

    /**
     * Casts this object to OverlayClass if possible, otherwise returns <tt>null</tt>.
     */
    public OverlayClass asOverlayClass() {
        if (this instanceof OverlayClass) {
            return (OverlayClass) this;
        }
        return null;
    }

    /**
     * Casts this object to OverlayProperty if possible, otherwise returns <tt>null</tt>.
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
                ",namespaceURI='" + namespaceURI + '\'' +
                ",overlayType=" + overlayType +
                ", className='" + className + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OverlayNode that = (OverlayNode) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (namespaceURI != null ? !namespaceURI.equals(that.namespaceURI) : that.namespaceURI != null) return false;
        if (overlayType != that.overlayType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (namespaceURI != null ? namespaceURI.hashCode() : 0);
        result = 31 * result + (overlayType != null ? overlayType.hashCode() : 0);
        return result;
    }

    /**
     * Returns the comments associated to the node
     *
     * @return comments as String
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the comments to the node
     *
     * @param comments as String
     */
    public void setComments(final String comments) {
        this.comments = comments;
    }
}
