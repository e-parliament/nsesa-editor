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
<#-- @ftlvariable name="overlayClasses" type="java.util.List<OverlayClass>" -->
<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->

package ${packageNameGenerator.getPackageName(overlayClass)};

import com.google.gwt.i18n.client.Messages;

/**
* Note: this file is generated. Rather than changing this file, correct the template called
<tt>overlayMessages.ftl</tt>.
*/

public interface ${overlayClass.className?cap_first} extends Messages {
<#list overlayClasses as cl>
@Key("name.${cl.className}")
String name${cl.className?cap_first}();
@Key("description.${cl.className}")
String description${cl.className?cap_first}();
</#list>
}
