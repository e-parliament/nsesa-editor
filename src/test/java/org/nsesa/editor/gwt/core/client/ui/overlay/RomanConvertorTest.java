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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import junit.framework.TestCase;

/**
 * Date: 23/10/12 11:05
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class RomanConvertorTest extends TestCase {

    public void testConversion() throws Exception {
        assertEquals("i", RomanConvertor.int2roman(1));
        assertEquals("ii", RomanConvertor.int2roman(2));
        assertEquals("iii", RomanConvertor.int2roman(3));
        assertEquals("iv", RomanConvertor.int2roman(4));
        assertEquals("v", RomanConvertor.int2roman(5));
        assertEquals("vi", RomanConvertor.int2roman(6));
        assertEquals("vii", RomanConvertor.int2roman(7));
        assertEquals("ix", RomanConvertor.int2roman(9));
        assertEquals("x", RomanConvertor.int2roman(10));
        assertEquals("xi", RomanConvertor.int2roman(11));
        assertEquals("xix", RomanConvertor.int2roman(19));
        assertEquals("xx", RomanConvertor.int2roman(20));
        assertEquals("l", RomanConvertor.int2roman(50));
        assertEquals("xcix", RomanConvertor.int2roman(99));
        assertEquals("c", RomanConvertor.int2roman(100));
    }
}
