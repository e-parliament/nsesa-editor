package org.nsesa.editor.app.xsd;

import com.sun.xml.xsom.*;
import com.sun.xml.xsom.impl.ElementDecl;
import com.sun.xml.xsom.parser.XSOMParser;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.OverlayClass;
import org.nsesa.editor.app.xsd.model.OverlayProperty;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidgetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Date: 03/08/12 19:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayGenerator.class);

    private static final String OVERLAY_ELEMENT_TEMPLATE_NAME = "overlayClass.ftl";
    private static final String OVERLAY_FACTORY_TEMPLATE_NAME = "overlayFactory.ftl";

    private File generatedSourcesDirectory;

    // Freemarker configuration
    private final Configuration configuration = new Configuration();

    // XSOM parser
    private final XSOMParser parser = new XSOMParser();

    public OverlayGenerator() {
        parser.setErrorHandler(new LoggingErrorHandler());
        configuration.setDefaultEncoding("UTF-8");
    }

    public void parse(final String xsd) throws SAXException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            final File directoryForTemplateLoading = new File(classLoader.getResource(OVERLAY_ELEMENT_TEMPLATE_NAME).getFile()).getParentFile();
            configuration.setDirectoryForTemplateLoading(directoryForTemplateLoading);

            // create the subdirectory name
            final String subDirectory = xsd.contains(".") ? xsd.substring(0, xsd.indexOf(".")) : xsd;
            final File targetDirectoryClasses = new File(classLoader.getResource(".").getFile());
            generatedSourcesDirectory = new File(targetDirectoryClasses.getParentFile().getParentFile(), "src/main/java/org/nsesa/editor/gwt/core/shared/" + subDirectory + "/gen/");
            if (!generatedSourcesDirectory.exists() && !generatedSourcesDirectory.mkdirs()) {
                throw new RuntimeException("Could not create generated source directory " + generatedSourcesDirectory.getAbsolutePath());
            }
            LOG.info("Writing files to {}", generatedSourcesDirectory.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not access template directory.", e);
        }

        parser.parse(classLoader.getResource("xml.xsd"));
        parser.parse(classLoader.getResource(xsd));

    }

    public void analyze(final String packageName, final String xsd) throws SAXException {
        final List<OverlayClass> allGeneratedClasses = new ArrayList<OverlayClass>();
        final XSSchemaSet set = parser.getResult();
        for (final XSSchema schema : set.getSchemas()) {
            final List<OverlayClass> generatedClasses = new ArrayList<OverlayClass>();
            final Collection<XSElementDecl> elementDecls = schema.getElementDecls().values();
            for (final XSElementDecl elementDecl : elementDecls) {
                // create the filename
                final File file = new File(generatedSourcesDirectory, StringUtils.capitalize(elementDecl.getName()) + ".java");
                Map<String, Object> rootMap = new HashMap<String, Object>();
                final OverlayClass overlayClass = generate(elementDecl, packageName);
                rootMap.put("overlayClass", overlayClass);
                generatedClasses.add(overlayClass);
                writeToFile(file, rootMap, OVERLAY_ELEMENT_TEMPLATE_NAME);
            }

            // create the factory
            final String className = StringUtils.capitalize(xsd.substring(0, xsd.indexOf("."))) + "OverlayFactory";
            final File file = new File(generatedSourcesDirectory, className + ".java");

            Map<String, Object> rootMap = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass();
            factoryClass.setName(className);
            factoryClass.setPackageName("org.nsesa.editor.gwt.core.shared." + packageName + ".gen");

            rootMap.put("overlayClass", factoryClass);
            rootMap.put("overlayClasses", generatedClasses);
            writeToFile(file, rootMap, OVERLAY_FACTORY_TEMPLATE_NAME);
        }
    }

    public void writeToFile(File file, Object rootMap, String templateName) {
        FileWriter writer = null;
        try {
            final Template template = configuration.getTemplate(templateName);
            writer = new FileWriter(file);
            final DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
            template.process(rootMap, writer, wrapper);
        } catch (IOException e) {
            throw new RuntimeException("Could not read template or write to file.", e);
        } catch (TemplateException e) {
            throw new RuntimeException("Could not parse template.", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error("Could not close written file '" + file.getAbsolutePath() + "'.", e);
                }
            }
        }
    }


    public static void main(String[] args) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
        OverlayGenerator generator = new OverlayGenerator();
        try {
            final String xsd = "akomantoso20.xsd";
            generator.parse(xsd);
            generator.analyze(xsd.substring(0, xsd.indexOf(".")), xsd);
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }

    public OverlayClass generate(final XSElementDecl elementDecl, final String packageName) {
        final OverlayClass overlayClass = new OverlayClass();
        // TODO: generate the correct package name
        overlayClass.setPackageName("org.nsesa.editor.gwt.core.shared." + packageName + ".gen");
        // TODO: correct the interfaces/extension
        //overlayClass.setInterfaces(new Class<?>[]{AmendableWidget.class});
        overlayClass.setName(elementDecl.getName());
        // some test classes
        findSuperClassAndAttributes(overlayClass, elementDecl.getType());

        return overlayClass;
    }

    public OverlayClass findSuperClassAndAttributes(OverlayClass overlayClass, XSType type) {
        if (type instanceof XSComplexType && !"AnyType".equals(type.getName())) {
            XSComplexType complexType = (XSComplexType) type;
//            overlayClass.setSuperClassName(StringUtils.capitalize(complexType.getName()));
            overlayClass.setSuperClassName(AmendableWidgetImpl.class.getName());
            for (XSAttributeUse attr : complexType.getAttributeUses()) {
                if (!"id".equalsIgnoreCase(attr.getDecl().getName())) {
                    overlayClass.getProperties().add(new OverlayProperty(overlayClass.getPackageName(), StringUtils.capitalize(attr.getDecl().getType().getName()), attr.getDecl().getName() + "Attribute", false));
                }
            }
            final List<XSElementDecl> allowedChildElements = complexType.getElementDecls();
            for (XSElementDecl allowedChild : allowedChildElements) {
                final XSType allowedChildType = allowedChild.getType();
                if (allowedChildType instanceof XSComplexType) {
                    boolean isCollection = false;
                    final XSComplexType xsComplexType = (XSComplexType) allowedChildType;
                    if (xsComplexType.getContentType() instanceof XSParticle) {
                        XSParticle xsParticle = (XSParticle) xsComplexType.getContentType();
                        isCollection = xsParticle.getMaxOccurs().intValue() > 1 || xsParticle.getMaxOccurs().intValue() == -1;
                    }
                    if (!"id".equalsIgnoreCase(allowedChild.getName())) {
                        overlayClass.getProperties().add(new OverlayProperty(overlayClass.getPackageName(), StringUtils.capitalize(allowedChild.getName()), allowedChild.getName() + "Element", isCollection));
                    }
                }
            }
        }
        return overlayClass;
    }

    public static void inspect(XSType type, int depth) {
        if (type instanceof XSComplexType && !("AnyType".equalsIgnoreCase(type.getName()))) {
            XSComplexType complexType = (XSComplexType) type;
            LOG.info(depth(depth) + "which is a {} (subclasses: {})", complexType.getName(), complexType.getSubtypes());
            // display attributes
            for (XSAttributeUse attr : complexType.getAttributeUses()) {
                LOG.info(depth(depth + 1) + "{} (type: {})", attr.getDecl().getName(), attr.getDecl().getType());
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
                        LOG.info(depth(depth + 1) + "=> allowed: {} ", elementDecl.getName());
                    }
                }
            }

            inspect(complexType.getBaseType(), ++depth);
        } else {
            //LOG.info("Type is {}" + type.getName());
        }
    }

    private static String depth(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append("   ");
        }
        return sb.toString();
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

    private static class ExposingObjectWrapper extends DefaultObjectWrapper {

    }
}
