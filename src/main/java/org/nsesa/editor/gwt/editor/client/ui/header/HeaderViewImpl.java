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
package org.nsesa.editor.gwt.editor.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.event.LocaleChangeEvent;
import org.nsesa.editor.gwt.core.client.ui.overlay.TextUtils;
import org.nsesa.editor.gwt.core.client.util.Scope;

import java.util.Arrays;
import java.util.List;

import static org.nsesa.editor.gwt.core.client.util.Scope.ScopeValue.EDITOR;

/**
 * Default implementation for the {@link HeaderView} using UIBinder.
 * Date: 24/06/12 21:44
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
@Scope(EDITOR)
public class HeaderViewImpl extends Composite implements HeaderView {
    interface MyUiBinder extends UiBinder<Widget, HeaderViewImpl> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    /**
     * The client factory, giving access to local dependencies and the user context.
     */
    protected final ClientFactory clientFactory;

    @UiField
    Label name;
    @UiField
    Label roles;

    @UiField
    ListBox availableLanguages;

    @Inject
    public HeaderViewImpl(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        final Widget widget = uiBinder.createAndBindUi(this);
        initWidget(widget);
        registerListeners();
    }

    private void registerListeners() {
        availableLanguages.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                clientFactory.getEventBus().fireEvent(new LocaleChangeEvent(availableLanguages.getValue(availableLanguages.getSelectedIndex())));
            }
        });
    }

    @Override
    public void setLoggedInPersonName(final String personName) {
        this.name.setText(personName);
    }

    @Override
    public void setLoggedInPersonRoles(final String[] roles) {
        if (roles != null) {
            this.roles.setText(Arrays.asList(roles).toString());
        }
    }

    @Override
    public void setAvailableLanguages(final List<String> localeNames) {
        availableLanguages.setVisible(false);
        if (localeNames != null && localeNames.size() > 1) {
            availableLanguages.clear();
            for (final String locale : localeNames) {
                final String displayName =
                        "default".equalsIgnoreCase(locale) ? "english" : LocaleInfo.getLocaleNativeDisplayName(locale);
                if (displayName != null) {
                    availableLanguages.addItem(TextUtils.capitalize(displayName), locale);
                }
            }
            availableLanguages.setVisible(true);
        }
        setSelectedUILanguage();
    }

    private void setSelectedUILanguage() {
        final String localeName = LocaleInfo.getCurrentLocale().getLocaleName();
        for (int i = 0; i < availableLanguages.getItemCount(); i++) {
            if (localeName.equalsIgnoreCase(availableLanguages.getValue(i))) {
                availableLanguages.setSelectedIndex(i);
                break;
            }
        }
    }
}
