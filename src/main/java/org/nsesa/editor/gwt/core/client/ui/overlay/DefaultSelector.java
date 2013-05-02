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
package org.nsesa.editor.gwt.core.client.ui.overlay;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.web.bindery.event.shared.HandlerRegistration;
import org.nsesa.editor.gwt.core.client.event.selection.*;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentEventBus;
import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24/04/13 11:32
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class DefaultSelector<T extends OverlayWidgetAware> implements Selector<T> {

    private final DocumentEventBus documentEventBus;
    private final List<T> selectedOverlayWidgetAwareList = new ArrayList<T>();
    private HandlerRegistration amendmentControllerSelectionActionEventHandlerRegistration;
    private HandlerRegistration amendmentControllerSelectEventHandlerRegistration;

    private CollectionFilter<T> collectionFilter;

    public DefaultSelector(DocumentEventBus documentEventBus) {
        this.documentEventBus = documentEventBus;
        registerListeners();
    }

    private void registerListeners() {
        // execute an action on the selected amendments
        amendmentControllerSelectionActionEventHandlerRegistration = documentEventBus.addHandler(OverlayWidgetAwareSelectionActionEvent.TYPE, new OverlayWidgetAwareSelectionActionEventHandler() {
            @Override
            public void onEvent(OverlayWidgetAwareSelectionActionEvent event) {
                event.getAction().execute(selectedOverlayWidgetAwareList);
            }
        });

        // apply a selection
        amendmentControllerSelectEventHandlerRegistration = documentEventBus.addHandler(OverlayWidgetAwareSelectionEvent.TYPE, new OverlayWidgetAwareSelectionEventHandler() {
            @Override
            public void onEvent(OverlayWidgetAwareSelectionEvent event) {
                applySelection((Selection<T>) event.getSelection());
            }
        });
    }


    public void removeListeners() {
        amendmentControllerSelectEventHandlerRegistration.removeHandler();
        amendmentControllerSelectionActionEventHandlerRegistration.removeHandler();
    }

    @Override
    public List<T> getSelected() {
        return selectedOverlayWidgetAwareList;
    }

    @Override
    public void setCollectionFilter(CollectionFilter<T> collectionFilter) {
        this.collectionFilter = collectionFilter;
    }

    @Override
    public void applySelection(final Selection<T> selection) {
        selectedOverlayWidgetAwareList.clear();
        if (collectionFilter != null) {
            List<T> toAdd = new ArrayList<T>(Collections2.filter(collectionFilter.getCollection(), new Predicate<T>() {
                @Override
                public boolean apply(T input) {
                    return selection.select(input);
                }
            }));
            addToSelectedAmendmentControllers(toAdd);
        }
    }

    @Override
    public void addToSelectedAmendmentControllers(List<T> amendmentControllers) {
        selectedOverlayWidgetAwareList.addAll(amendmentControllers);
        documentEventBus.fireEvent(new OverlayWidgetAwareSelectedEvent(selectedOverlayWidgetAwareList));
    }

    @Override
    public void removeFromSelectedAmendmentControllers(List<T> amendmentControllers) {
        selectedOverlayWidgetAwareList.removeAll(amendmentControllers);
        documentEventBus.fireEvent(new OverlayWidgetAwareSelectedEvent(selectedOverlayWidgetAwareList));
    }
}
