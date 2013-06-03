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

<#list overlayClass.getImports(packageNameGenerator) as import>
import ${import};
</#list>
import com.google.gwt.dom.client.Element;
import java.util.ArrayList;
import java.util.Arrays;
<#if overlayClass.complex || overlayClass.element || overlayClass.hasWildCardProperties()>
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import java.util.HashMap;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.StructureIndicator;
</#if>
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gwt.user.client.DOM;

/**
* <#if overlayClass.comments??>${overlayClass.comments?replace("\n", "<br/>")?replace("\t", " ")?replace("'", "''")?replace("\\s+"," ", "r")}<#else>Generated class</#if>
* This file is generated. Rather than changing this file, correct the template called <tt>overlayClass.ftl</tt>.
*/

public class ${overlayClass.className?cap_first} <#if overlayClass.parent?? && (overlayClass.parent.complex || overlayClass.parent.element || overlayClass.parent.simple)>extends ${overlayClass.parent.className?cap_first}<#else><#if overlayClass.complex || overlayClass.element>extends OverlayWidgetImpl</#if></#if>  <#if overlayClass.interfaces??>implements <#list overlayClass.interfaces as interface>${interface}<#if interface_has_next>, </#if></#list> </#if>{
<#if overlayClass.complex || overlayClass.element>

/** Stores a structure indicator coming from xsd structure **/
private static StructureIndicator STRUCTURE_INDICATOR = new StructureIndicator.DefaultStructureIndicator(1,1
    <#if overlayClass.allStructureProperties?size != 0>
        ,<#list overlayClass.allStructureProperties as prop>
        <@structureIndicator prop=prop/>
        <#if prop_has_next>,</#if>
        </#list>
    </#if>
);
</#if>


/**
* Create a browser DOM span element and set up "type", "ns" and css class attributes
*/
public static Element create() {
com.google.gwt.user.client.Element span = DOM.createSpan();
span.setAttribute("type", "${overlayClass.className}");
span.setAttribute("ns", "${overlayClass.namespaceURI}");
span.setClassName("widget ${overlayClass.className}");
return span;
}

// CONSTRUCTORS ------------------
<#if overlayClass.element>
/**
* Create a <code>${overlayClass.className?cap_first}</code> object and set up its type
*/
public ${overlayClass.className?cap_first}() {
super(create());
setType("${overlayClass.className}");
}

<#assign requiredConstructor = false>
<#list overlayClass.allFlatAttributesProperties as prop>
<#if prop.required><#assign requiredConstructor = true></#if>
</#list>
<#if requiredConstructor>
/**
* Constructor with required attributes
*/
public ${overlayClass.className?cap_first}(<#assign delim=""><#list overlayClass.allFlatAttributesProperties as property><#if property.required>${delim}<@propertyClassName property=property/> <@propertyName property = property/><#assign delim=","></#if></#list>) {
this();
    <#list overlayClass.allFlatAttributesProperties as property>
        <#if property.required>
set<@propertyNameCap property = property/>(<@propertyName property = property/>);
        </#if>
    </#list>
}
</#if>

</#if>

<#if overlayClass.complex || overlayClass.element>
/**
* Create a <code>${overlayClass.className?cap_first}</code> object with the given DOM element
*/
public ${overlayClass.className?cap_first}(Element element) {
super(element);
}
</#if>
<#if overlayClass.simple>
/**
* Create an empty <code>${overlayClass.className?cap_first}</code> object
*/
public ${overlayClass.className?cap_first}() {
super();
}
/**
* Create a <code>${overlayClass.className?cap_first}</code> object with teh given input data
*/
public ${overlayClass.className?cap_first}(String value) {
super();
this.value = value;
}
</#if>

// FIELDS ------------------
<#if overlayClass.complex || overlayClass.element>
    <#list overlayClass.flatProperties as property>
        <#if property.attribute>
            <@generateField property=property accessType="private"/>
        </#if>
    </#list>
</#if>
<#if overlayClass.simple>
    <#list overlayClass.flatProperties as property>
        <@generateField property=property accessType="protected"/>
    </#list>
</#if>
<#if overlayClass.complex || overlayClass.element>

    <#list overlayClass.flatProperties as property>
        <#if property.attribute>
        /**
        * Return <code><@propertyName property = property/></code> property
        * @return <@propertyName property=property/>
        */
        public <@propertyClassName property=property/> <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() {
        if (<@propertyName property = property/> == null) {
            <#if property.baseClass?? && !property.baseClass.enumeration>
                <@propertyName property = property/> = new ${property.className?cap_first}();
                <@propertyName property = property/>.setValue(getElement().getAttribute("${property.name}"));
            <#elseif property.baseClass?? && property.baseClass.enumeration>
                <@propertyName property = property/> = ${property.className?cap_first}.fromString(getElement().getAttribute("${property.name}"));
            <#elseif !property.wildCard>
                <@propertyName property = property/> = getElement().getAttribute("${property.name}");
            <#else>
            //hmm nothing to do here
            </#if>
        }

        return <@propertyName property = property/>;
        }
        /**
        * Return <code><@propertyName property=property/></code> property in DSL way
        * @return <@propertyName property=property/>
        */
        public <@propertyClassName property=property/> <@propertyName property = property/>() {
        return  <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>();
        }
        /**
        * Set <code><@propertyName property=property/></code> property
        * @param <@propertyName property=property/> the new value
        */
        public void set<@propertyNameCap property = property/>(final <@propertyClassName property=property/> <@propertyName property = property/>) {
        this.<@propertyName property = property/> = <@propertyName property = property/>;
        <#if property.wildCard>
        getElement().setAttribute("${property.name}",<@propertyName property = property/>);
        <#elseif property.baseClass?? && property.baseClass.enumeration>
        getElement().setAttribute("${property.name}",<@propertyName property = property/>.value());
        <#else>
        getElement().setAttribute("${property.name}",<@propertyName property = property/>.getValue());
        </#if>
        }
        /**
        * Set <code><@propertyName property=property/></code> property in DSL way
        * @param <@propertyName property=property/> the new value
        * @return <code>${overlayClass.className?cap_first}</code> instance
        */
        public ${overlayClass.className?cap_first} <@propertyName property = property/>(final <@propertyClassName property=property/> <@propertyName property = property/>) {
        set<@propertyNameCap property = property/>(<@propertyName property = property/>);
        return this;
        }
        <#else>
            <#if property.collection>
            /**
            * Return <code><@propertyClassName property=property/></code> property
            * @return The property as unmodifiable list
            */
            public <@propertyClassName property=property/> <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() {
                <@propertyClassName property=property/> result = new ArrayList<<@elementClassName property=property/>>();
                for (OverlayWidget widget : getChildOverlayWidgets()) {
                    if ("<@elementClassName property=property/>".equalsIgnoreCase(widget.getType()) && "${overlayClass.namespaceURI}".equalsIgnoreCase(widget.getNamespaceURI())) {
                        result.add((<@elementClassName property=property/>)widget);
                    }
                }
                return java.util.Collections.unmodifiableList(result);
            }

            /**
            * Return <code><@propertyClassName property=property/></code> property in DSL way
            * @return The property as unmodifiable list
            */
            public <@propertyClassName property=property/> get${property.javaName?cap_first}List() {
                return  <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>();
            }
            /**
            * Add <code><@propertyClassName property=property/></code> property in the list of properties
            * @return The property as unmodifiable list
            */
            public ${property.className?cap_first} add${property.javaName?cap_first}(${property.className?cap_first} ${property.javaName}Elem) {
                <#if property.wildCard>
                throw new RuntimeException("Adding wildcard content is not supported yet");
                <#else>
                this.addOverlayWidget(${property.javaName}Elem);
                return ${property.javaName}Elem;
                </#if>
            }

            <#else>
            /**
            * Add <code><@propertyClassName property=property/></code> property in the list of properties
            * @return The property as unmodifiable list
            */
            public <@propertyClassName property=property/> <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() {
                <@propertyClassName property=property/> result = null;
                for (OverlayWidget widget : getChildOverlayWidgets()) {
                    if ("<@propertyClassName property=property/>".equalsIgnoreCase(widget.getType()) && "${overlayClass.namespaceURI}".equalsIgnoreCase(widget.getNamespaceURI())) {
                        result = (<@propertyClassName property=property/>)widget;
                        break;
                    }
                }
                return result;
            }
            /**
            * Set <code>${property.javaName}Elem</code> property in DSL way
            * @param ${property.javaName}Elem new value
            * @return <code>${property.className?cap_first}</code> instance
            */
            public ${property.className?cap_first} set${property.javaName?cap_first}(${property.className?cap_first} ${property.javaName}Elem) {
                <#if property.wildCard>
                throw new RuntimeException("Setting wildcard content is not supported yet");
                <#else>
                    <@propertyClassName property=property/> result = <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>();
                // remove the child of the same type if exist
                if (result != null) {
                    this.removeOverlayWidget(result);
                }
                this.addOverlayWidget(${property.javaName}Elem);

                return ${property.javaName}Elem;
                </#if>
            }
            </#if>
        </#if>
    </#list>
//Override all attributes methods to be conformant with DSL approach
    <#if overlayClass.parent.complex || overlayClass.parent.element || overlayClass.parent.simple>
        <#list overlayClass.parent.allFlatAttributesProperties as parentProp>
        /**
        * Set <code><@propertyName property = parentProp/></code> property in DSL way
        * @param <@propertyName property = parentProp/> new value
        * @return <code> ${overlayClass.className?cap_first}</code> instance
        */
        public ${overlayClass.className?cap_first} <@propertyName property = parentProp/>(final <@propertyClassName property=parentProp/> <@propertyName property = parentProp/>) {
        set<@propertyNameCap property = parentProp/>(<@propertyName property = parentProp/>);
        return this;
        }
        </#list>
    </#if>

/**
* Returns the namespace URI of this amendable widget.
* @return The namesapce as String
*/
@Override
public String getNamespaceURI() {
return "${overlayClass.namespaceURI}";
}

@Override
public LinkedHashMap<String, String> getAttributes() {
final LinkedHashMap<String, String> attrs = new LinkedHashMap<String, String>();
attrs.putAll(super.getAttributes());
    <#list overlayClass.flatProperties as property>
        <#if property.attribute>
            <#assign stripped><@propertyName property=property/></#assign>
        attrs.put("<#if stripped?ends_with("Attr")>${stripped?substring(0, stripped?length - 4)}<#else>${stripped}</#if>", <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() != null ? <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>()<#if property.baseClass?? && property.baseClass.enumeration>.value()<#elseif property.baseClass?? && property.baseClass.simple>.getValue()<#elseif property.wildCard && !property.attribute>.getContent()<#else>.toString()</#if> : null);
        </#if>
    </#list>
return attrs;
}

@Override
public StructureIndicator getStructureIndicator() {
    return STRUCTURE_INDICATOR;
}

/**
* DSL Style for html method
*/
@Override
public ${overlayClass.className?cap_first} html(String s) {
    super.html(s);
    return this;
}
</#if>
<#if overlayClass.simple>
    <#list overlayClass.flatProperties as property>
    /**
    * Get <code><@propertyName property = property/></code> property
    * @return <@propertyName property = property/>
    */
    public <@propertyClassName property=property/> <#if property.className?cap_first == "Boolean">is<#else>get</#if><@propertyNameCap property = property/>() {
    return <@propertyName property = property/>;
    }
    /**
    * Set <code><@propertyName property = property/></code> property
    * @param <@propertyName property = property/> new value
    */
    public void set<@propertyNameCap property = property/>(final <@propertyClassName property=property/> <@propertyName property = property/>) {
    this.<@propertyName property = property/> = <@propertyName property = property/>;
    }
    </#list>
</#if>
}

<#macro propertyClassName property><#compress>
    <#assign propName="OverlayWidgetImpl">
    <#if property.wildCard>
        <#if property.attribute>
            <#assign propName="String">
        <#else>
            <#assign propName="OverlayWidgetImpl">
        </#if>
    <#else>
        <#assign propName=property.className?cap_first>
    </#if>
    <#if property.collection>
    java.util.List<${propName}>
    <#else>
    ${propName}
    </#if>
</#compress></#macro>

<#macro elementClassName property><#compress>
    <#assign propName="OverlayWidgetImpl">
    <#if property.wildCard>
        <#if property.attribute>
            <#assign propName="String">
        <#else>
            <#assign propName="OverlayWidgetImpl">
        </#if>
    <#else>
        <#assign propName=property.className?cap_first>
    </#if>
${propName}
</#compress></#macro>

<#macro propertyName property><#compress>
    <#if property.javaName == "class">
    className
    <#elseif property.javaName == "extends">
    extendz
    <#elseif property.javaName == "for">
    forURI
    <#elseif property.javaName == "new">
    newEl
    <#else>
        <#if property.collection><@pl property=property/><#else>${property.javaName}</#if>
    </#if>
</#compress></#macro>

<#macro propertyNameCap property><#compress>
    <#if property.javaName == "class">
    ClassName
    <#else>
        <#if property.collection><@plural propertyName=property.javaName?cap_first/><#else>${property.javaName?cap_first}</#if>
    </#if>
</#compress></#macro>

<#macro pl property><#compress>
    <@plural propertyName=property.javaName/>
</#compress></#macro>

<#macro plural propertyName><#compress><#if propertyName?ends_with("y")>${propertyName?substring(0, propertyName?length - 1)}ies<#elseif propertyName?ends_with("s")>${propertyName}es<#else>${propertyName}s</#if></#compress></#macro>

<#macro generateField property accessType>
    ${accessType} <@propertyClassName property=property/> <#if property.collection><@pl property=property/> = new ArrayList
        <<#if property.wildCard && !property.attribute>OverlayWidgetImpl>
        ();<#elseif property.wildCard && property.attribute> String>()<#else>${property.className?cap_first}
        >();</#if><#else><@propertyName property = property/>;</#if>
</#macro>

<#macro structureIndicator prop>
    <#if prop.sequenceIndicator>
        new StructureIndicator.DefaultSequence(${prop.minOccurs},${prop.maxOccurs},<#if prop.baseClass??><#list prop.baseClass.properties as childProp><@structureIndicator prop=childProp/><#if childProp_has_next>,</#if></#list><#else>null</#if>)
    <#elseif prop.allIndicator>
        new StructureIndicator.DefaultAll(${prop.minOccurs},${prop.maxOccurs},<#if prop.baseClass??><#list prop.baseClass.properties as childProp><@structureIndicator prop=childProp/><#if childProp_has_next>,</#if></#list><#else>null</#if>)
    <#elseif prop.choiceIndicator>
        new StructureIndicator.DefaultChoice(${prop.minOccurs},${prop.maxOccurs},<#if prop.baseClass??><#list prop.baseClass.properties as childProp><@structureIndicator prop=childProp/><#if childProp_has_next>,</#if></#list><#else>null</#if>)
    <#elseif prop.groupIndicator>
        new StructureIndicator.DefaultGroup(${prop.minOccurs},${prop.maxOccurs},<#if prop.baseClass??><#list prop.baseClass.properties as childProp><@structureIndicator prop=childProp/><#if childProp_has_next>,</#if></#list><#else>null</#if>)
    <#elseif prop.wildCard>
        new StructureIndicator.DefaultWildcard(${prop.minOccurs},${prop.maxOccurs})
    <#else>
        new StructureIndicator.DefaultElement(${prop.minOccurs},${prop.maxOccurs},new ${prop.className?cap_first}())
    </#if>
</#macro>
