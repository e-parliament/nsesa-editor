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

import com.google.inject.Inject;
import com.google.gwt.core.client.GWT;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.AmendableWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.DefaultOverlayLocalizableResource;
import com.google.gwt.i18n.client.Messages;
import java.util.logging.Logger;
import java.util.Map;
/**
* Note: this file is generated. Rather than changing this file, correct the template called <tt>overlayLocalizableResource.ftl</tt>.
*/
public class ${overlayClass.name?cap_first} extends DefaultOverlayLocalizableResource {
private static ${factoryName?cap_first}OverlayMessages MESSAGES = GWT.create(${factoryName?cap_first}OverlayMessages.class);

@Override
public String getName(final AmendableWidget widget) {
if (widget == null) {
throw new IllegalArgumentException("Null widget passed.");
}
<#list overlayClasses as cl>
else if ("${packageNameGenerator.getPackageName(cl)}.${cl.className}".equalsIgnoreCase(widget.getClass().getName())) {
return MESSAGES.name${cl.className?cap_first}();
}
</#list>
else {
return widget.getType();
}
}

@Override
public String getDescription(final AmendableWidget widget) {
if (widget == null) {
throw new IllegalArgumentException("Null widget passed.");
}
<#list overlayClasses as cl>
else if ("${packageNameGenerator.getPackageName(cl)}.${cl.className}".equalsIgnoreCase(widget.getClass().getName())) {
return MESSAGES.description${cl.className?cap_first}();
}
</#list>
else {
return widget.getType();
}
}

}