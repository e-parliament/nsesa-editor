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
package org.nsesa.editor.gwt.editor.client;

import com.google.gwt.activity.shared.ActivityMapper;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ServiceFactory;
import org.nsesa.editor.gwt.core.client.ui.confirmation.ConfirmationController;
import org.nsesa.editor.gwt.core.client.ui.error.ErrorController;
import org.nsesa.editor.gwt.core.client.ui.notification.NotificationController;
import org.nsesa.editor.gwt.editor.client.activity.EditorPlaceFactory;
import org.nsesa.editor.gwt.core.client.ui.document.DocumentController;
import org.nsesa.editor.gwt.editor.client.ui.main.EditorController;

/**
 * Interface for GIN injectors. This interface must be extended in any module that extends {@link Editor}.
 *
 * Date: 24/06/12 15:56
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Injector {

    /**
     * Get the client factory with access to the client context.
     * @return the client factory
     */
    ClientFactory getClientFactory();

    /**
     * Get the service factory, with access to GWT RPC services.
     * @return the service factory
     */
    ServiceFactory getServiceFactory();

    /**
     * Return the main editor controller.
     * @return the editor controller
     */
    EditorController getEditorController();

    /**
     * Return a document controller.
     * @return a document controller
     */
    DocumentController getDocumentController();

    /**
     * Return the error controller.
     * @return the error controller
     */
    ErrorController getErrorController();

    /**
     * Return the confirmation controller.
     * @return the confirmation controller
     */
    ConfirmationController getConfirmationController();

    /**
     * Return the notification controller.
     * @return the notification controller
     */
    NotificationController getNotificationController();

    /**
     * Return the activity mapper.
     * @return the activity mapper
     */
    ActivityMapper getActivityMapper();

    /**
     * Return the place factory.
     * @return the place factory
     */
    EditorPlaceFactory getPlaceFactory();
}
