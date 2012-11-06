package org.nsesa.editor.app.xsd;

import com.sun.xml.xsom.*;
import com.sun.xml.xsom.parser.XSOMParser;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Generates a hierarchy of Java classes by parsing an XSD Schema
 * Date: 03/08/12 19:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayGenerator {

    public static final Logger LOG = LoggerFactory.getLogger(OverlayGenerator.class);

    private static final String OVERLAY_ELEMENT_TEMPLATE_NAME = "overlayClass.ftl";
    private static final String OVERLAY_ENUM_TEMPLATE_NAME    = "overlayEnum.ftl";
    private static final String OVERLAY_FACTORY_TEMPLATE_NAME = "overlayFactory.ftl";

    private static final String BASE_DIRECTORY = "src/main/java/org/nsesa/editor/gwt/core/client/ui/overlay/document/gen/";
    private static final String BASE_PACKAGE = "org.nsesa.editor.gwt.core.client.ui.overlay.document.gen.";

    private File generatedSourcesDirectory;

    // Freemarker configuration
    private final Configuration configuration = new Configuration();

    // XSOM parser
    private final XSOMParser parser = new XSOMParser();

    private final List<OverlayClass> generatedClasses = new ArrayList<OverlayClass>();

    public OverlayGenerator() {
        parser.setErrorHandler(new LoggingErrorHandler());
        configuration.setDefaultEncoding("UTF-8");
    }

    /**
     * Parse the xsd schema
     * @param xsd
     * @throws SAXException
     */
    public void parse(final String xsd) throws SAXException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            final File directoryForTemplateLoading =
                    new File(classLoader.getResource(OVERLAY_ELEMENT_TEMPLATE_NAME).getFile()).getParentFile();
            configuration.setDirectoryForTemplateLoading(directoryForTemplateLoading);

            // create the subdirectory name
            final String subDirectory = xsd.contains(".") ? xsd.substring(0, xsd.indexOf(".")) : xsd;
            final File targetDirectoryClasses = new File(classLoader.getResource(".").getFile());
            generatedSourcesDirectory = new File(targetDirectoryClasses.getParentFile().getParentFile(), BASE_DIRECTORY);
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

    /**
     * Analyze the xsd schema and generate overlayclasses
     * @param packageName
     * @param xsd
     * @throws SAXException
     */
    public void analyze(final String packageName, final String xsd) throws SAXException {
        final XSSchemaSet set = parser.getResult();

        OverlayClassGenerator classGenerator = new OverlayClassGeneratorImpl(set.getSchemas());
        generatedClasses.addAll(classGenerator.getResult());
    }

    /**
     * Print overlay classes in the files
     * @param packageName
     * @param xsd
     */
    public void print(String packageName, String xsd) {
        final PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl(packageName);
        final PackageNameGenerator directoryNameGenerator = new PackageNameGeneratorImpl("");

        List<OverlayClass> elementClases = new ArrayList<OverlayClass>();
        for(OverlayClass overlayClass : generatedClasses) {
            String filePackageName = directoryNameGenerator.getPackageName(overlayClass);
            File filePackage = new File(generatedSourcesDirectory, filePackageName);
            if (!filePackage.exists()) {
                try {
                    filePackage.mkdir();
                } catch (Exception e) {
                    throw new RuntimeException("The directory can not be created" + filePackageName);
                }
            }

            final File file = new File(filePackage, StringUtils.capitalize(overlayClass.getClassName()) + ".java");
            try {
                Map<String, Object> rootMap = new HashMap<String, Object>();
                rootMap.put("overlayClass", overlayClass);
                rootMap.put("packageNameGenerator", packageNameGenerator);
                String templateName = OVERLAY_ELEMENT_TEMPLATE_NAME;
                if (overlayClass.isEnumeration()) {
                    templateName = OVERLAY_ENUM_TEMPLATE_NAME;
                }
                writeToFile(file, rootMap, templateName);
            } catch(Exception e) {
                throw new RuntimeException("The class can not be generated " + file.getAbsolutePath());
            }
            if (overlayClass.isElement()) {
                elementClases.add(overlayClass);
            }
        }
            // create the factory
        final String className = StringUtils.capitalize(xsd.substring(0, xsd.indexOf("."))) + "OverlayFactory";
        final File file = new File(generatedSourcesDirectory, className + ".java");

        Map<String, Object> rootMap = new HashMap<String, Object>();

        final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
        factoryClass.setClassName(className);
        factoryClass.setPackageName(BASE_PACKAGE.endsWith(".")
                ? BASE_PACKAGE.substring(0, BASE_PACKAGE.length() - 1) : BASE_PACKAGE);

        rootMap.put("overlayClass", factoryClass);
        rootMap.put("overlayClasses", elementClases);
        rootMap.put("packageNameGenerator", packageNameGenerator);
        writeToFile(file, rootMap, OVERLAY_FACTORY_TEMPLATE_NAME);
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

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
        OverlayGenerator generator = new OverlayGenerator();
        try {
            final String xsd = "akomantoso20.xsd";
            generator.parse(xsd);
            generator.analyze(BASE_PACKAGE, xsd);
//            generator.print(BASE_PACKAGE, xsd);
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
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
