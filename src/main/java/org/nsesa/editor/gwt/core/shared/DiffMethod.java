package org.nsesa.editor.gwt.core.shared;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "diffmethod")
@XmlEnum
public enum DiffMethod {
    WORD, CHARACTER
}
