package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.inject.client.GinModule;
import com.google.gwt.inject.client.binder.GinBinder;
import com.google.inject.Singleton;

/**
 * Date: 24/06/12 15:11
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DeadlineModule implements GinModule {
    @Override
    public void configure(GinBinder binder) {
        binder.bind(DeadlineView.class).to(DeadlineViewImpl.class).in(Singleton.class);
    }
}
