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

import org.nsesa.editor.gwt.core.client.ui.document.OverlayWidgetAware;
import org.nsesa.editor.gwt.core.client.util.Selection;

import java.util.List;

/**
 * Class responsible for reporting the location of an {@link org.nsesa.editor.gwt.core.client.ui.overlay.document.OverlayWidget}.
 * Date: 06/07/12 17:21
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Selector<T extends OverlayWidgetAware> {

    public static interface CollectionFilter<T> {
        List<T> getCollection();
    }

    List<T> getSelected();

    /**
     * Apply a selection to a set of amendment controllers, and keep only those that pass the
     * {@link org.nsesa.editor.gwt.core.client.util.Selection#select(OverlayWidgetAware)} filter.
     *
     * @param selection the selection to filter
     */
    void applySelection(final Selection<T> selection);

    void setCollectionFilter(CollectionFilter<T> collectionFilter);

    /**
     * Add a set of amendment controllers to the list of selected amendment controllers for this document.
     *
     * @param amendmentControllers the amendment controllers to add to the selection
     */
    void addToSelectedAmendmentControllers(final List<T> amendmentControllers);

    /**
     * Remove a set of amendment controllers from the list of selected amendment controllers for this document.
     *
     * @param amendmentControllers the amendment controllers to remove from the selection
     */
    void removeFromSelectedAmendmentControllers(final List<T> amendmentControllers);
}
