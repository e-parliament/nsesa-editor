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
package org.nsesa.editor.gwt.dialog.client.ui.handler.common.author;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.util.Scope;
import org.nsesa.editor.gwt.core.shared.PersonDTO;
import org.nsesa.editor.gwt.dialog.client.ui.dialog.DialogContext;
import org.nsesa.editor.gwt.dialog.client.ui.handler.common.AmendmentDialogAwareController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.DIALOG;

/**
 * Date: 24/06/12 21:42
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Scope(DIALOG)
public class AuthorPanelController implements AmendmentDialogAwareController {

    private final AuthorPanelView view;
    private final AuthorPanelViewCss authorPanelViewCss;

    private final ClientFactory clientFactory;

    private DialogContext dialogContext;

    private Set<PersonDTO> selectedPersons = new LinkedHashSet<PersonDTO>();

    @Inject
    public AuthorPanelController(final ClientFactory clientFactory, final AuthorPanelView view,
                                 final AuthorPanelViewCss authorPanelViewCss) {
        this.clientFactory = clientFactory;
        this.view = view;
        this.authorPanelViewCss = authorPanelViewCss;
        registerListeners();
    }

    @Override
    public boolean validate() {
        return true;
    }

    public Set<PersonDTO> getSelectedPersons() {
        return selectedPersons;
    }

    public void addPerson(PersonDTO person) {
        if (selectedPersons.add(person)) {
            drawPersons();
        }
    }

    public void removePerson(PersonDTO person) {
        if (selectedPersons.remove(person)) {
            drawPersons();
        }
    }

    public void clear() {
        this.selectedPersons.clear();
        this.view.getAuthorsPanel().clear();
    }

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

    @Override
    public void setContext(final DialogContext dialogContext) {
        this.dialogContext = dialogContext;
    }

    private void registerListeners() {
        // nothing yet
        view.getSuggestBox().addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
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

    public AuthorPanelView getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return "Author";
    }
}
