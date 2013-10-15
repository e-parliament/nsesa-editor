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
import java.io.Writer;
import java.net.URL;
import java.util.*;

/**
 * Get the result of the XSD parsing and generate, based on the Freemarker template an overlay class for each element
 * defined in the XSD schema. A factory implementation is also generated in order to facilitate the object's creation.
 * <p/>
 * The generator is coming with the following predefined Freemarker templates:
 * <ul>
 * <li>overlayClass.ftl: responsible for generation of the Java overlay classes.</li>
 * <li>overlayEnum.ftl: responsible for generation of the Java enum.</li>
 * <li>overlayFactory.ftl: responsible for generation of Java classes that implement the
 * {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory} interface.</li>
 * <li>overlayLocalizableResource.ftl, overlayMessagesProperties.ftl, overlayMessagesProperties.ftl:
 * responsible for generation of GWT localizable resources.</li>
 * </ul>
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         Date: 06/11/12 9:09
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
     * used as a collection or not.
     */
    private static final class OverlayPropertyHolder {
        private OverlayProperty property;
        private boolean parentCollection;

        OverlayPropertyHolder(final OverlayProperty property, final boolean parentCollection) {
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

    /**
     * The directory to hold the generated source classes.
     */
    private File generatedSourcesDirectory;

    /**
     * The Freemarker configuration object.
     */
    private final Configuration configuration;

    /**
     * The base package name to use.
     */
    private String basePackageName;

    /**
     * The target directory where the generated files will be stored.
     */
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
     */
    public void export() {

        final PackageNameGenerator packageNameGenerator = new PackageNameGeneratorImpl(basePackageName);
        final PackageNameGenerator directoryNameGenerator = new PackageNameGeneratorImpl("");

        //generate source directory
        generateSourcesDirectory();

        final OverlayClassGenerator.OverlayRootClass root = overlayClassGenerator.getResult();
        final List<OverlayClass> generatedClasses = getFlatListWithNoGroups(root);

        // generate classes
        generateClasses(generatedClasses, packageNameGenerator, directoryNameGenerator);

        final Map<String, List<OverlayClass>> elementClasses = filter(generatedClasses, OverlayType.Element);

        // generate factories for element types
        generateFactories(elementClasses, packageNameGenerator, directoryNameGenerator);

        //generate overlay messages for element types
        generateOverlayMessages(elementClasses, packageNameGenerator, directoryNameGenerator);

        //generate overlay resources for element types
        generateOverlayResources(elementClasses, packageNameGenerator, directoryNameGenerator);
    }

    /**
     * Generate the directory where the Java source files will be created.
     */
    private void generateSourcesDirectory() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final URL resource = classLoader.getResource(OVERLAY_ELEMENT_TEMPLATE_NAME);
            if (resource == null) throw new RuntimeException("Could not find " + OVERLAY_ELEMENT_TEMPLATE_NAME +
                    " template file in the classpath.");
            final File directoryForTemplateLoading = new File(resource.getFile()).getParentFile();
            // freemarker directory with the templates
            configuration.setDirectoryForTemplateLoading(directoryForTemplateLoading);
            // create the subdirectory name
            final File targetDirectoryClasses = new File(classLoader.getResource(".").getFile());
            generatedSourcesDirectory = new File(targetDirectoryClasses.getParentFile().getParentFile(),
                    targetDirectory);
            if (!generatedSourcesDirectory.exists() && !generatedSourcesDirectory.mkdirs()) {
                throw new RuntimeException("Could not create generated source directory " +
                        generatedSourcesDirectory.getAbsolutePath());
            }
            LOG.info("Writing files to {}", generatedSourcesDirectory.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Could not access template directory.", e);
        }
    }

    /**
     * Generate overlay resources for element types.
     *
     * @param elementClasses         the map with overlay classes for each namespace.
     * @param packageNameGenerator   the package name generator
     * @param directoryNameGenerator the directory name generator
     */
    private void generateOverlayResources(final Map<String, List<OverlayClass>> elementClasses,
                                          final PackageNameGenerator packageNameGenerator,
                                          final PackageNameGenerator directoryNameGenerator) {
        // create the resource for each namespace
        for (final Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");
            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayLocalizableResource";
            final File file = new File(generatedSourcesDirectory, className + ".java");

            final Map<String, Object> context = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            context.put("overlayClass", factoryClass);
            context.put("factoryName", factoryName);
            context.put("overlayClasses", entry.getValue());
            context.put("packageNameGenerator", packageNameGenerator);
            writeToFile(file, context, OVERLAY_RESOURCE_TEMPLATE_NAME);
        }
    }

    /**
     * Generate overlay messages for element types based on xsd documentation.
     *
     * @param elementClasses         the map with overlay classes for each namespace.
     * @param packageNameGenerator   the package name generator
     * @param directoryNameGenerator the directory name generator
     */
    private void generateOverlayMessages(final Map<String, List<OverlayClass>> elementClasses,
                                         final PackageNameGenerator packageNameGenerator,
                                         final PackageNameGenerator directoryNameGenerator) {
        // create overlay message properties file for each namespace
        for (final Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");
            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayMessages";
            final File classFile = new File(generatedSourcesDirectory, className + ".java");
            final File propFile = new File(generatedSourcesDirectory, className + ".properties");

            final Map<String, Object> context = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            context.put("overlayClass", factoryClass);
            context.put("overlayClasses", entry.getValue());
            context.put("packageNameGenerator", packageNameGenerator);

            writeToFile(classFile, context, OVERLAY_MESSAGE_TEMPLATE_NAME);
            writeToFile(propFile, context, OVERLAY_MESSAGE_PROP_TEMPLATE_NAME);

        }
    }

    /**
     * Generates overlay factories that implement {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayFactory}.
     * Used during the overlay mechanism when creating a higher abstraction based on the DOM.
     *
     * @param elementClasses         the map with overlay classes for each namespace.
     * @param packageNameGenerator   the package name generator
     * @param directoryNameGenerator the directory name generator
     */
    private void generateFactories(Map<String, List<OverlayClass>> elementClasses,
                                   PackageNameGenerator packageNameGenerator,
                                   PackageNameGenerator directoryNameGenerator) {
        // create the factory for each namespace
        for (final Map.Entry<String, List<OverlayClass>> entry : elementClasses.entrySet()) {
            String factoryName = directoryNameGenerator.getPackageName(entry.getKey()).replace("_", "");
            if (!Character.isJavaIdentifierStart(factoryName.charAt(0))) {
                factoryName = "_" + factoryName;
            }

            final String className = StringUtils.capitalize(factoryName) + "OverlayFactory";
            final File file = new File(generatedSourcesDirectory, className + ".java");

            final Map<String, Object> context = new HashMap<String, Object>();

            final OverlayClass factoryClass = new OverlayClass(className, null, OverlayType.Unknown);
            factoryClass.setClassName(className);
            factoryClass.setNamespaceURI(entry.getKey());
            factoryClass.setPackageName(basePackageName.endsWith(".")
                    ? basePackageName.substring(0, basePackageName.length() - 1) : basePackageName);

            context.put("overlayClass", factoryClass);
            context.put("overlayClasses", entry.getValue());
            context.put("packageNameGenerator", packageNameGenerator);
            writeToFile(file, context, OVERLAY_FACTORY_TEMPLATE_NAME);
        }
    }

    /**
     * Generates Java source code for the overlay classes.
     *
     * @param generatedClasses       the overlay classes
     * @param packageNameGenerator   the package name generator
     * @param directoryNameGenerator the directory name generator
     */
    private void generateClasses(final List<OverlayClass> generatedClasses,
                                 final PackageNameGenerator packageNameGenerator,
                                 final PackageNameGenerator directoryNameGenerator) {

        for (final OverlayClass overlayClass : generatedClasses) {
            // generate directories
            String filePackageName = directoryNameGenerator.getPackageName(overlayClass);
            if (filePackageName == null || filePackageName.length() == 0) {
                continue;
            }
            final File filePackage = new File(generatedSourcesDirectory, filePackageName);
            if (!filePackage.exists()) {
                try {
                    filePackage.mkdir();
                } catch (Exception e) {
                    throw new RuntimeException("The directory can not be created" + filePackageName);
                }
            }
            final File file = new File(filePackage, StringUtils.capitalize(overlayClass.getClassName()) + ".java");
            try {
                final Map<String, Object> context = new HashMap<String, Object>();
                context.put("overlayClass", overlayClass);
                context.put("packageNameGenerator", packageNameGenerator);
                String templateName = OVERLAY_ELEMENT_TEMPLATE_NAME;
                if (overlayClass.isEnumeration()) {
                    templateName = OVERLAY_ENUM_TEMPLATE_NAME;
                }
                writeToFile(file, context, templateName);
            } catch (Exception e) {
                throw new RuntimeException("The Java source code for " + overlayClass + " could not be generated at "
                        + file.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Filter generated classes based on the given <code>OverlayType</code> type.
     *
     * @param generatedClasses the overlay classes
     * @param overlayType      the overlay type
     * @return the map with namespace and the generated overlay classes
     */
    private Map<String, List<OverlayClass>> filter(List<OverlayClass> generatedClasses, OverlayType overlayType) {
        final Map<String, List<OverlayClass>> result = new HashMap<String, List<OverlayClass>>();
        for (final OverlayClass generatedClass : generatedClasses) {
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
     * Write a file after processing it in Freemarker.
     *
     * @param file         the file to write to
     * @param context      the context map
     * @param templateName the template to use
     */
    public void writeToFile(final File file, final Object context, final String templateName) {
        Writer writer = null;
        try {
            final Template template = configuration.getTemplate(templateName);
            writer = new FileWriter(file);
            final DefaultObjectWrapper wrapper = new DefaultObjectWrapper();
            template.process(context, writer, wrapper);
        } catch (IOException e) {
            throw new RuntimeException("Could not read template or write to file.", e);
        } catch (TemplateException e) {
            throw new RuntimeException("Could not parse template.", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error("Could not close writer.", e);
                }
            }
        }
    }

    /**
     * The main method. The following arguments are expected:
     * <ul>
     * <li>base_package: the base package name, <strong>must</strong> end with a dot (eg. 'org.nsesa.editor.gwt.an.client.ui.overlay.document.gen.')</li>
     * <li>target_dir: the target directory for writing the generated source files (eg. 'src/main/java/org/nsesa/editor/gwt/an/client/ui/overlay/document/gen/')</li>
     * <li>xsd_schema: the XSD file to generate the classes for (eg. 'akomaNtoso/akomantoso20.xsd')</li>
     * </ul>
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java org.nsesa.editor.app.xsd.FileClassOverlayGenerator <<base_package>> <<target_dir>> <<xsd_schema>>");
            System.out.println("eg: java org.nsesa.editor.app.xsd.FileClassOverlayGenerator org.nsesa.editor.gwt.an.client.ui.overlay.document.gen. src/main/java/org/nsesa/editor/gwt/an/client/ui/overlay/document/gen/ akomaNtoso/akomantoso20.xsd");
            System.exit(1);
        }

        final String basePackage = args[0]; //org.nsesa.editor.gwt.core.client.ui.overlay.document.gen.
        final String targetDirectory = args[1]; //src/main/java/org/nsesa/editor/gwt/core/client/ui/overlay/document/gen/
        final String schema = args[2]; // xsd

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // set up log4j logging
        DOMConfigurator.configure(classLoader.getResource("log4j.xml"));
        // the first schema name is the main one

        final FileClassOverlayGenerator generator = new FileClassOverlayGenerator(basePackage, targetDirectory);
        try {
            generator.parse(schema);
            generator.analyze();
            generator.export();
        } catch (SAXException e) {
            LOG.error("SAX problem while parsing the XSD.", e);
        }
    }

    /**
     * Returns a flat list of overlay classes by traversing the root class and exclude the root, schema class type
     * and group classes
     *
     * @param rootClass The root overlay class that will be traversed
     * @return A List of overlay classes
     */
    protected List<OverlayClass> getFlatListWithNoGroups(final OverlayClassGenerator.OverlayRootClass rootClass) {
        final Stack<OverlayClass> stack = new Stack<OverlayClass>();
        final List<OverlayClass> result = new ArrayList<OverlayClass>();
        stack.push(rootClass);
        while (!stack.isEmpty()) {
            final OverlayClass aClass = stack.pop();
            // skip creation for such classes
            if (!(aClass instanceof OverlayClassGenerator.OverlayRootClass ||
                    aClass instanceof OverlayClassGenerator.OverlaySchemaClass ||
                    aClass.getOverlayType().equals(OverlayType.GroupDecl) ||
                    aClass.getOverlayType().equals(OverlayType.Group) ||
                    aClass.getOverlayType().equals(OverlayType.AttrGroup)
            )) {
                // replace the properties
                replaceGroupProperties(aClass);
                // add now in the result
                result.add(aClass);
            }
            final OverlayClass[] children = aClass.getChildren().toArray(new OverlayClass[aClass.getChildren().size()]);
            for (int i = children.length - 1; i >= 0; i--) {
                stack.push(children[i]);
            }
        }
        return result;
    }


    /**
     * Replace the group properties with their collection of simple properties.
     *
     * @param aClass the overlay class
     */
    private void replaceGroupProperties(final OverlayClass aClass) {
        List<OverlayPropertyHolder> stack = new ArrayList<OverlayPropertyHolder>();
        List<OverlayProperty> result = new ArrayList<OverlayProperty>();
        for (OverlayProperty property : aClass.getProperties()) {
            stack.add(new OverlayPropertyHolder(property, property.isCollection()));
        }
        while (!stack.isEmpty()) {
            final OverlayPropertyHolder propertyWrapper = stack.remove(0);
            final OverlayProperty property = propertyWrapper.getProperty();
            final Boolean parentCollection = propertyWrapper.isParentCollection();
            final OverlayClass baseClass = property.getBaseClass();
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
                    // create a new copy of overlay property and set its collection flag
                    final OverlayProperty newProperty = property.copy();
                    newProperty.setCollection(parentCollection || property.isCollection());
                    result.add(newProperty);
                }
            } else {
                // create a new copy of overlay property and set its collection flag
                final OverlayProperty newProperty = property.copy();
                newProperty.setCollection(parentCollection || property.isCollection());
                result.add(newProperty);
            }
        }
        aClass.setFlatProperties(result);
    }
}
