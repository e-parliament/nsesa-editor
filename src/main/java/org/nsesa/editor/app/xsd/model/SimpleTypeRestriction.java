/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */
package org.nsesa.editor.app.xsd.model;

import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSimpleType;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Contains a mapping of xsd facet restriction
 * (See {@link XSFacet})
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 18/10/12 09:11
 */
public class SimpleTypeRestriction {
    private List<String> enumeration = null;
    private String fractionDigits = null;
    private String maxExclusive = null;
    private String maxInclusive = null;
    private String minExclusive = null;
    private String minInclusive = null;
    private String length = null;
    private String maxLength = null;
    private String minLength = null;
    private String pattern = null;
    private String totalDigits = null;
    private String whiteSpace = null;

    public List<String> getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(List<String> enumeration) {
        this.enumeration = enumeration;
    }

    public String getFractionDigits() {
        return fractionDigits;
    }

    public void setFractionDigits(String fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public String getMaxExclusive() {
        return maxExclusive;
    }

    public void setMaxExclusive(String maxExclusive) {
        this.maxExclusive = maxExclusive;
    }

    public String getMaxInclusive() {
        return maxInclusive;
    }

    public void setMaxInclusive(String maxInclusive) {
        this.maxInclusive = maxInclusive;
    }

    public String getMinExclusive() {
        return minExclusive;
    }

    public void setMinExclusive(String minExclusive) {
        this.minExclusive = minExclusive;
    }

    public String getMinInclusive() {
        return minInclusive;
    }

    public void setMinInclusive(String minInclusive) {
        this.minInclusive = minInclusive;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTotalDigits() {
        return totalDigits;
    }

    public void setTotalDigits(String totalDigits) {
        this.totalDigits = totalDigits;
    }

    public String getWhiteSpace() {
        return whiteSpace;
    }

    public void setWhiteSpace(String whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    /**
     * Generates a restriction based on the given xsd simple type
     *
     * @param simpleType The simple type processed
     * @return
     */
    public static SimpleTypeRestriction getRestriction(XSSimpleType simpleType) {
        SimpleTypeRestriction typeRestriction = new SimpleTypeRestriction();
        XSRestrictionSimpleType restriction = simpleType.asRestriction();
        if (restriction != null) {
            Vector<String> enumeration = new Vector<String>();
            Iterator<? extends XSFacet> i = restriction.getDeclaredFacets().iterator();
            while (i.hasNext()) {
                XSFacet facet = i.next();
                if (facet.getName().equals(XSFacet.FACET_ENUMERATION)) {
                    enumeration.add(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MAXINCLUSIVE)) {
                    typeRestriction.setMaxInclusive(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MAXEXCLUSIVE)) {
                    typeRestriction.setMaxExclusive(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MININCLUSIVE)) {
                    typeRestriction.setMinInclusive(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MINEXCLUSIVE)) {
                    typeRestriction.setMinExclusive(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_LENGTH)) {
                    typeRestriction.setLength(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MINLENGTH)) {
                    typeRestriction.setMinLength(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_MAXLENGTH)) {
                    typeRestriction.setMaxLength(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_PATTERN)) {
                    typeRestriction.setPattern(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_TOTALDIGITS)) {
                    typeRestriction.setTotalDigits(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_WHITESPACE)) {
                    typeRestriction.setWhiteSpace(facet.getValue().value);
                }
                if (facet.getName().equals(XSFacet.FACET_FRACTIONDIGITS)) {
                    typeRestriction.setFractionDigits(facet.getValue().value);
                }
            }
            if (enumeration.size() > 0) {
                typeRestriction.setEnumeration(enumeration);
            }
        }
        return typeRestriction;
    }

}