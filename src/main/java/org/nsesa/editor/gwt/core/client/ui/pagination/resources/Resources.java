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
package org.nsesa.editor.gwt.core.client.ui.pagination.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *  Interface to facilitate access to "pagination" image resources
 *
 * @author <a href="stelian.groza@gmail.com">Stelian Groza</a>
 * Date: 30/11/12 15:29
 */
public interface Resources extends ClientBundle {
    /**
     * "First" image
     * @return an ImageResource
     */
    @Source("go-first.png")
    public ImageResource first();

    /**
     * "Last" image
     * @return an ImageResource
     */
    @Source("go-last.png")
    public ImageResource last();

    /**
     * "Previous" image
     * @return an ImageResource
     */
    @Source("go-previous.png")
    public ImageResource previous();

    /**
     * "Next" image
     * @return an ImageResource
     */
    @Source("go-next.png")
    public ImageResource next();
}
