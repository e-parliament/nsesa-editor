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

# Note: this file is generated. Rather than editing this file, edit the template called overlayMessagesProperties.ftl
<#list overlayClasses as cl>

name.${cl.className}=${cl.name}
description.${cl.className}=<#if cl.comments??>${cl.comments?replace("\n", " ")?replace("\t", " ")?replace("'", "''")?replace("\\s+"," ", "r")}<#else></#if>
</#list>
