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
package org.nsesa.editor.gwt.core.client.ui.rte;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class is available as a convenience base class for all plugins;
 * it provides default empty implementation for all methods.
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 14/03/13 13:27
 */
public class DefaultRichTextEditorPlugin implements RichTextEditorPlugin {
    @Override
    public void beforeInit(JavaScriptObject editor) {
        //do nothing
    }

    @Override
    public void init(JavaScriptObject editor) {
        //do nothing.
    }

    @Override
    public void afterInit(JavaScriptObject editor) {
        //do nothing.
    }
}
