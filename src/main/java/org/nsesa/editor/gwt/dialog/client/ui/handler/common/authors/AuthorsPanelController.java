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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.authors;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.PersonDTO;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Controller for the common author panel in the amendment dialog. Part of the
 * {@link AmendmentDialogAwareController}s so it can easily be added to a tab panel.
 * <p/>
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorsPanelController implements AmendmentDialogAwareController {

    /**
     * The main view.
     */
    protected final AuthorsPanelView view;

    /**
     * The CSS resource.
     */
    protected final AuthorsPanelViewCss authorsPanelViewCss;

    /**
     * The client factory.
     */
    protected final ClientFactory clientFactory;

    /**
     * The dialog context.
     */
    protected DialogContext dialogContext;

    /**
     * The set of selected persons.
     */
    private Set<PersonDTO> selectedPersons = new LinkedHashSet<PersonDTO>();
    private HandlerRegistration selectionHandlerRegistration;

    @Inject
    public AuthorsPanelController(final ClientFactory clientFactory, final AuthorsPanelView view,
                                  final AuthorsPanelViewCss authorsPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.authorsPanelViewCss = authorsPanelViewCss;
    }

    /**
     * Perform validation on the author panel. Currently just returns <tt>true</tt>.
     *
     * @return <tt>true</tt> if the validation passes, and a valid (set of) author(s) has been selected.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Get the selected persons to act as authors for this amendment.
     *
     * @return the selected persons.
     */
    public Set<PersonDTO> getSelectedPersons() {
        return selectedPersons;
    }

    /**
     * Add a person dto to the group of selected persons.
     *
     * @param person the person to add as an author
     * @see #getSelectedPersons() to get the list
     */
    public void addPerson(final PersonDTO person) {
        if (selectedPersons.add(person)) {
            drawPersons();
        }
    }

    /**
     * Remove a person dto from the group of selected persons.
     *
     * @param person the person to remove as an author
     * @see #getSelectedPersons() to get the list
     */
    public void removePerson(final PersonDTO person) {
        if (selectedPersons.remove(person)) {
            drawPersons();
        }
    }

    /**
     * Clear all the selected persons and clear the representation in the view.
     */
    public void clear() {
        this.selectedPersons.clear();
        this.view.getAuthorsPanel().clear();
    }

    /**
     * Draw the selected persons using some quick & dirty panel with removal button.
     * TODO replace with a UI binder
     */
    private void drawPersons() {
        view.getAuthorsPanel().clear();
        for (final PersonDTO person : selectedPersons) {
            final HTML html = new HTML(person.getDisplayName());
            final Button removeButton = new Button("x", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    removePerson(person);
                }
            });
            HorizontalPanel holder = new HorizontalPanel();
            holder.add(html);
            holder.setWidth("100%");
            holder.add(removeButton);
            holder.setCellHorizontalAlignment(html, HasHorizontalAlignment.ALIGN_LEFT);
            holder.setCellHorizontalAlignment(removeButton, HasHorizontalAlignment.ALIGN_RIGHT);
            view.getAuthorsPanel().add(holder);
        }
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

    public void registerListeners() {
        // add a handler when a selection is made from the autocomplete results
        selectionHandlerRegistration = view.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
                final SuggestOracle.Suggestion selectedItem = event.getSelectedItem();
                if (selectedItem instanceof PersonMultiWordSuggestion) {
                    final PersonMultiWordSuggestion personMultiWordSuggestion = (PersonMultiWordSuggestion) selectedItem;
                    // clear the selection
                    view.getSuggestBox().setText("");
                    addPerson(personMultiWordSuggestion.getPerson());
                }
            }
        });
    }

    /**
     * Removes all registered event handlers from the event bus and UI.
     */
    public void removeListeners() {
        selectionHandlerRegistration.removeHandler();
    }

    /**
     * Return the view
     *
     * @return the view
     */
    public AuthorsPanelView getView() {
        return view;
    }

    /**
     * Return the title for this tab.
     * TODO i18n
     *
     * @return the title
     */
    @Override
    public String getTitle() {
        return "Author";
    }
}
