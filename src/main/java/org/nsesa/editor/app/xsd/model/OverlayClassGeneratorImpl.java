package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of {@link OverlayClassGenerator} interface
 * User: sgroza
 * Date: 18/10/12
 * Time: 15:31
 */
public class OverlayClassGeneratorImpl implements OverlayClassGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayClassGeneratorImpl.class);

    /**
     * Default constructor
     */
    public OverlayClassGeneratorImpl() {
    }


    @Override
    public OverlayClass generate(XSSimpleType simpleType) {
        LOG.debug("Generate overlayclass from simple type {0}", simpleType);
        OverlayClass overlayClass = new OverlayClass(OverlayType.SimpleType);
        overlayClass.setNameSpace(simpleType.getTargetNamespace());
        overlayClass.setName(simpleType.getName() + OverlayType.SimpleType);
        if (simpleType.getSimpleBaseType() != null ) {
            if (simpleType.getSimpleBaseType().isLocal()) {
                overlayClass.setSuperClassName(simpleType.getSimpleBaseType().getBaseType().getName() + OverlayType.SimpleType);
                overlayClass.setSuperNameSpace(simpleType.getSimpleBaseType().getBaseType().getTargetNamespace());
            }  else {
                overlayClass.setSuperClassName(simpleType.getSimpleBaseType().getName() + OverlayType.SimpleType);
                overlayClass.setSuperNameSpace(simpleType.getSimpleBaseType().getTargetNamespace());
            }
        }
        SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(simpleType);
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getSuperClassName() == null) {
            OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang",null, "String","content", false);
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
    }

    @Override
    public OverlayClass generate(XSComplexType complexType) {
        LOG.debug("Generate overlayclass from complex type {0}", complexType);
        OverlayClass overlayClass = new OverlayClass(OverlayType.ComplexType);
        overlayClass.setNameSpace(complexType.getTargetNamespace());
        overlayClass.setName(complexType.getName() + OverlayType.ComplexType) ;
        if (!complexType.getBaseType().getName().equalsIgnoreCase("anytype")) {
            overlayClass.setSuperClassName(
                    complexType.getBaseType().getName() != null ?
                            complexType.getBaseType().getName() + OverlayType.ComplexType : null);
            overlayClass.setSuperNameSpace(
                    complexType.getBaseType() != null ? complexType.getBaseType().getTargetNamespace() : null);
        }
        overlayClass.getProperties().addAll(generateProperty(complexType));
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
    }


    @Override
    public OverlayClass generate(XSAttributeDecl attribute) {
        LOG.debug("Generate overlayclass from attribute type {0}", attribute);
        OverlayClass overlayClass = new OverlayClass(OverlayType.Attribute);
        overlayClass.setNameSpace(attribute.getTargetNamespace());
        overlayClass.setName(attribute.getName() + OverlayType.Attribute);
        if (attribute.getType().isLocal()) {
            overlayClass.setSuperClassName(attribute.getType().getBaseType().getName() + OverlayType.SimpleType);
            overlayClass.setSuperNameSpace(attribute.getType().getBaseType().getTargetNamespace());
        }  else {
            overlayClass.setSuperClassName(attribute.getType() != null ? attribute.getType().getName() + OverlayType.SimpleType: null);
            overlayClass.setSuperNameSpace(attribute.getType() != null ? attribute.getType().getTargetNamespace() : null);
        }
        SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(attribute.getType());
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getSuperClassName() == null) {
            OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String","content", false);
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
    }

    @Override
    public OverlayClass generate(XSModelGroupDecl modelGroup) {
        LOG.debug("Generate overlayclass from group type {0}", modelGroup);

        OverlayClass overlayClass = new OverlayClass(OverlayType.Group);
        overlayClass.setNameSpace(modelGroup.getTargetNamespace());
        overlayClass.setName(modelGroup.getName() + OverlayType.Group);
        // the overlay class based on group does not extend any base class
        overlayClass.setSuperClassName(null);
        overlayClass.setSuperNameSpace(null);
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
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
    }

    @Override
    public OverlayClass generate(XSAttGroupDecl attrGroup) {
        LOG.debug("Generate overlayclass from attribute group type {0}", attrGroup);
        OverlayClass overlayClass = new OverlayClass(OverlayType.AttrGroup);
        overlayClass.setNameSpace(attrGroup.getTargetNamespace());
        overlayClass.setName(attrGroup.getName() + OverlayType.AttrGroup);

        overlayClass.setSuperClassName(null);
        overlayClass.setSuperNameSpace(null);
        final Collection<? extends XSAttributeUse> declaredAttributeUses = attrGroup.getDeclaredAttributeUses();
        if (declaredAttributeUses != null) {
            final Iterator<? extends XSAttributeUse> iterator = declaredAttributeUses.iterator();
            while(iterator.hasNext()) {
                XSAttributeUse attributeUse = iterator.next();
                overlayClass.getProperties().add(generateProperty(attributeUse.getDecl()));
            }
        }
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
    }

    @Override
    public OverlayClass generate(XSElementDecl element) {
        LOG.debug("Generate overlayclass from element type {0}", element);

        OverlayClass overlayClass = new OverlayClass(OverlayType.Element);
        overlayClass.setNameSpace(element.getTargetNamespace());
        overlayClass.setName(element.getName());
        XSType xsType = element.getType();
        if (xsType.isGlobal()) {
            if (xsType.isComplexType()) {
                overlayClass.setSuperClassName(xsType.getName() + OverlayType.ComplexType);
            } else {
                overlayClass.setSuperClassName(xsType.getName() + OverlayType.SimpleType);
            }
            overlayClass.setSuperNameSpace(xsType.getTargetNamespace());
            return overlayClass;
        }

        if (!xsType.getBaseType().getName().equalsIgnoreCase("AnyType")) {
            overlayClass.setSuperClassName(xsType.getBaseType().getName() + OverlayType.ComplexType);
            overlayClass.setSuperNameSpace(xsType.getBaseType().getTargetNamespace());
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
        LOG.debug("Generated overlayclass {0}", overlayClass);
        return overlayClass;
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
                LOG.warn("No property is added.The overlay class extends already this base type {0} ",
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
            } else {
                LOG.warn("No property is generated for xs particle {0}", xsParticle);
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
