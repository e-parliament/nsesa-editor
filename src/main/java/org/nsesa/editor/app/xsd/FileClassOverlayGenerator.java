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
 * Get the result of XSD parsing and based on Freemarker template mechanism it generate a class for each element
 * defined in XSD schema. A factory API is also generated in order to facilitate the object's creation.
 * <p>
 * The generator is coming with the following predefined Freemarker templates which shall exist
 * before running the program:
 * <p>
 *     overlayClass.ftl : responsible for classes generation
 * <p>
 *     overlayEnum.ftl : responsible for classes of type enum generation
 * <p>
 *    overlayFactory.ftl: responsible for factories generation
 * <p>
 *    overlayLocalizableResource.ftl,overlayMessagesProperties.ftl, overlayMessagesProperties.ftl:
 *    responsible for localizable resources generation
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 06/11/12 9:09
 */
public class FileClassOverlayGenerator extends OverlayGenerator {
    public static final Logger LOG = LoggerFactory.getLogger(FileClassOverlayGenerator.class);

    // freemarker templates used to generate classes
    private static final String OVERLAY_ELEMENT_TEMPLATE_NAME = "overlayClass.ftl";
    private static final String OVERLAY_ENUM_TEMPLATE_NAME = "overlayEnum.ftl";
    private static final String OVERLAY_FACTORY_TEMPLATE_NAME = "overlayFactory.ftl";
    private static final String OVERLAY_RESOURCE_TEMPLATE_NAME = "overlayLocalizableResource.ftl";
    private static final String OVERLAY_MESSAGE_PROP_TEMPLATE_NAME = "overlayMessagesProperties.ftl";
    private static final String OVERLAY_MESSAGE_TEMPLATE_NAME = "overlayMessages.ftl";

    /**
     * A holder for overlay property. It will keep a flag to identify whether the parent was
     * used as a collection or not
     */
    private static final class OverlayPropertyHolder {
        private OverlayProperty property;
        private boolean parentCollection;

        OverlayPropertyHolder(OverlayProperty property, boolean parentCollection) {
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
    private String basePackageName;
    private String targetDirectory;

    /**
     * Constructor
     *
     * @param basePackageName The base package name
     * @param targetDirectory The base location where the files will be saved
     */
    public FileClassOverlayGenerator(String basePackageName, String targetDirectory) {
        super();
        this.basePackageName = basePackageName;
        this.targetDirectory = targetDirectory;
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    /**
     * Generate files containing the source code for classes, for factories.
     *
     */
    public void print() {

        final PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl(basePackageName);
        final PackageNameGenerator directoryNameGenerator = new PackageNameGeneratorImpl("");

        //generate source directory
        generateSourcesDirectory();

        OverlayClassGenerator.OverlayRootClass root = overlayClassGenerator.getResult();
        List<OverlayClass> generatedClasses = getFlatListWithNoGroups(root);

        // generate classes
        generateClasses(generatedClasses, packageNameGenerator, directoryNameGenerator);

        Map<String, List<OverlayClass>> elementClasses = filter(generatedClasses, OverlayType.Element);

        // generate factories for element types
        generateFactories(elementClasses, packageNameGenerator, directoryNameGenerator);

        //generate overlay messages for element types
        generateOverlayMessages(elementClasses, packageNameGenerator, directoryNameGenerator);

        //generate overlay resources for element types
        generateOverlayResources(elementClasses, packageNameGenerator, directoryNameGenerator);
    }

    /**
     * Generate the directory where the sources will be created
     */
    private void generateSourcesDirectory() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final File directoryForTemplateLoading =
                    new File(classLoader.getResource(OVERLAY_ELEMENT_TEMPLATE_NAME).getFile()).getParentFile();
            configuration.setDirectoryForTemplateLoading(directoryForTemplateLoading);
            // create the subdirectory name
            final File targetDirectoryClasses = new File(classLoader.getResource(".").getFile());
            generatedSourcesDirectory = new File(targetDirectoryClasses.getParentFile().getParentFile(), targetDirectory);
            if (!generatedSourcesDirectory.exists() && !generatedSourcesDirectory.mkdirs()) {
                throw new RuntimeException("Could not create generated source directory " + generatedSourcesDirectory.getAbsolutePath());
            }
            LOG.info("Writing files to {}", generatedSourcesDirectory.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not access template directory.", e);
        }
    }

    /**
     * Generate overlay resources for element types
     * @param elementClasses
     * @param packageNameGenerator
     * @param directoryNameGenerator
     */
    private void generateOverlayResources(Map<String, List<OverlayClass>> elementClasses,
                                          PackageNameGenerator packageNameGenerator,
                                          PackageNameGenerator directoryNameGenerator) {
        // create the resource for each namespace
        for (Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");
            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayLocalizableResource";
            final File file = new File(generatedSourcesDirectory, className + ".java");

            Map<String, Object> rootMap = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            rootMap.put("overlayClass", factoryClass);
            rootMap.put("factoryName", factoryName);
            rootMap.put("overlayClasses", entry.getValue());
            rootMap.put("packageNameGenerator", packageNameGenerator);
            writeToFile(file, rootMap, OVERLAY_RESOURCE_TEMPLATE_NAME);
        }
    }

    /**
     * Generate overlay messages for element types based on xsd documentation
     * @param elementClasses
     * @param packageNameGenerator
     * @param directoryNameGenerator
     */
    private void generateOverlayMessages(Map<String, List<OverlayClass>> elementClasses,
                                         PackageNameGenerator packageNameGenerator,
                                         PackageNameGenerator directoryNameGenerator) {
        // create overlay message properties file for each namespace
        for (Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");
            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayMessages";
            final File classFile = new File(generatedSourcesDirectory, className + ".java");
            final File propFile = new File(generatedSourcesDirectory, className + ".properties");

            Map<String, Object> rootMap = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            rootMap.put("overlayClass", factoryClass);
            rootMap.put("overlayClasses", entry.getValue());
            rootMap.put("packageNameGenerator", packageNameGenerator);

            writeToFile(classFile, rootMap, OVERLAY_MESSAGE_TEMPLATE_NAME);
            writeToFile(propFile, rootMap, OVERLAY_MESSAGE_PROP_TEMPLATE_NAME);

        }
    }

    /**
     * Generates factories of elements
     * @param elementClasses
     * @param packageNameGenerator
     * @param directoryNameGenerator
     */
    private void generateFactories(Map<String, List<OverlayClass>> elementClasses,
                                   PackageNameGenerator packageNameGenerator,
                                   PackageNameGenerator directoryNameGenerator) {
        // create the factory for each namespace
        for (Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");

            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayFactory";
            final File file = new File(generatedSourcesDirectory, className + ".java");

            Map<String, Object> rootMap = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            rootMap.put("overlayClass", factoryClass);
            rootMap.put("overlayClasses", entry.getValue());
            rootMap.put("packageNameGenerator", packageNameGenerator);
            writeToFile(file, rootMap, OVERLAY_FACTORY_TEMPLATE_NAME);
        }
    }

    /**
     * Generates source code for elements
     * @param generatedClasses
     * @param packageNameGenerator
     * @param directoryNameGenerator
     */
    private void generateClasses(List<OverlayClass> generatedClasses,
                                 PackageNameGenerator packageNameGenerator,
                                 PackageNameGenerator directoryNameGenerator) {

        for (OverlayClass overlayClass : generatedClasses) {
            // generate directories
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
            } catch (Exception e) {
                throw new RuntimeException("The class can not be generated " + file.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Filter generated classes based on the given <code>OverlayType</code> type
     * @param generatedClasses
     * @param overlayType
     * @return
     */
    private Map<String, List<OverlayClass>> filter(List<OverlayClass> generatedClasses, OverlayType overlayType) {
        Map<String, List<OverlayClass>> result = new HashMap<String, List<OverlayClass>>();
        for (OverlayClass generatedClass : generatedClasses) {
            if (generatedClass.getNamespaceURI() != null) {
                List<OverlayClass> namespaceElements = result.get(generatedClass.getNamespaceURI());
                if (namespaceElements == null) {
                    namespaceElements = new ArrayList<OverlayClass>();
                    result.put(generatedClass.getNamespaceURI(), namespaceElements);
                }
                // add only required types
                if (generatedClass.getOverlayType().equals(overlayType)) {
                    namespaceElements.add(generatedClass);
                }
            }
        }
        return result;
    }

    /**
     * Create a file using freemarker template mechanism
     * @param file
     * @param rootMap
     * @param templateName
     */
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
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage org.nsesa.editor.app.xsd.FileClassOverlayGenerator <<base_package>> <<target_dir>> <<xsd_schema>>");
            System.out.println("eg: org.nsesa.editor.app.xsd.FileClassOverlayGenerator org.nsesa.editor.gwt.an.client.ui.overlay.document.gen. src/main/java/org/nsesa/editor/gwt/an/client/ui/overlay/document/gen/ akomantoso20.xsd");
            System.exit(1);
        }

        String basePackage = args[0]; //org.nsesa.editor.gwt.core.client.ui.overlay.document.gen.
        String targetDirectory = args[1]; //src/main/java/org/nsesa/editor/gwt/core/client/ui/overlay/document/gen/
        String schema = args[2];

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
        // the first schema name is the main one

        final FileClassOverlayGenerator generator = new FileClassOverlayGenerator(basePackage, targetDirectory);
        try {
            generator.parse(schema);
            generator.analyze();
            generator.print();
        } catch (SAXException e) {
            LOG.error("SAX problem.", e);
        }
    }

    /**
     * Returns a flat list of overlay classes by traversing the root class and exclude the root, schema class type
     * and group classes
     *
     * @param rootClass The schema overlay class that will be traversed
     * @return A List of overlay classes
     */
    protected List<OverlayClass> getFlatListWithNoGroups(OverlayClassGenerator.OverlayRootClass rootClass) {
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
            OverlayClass[] children = aClass.getChildren().toArray(new OverlayClass[aClass.getChildren().size()]);
            //Collections.sort(children, comparator);
            for (int i = children.length - 1; i >= 0; i--) {
                stack.push(children[i]);
            }
        }
        return result;
    }


    /**
     * Replace the group properties with their collection of simple properties
     */
    private void replaceGroupProperties(OverlayClass aClass) {
        List<OverlayPropertyHolder> stack = new ArrayList<OverlayPropertyHolder>();
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        for (OverlayProperty property : aClass.getProperties()) {
            stack.add(new OverlayPropertyHolder(property, property.isCollection()));
        }
        while (!stack.isEmpty()) {
            OverlayPropertyHolder propertyWrapper = stack.remove(0);
            OverlayProperty property = propertyWrapper.getProperty();
            Boolean parentCollection = propertyWrapper.isParentCollection();
            OverlayClass baseClass = property.getBaseClass();
            if (baseClass != null) {
                // check whether is group or attribute group and replace with its properties
                if (baseClass.getOverlayType().equals(OverlayType.AttrGroup) ||
                        baseClass.getOverlayType().equals(OverlayType.Group) ||
                        baseClass.getOverlayType().equals(OverlayType.GroupDecl) ||
                        baseClass.getOverlayType().equals(OverlayType.GroupChoice) ||
                        baseClass.getOverlayType().equals(OverlayType.GroupAll) ||
                        baseClass.getOverlayType().equals(OverlayType.GroupSequence)) {
                    // add in the stack the properties of the base class and parent
                    OverlayClass parent = baseClass;
                    while (parent != null) {
                        for (OverlayProperty parentProp : parent.getProperties()) {
                            // save the previous collection flag
                            stack.add(new OverlayPropertyHolder(parentProp, parentCollection || parentProp.isCollection()));
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
        aClass.setFlatProperties(result);
    }
}
