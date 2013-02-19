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
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.Occurrence;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;

import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Date: 19/02/13 14:25
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class OverlayWidgetValidatorTest extends GwtTest {

    @Test
    public void testValidate() throws Exception {
        final OverlayWidget parent = new OverlayWidgetImpl() {
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

        final OverlayWidget childA1 = getOverlayWidget("typeA");
        final OverlayWidget childA2 = getOverlayWidget("typeA");
        final OverlayWidget childB1 = getOverlayWidget("typeB");
        final OverlayWidget childC1 = getOverlayWidget("typeC");
        final OverlayWidget childD1 = getOverlayWidget("typeD");

        parent.addOverlayWidget(childA1);
        parent.addOverlayWidget(childA2);
        parent.addOverlayWidget(childB1);
        parent.addOverlayWidget(childC1);
        parent.addOverlayWidget(childD1);

        OverlayWidgetValidator validator = new OverlayWidgetValidator();
        final ValidationResult result1 = validator.validate(childA1);
        assertNotNull(result1);
        assertTrue(result1.isSuccessful());

        final ValidationResult result2 = validator.validate(childA2);
        assertNotNull(result2);
        assertFalse(result2.getErrorMessage(), result2.isSuccessful());

        final ValidationResult result3 = validator.validate(childB1);
        assertNotNull(result3);
        assertTrue(result3.isSuccessful());

        final ValidationResult result4 = validator.validate(childC1);
        assertNotNull(result4);
        assertTrue(result4.isSuccessful());

        final ValidationResult result5 = validator.validate(childD1);
        assertNotNull(result5);
        assertFalse(result5.getErrorMessage(), result5.isSuccessful());
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
