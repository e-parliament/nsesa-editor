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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.gwt.user.client.DOM;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidgetImpl;

/**
 * Date: 16/04/13 15:36
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@GwtModule("org.nsesa.editor.gwt.editor.Editor")
public class HTMLTransformerTest extends GwtTest {

    private OverlayWidget root;
    private HTMLTransformer htmlTransformer = new HTMLTransformer();

    @Before
    public void setUp() throws Exception {
        root = new OverlayWidgetImpl(DOM.createSpan()) {
            @Override
            public String getNamespaceURI() {
                return "http://ns.tld";
            }
        };
        root.setType("root");

        OverlayWidget childA = new OverlayWidgetImpl(DOM.createSpan()) {
            @Override
            public String getNamespaceURI() {
                return "http://ns.tld";
            }
        };
        childA.setType("childA");
        root.addOverlayWidget(childA);

        OverlayWidget childAA = new OverlayWidgetImpl(DOM.createSpan()) {
            @Override
            public String getNamespaceURI() {
                return "http://ns.tld";
            }
        };
        childAA.setType("childAA");
        childAA.setInnerHTML("childAA.innerHTML");
        childA.addOverlayWidget(childAA);

        OverlayWidget childB = new OverlayWidgetImpl(DOM.createSpan()) {
            @Override
            public String getNamespaceURI() {
                return "http://ns.tld";
            }
        };
        childB.setType("childB");
        childB.setInnerHTML("childB.innerHTML");
        root.addOverlayWidget(childB);
    }

    @Test
    public void testTransform() throws Exception {
        final String result = htmlTransformer.transform(root);
        Assert.assertEquals("<span class=\"root\" data-type=\"root\" data-ns=\"http://ns.tld\"><span class=\"childA\" data-type=\"childA\" data-ns=\"http://ns.tld\"><span class=\"childAA\" data-type=\"childAA\" data-ns=\"http://ns.tld\">childAA.innerHTML</span></span><span class=\"childB\" data-type=\"childB\" data-ns=\"http://ns.tld\">childB.innerHTML</span></span>", result);
    }
}
