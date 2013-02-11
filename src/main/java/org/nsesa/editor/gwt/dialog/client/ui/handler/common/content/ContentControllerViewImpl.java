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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.content;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.core.client.ui.rte.RichTextEditor;

/**
 * Date: 24/06/12 21:44
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ContentControllerViewImpl extends Composite implements ContentControllerView {

    interface MyUiBinder extends UiBinder<Widget, ContentControllerViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);


    @UiField(provided = true)
    final RichTextEditor originalText;

    @Inject
    public ContentControllerViewImpl(@Named("originalText") final RichTextEditor originalText) {
        this.originalText = originalText;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
    }

    @Override
    public void addBodyClass(String className) {
        originalText.addBodyClass(className);
    }

    @Override
    public void resetBodyClass() {
        originalText.resetBodyClass();
    }

    @Override
    public void setOriginalText(String content) {
        originalText.setHTML(content);
    }

    @Override
    public String getOriginalText() {
        return originalText.getHTML();
    }

    @Override
    public RichTextEditor getRichTextEditor() {
        return originalText;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
    }
}
