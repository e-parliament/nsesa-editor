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
package org.nsesa.editor.gwt.core.client.ui.overlay.document;

import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;

/**
 * Date: 18/01/13 15:13
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class OverlayWidgetListenerMock implements OverlayWidgetListener {
    @Override
    public boolean beforeOverlayWidgetAwareAdded(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterOverlayWidgetAwareAdded(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean beforeOverlayWidgetAwareRemoved(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterOverlayWidgetAwareRemoved(OverlayWidget overlayWidget, OverlayWidgetAware amendmentController) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean beforeOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterOverlayWidgetAdded(OverlayWidget overlayWidget, OverlayWidget child) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean beforeOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterOverlayWidgetRemoved(OverlayWidget overlayWidget, OverlayWidget child) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
