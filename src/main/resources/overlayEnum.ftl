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
<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->
package ${packageNameGenerator.getPackageName(overlayClass)};

import com.google.gwt.dom.client.Element;
import java.util.ArrayList;

/**
* This file is generated.
*/
public enum ${overlayClass.className?cap_first} {

// FIELDS ------------------

<#list overlayClass.restriction.enumeration as enum>
${enum?upper_case}("${enum}")<#if enum_has_next>,</#if>
</#list>;
private final String value;

${overlayClass.className?cap_first}(String v) {
value = v;
}

public String value() {
return value;
}

public static ${overlayClass.className?cap_first} fromString(String text) {
if (text == null) return null;
for (${overlayClass.className?cap_first} en : ${overlayClass.className?cap_first}.values()) {
if(text.equalsIgnoreCase(en.value())) {
return en;
}
}
return null;
}
}
