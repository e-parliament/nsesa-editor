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
package org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Resources for the {@link org.nsesa.editor.gwt.core.client.ui.document.sourcefile.actionbar.ActionBarView}.
 * Date: 03/08/12 15:37
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Resources extends ClientBundle {

    @Source("comment_fill_16x14.png")
    ImageResource translate();

    @Source("lock_fill_12x16.png")
    ImageResource lock();

    @Source("move_alt1_16x16.png")
    ImageResource move();

    @Source("pen_alt2_16x16.png")
    ImageResource amend();

    @Source("plus_alt_16x16.png")
    ImageResource children();

    @Source("x_alt_16x16.png")
    ImageResource delete();
}
