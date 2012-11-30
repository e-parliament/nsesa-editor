package org.nsesa.editor.gwt.editor.client.ui.pagination.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created with IntelliJ IDEA.
 * User: groza
 * Date: 30/11/12
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public interface Resources extends ClientBundle {
    @Source("go-first.png")
    public ImageResource first();
    @Source("go-last.png")
    public ImageResource last();
    @Source("go-previous.png")
    public ImageResource previous();
    @Source("go-next.png")
    public ImageResource next();
}
