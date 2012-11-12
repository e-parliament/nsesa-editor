package org.nsesa.editor.app.xsd;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.nsesa.editor.app.xsd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Generates a hierarchy of Java classes by parsing an XSD Schema
 * User: sgroza
 * Date: 06/11/12
 * Time: 09:09
 */
public class FileClassOverlayGenerator extends OverlayGenerator {
    public static final Logger LOG = LoggerFactory.getLogger(FileClassOverlayGenerator.class);

    private static final String OVERLAY_ELEMENT_TEMPLATE_NAME = "overlayClass.ftl";
    private static final String OVERLAY_ENUM_TEMPLATE_NAME    = "overlayEnum.ftl";
    private static final String OVERLAY_FACTORY_TEMPLATE_NAME = "overlayFactory.ftl";

    private static final String BASE_DIRECTORY = "src/main/java/org/nsesa/editor/gwt/core/client/ui/overlay/document/gen/";
    private static final String BASE_PACKAGE = "org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.";

    // a wrapper over overlay property to keep the collection flag
    private static final class OverlayPropertyWrapper {
        private OverlayProperty property;
        private boolean parentCollection;

        OverlayPropertyWrapper(OverlayProperty property, boolean parentCollection) {
            this.property = property;
            this.parentCollection = parentCollection;
        }

        public boolean isParentCollection() {
            return parentCollection;
        }

        public void setParentCollection(boolean parentCollection) {
            this.parentCollection = parentCollection;
        }

        public OverlayProperty getProperty() {
            return property;
        }

        public void setProperty(OverlayProperty property) {
            this.property = property;
        }
    }

    private File generatedSourcesDirectory;

    // Freemarker configuration
    private final Configuration configuration;
    private String mainSchema;
    private String basePackageName;

    public FileClassOverlayGenerator(String mainSchema, String basePackageName) {
        super();
        this.mainSchema = mainSchema;
        this.basePackageName = basePackageName;
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    /**
     * Print overlay classes in the files
     */
    public void print() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final File directoryForTemplateLoading =
                    new File(classLoader.getResource(OVERLAY_ELEMENT_TEMPLATE_NAME).getFile()).getParentFile();
            configuration.setDirectoryForTemplateLoading(directoryForTemplateLoading);
            // create the subdirectory name
            final File targetDirectoryClasses = new File(classLoader.getResource(".").getFile());
            generatedSourcesDirectory = new File(targetDirectoryClasses.getParentFile().getParentFile(), BASE_DIRECTORY);
            if (!generatedSourcesDirectory.exists() && !generatedSourcesDirectory.mkdirs()) {
                throw new RuntimeException("Could not create generated source directory " + generatedSourcesDirectory.getAbsolutePath());
            }
            LOG.info("Writing files to {}", generatedSourcesDirectory.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not access template directory.", e);
        }

        final PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl(basePackageName);
        final PackageNameGenerator directoryNameGenerator = new PackageNameGeneratorImpl("");

        List<OverlayClass> generatedClasses = getFlatListWithNoGroups(overlayClassGenerator.getResult());
        // keep only the element overlay classes
        List<OverlayClass> elementClases = new ArrayList<OverlayClass>();

        for(OverlayClass overlayClass : generatedClasses) {

            String filePackageName = directoryNameGenerator.getPackageName(overlayClass);
            if (filePackageName == null || filePackageName.length() == 0) {
                continue;
            }
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
        final String className = StringUtils.capitalize(mainSchema) + "OverlayFactory";
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
        FileClassOverlayGenerator generator = new FileClassOverlayGenerator("akomantoso20", BASE_PACKAGE);
        try {
            final String[] xsds = {"akomantoso20.xsd", "xml.xsd"};
            generator.parse(xsds);
            generator.analyze();
            generator.print();
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }

    /**
     * Returns a flat list of overlay classes by traversing the root class and exclude the root, schema class type
     * and group classes
     * @param rootClass The root overlay class that will be traversed
     * @return A List of overlay classes
     */
    private List<OverlayClass> getFlatListWithNoGroups(OverlayClassGenerator.OverlayRootClass rootClass) {
        Stack<OverlayClass> stack = new Stack<OverlayClass>();
        List<OverlayClass> result = new ArrayList<OverlayClass>();
        stack.push(rootClass);
        while (!stack.isEmpty()) {
            OverlayClass aClass = stack.pop();
//            skip creation for such classes
            if (!(aClass instanceof OverlayClassGenerator.OverlayRootClass ||
                    aClass instanceof OverlayClassGenerator.OverlaySchemaClass ||
                    aClass.getOverlayType().equals(OverlayType.GroupDecl) ||
                    aClass.getOverlayType().equals(OverlayType.Group) ||
                    aClass.getOverlayType().equals(OverlayType.AttrGroup)
                    )) {
                // replace the properties
                replaceGroupProperties(aClass);
//                add now in the result
                result.add(aClass);
            }
            OverlayClass[] children = aClass.getChildren().toArray(new OverlayClass[]{});
            //Collections.sort(children, comparator);
            for (int i = children.length - 1; i >= 0 ; i--) {
                stack.push(children[i]);
            }
        }
        return result;
    }

    // replace the group properties with their collection of simple properties
    private void replaceGroupProperties(OverlayClass aClass) {
        List<OverlayPropertyWrapper> stack = new ArrayList<OverlayPropertyWrapper>();
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        for (OverlayProperty property : aClass.getProperties()) {
            stack.add(new OverlayPropertyWrapper(property, property.isCollection()));
        }
        while (!stack.isEmpty()) {
            OverlayPropertyWrapper propertyWrapper = stack.remove(0);
            OverlayProperty property = propertyWrapper.getProperty();
            Boolean parentCollection = propertyWrapper.isParentCollection();
            OverlayClass baseClass = property.getBaseClass();
            if (baseClass != null) {
                 // check whether is group or attribute group and replace with its properties
                if (baseClass.getOverlayType().equals(OverlayType.AttrGroup) ||
                        baseClass.getOverlayType().equals(OverlayType.Group) ||
                        baseClass.getOverlayType().equals(OverlayType.GroupDecl)) {
                    // add in the stack the properties of the base class and parent
                    OverlayClass parent = baseClass;
                    while (parent != null) {
                        for (OverlayProperty parentProp : parent.getProperties()) {
                            // save the previous collection flag
                            stack.add(new OverlayPropertyWrapper(parentProp, parentCollection || parentProp.isCollection()));
                        }
                        parent = parent.getParent();
                    }
                } else {
                    //create a new copy of overlay property and set its collection flag
                    OverlayProperty newProperty = property.copy();
                    newProperty.setCollection(parentCollection || property.isCollection());
                    result.add(newProperty);
                }
            } else {
                    //create a new copy of overlay property and set its collection flag
                OverlayProperty newProperty = property.copy();
                newProperty.setCollection(parentCollection || property.isCollection());
                result.add(newProperty);
            }
        }
        aClass.setProperties(result);
    }
}
