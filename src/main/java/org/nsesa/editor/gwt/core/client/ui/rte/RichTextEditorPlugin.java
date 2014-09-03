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
import com.google.inject.ImplementedBy;
import org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget;

/**
 * Provides possibility to customize the rich text editor functionality without bloating the editor with unneeded
 * features.
 *
 * @author <a href="mailto:stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 09/01/13 11:06
 */
@ImplementedBy(RichTextCompositePlugin.class)
public interface RichTextEditorPlugin {
    /** used to fill in the content of html element with an empty char to be accessible from the user interface**/
    public static final String EMPTY_CHAR = "\u200b";

    /**
     * Run before editor initialization
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    void beforeInit(JavaScriptObject editor);

    /**
     * Run during editor initialization
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    void init(JavaScriptObject editor);

    /**
     * Run after editor initialization
     *
     * @param editor The Rich Text editor as JavaScriptObject
     */
    void afterInit(JavaScriptObject editor);

    /**
     * Set the parent overlay widget on the rich text editor. This can be used to link between the existing document
     * and the elements in the RTE.
     * @param parentOverlayWidget the parent overlay widget
     */
    void setParentOverlayWidget(OverlayWidget parentOverlayWidget);

}
