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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.meta;

import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Controller for the meta part of the amendment (justification, notes, ...).
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class MetaPanelController implements AmendmentDialogAwareController {

    /**
     * The view for this controller.
     */
    private final MetaPanelView view;

    /**
     * The CSS resource for this view.
     */
    private final MetaPanelViewCss metaPanelViewCss;

    /**
     * The client factory.
     */
    private final ClientFactory clientFactory;

    /**
     * The dialog context giving access to runtime information.
     */
    private DialogContext dialogContext;

    @Inject
    public MetaPanelController(final ClientFactory clientFactory, final MetaPanelView view,
                               final MetaPanelViewCss metaPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.metaPanelViewCss = metaPanelViewCss;
        registerListeners();
    }

    private void registerListeners() {
        // nothing yet
    }

    public void removeListeners() {
        // nothing yet
    }

    /**
     * Validate the meta data (justification, notes).
     *
     * @return <tt>true</tt> if the validation succeeded.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Set the dialog context with runtime information.
     *
     * @param dialogContext the dialog context
     */
    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }

    /**
     * Return the view.
     * @return the view
     */
    public MetaPanelView getView() {
        return view;
    }

    /**
     * Return the title for the tab under which this controller is registered.
     * TODO i18n
     * @return the title
     */
    @Override
    public String getTitle() {
        return "Meta";
    }

    /**
     * Set the notes on this panel.
     * @param notes the notes
     */
    public void setNotes(final String notes) {
        this.view.setNotes(notes);
    }

    /**
     * Get the notes that were entered on this panel.
     * @return the notes
     */
    public String getNotes() {
        final String notes = view.getNotes();
        return notes != null ? notes.trim() : null;
    }

    /**
     * Set the justification on this panel.
     * @param justification the justification
     */
    public void setJustification(final String justification) {
        this.view.setJustification(justification);
    }

    /**
     * Get the trimmed justification.
     * @return the justification.
     */
    public String getJustification() {
        final String justification = view.getJustification();
        return justification != null ? justification.trim() : null;
    }
}
