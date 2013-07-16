/**
 * Copyright 2013 European Parliament
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
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

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a mapping of xsd facet restriction (See {@link XSFacet}).
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a> (cleanup and documentation)
 *         <p/>
 *         Date: 18/10/12 09:11
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

    public void setEnumeration(final List<String> enumeration) {
        this.enumeration = enumeration;
    }

    public String getFractionDigits() {
        return fractionDigits;
    }

    public void setFractionDigits(final String fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public String getMaxExclusive() {
        return maxExclusive;
    }

    public void setMaxExclusive(final String maxExclusive) {
        this.maxExclusive = maxExclusive;
    }

    public String getMaxInclusive() {
        return maxInclusive;
    }

    public void setMaxInclusive(final String maxInclusive) {
        this.maxInclusive = maxInclusive;
    }

    public String getMinExclusive() {
        return minExclusive;
    }

    public void setMinExclusive(final String minExclusive) {
        this.minExclusive = minExclusive;
    }

    public String getMinInclusive() {
        return minInclusive;
    }

    public void setMinInclusive(final String minInclusive) {
        this.minInclusive = minInclusive;
    }

    public String getLength() {
        return length;
    }

    public void setLength(final String length) {
        this.length = length;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(final String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(final String minLength) {
        this.minLength = minLength;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public String getTotalDigits() {
        return totalDigits;
    }

    public void setTotalDigits(final String totalDigits) {
        this.totalDigits = totalDigits;
    }

    public String getWhiteSpace() {
        return whiteSpace;
    }

    public void setWhiteSpace(final String whiteSpace) {
        this.whiteSpace = whiteSpace;
    }

    /**
     * Generates a restriction based on the given xsd simple type.
     *
     * @param simpleType The simple type to be processed
     * @return the restriction
     */
    public static SimpleTypeRestriction getRestriction(final XSSimpleType simpleType) {
        final SimpleTypeRestriction typeRestriction = new SimpleTypeRestriction();
        final XSRestrictionSimpleType restriction = simpleType.asRestriction();
        if (restriction != null) {
            final List<String> enumeration = new ArrayList<String>();
            for (final XSFacet facet : restriction.getDeclaredFacets()) {
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
            if (!enumeration.isEmpty()) {
                typeRestriction.setEnumeration(enumeration);
            }
        }
        return typeRestriction;
    }

}