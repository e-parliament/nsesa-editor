<#-- @ftlvariable name="overlayClasses" type="java.util.List<OverlayClass>" -->
<#-- @ftlvariable name="overlayClass" type="org.nsesa.editor.app.xsd.model.OverlayClass" -->
package ${overlayClass.packageName};

<#list overlayClass.imports as import>
import ${import}.*;
</#list>
import org.nsesa.editor.gwt.core.client.ui.overlay.xml.*;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.*;

import com.google.gwt.dom.client.Element;
import java.util.ArrayList;

/**
* This file is generated.
*/
public class ${overlayClass.name?cap_first} implements OverlayFactory {

public AmendableWidget getAmendableWidget(final Element element) {
if ("".equals(element.getTagName())) {
throw new IllegalArgumentException("Empty element or null passed.");
}

<#list overlayClasses as cl>
else if ("${cl.name}".equalsIgnoreCase(element.getTagName())) {
return new ${cl.name?cap_first}(element);
}
</#list>

// nothing found
return null;
}
}