package org.nsesa.editor.gwt.core.server.generator;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.sun.xml.xsom.*;
import com.sun.xml.xsom.impl.ElementDecl;
import com.sun.xml.xsom.parser.XSOMParser;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Overlay;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Date: 04/08/12 17:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class GwtOverlayGenerator extends Generator {

    public static final Logger LOG = LoggerFactory.getLogger(GwtOverlayGenerator.class);

    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
        final TypeOracle oracle = context.getTypeOracle();
        try {
            final JClassType type = oracle.getType(typeName);
            final String packageName = type.getPackage().getName();

            final Overlay overlay = type.getAnnotation(Overlay.class);
            if (overlay == null) {
                throw new RuntimeException("No @Overlay annotation specified on OverlayFactory " + type.getName());
            }
            String xsd = overlay.value();
            if (xsd == null || "".equals(xsd.trim())) {
                throw new RuntimeException("No xsd value specified on @Overlay annotation on " + type.getName());
            }

            final String implementationName = type.getName() + "_GeneratedImpl";
            ClassSourceFileComposerFactory mainComposer = new ClassSourceFileComposerFactory(packageName, implementationName);

            PrintWriter printWriter = context.tryCreate(logger, mainComposer.getCreatedPackage(), mainComposer.getCreatedClassShortName());
            if (printWriter != null) {
                SourceWriter writer = mainComposer.createSourceWriter(context, printWriter);

                writer.commit(logger);
            }

            generateElements(xsd, packageName, context, logger);


            final String interfaceName = OverlayFactory.class.getName();
            mainComposer.addImplementedInterface(interfaceName);


            return mainComposer.getCreatedClassName();

        } catch (Exception e) {
            logger.log(TreeLogger.ERROR, "unable to generate code for " + typeName, e);
            throw new UnableToCompleteException();
        }
    }

    public void generateElements(final String xsd, final String packageName, GeneratorContext context, TreeLogger logger) {
        // try to load the XSD
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //DOMConfigurator.configure(classLoader.getResource("log4j.xml"));

        try {
            XSOMParser parser = new XSOMParser();
            parser.setErrorHandler(new LoggingErrorHandler());
            parser.parse(classLoader.getResource("xml.xsd"));
            parser.parse(classLoader.getResource(xsd));
            XSSchemaSet set = parser.getResult();

            for (final XSSchema schema : set.getSchemas()) {
                for (Map.Entry<String, XSElementDecl> entry : schema.getElementDecls().entrySet()) {
                    final XSElementDecl elementDecl = entry.getValue();

                    final String elementClassName = elementDecl.getName().substring(0, 1).toUpperCase() + elementDecl.getName().substring(1);
                    String parentElementClassName = AmendableWidgetImpl.class.getSimpleName();
                    if (elementDecl.getType().getName() != null && !elementDecl.getType().getName().equalsIgnoreCase(elementClassName)) {
                        parentElementClassName = elementDecl.getType().getName().substring(0, 1).toUpperCase() + elementDecl.getType().getName().substring(1);
                    }

                    LOG.info("{} -> {}", elementClassName, parentElementClassName + " (" + elementDecl.getType().getBaseType().getName() + ")");

                    //final ClassSourceFileComposerFactory elementComposer = new ClassSourceFileComposerFactory(packageName, elementClassName);

                    final XSType xsType = elementDecl.getType();
                    //inspect(xsType, 0);

                    /*PrintWriter elementWriter = context.tryCreate(logger, elementComposer.getCreatedPackage(), elementComposer.getCreatedClassShortName());
                    if (elementWriter != null) {
                        SourceWriter writer = elementComposer.createSourceWriter(context, elementWriter);

                        writer.commit(logger);
                    }*/
                }
            }

        } catch (SAXException e) {
            LOG.error("Could not parse XSD schema " + xsd, e);
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
