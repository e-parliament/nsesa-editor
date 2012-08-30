<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->
package ${overlayClass.packageName};

<#list overlayClass.imports as import>
import ${import}.*;
</#list>
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.*;

import com.google.gwt.dom.client.Element;
import java.util.ArrayList;

/**
* This file is generated.
*/
public class ${overlayClass.name?cap_first} <#if overlayClass.superClassName??>extends ${overlayClass.superClassName}</#if> <#if overlayClass.interfaces??>implements <#list overlayClass.interfaces as interface>${interface.getSimpleName()}<#if interface_has_next>, </#if></#list> </#if>{

// FIELDS ------------------

<#list overlayClass.properties as property>
    <@generateField property=property/>
</#list>

// CONSTRUCTORS ------------------

public ${overlayClass.name?cap_first}(final Element amendableElement) {
super(amendableElement);
}

// ACCESSORS ------------------

<#list overlayClass.properties as property>
public <@propertyClassName property=property/> <#if property.className == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() {
return <@propertyName property = property/>;
}

public void set<@propertyNameCap property = property/>(final <@propertyClassName property=property/> <@propertyName property = property/>) {
this.<@propertyName property = property/> = <@propertyName property = property/>;
}

</#list>
}

<#macro propertyClassName property><#compress>
    <#if property.collection>
    java.util.List<${property.className}>
    <#else>
    ${property.className}
    </#if>
</#compress></#macro>

<#macro propertyName property><#compress>
    <#if property.name == "class">
    className
    <#elseif property.name == "extends">
    extendz
    <#elseif property.name == "for">
    forURI
    <#elseif property.name == "new">
    newEl
    <#else>
        <#if property.collection><@pl property=property/><#else>${property.name}</#if>
    </#if>
</#compress></#macro>

<#macro propertyNameCap property><#compress>
    <#if property.name == "class">
    ClassName
    <#else>
    ${property.name?cap_first}
    </#if>
</#compress></#macro>

<#macro generateField property>
private <@propertyClassName property=property/> <#if property.collection><@pl property=property/> = new ArrayList<${property.className}>();<#else><@propertyName property = property/>;</#if>
</#macro>

<#macro pl property><#compress>
    <#if property.name?ends_with("y")>${property.name?substring(0, property.name?length - 1)}ies<#elseif property.name?ends_with("s")>${property.name}es<#else>${property.name}s</#if>
</#compress></#macro>