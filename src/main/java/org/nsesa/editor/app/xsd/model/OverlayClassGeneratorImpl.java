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

import com.sun.xml.xsom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An implementation of {@link OverlayClassGenerator} interface
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 18/10/12 15:31
 */
public class OverlayClassGeneratorImpl implements OverlayClassGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(OverlayClassGeneratorImpl.class);

    private int counter;

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
    public void generate(final XSSimpleType simpleType) {
        LOG.debug("Generate overlayclass from simple type {}", simpleType);
        generatedClasses.add(generateClass(simpleType));
    }

    @Override
    public void generate(final XSComplexType complexType) {
        LOG.debug("Generate overlayclass from complex type {}", complexType);
        if ("hierarchy".equalsIgnoreCase(complexType.getName())) {
            LOG.debug("Generate overlayclass from hierarchical complex type {}", complexType);
        }
        final OverlayClass overlayClass = new OverlayClass(complexType.getName(), complexType.getTargetNamespace(), OverlayType.ComplexType);
        if (complexType.getAnnotation() != null && complexType.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(complexType.getAnnotation().getAnnotation().toString());
        }
        overlayClass.setClassName(complexType.getName() + OverlayType.ComplexType);
        if (complexType.getBaseType() != null && !complexType.getBaseType().getName().equalsIgnoreCase("anytype")) {
            final OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.ComplexType);
            parent.setName(complexType.getBaseType().getName());
            parent.setClassName(parent.getName() + OverlayType.ComplexType);
            parent.setNamespaceURI(complexType.getBaseType().getTargetNamespace());
            overlayClass.setParent(parent);
        }
        overlayClass.getProperties().addAll(generateProperty(complexType));
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(final XSAttributeDecl attribute) {
        LOG.debug("Generate overlayclass from attribute type {}", attribute);
        OverlayClass overlayClass = new OverlayClass(attribute.getName(), attribute.getTargetNamespace(), OverlayType.Attribute);
        if (attribute.getAnnotation() != null && attribute.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(attribute.getAnnotation().getAnnotation().toString());
        }

        overlayClass.setClassName(attribute.getName() + OverlayType.Attribute);
        if (attribute.getType().isLocal()) {
            final OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.SimpleType);
            parent.setName(attribute.getType().getBaseType().getName());
            parent.setClassName(parent.getName() + OverlayType.SimpleType);
            parent.setNamespaceURI(attribute.getType().getBaseType().getTargetNamespace());
            overlayClass.setParent(parent);
        } else {
            if (attribute.getType() != null) {
                final OverlayClass parent = new OverlayClass();
                parent.setOverlayType(OverlayType.SimpleType);
                parent.setName(attribute.getType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNamespaceURI(attribute.getType().getTargetNamespace());
                overlayClass.setParent(parent);
            }
        }
        final SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(attribute.getType());
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getParent() == null) {
            final OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String", "value", false, true);
            property.setBaseClass(new OverlayClass("String", "java.lang", OverlayType.SimpleType));
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(final XSModelGroupDecl modelGroup) {
        LOG.debug("Generate overlayclass from group type {}", modelGroup);

        final OverlayClass overlayClass = new OverlayClass(modelGroup.getName(), modelGroup.getTargetNamespace(), OverlayType.Group);
        if (modelGroup.getAnnotation() != null && modelGroup.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(modelGroup.getAnnotation().getAnnotation().toString());
        }

        overlayClass.setClassName(modelGroup.getName() + OverlayType.Group);

        final XSModelGroup xsModelGroup = modelGroup.getModelGroup();
        OverlayType type = null;
        if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.ALL)) {
            type = OverlayType.GroupAll;
        } else if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.CHOICE)) {
            type = OverlayType.GroupChoice;
        } else if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.SEQUENCE)) {
            type = OverlayType.GroupSequence;
        } else {
            throw new RuntimeException(xsModelGroup.getCompositor() + " is not treated;");
        }
        counter++;
        OverlayClass baseClass = new OverlayClass(xsModelGroup.getCompositor().name().toLowerCase() + "_" + counter,
                null, type);
        generatedClasses.add(baseClass);

        final OverlayProperty property = new OverlayProperty(type, null,
                null,
                xsModelGroup.getCompositor().name().toLowerCase() + "_" + counter,
                xsModelGroup.getCompositor().name().toLowerCase(), false, false);

        property.setBaseClass(baseClass);

        property.setMinOccurs(1);
        property.setMaxOccurs(1);

        overlayClass.getProperties().add(property);

        // the overlay class based on group does not extend any base class
        final XSParticle[] particles = xsModelGroup.getChildren();
        // create a property for each particle in the collection
        for (final XSParticle particle : particles) {
            generateProperty(particle, baseClass.getProperties(), isCollection(particle));
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(final XSAttGroupDecl attrGroup) {
        LOG.debug("Generate overlayclass from attribute group type {}", attrGroup);
        if (attrGroup.getName().equalsIgnoreCase("core")) {
            LOG.debug("Generate overlayclass from attribute group type {}", attrGroup);
        }
        OverlayClass overlayClass = new OverlayClass(attrGroup.getName(), attrGroup.getTargetNamespace(), OverlayType.AttrGroup);
        if (attrGroup.getAnnotation() != null && attrGroup.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(attrGroup.getAnnotation().getAnnotation().toString());
        }

        overlayClass.setClassName(attrGroup.getName() + OverlayType.AttrGroup);

        final Collection<? extends XSAttributeUse> declaredAttributeUses = attrGroup.getDeclaredAttributeUses();
        if (declaredAttributeUses != null) {
            for (final XSAttributeUse attributeUse : declaredAttributeUses) {
                overlayClass.getProperties().add(generateProperty(attributeUse));
            }
        }
        final Collection<? extends XSAttGroupDecl> attGroups = attrGroup.getAttGroups();
        if (attGroups != null) {
            for (final XSAttGroupDecl attGroupDecl : attGroups) {
                overlayClass.getProperties().add(generateProperty(attGroupDecl));
            }
        }

        if (attrGroup.getAttributeWildcard() != null) {
            // add wildcard property as an amendable widget
            final OverlayProperty property = new OverlayProperty(OverlayType.WildcardType, "java.lang", null, "String", "wildcardContent", false, true);
            property.setBaseClass(new OverlayClass("String", "java.lang", OverlayType.WildcardType));
            overlayClass.getProperties().add(property);
        }

        LOG.debug("Generated overlayclass {}", overlayClass);
        generatedClasses.add(overlayClass);
    }

    @Override
    public void generate(final XSElementDecl element) {
        LOG.debug("Generate overlayclass from element type {}", element);
        if (element.getName().equalsIgnoreCase("BlockList")) {
            LOG.debug("Generate overlayclass from element {}", element);
        }

        final OverlayClass overlayClass = new OverlayClass(element.getName(), element.getTargetNamespace(), OverlayType.Element);
        if (element.getAnnotation() != null && element.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(element.getAnnotation().getAnnotation().toString());
        }

        overlayClass.setClassName(element.getName());
        final XSType xsType = element.getType();
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
            return;
        }

        if (!xsType.getBaseType().getName().equalsIgnoreCase("AnyType")) {
            final OverlayClass parent = new OverlayClass(xsType.getBaseType().getName(),
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
    public OverlayRootClass getResult() {
        final OverlayRootClass rootClass = new OverlayRootClass();
        cache.put(rootClass, rootClass);
        for (final XSSchema schema : schemas) {
            OverlaySchemaClass schemaClass = new OverlaySchemaClass(schema.getTargetNamespace());
            rootClass.getChildren().add(schemaClass);
            cache.put(schemaClass, schemaClass);
        }
        for (final OverlayClass aClass : generatedClasses) {
            // get the parent from cache if possible
            OverlayClass parentClass;
            if (aClass.getParent() != null) {
                parentClass = cache.get(aClass.getParent());
            } else {
                // identify the schema class
                OverlaySchemaClass schemaClass = new OverlaySchemaClass(aClass.getNamespaceURI());
                parentClass = cache.get(schemaClass);
                if (parentClass == null) {
                    parentClass = rootClass;
                }
            }
            parentClass.getChildren().add(aClass);
            aClass.setParent(parentClass);
            // for each property of aClass set the base class from cache
            for (final OverlayProperty property : aClass.getProperties()) {
                property.setBaseClass(cache.get(property.getBaseClass()));
            }
        }
        return rootClass;
    }

    @Override
    public void generate(final Collection<XSSchema> schemas) {
        this.schemas = schemas;
        generatedClasses = new ArrayList<OverlayClass>();
        for (final XSSchema schema : schemas) {
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
        for (final OverlayClass overlayClass : generatedClasses) {
            if (cache.containsKey(overlayClass)) {
                throw new RuntimeException("Stop generation... The class already exists:" + overlayClass);
            }
            cache.put(overlayClass, overlayClass);
        }
        LOG.info("{} classes will be generated", generatedClasses.size());

    }

    /**
     * Generate {@link OverlayProperty} property from xsd group component
     *
     * @param attGroupDecl The xsd group processed
     * @return An overlay property based on the given xsd group
     */
    private OverlayProperty generateProperty(final XSAttGroupDecl attGroupDecl) {
        final OverlayProperty property = new OverlayProperty(OverlayType.AttrGroup, null, attGroupDecl.getTargetNamespace(),
                attGroupDecl.getName() + OverlayType.AttrGroup, attGroupDecl.getName(), false, true);
        if (attGroupDecl.getAnnotation() != null && attGroupDecl.getAnnotation().getAnnotation() != null) {
            property.setComments(attGroupDecl.getAnnotation().getAnnotation().toString());
        }

        property.setBaseClass(new OverlayClass(attGroupDecl.getName(), attGroupDecl.getTargetNamespace(), OverlayType.AttrGroup));
        return property;
    }

    /**
     * Generate {@link OverlayProperty} property from xsd attribute component
     *
     * @param attributeUse The xsd attribute processed
     * @return An overlay property based on the given xsd attribute
     */
    private OverlayProperty generateProperty(final XSAttributeUse attributeUse) {
        final XSAttributeDecl attributeDecl = attributeUse.getDecl();
        final String className = attributeDecl.getType().isLocal() ?
                attributeDecl.getType().getBaseType().getName() : attributeDecl.getType().getName();
        final String nameSpace = attributeDecl.getType().isLocal() ?
                attributeDecl.getType().getBaseType().getTargetNamespace() : attributeDecl.getType().getTargetNamespace();
        final OverlayProperty property = new OverlayProperty(OverlayType.Attribute, null,
                nameSpace,
                className + OverlayType.SimpleType,
                attributeDecl.getName(), false, true);

        property.setRequired(attributeUse.isRequired());

        if (attributeDecl.getAnnotation() != null && attributeDecl.getAnnotation().getAnnotation() != null) {
            property.setComments(attributeDecl.getAnnotation().getAnnotation().toString());
        }

        property.setBaseClass(new OverlayClass(className, nameSpace, OverlayType.SimpleType));
        return property;
    }

    /**
     * Generate {@link OverlayProperty} property from xsd simple type component
     *
     * @param simpleType The xsd simple type processed
     * @return An overlay property based on the given xsd simple type
     */
    private OverlayProperty generateProperty(final XSSimpleType simpleType) {
        final OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String", simpleType.getName(), false, false);
        if (simpleType.getAnnotation() != null && simpleType.getAnnotation().getAnnotation() != null) {
            property.setComments(simpleType.getAnnotation().getAnnotation().toString());
        }
        property.setBaseClass(new OverlayClass("String", "java.lang", OverlayType.Unknown));
        return property;
    }

    /**
     * Generate {@link OverlayProperty} property from xsd complex type component
     *
     * @param complexType The xsd complex type processed
     * @return A list of overlay properties based on the given xsd complex type
     */
    private List<OverlayProperty> generateProperty(final XSComplexType complexType) {
        if ("modType".equalsIgnoreCase(complexType.getName())) {
            LOG.debug("Generate overlayclass from complexType {}", complexType);
        }
        final List<OverlayProperty> properties = new ArrayList<OverlayProperty>();
        // treat the groups and the attributes
        final Collection<? extends XSAttributeUse> declaredAttributeUses = complexType.getDeclaredAttributeUses();
        if (declaredAttributeUses != null) {
            for (final XSAttributeUse attributeUse : declaredAttributeUses) {
                properties.add(generateProperty(attributeUse));
            }
        }

        final Collection<? extends XSAttGroupDecl> declaredAttrGroups = complexType.getAttGroups();
        if (declaredAttrGroups != null) {
            for (final XSAttGroupDecl attrGroup : declaredAttrGroups) {
                properties.add(generateProperty(attrGroup));
            }
        }
        if (complexType.getExplicitContent() == null || complexType.getExplicitContent().asEmpty() != null) {
            if ("anyType".equalsIgnoreCase(complexType.getBaseType().getName())) {
                final XSParticle xsParticle = complexType.getContentType().asParticle();
                generateProperty(xsParticle, properties, false);
            } else {
                // the overlay class extends already this base type
                LOG.warn("No property is added.The overlay class extends already this base type {} ",
                        complexType.getBaseType());
            }
        } else {
            final XSParticle xsParticle = complexType.getExplicitContent().asParticle();
            generateProperty(xsParticle, properties, false);
        }
        return properties;
    }

    /**
     * A recursive method to create overlay properties from the {@link XSParticle} component.
     *
     * @param xsParticle    The particle to be processed
     * @param properties    The list with properties
     * @param wasCollection Flag to keep the property need to be generated as collection
     */
    private void generateProperty(final XSParticle xsParticle, final List<OverlayProperty> properties, final boolean wasCollection) {
        if (xsParticle != null) {
            boolean isCollection = isCollection(xsParticle);
            if (xsParticle.getTerm().isElementDecl()) {
                final OverlayProperty property = new OverlayProperty(OverlayType.Element, null,
                        xsParticle.getTerm().asElementDecl().getTargetNamespace(),
                        xsParticle.getTerm().asElementDecl().getName(),
                        xsParticle.getTerm().asElementDecl().getName(), isCollection || wasCollection, false);
                property.setBaseClass(new OverlayClass(xsParticle.getTerm().asElementDecl().getName(),
                        xsParticle.getTerm().asElementDecl().getTargetNamespace(),
                        OverlayType.Element));
                property.setMinOccurs(xsParticle.getMinOccurs().intValue());
                property.setMaxOccurs(xsParticle.getMaxOccurs().intValue());

                properties.add(property);
            } else if (xsParticle.getTerm().isModelGroupDecl()) {
                final OverlayProperty property = new OverlayProperty(OverlayType.Group, null,
                        xsParticle.getTerm().asModelGroupDecl().getTargetNamespace(),
                        xsParticle.getTerm().asModelGroupDecl().getName() + OverlayType.Group,
                        xsParticle.getTerm().asModelGroupDecl().getName(), isCollection || wasCollection, false);
                property.setBaseClass(new OverlayClass(xsParticle.getTerm().asModelGroupDecl().getName(),
                        xsParticle.getTerm().asModelGroupDecl().getTargetNamespace(),
                        OverlayType.Group));
                property.setMinOccurs(xsParticle.getMinOccurs().intValue());
                property.setMaxOccurs(xsParticle.getMaxOccurs().intValue());

                properties.add(property);
            } else if (xsParticle.getTerm().isModelGroup()) {
                final XSModelGroup xsModelGroup = xsParticle.getTerm().asModelGroup();
                final OverlayType type;
                if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.ALL)) {
                    type = OverlayType.GroupAll;
                } else if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.CHOICE)) {
                    type = OverlayType.GroupChoice;
                } else if (xsModelGroup.getCompositor().equals(XSModelGroup.Compositor.SEQUENCE)) {
                    type = OverlayType.GroupSequence;
                } else {
                    throw new RuntimeException(xsModelGroup.getCompositor() + " is not treated;");
                }
                counter++;
                final OverlayClass baseClass = new OverlayClass(xsModelGroup.getCompositor().name().toLowerCase() + "_" + counter,
                        null, type);
                generatedClasses.add(baseClass);

                final OverlayProperty property = new OverlayProperty(type, null,
                        null,
                        xsModelGroup.getCompositor().name().toLowerCase() + "_" + counter,
                        xsModelGroup.getCompositor().name().toLowerCase(), isCollection || wasCollection, false);

                property.setBaseClass(baseClass);
                property.setMinOccurs(xsParticle.getMinOccurs().intValue());
                property.setMaxOccurs(xsParticle.getMaxOccurs().intValue());
                properties.add(property);

                // add children in the property now
                final XSParticle[] particles = xsModelGroup.getChildren();
                for (final XSParticle particle : particles) {
                    generateProperty(particle, baseClass.getProperties(), isCollection || wasCollection);
                }
            } else if (xsParticle.getTerm().isWildcard()) {
                final OverlayProperty property = new OverlayProperty(OverlayType.WildcardType, "java.lang", null, "String",
                        "wildcardContent", isCollection || wasCollection, false);
                property.setBaseClass(new OverlayClass("String", "java.lang", OverlayType.WildcardType));
                property.setMinOccurs(xsParticle.getMinOccurs().intValue());
                property.setMaxOccurs(xsParticle.getMaxOccurs().intValue());

                properties.add(property);
            } else {
                LOG.warn("No property is generated for xs particle {}", xsParticle);
            }
        }
    }

    /**
     * Checks whether or not the xs particle needs to be treated as a collection
     *
     * @param particle The particle to be checked
     * @return True when the particle is a collection
     */
    private boolean isCollection(final XSParticle particle) {
        return particle.getMaxOccurs().intValue() > 1 || particle.getMaxOccurs().intValue() == -1;
    }

    /**
     * Generate a class from simple type.
     *
     * @param simpleType the simple type to generate the overlay class for
     * @return the overlay class
     */
    private OverlayClass generateClass(final XSSimpleType simpleType) {
        final OverlayClass overlayClass = new OverlayClass(simpleType.getName(), simpleType.getTargetNamespace(), OverlayType.SimpleType);
        if (simpleType.getAnnotation() != null && simpleType.getAnnotation().getAnnotation() != null) {
            overlayClass.setComments(simpleType.getAnnotation().getAnnotation().toString());
        }

        overlayClass.setClassName(simpleType.getName() + OverlayType.SimpleType);
        if (simpleType.getSimpleBaseType() != null) {
            final OverlayClass parent = new OverlayClass();
            parent.setOverlayType(OverlayType.SimpleType);
            if (simpleType.getSimpleBaseType().isLocal()) {
                parent.setName(simpleType.getSimpleBaseType().getBaseType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNamespaceURI(simpleType.getSimpleBaseType().getBaseType().getTargetNamespace());
            } else {
                parent.setName(simpleType.getSimpleBaseType().getName());
                parent.setClassName(parent.getName() + OverlayType.SimpleType);
                parent.setNamespaceURI(simpleType.getSimpleBaseType().getTargetNamespace());
            }
            overlayClass.setParent(parent);
        }
        final SimpleTypeRestriction typeRestriction = SimpleTypeRestriction.getRestriction(simpleType);
        overlayClass.setRestriction(typeRestriction);
        if (overlayClass.getParent() == null) {
            final OverlayProperty property = new OverlayProperty(OverlayType.SimpleType, "java.lang", null, "String", "value", false, false);
            property.setBaseClass(new OverlayClass("String", "java.lang", OverlayType.SimpleType));
            overlayClass.getProperties().add(property);
        }
        LOG.debug("Generated overlayclass {}", overlayClass);
        return overlayClass;
    }
}
