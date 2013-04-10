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
package org.nsesa.editor.gwt.core.client.ui.deadline;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

/**
 * View for the deadline component.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@ImplementedBy(DeadlineViewImpl.class)
public interface DeadlineView extends IsWidget {

    /**
     * Set the formatted deadline.
     * @param deadline the deadline to set
     */
    void setDeadline(String deadline);

    /**
     * Set the style when the deadline has passed.
     */
    void setPastStyle();

    /**
     * Set the style to warn that the deadline is within 24 hours.
     */
    void set24HourStyle();

    /**
     * Set the style to warn that the deadline is within 1 hour.
     */
    void set1HourStyle();

    /**
     * General stylename to be used in the UIBinder.
     * @param style the stylename to set
     */
    void setStyleName(String style);
}
