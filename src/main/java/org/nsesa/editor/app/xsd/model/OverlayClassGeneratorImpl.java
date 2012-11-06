package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An implementation of {@link OverlayClassGenerator} interface
 * User: sgroza
 * Date: 18/10/12
 * Time: 15:31
 */
public class OverlayClassGeneratorImpl implements OverlayClassGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayClassGeneratorImpl.class);

    private List<OverlayClass> generatedClasses;
    private Map<OverlayClass, OverlayClass> cache;

    private Collection<XSSchema> schemas;
    /**
     * Default constructor
     */
    public OverlayClassGeneratorImpl() {
        this.cache = new HashMap<OverlayClass, OverlayClass>();
    }

    @Override
    public void generate(XSSimpleType simpleType) {
        LOG.debug("Generate overlayclass from simple type {}", simpleType);
        OverlayClass overlayClass = new OverlayClass(simpleType.getName(), simpleType.getTargetNamespace(), OverlayType.SimpleType);
        overlayClass.setClassName(simpleType.getName() + OverlayType.SimpleType);
        if (simpleType.getSimpleBaseType() != null ) {
            OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.SimpleType);
            if (simpleType.getSimpleBaseType().isLocal()) {
                parent.setName(simpleType.getSimpleBaseType().getBaseType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNameSpace(simpleType.getSimpleBaseType().getBaseType().getTargetNamespace());
            }  else {
                parent.setName(simpleType.getSimpleBaseType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNameSpace(simpleType.getSimpleBaseType().getTargetNamespace());
            }
            overlayClass.setParent(parent);
        }
        SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(simpleType);
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getParent() == null) {
            OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang",null, "String","content", false);
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(XSComplexType complexType) {
        LOG.debug("Generate overlayclass from complex type {}", complexType);
        if (complexType.getName().equalsIgnoreCase("AnyOtherType")) {
            LOG.debug("Generate overlayclass from complex type {}", complexType);
        }
        OverlayClass overlayClass = new OverlayClass(complexType.getName(), complexType.getTargetNamespace(), OverlayType.ComplexType);
        overlayClass.setClassName(complexType.getName() + OverlayType.ComplexType) ;
        if (complexType.getBaseType() != null && !complexType.getBaseType().getName().equalsIgnoreCase("anytype")) {
            OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.ComplexType);
            parent.setName(complexType.getBaseType().getName());
            parent.setClassName(parent.getName() + OverlayType.ComplexType);
            parent.setNameSpace(complexType.getBaseType().getTargetNamespace());
            overlayClass.setParent(parent);
        }
        overlayClass.getProperties().addAll(generateProperty(complexType));
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }


    @Override
    public void generate(XSAttributeDecl attribute) {
        LOG.debug("Generate overlayclass from attribute type {}", attribute);
        OverlayClass overlayClass = new OverlayClass(attribute.getName(), attribute.getTargetNamespace(), OverlayType.Attribute);
        overlayClass.setClassName(attribute.getName() + OverlayType.Attribute);
        if (attribute.getType().isLocal()) {
            OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.SimpleType);
            parent.setName(attribute.getType().getBaseType().getName());
            parent.setClassName(parent.getName() + OverlayType.SimpleType);
            parent.setNameSpace(attribute.getType().getBaseType().getTargetNamespace());
            overlayClass.setParent(parent);
        }  else {
            if (attribute.getType() != null) {
                OverlayClass parent = new OverlayClass();
                parent.setOverlayType(OverlayType.SimpleType);
                parent.setName(attribute.getType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNameSpace(attribute.getType().getTargetNamespace());
                overlayClass.setParent(parent);
            }
        }
        SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(attribute.getType());
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getParent() == null) {
            OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String","content", false);
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(XSModelGroupDecl modelGroup) {
        LOG.debug("Generate overlayclass from group type {}", modelGroup);

        OverlayClass overlayClass = new OverlayClass(modelGroup.getName(), modelGroup.getTargetNamespace(), OverlayType.Group);
        overlayClass.setClassName(modelGroup.getName() + OverlayType.Group);
        // the overlay class based on group does not extend any base class
        XSParticle[] particles = modelGroup.getModelGroup().getChildren();
        // create a property for each particle in the collection
        for (XSParticle particle : particles) {
            String name;
            String className;
            String nameSpace;
            OverlayType type;
            if (particle.getTerm().asElementDecl() != null) {
                name = particle.getTerm().asElementDecl().getName();
                className = particle.getTerm().asElementDecl().getName();
                nameSpace = particle.getTerm().asElementDecl().getTargetNamespace();
                type = OverlayType.Element;
            } else if (particle.getTerm().asModelGroupDecl() != null){
                name = particle.getTerm().asModelGroupDecl().getName();
                className = particle.getTerm().asModelGroupDecl().getName() + OverlayType.Group;
                nameSpace = particle.getTerm().asModelGroupDecl().getTargetNamespace();
                type = OverlayType.Group;
            } else  {
                throw new RuntimeException("Not implemented yet " + modelGroup.getName());
            }
            boolean isCollection = particle.getMaxOccurs().intValue() > 1 || particle.getMaxOccurs().intValue() == -1;
            OverlayProperty property = new OverlayProperty(type, null, nameSpace, className, name, isCollection);
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(XSAttGroupDecl attrGroup) {
        LOG.debug("Generate overlayclass from attribute group type {}", attrGroup);
        if (attrGroup.getName().equalsIgnoreCase("core")) {
            LOG.debug("Generate overlayclass from attribute group type {}", attrGroup);
        }
        OverlayClass overlayClass = new OverlayClass(attrGroup.getName(), attrGroup.getTargetNamespace(), OverlayType.AttrGroup);
        overlayClass.setClassName(attrGroup.getName() + OverlayType.AttrGroup);

        final Collection<? extends XSAttributeUse> declaredAttributeUses = attrGroup.getDeclaredAttributeUses();
        if (declaredAttributeUses != null) {
            final Iterator<? extends XSAttributeUse> iterator = declaredAttributeUses.iterator();
            while(iterator.hasNext()) {
                XSAttributeUse attributeUse = iterator.next();
                overlayClass.getProperties().add(generateProperty(attributeUse.getDecl()));
            }
        }
        if (attrGroup.getAttributeWildcard() != null) {
            // add wildcard property
            OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String", "wildcardContent", false);
            overlayClass.getProperties().add(property);
        }

        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(XSElementDecl element) {
        LOG.debug("Generate overlayclass from element type {}", element);
        OverlayClass overlayClass = new OverlayClass(element.getName(), element.getTargetNamespace(), OverlayType.Element);
        overlayClass.setClassName(element.getName());
        XSType xsType = element.getType();
        if (xsType.isGlobal()) {
            if (xsType.isComplexType()) {
                OverlayClass parent = new OverlayClass(xsType.getName(), xsType.getTargetNamespace(), OverlayType.ComplexType);
                parent.setClassName(parent.getName() + OverlayType.ComplexType);
                overlayClass.setParent(parent);
            } else {
                OverlayClass parent = new OverlayClass(xsType.getName(), xsType.getTargetNamespace(), OverlayType.SimpleType);
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                overlayClass.setParent(parent);
            }
            generatedClasses.add(overlayClass);
            return ;
        }

        if (!xsType.getBaseType().getName().equalsIgnoreCase("AnyType")) {
            OverlayClass parent = new OverlayClass(xsType.getBaseType().getName(),
                    xsType.getBaseType().getTargetNamespace(), OverlayType.ComplexType);
            parent.setClassName(parent.getName() + OverlayType.ComplexType);
            overlayClass.setParent(parent);
        }

        // xsType is local defined
        if (xsType.isComplexType()) {
            overlayClass.getProperties().addAll(generateProperty(xsType.asComplexType()));
        } else {
            //treat as simple type
            if (xsType.asSimpleType() != null) {
                overlayClass.getProperties().add(generateProperty(xsType.asSimpleType()));
            }
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public OverlayRootClass getTreeResult() {
        return buildTree();
    }

    @Override
    public List<OverlayClass> getResult() {
        return generatedClasses;
    }

    @Override
    public List<OverlayClass> getResult(Comparator<OverlayClass> comparator) {
        OverlayRootClass rootClass = buildTree();
        // for every children list apply the comparator
        Stack<OverlayClass> stack = new Stack<OverlayClass>();
        List<OverlayClass> result = new ArrayList<OverlayClass>();
        stack.push(rootClass);
        while (!stack.isEmpty()) {
            OverlayClass aClass = stack.pop();
            result.add(aClass);
            OverlayClass[] children = aClass.getChildren().toArray(new OverlayClass[]{});
            //Collections.sort(children, comparator);
            for (int i = children.length -1; i >= 0 ; i--) {
                stack.push(children[i]);
            }
        }
        return result;
    }

    private OverlayRootClass buildTree() {
        OverlayRootClass rootClass = new OverlayRootClass();
        cache.put(rootClass, rootClass);
        for (XSSchema schema : schemas) {
            OverlaySchemaClass schemaClass = new OverlaySchemaClass(schema.getTargetNamespace());
            rootClass.getChildren().add(schemaClass);
            cache.put(schemaClass, schemaClass);
        }
        for (OverlayClass aClass : generatedClasses) {
            // get the parent from cache if possible
            OverlayClass parentClass;
            if (aClass.getParent() != null) {
                parentClass = cache.get(aClass.getParent());
            } else {
                // identify the schema class
                OverlaySchemaClass schemaClass = new OverlaySchemaClass(aClass.getNameSpace());
                parentClass = cache.get(schemaClass);
                if (parentClass == null) {
                    parentClass = rootClass;
                }
            }
            parentClass.getChildren().add(aClass);
            aClass.setParent(parentClass);
        }
        return rootClass;
    }


    @Override
    public void generate(Collection<XSSchema> schemas) {
        this.schemas = schemas;
        generatedClasses = new ArrayList<OverlayClass>();
        for (XSSchema schema : schemas) {

            final Collection<XSSimpleType> simpleTypes = schema.getSimpleTypes().values();
            for (final XSSimpleType simpleType : simpleTypes) {
                generate(simpleType);
            }

            final Collection<XSComplexType> complexTypes = schema.getComplexTypes().values();
            for (final XSComplexType complexType : complexTypes) {
                generate(complexType);
            }

            final Collection<XSAttGroupDecl> xsAttGroupDecls = schema.getAttGroupDecls().values();
            for (final XSAttGroupDecl attGroupDecl : xsAttGroupDecls) {
                generate(attGroupDecl);
            }
            final Collection<XSModelGroupDecl> modelGroupDecls = schema.getModelGroupDecls().values();
            for (final XSModelGroupDecl modelGroupDecl : modelGroupDecls) {
                generate(modelGroupDecl);
            }

            final Collection<XSAttributeDecl> xsAttributeDecls = schema.getAttributeDecls().values();
            for (final XSAttributeDecl attributeDecl : xsAttributeDecls) {
                generate(attributeDecl);
            }
            final Collection<XSElementDecl> elementDecls = schema.getElementDecls().values();
            for (final XSElementDecl elementDecl : elementDecls) {
                generate(elementDecl);
            }
        }
        for(OverlayClass overlayClass : generatedClasses) {
            if (cache.containsKey(overlayClass)) {
                throw new RuntimeException("Stop generation... The class already exists:" + overlayClass);
            }
            cache.put(overlayClass, overlayClass);
        }
        LOG.info("{} classes will be generated", generatedClasses.size());

    }

    /**
     * Generate {@link OverlayProperty} property from xsd group component
     * @param attGroupDecl The xsd group processed
     * @return An overlay property based on the given xsd group
     */
    private OverlayProperty generateProperty(XSAttGroupDecl attGroupDecl) {
        return new OverlayProperty(OverlayType.AttrGroup, null, attGroupDecl.getTargetNamespace(),
                attGroupDecl.getName() + OverlayType.AttrGroup, attGroupDecl.getName(), false);
    }

    /**
     * Generate {@link OverlayProperty} property from xsd attribute component
     * @param attributeDecl The xsd attribute processed
     * @return An overlay property based on the given xsd attribute
     */
    private OverlayProperty generateProperty(XSAttributeDecl attributeDecl) {
        String className = attributeDecl.getType().isLocal() ?
                attributeDecl.getType().getBaseType().getName() : attributeDecl.getType().getName();
        String nameSpace = attributeDecl.getType().isLocal() ?
                attributeDecl.getType().getBaseType().getTargetNamespace() : attributeDecl.getType().getTargetNamespace();
        return new OverlayProperty(OverlayType.Attribute, null,
                nameSpace,
                className + OverlayType.SimpleType,
                attributeDecl.getName(), false);
    }

    /**
     * Generate {@link OverlayProperty} property from xsd simple type component
     * @param simpleType The xsd simple type processed
     * @return An overlay property based on the given xsd simple type
     */
    private OverlayProperty generateProperty(XSSimpleType simpleType) {
        return new OverlayProperty(OverlayType.SimpleType, "java.lang", null,"String", simpleType.getName(), false);
    }

    /**
     * Generate {@link OverlayProperty} property from xsd complex type component
     * @param complexType The xsd complex type processed
     * @return A list of overlay properties based on the given xsd complex type
     */
    private List<OverlayProperty> generateProperty(XSComplexType complexType) {
        List<OverlayProperty> properties = new ArrayList<OverlayProperty>();
        // treat the groups and the attributes
        final Collection<? extends XSAttributeUse> declaredAttributeUses = complexType.getDeclaredAttributeUses();
        if (declaredAttributeUses != null) {
            final Iterator<? extends XSAttributeUse> iterator = declaredAttributeUses.iterator();
            while(iterator.hasNext()) {
                XSAttributeUse attributeUse = iterator.next();
                properties.add(generateProperty(attributeUse.getDecl()));
            }
        }

        final Collection<? extends XSAttGroupDecl> declaredAttrGroups = complexType.getAttGroups();
        if (declaredAttrGroups != null) {
            final Iterator<? extends XSAttGroupDecl> iterator = declaredAttrGroups.iterator();
            while(iterator.hasNext()) {
                XSAttGroupDecl attrGroup = iterator.next();
                properties.add(generateProperty(attrGroup));
            }
        }
        if (complexType.getExplicitContent() == null || complexType.getExplicitContent().asEmpty() != null) {
            if (complexType.getBaseType().getName().equalsIgnoreCase("anyType")) {
                final XSParticle xsParticle = complexType.getContentType().asParticle();
                generateProperty(xsParticle, properties, false);
            } else {
                // the overlay class extends already this base type
                LOG.warn("No property is added.The overlay class extends already this base type {} ",
                        complexType.getBaseType());
                // create a property from base type
//                OverlayProperty property = new OverlayProperty(OverlayType.ComplexType, null, complexType.getBaseType().getTargetNamespace(),
//                        complexType.getBaseType().getName() + OverlayType.ComplexType,
//                        complexType.getBaseType().getName(), false);
//                properties.add(property);
            }
        } else {
            final XSParticle xsParticle = complexType.getExplicitContent().asParticle();
            generateProperty(xsParticle, properties, false);
        }
        return properties;
    }

    /**
     * A recursive method to create overlay properties from {@link XSParticle} component
     * @param xsParticle The particule to be processed
     * @param properties The list with properties
     * @param wasCollection Flag to keep the property need to be generated as collection
     */
    private void generateProperty(XSParticle xsParticle, List<OverlayProperty> properties, boolean wasCollection) {
        if (xsParticle != null) {
            boolean isCollection = isCollection(xsParticle);
            if (xsParticle.getTerm().isElementDecl()) {
                OverlayProperty property = new OverlayProperty(OverlayType.Element, null,
                        xsParticle.getTerm().asElementDecl().getTargetNamespace(),
                        xsParticle.getTerm().asElementDecl().getName(),
                        xsParticle.getTerm().asElementDecl().getName(), isCollection || wasCollection);
                properties.add(property);
            } else if (xsParticle.getTerm().isModelGroupDecl()) {
                OverlayProperty property = new OverlayProperty(OverlayType.Group, null,
                        xsParticle.getTerm().asModelGroupDecl().getTargetNamespace(),
                        xsParticle.getTerm().asModelGroupDecl().getName() + OverlayType.Group,
                        xsParticle.getTerm().asModelGroupDecl().getName() , isCollection || wasCollection);
                properties.add(property);
            } else if (xsParticle.getTerm().isModelGroup()) {
                final XSParticle[] particles = xsParticle.getTerm().asModelGroup().getChildren();
                for(XSParticle particle : particles) {
                    generateProperty(particle, properties, isCollection || wasCollection);
                }
            } else if (xsParticle.getTerm().isWildcard()) {
                OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String",
                        "wildcardContent" , isCollection || wasCollection);
                properties.add(property);
            } else {
                LOG.warn("No property is generated for xs particle {}", xsParticle);
            }
        }
    }

    /**
     * Checks whether or not the xs particle needs to be treated as a collection
     * @param particle  The particle to be checked
     * @return True when the particle is a collection
     */
    private boolean isCollection(XSParticle particle) {
        return particle.getMaxOccurs().intValue() > 1 || particle.getMaxOccurs().intValue() == -1;
    }
}
