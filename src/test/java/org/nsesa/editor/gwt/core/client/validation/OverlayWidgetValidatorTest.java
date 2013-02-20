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
package org.nsesa.editor.gwt.core.client.validation;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import org.junit.Before;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * Date: 19/02/13 14:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class OverlayWidgetValidatorTest extends GwtTest {

    OverlayWidget parent;

    final OverlayWidget childA1 = getOverlayWidget("typeA");
    final OverlayWidget childA2 = getOverlayWidget("typeA");
    final OverlayWidget childB1 = getOverlayWidget("typeB");
    final OverlayWidget childC1 = getOverlayWidget("typeC");
    final OverlayWidget childD1 = getOverlayWidget("typeD");

    @Before
    public void setup() throws Exception {
        parent = new OverlayWidgetImpl() {
            @Override
            public Map<OverlayWidget, Occurrence> getAllowedChildTypes() {
                Map<OverlayWidget, Occurrence> allowedChildTypes = new LinkedHashMap<OverlayWidget, Occurrence>();
                allowedChildTypes.put(getOverlayWidget("typeA"), new Occurrence(0, 1));
                allowedChildTypes.put(getOverlayWidget("typeB"), new Occurrence(0, -1));
                allowedChildTypes.put(getOverlayWidget("typeC"), new Occurrence(1, -1));
                return allowedChildTypes;
            }

            @Override
            public String getNamespaceURI() {
                return "myNameSpaceURI";
            }

            @Override
            public String getType() {
                return "typeA";
            }
        };
        parent.addOverlayWidget(childA1);
        parent.addOverlayWidget(childA2);
        parent.addOverlayWidget(childB1);
        parent.addOverlayWidget(childC1);
        parent.addOverlayWidget(childD1);
    }

    @Test
    public void testValidate() throws Exception {
        OverlayWidgetValidator validator = new OverlayWidgetValidator();
        final ValidationResult result = validator.validate(childA1);
        assertNotNull(result);
        assertTrue(result.isSuccessful());
    }

    @Test
    public void testValidateMaxOccurence() throws Exception {
        OverlayWidgetValidator validator = new OverlayWidgetValidator();
        final ValidationResult result1 = validator.validate(childA2);
        assertNotNull(result1);
        assertFalse("Max occurrence for typA has been passed!", result1.isSuccessful());

        // check max occurrence for typeB, which is unbounded
        final OverlayWidget childB2 = getOverlayWidget("typeB");
        parent.addOverlayWidget(childB2);
        final OverlayWidget childB3 = getOverlayWidget("typeB");
        parent.addOverlayWidget(childB3);
        final ValidationResult result2 = validator.validate(childB2);
        assertTrue(result2.isSuccessful());
        final ValidationResult result3 = validator.validate(childB3);
        assertTrue(result3.isSuccessful());
    }

    @Test
    public void testValidateMinOccurence() throws Exception {
        OverlayWidgetValidator validator = new OverlayWidgetValidator();

        final OverlayWidget missingOccurrence = new OverlayWidgetImpl() {
            @Override
            public Map<OverlayWidget, Occurrence> getAllowedChildTypes() {
                Map<OverlayWidget, Occurrence> allowedChildTypes = new LinkedHashMap<OverlayWidget, Occurrence>();
                allowedChildTypes.put(getOverlayWidget("typeA"), new Occurrence(1, -1));
                return allowedChildTypes;
            }

            @Override
            public String getNamespaceURI() {
                return "myNameSpaceURI";
            }

            @Override
            public String getType() {
                return "typeA";
            }
        };
        assertFalse(validator.validate(missingOccurrence).isSuccessful());

        final OverlayWidget noOccurrence = new OverlayWidgetImpl() {
            @Override
            public Map<OverlayWidget, Occurrence> getAllowedChildTypes() {
                Map<OverlayWidget, Occurrence> allowedChildTypes = new LinkedHashMap<OverlayWidget, Occurrence>();
                allowedChildTypes.put(getOverlayWidget("typeA"), new Occurrence(0, -1));
                return allowedChildTypes;
            }

            @Override
            public String getNamespaceURI() {
                return "myNameSpaceURI";
            }

            @Override
            public String getType() {
                return "typeA";
            }
        };
        assertTrue(validator.validate(noOccurrence).isSuccessful());
    }


    private OverlayWidget getOverlayWidget(final String type) {
        return new OverlayWidgetImpl() {
            @Override
            public String getType() {
                return type;
            }

            @Override
            public String getNamespaceURI() {
                return "myNameSpaceURI";
            }
        };
    }
}
