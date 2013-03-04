<#--

    Copyright 2013 European Parliament

    Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
    You may not use this work except in compliance with the Licence.
    You may obtain a copy of the Licence at:

    http://joinup.ec.europa.eu/software/page/eupl

    Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the Licence for the specific language governing permissions and limitations under the Licence.

-->
<#-- @ftlvariable name="overlayClasses" type="java.util.List<OverlayClass>" -->
<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->
package ${packageNameGenerator.getPackageName(overlayClass)};

<#list overlayClasses as cl>
import ${packageNameGenerator.getPackageName(cl)}.${cl.className?cap_first};
</#list>
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayStrategy;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import com.google.gwt.dom.client.Element;
import java.util.logging.Logger;
import java.util.Map;
/**
* A factory class specialization used to create overlay wigets based on the given DOM element input data
* Note: this file is generated. Rather than changing this file, correct the template called <tt>overlayFactory.ftl</tt>.
*/
public class ${overlayClass.name?cap_first} extends DefaultOverlayFactory  {

private final static Logger LOG = Logger.getLogger(${overlayClass.name?cap_first}.class.getName());
/** the namespace of the overlay factory **/
private final String namespace = "${overlayClass.nameSpace}";

/**
* Create <code>${overlayClass.name?cap_first}</code> object with the given overlay strategy
* @param overlayStrategy The strategy to be used to create overlay widget objects
*/
@Inject
public ${overlayClass.name?cap_first}(final OverlayStrategy overlayStrategy) {
super(overlayStrategy);
}
/**
* Return <code>namespace</code> of the overlay class
* @return the namespace as String
*/
@Override
public String getNamespace() {
return namespace;
}
/**
* Return <code>OverlayWidget</code> instance based on the given DOM element or null if there is no
* possibility to create an overlay widget with the type specified in the DOM element "type" attribute
* @return an overlay widget instance
*/
@Override
public OverlayWidget toAmendableWidget(final Element element) {
final String nodeName = overlayStrategy.getType(element);
final String namespaceURI = overlayStrategy.getNamespaceURI(element);

if ("".equals(nodeName)) {
throw new IllegalArgumentException("Empty element or null passed.");
}
<#list overlayClasses as cl>
else if ("${cl.name}".equalsIgnoreCase(nodeName)) {
return new ${cl.className?cap_first}(element);
}
</#list>
// nothing found
LOG.warning("Could not find overlay element (nodename: " + nodeName + " in namespace URI '" + namespaceURI + "')");
return null;
}
}