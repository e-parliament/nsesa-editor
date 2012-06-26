package org.nsesa.editor.gwt.editor.client.ui.document;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DocumentModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(DocumentView.class).to(DocumentViewImpl.class);
    }
}
