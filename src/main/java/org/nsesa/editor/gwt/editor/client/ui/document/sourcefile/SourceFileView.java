package org.nsesa.editor.gwt.editor.client.ui.document.sourcefile;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * Date: 28/01/13 15:27
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(SourceFileViewImpl.class)
public interface SourceFileView extends IsWidget {
    public void setStyleName(String styleName);
}
