package org.nsesa.editor.gwt.core.server.generator;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.sun.xml.xsom.*;
import com.sun.xml.xsom.impl.ElementDecl;
import org.nsesa.editor.app.xsd.OverlayGenerator;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Overlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Date: 04/08/12 17:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GwtOverlayGenerator extends Generator {

    private static final String OVERLAY_FACTORY_CLASS_NAMES_PROPERTY = "overlayFactories";

    public static final Logger LOG = LoggerFactory.getLogger(GwtOverlayGenerator.class);
    public static final OverlayGenerator OVERLAY_GENERATOR = new OverlayGenerator();

    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
        final TypeOracle oracle = context.getTypeOracle();
        try {


            final PropertyOracle propertyOracle = context.getPropertyOracle();


            final ConfigurationProperty overlayFactoriesProperty = propertyOracle.getConfigurationProperty(OVERLAY_FACTORY_CLASS_NAMES_PROPERTY);

            final String className = overlayFactoriesProperty.getValues().get(0);

            final Class<?> type = Class.forName(className);
            final String packageName = type.getPackage().getName();
            final Overlay overlay = type.getAnnotation(Overlay.class);
            if (overlay == null) {
                throw new RuntimeException("No @Overlay annotation specified on OverlayFactory " + type.getName());
            }
            String xsd = overlay.value();
            if (xsd == null || "".equals(xsd.trim())) {
                throw new RuntimeException("No xsd value specified on @Overlay annotation on " + type.getName());
            }

            // generate the matching elements
            generateOverlayElements(xsd);

            final String xsdFileName = xsd.substring(0, xsd.indexOf("."));


            /*final String implementationName = type.getName() + "_GeneratedImpl";
            final ClassSourceFileComposerFactory mainComposer = new ClassSourceFileComposerFactory(packageName, implementationName);

            PrintWriter printWriter = context.tryCreate(logger, mainComposer.getCreatedPackage(), mainComposer.getCreatedClassShortName());
            if (printWriter != null) {
                SourceWriter writer = mainComposer.createSourceWriter(context, printWriter);
                writer.indent();
                writer.println("// this is a test");
                writer.commit(logger);
            }



            final String interfaceName = OverlayFactory.class.getName();
            mainComposer.addImplementedInterface(interfaceName);*/


            return "org.nsesa.editor.gwt.core.shared." + xsdFileName + ".gen." + StringUtils.capitalize(xsdFileName) + "OverlayFactory";


        } catch (Exception e) {
            logger.log(TreeLogger.ERROR, "unable to generate code for " + typeName, e);
            throw new UnableToCompleteException();
        }
    }


    private void generateOverlayElements(final String xsd) {
        try {
            OVERLAY_GENERATOR.parse(xsd);
            OVERLAY_GENERATOR.analyze(xsd.substring(0, xsd.indexOf(".")), xsd);
        } catch (SAXException e) {
            throw new RuntimeException("Could not parse XSD " + xsd, e);
        }
    }

    public static void inspect(XSType type, int depth) {
        if (type instanceof XSComplexType && !("AnyType".equalsIgnoreCase(type.getName()))) {
            XSComplexType complexType = (XSComplexType) type;
            //LOG.info("which is a {} (subclasses: {})", complexType.getName(), complexType.getSubtypes());
            // display attributes
            for (XSAttributeUse attr : complexType.getAttributeUses()) {
                LOG.info("{} (type: {})", attr.getDecl().getName(), attr.getDecl().getType());
            }
            // display allowed children
            final XSContentType contentType = complexType.getContentType();
            if (contentType instanceof XSParticle) {
                XSParticle xsParticle = (XSParticle) contentType;
                final XSTerm term = xsParticle.getTerm();
                final XSModelGroup modelGroup = term.asModelGroup();
                for (XSParticle childParticle : modelGroup.getChildren()) {
                    final XSTerm childParticleTerm = childParticle.getTerm();
                    if (childParticleTerm instanceof ElementDecl) {
                        ElementDecl elementDecl = (ElementDecl) childParticleTerm;
                        LOG.info("=> allowed: {} ", elementDecl.getName());
                    }
                }
            }

            inspect(complexType.getBaseType(), ++depth);
        } else {
            //LOG.info("Type is {}" + type.getName());
        }
    }

    private static class LoggingErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException e) throws SAXException {
            LOG.info("Warning: " + e.getMessage(), e);
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            LOG.warn("Error: " + e.getMessage(), e);
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            LOG.error("Fatal: " + e.getMessage(), e);
        }
    }
}
