<#--

    Copyright 2013 European Parliament

    Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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
* <#if overlayClass.comments??>${overlayClass.comments?replace("\n", " ")?replace("\t", " ")?replace("'", "''")?replace("\\s+"," ", "r")}<#else></#if>
* This file is generated. Rather than changing this file, correct the template called <tt>overlayEnum.ftl</tt>.
*/
public enum ${overlayClass.className?cap_first} {

// FIELDS ------------------

<#list overlayClass.restriction.enumeration as enum>
${enum?upper_case}("${enum}")<#if enum_has_next>,</#if>
</#list>;
private final String value;

/**
* Create an instance of ${overlayClass.className?cap_first} class with the given String
*/
${overlayClass.className?cap_first}(String v) {
value = v;
}

/**
* Return the value of the instance
* @return value as String
*/
public String value() {
return value;
}

/**
* Return an Enum based on the given input text or null if it does not fit
* @param text the String representation of the enum
* @return ${overlayClass.className?cap_first} or <code>null</code>
*/
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
