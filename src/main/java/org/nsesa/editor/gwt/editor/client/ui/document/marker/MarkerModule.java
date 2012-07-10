package org.nsesa.editor.gwt.editor.client.ui.document.marker;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class MarkerModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(MarkerView.class).to(MarkerViewImpl.class);
    }
}