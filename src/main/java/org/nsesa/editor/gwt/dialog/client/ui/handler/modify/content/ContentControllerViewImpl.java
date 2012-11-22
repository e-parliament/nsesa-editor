package org.nsesa.editor.gwt.dialog.client.ui.handler.modify.content;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.nsesa.editor.gwt.dialog.client.ui.rte.RichTextEditor;

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
    public void setOriginalText(String content) {
        originalText.setHTML(content);
    }
}
