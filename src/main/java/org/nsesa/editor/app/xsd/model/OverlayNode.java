package org.nsesa.editor.app.xsd.model;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Class description
 * User: sgroza
 * Date: 29/10/12
 * Time: 10:05
 */
public class OverlayNode {
    public static final Logger LOG = LoggerFactory.getLogger(OverlayClass.class);

    protected String name;
    protected String nameSpace;
    protected String className;
    protected OverlayType overlayType;

    // flag for short circuit when creating the tree
    protected boolean visited;

    public OverlayNode() {
    }

    public OverlayNode(String name, String nameSpace, OverlayType overlayType) {
        this.name = name;
        this.nameSpace = nameSpace;
        this.overlayType = overlayType;
    }

    // a simple way to clone an object by invoking the contructor
    public OverlayNode(OverlayNode toClone) {
        this(toClone.getName(), toClone.getNameSpace(), toClone.getOverlayType());
        this.className = toClone.getClassName();
    }

    public OverlayType getOverlayType() {
        return overlayType;
    }

    public void setOverlayType(OverlayType overlayType) {
        this.overlayType = overlayType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSimple() {
        return OverlayType.SimpleType.equals(overlayType);
    }
    public boolean isComplex() {
        return OverlayType.ComplexType.equals(overlayType);
    }
    public boolean isElement() {
        return OverlayType.Element.equals(overlayType);
    }
    public boolean isWildCard() {
        return OverlayType.WildcardType.equals(overlayType);
    }

    /**
     * Traverse the node and its descendants
     * @param processor The processor applied for the visited node
     */
//    public void process(OverlayClassProcessor processor) {
//        processor.process(this);
//        Stack<OverlayNode> stack = new Stack<OverlayNode>();
//        stack.add(this);
//        Map<String, OverlayNode> map = new HashMap<String, OverlayNode>();
//        while(!stack.isEmpty()) {
//            OverlayNode node = stack.pop();
//            processor.process(node, context);
//            if (node.getProperties().size() > 0) {
//                for (int i = node.getProperties().size(); i > 0 ; i--) {
//                    if (!map.containsKey(node.getProperties().get(i-1).getKey())) {
//                        map.put(node.getProperties().get(i-1).getKey(), node.getProperties().get(i-1));
//                        stack.push(node.getProperties().get(i-1));
//                    } else {
//                        System.out.println(node.getProperties().get(i-1) + " is already traversed");
//                    }
//                }
//            }
//        }
//    }
    /**
     * Build the tree containing its descendants
     * @param rawNodes The list of all possible descendants nodes
     */
//    public OverlayNode asTree(List<OverlayClass> rawNodes) {
//        final Map<String, OverlayClass> map = Maps.uniqueIndex(rawNodes, new Function<OverlayClass, String>() {
//            @Override
//            public String apply(@Nullable OverlayClass input) {
//                return input.getKey();
//            }
//        });
//        OverlayNode rootNode = new OverlayNode(this);
//        buildTree(rootNode, map);
//        return rootNode;
//    }

//    protected void buildTree(OverlayNode node, Map<String, OverlayClass> map) {
//        // traverse the children of the parent if exist and add as descendants
//        LOG.debug("Generate tree for node {}", node);
//        OverlayNode rawNode = map.get(node.getKey());
//        // create a copy for each child
//        if (rawNode != null) {
//            for(OverlayNode child : rawNode.getProperties()) {
//                OverlayNode newChild = new OverlayNode(child);
//                node.add(newChild);
//            }
//            // create a copy for each child of the parent and descendants
//            OverlayNode parent = rawNode.getParent();
//            while (parent != null) {
//                OverlayNode parentRawNode = map.get(parent.getKey());
//                if (parentRawNode != null) {
//                    for(OverlayNode child : parentRawNode.getProperties()) {
//                        OverlayNode newChild = new OverlayNode(child);
//                        node.add(newChild);
//                    }
//                }
//                parent = parentRawNode.getParent();
//            }
//
//            // traverse the children and replace with their overlay classes
//            ListIterator<OverlayNode> iterator = node.children.listIterator();
//            while (iterator.hasNext()) {
//                OverlayNode child = iterator.next();
//                // find the overlay node in the map
//                OverlayNode childRawNode = map.get(child.getKey());
//                if (childRawNode != null) {
//                    LOG.debug("Get the child class in the map: " + childRawNode);
//                    // remove the overlay property with the class from map
//                    if (!childRawNode.visited) {
//                        buildTree(child, map);
//                    } else {
//                        LOG.debug("The child has been already visited: " + childRawNode);
//                    }
//                }
//                rawNode.visited = true;
//            }
//        }
//    }

    /** Casts this object to OverlayClass if possible, otherwise returns null. */
    public OverlayClass asOverlayClass() {
        if (this instanceof OverlayClass) {
            return (OverlayClass)this;
        }
        return null;
    }
    /** Casts this object to OverlayProperty if possible, otherwise returns null. */
    public OverlayProperty asOverlayProperty() {
        if (this instanceof OverlayProperty) {
            return (OverlayProperty)this;
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
}
