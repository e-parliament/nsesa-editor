package org.nsesa.editor.gwt.core.client.ui.amendment.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Date: 17/12/12 16:14
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Resources extends ClientBundle {
    @Source("delete-on.png")
    ImageResource deleteOn();

    @Source("delete-off.png")
    ImageResource deleteOff();

    @Source("more-on.png")
    ImageResource moreOn();

    @Source("more-off.png")
    ImageResource moreOff();

    @Source("edit-on.png")
    ImageResource editOn();

    @Source("edit-off.png")
    ImageResource editOff();
}
