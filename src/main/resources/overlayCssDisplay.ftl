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
<#-- @ftlvariable name="overlayStyleFactory" type="org.nsesa.editor.app.xsd.model.CssOverlayStyle.CssOverlayFactory" -->
<#-- @ftlvariable name="styles" type="java.util.List<CssOverlayStyle>" -->
<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->
<#-- @ftlvariable name="cssConfiguration" type="java.util.Map<String, Object>" -->
<#-- @ftlvariable name="colorGenerator" type="org.nsesa.editor.app.xsd.model.CssColorGenerator" -->

/*
* --------------------------------------------------------------------------
* Akoma Ntoso Display default stylesheet.
* Note: this file is generated!
* --------------------------------------------------------------------------
*/
<@generateCss overlayClass=overlayClass styles=styles/>

<#macro generateCss overlayClass styles>
    <#assign overlayStyle = overlayStyleFactory.create(overlayClass,styles)>
    <#assign withBanner = overlayStyle.name?? && overlayClass.children?size != 0>
    <#if withBanner>
    /*
    * --------------------------
    * ${overlayClass.name}
    * --------------------------
    */
    </#if>
    <#if overlayStyle.name??>
        <#if cssConfiguration['printEmptyCss'] || (overlayStyle.values?size != 0)>
            <#if overlayStyle.values['display']??>
                <#if overlayStyle.values['display'] == "inline">
                    <@displayInline overlayStyle=overlayStyle overlayClass=overlayClass/>
                <#else>
                    <@displayBlock overlayStyle=overlayStyle overlayClass=overlayClass/>
                </#if>
            <#else>
                <#if overlayClass.isDescendentOf("Inline") && (overlayClass.isElement() || overlayClass.isComplex())>
                    <@displayInline overlayStyle=overlayStyle overlayClass=overlayClass/>
                <#else>
                    <@displayBlock overlayStyle=overlayStyle overlayClass=overlayClass/>
                </#if>
            </#if>
        </#if>
    </#if>
    <#if overlayClass.children?size != 0 >
        <#list overlayClass.orderedChildren as child>
            <@generateCss overlayClass=child styles=styles/>
        </#list>
    </#if>
</#macro>

<#macro displayInline overlayStyle overlayClass>
.akomaNtoso-drafting .${overlayStyle.name} {
background-color:<#if overlayStyle.values["background-color"]??>${overlayStyle.values["background-color"]};<#else>#${colorGenerator.getColor(overlayStyle.name)};</#if>
color:<#if overlayStyle.values["color"]??>${overlayStyle.values["color"]};<#elseif overlayStyle.values["background-color"]??>#${colorGenerator.matchTextColor(overlayStyle.values["background-color"])};<#else>#${colorGenerator.getTextColor(overlayStyle.name)};</#if>
}

</#macro>

<#macro displayBlock overlayStyle overlayClass>
.akomaNtoso-drafting .${overlayStyle.name}:before {
content: "${overlayStyle.name}";
border: 1px solid #000000;
background-color: #${colorGenerator.getColor(overlayStyle.name)};
display:block;
text-align:center;
font-family: Sans-Serif;
font-size: 10pt;
left: 80%;
color: #${colorGenerator.getTextColor(overlayStyle.name)};
width: 90px;
border-radius: 3px;
margin: 0px;
padding: 1px;
position: relative;

}
.akomaNtoso-drafting .${overlayStyle.name} {
border:<#if overlayStyle.values["border"]??>${overlayStyle.values["border"]};<#else>1px solid #${colorGenerator.getColor(overlayStyle.name)};</#if>
}

</#macro>