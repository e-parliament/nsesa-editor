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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.nsesa.editor.gwt.core.client.ClientFactory;
import org.nsesa.editor.gwt.core.client.ui.overlay.Format;
import org.nsesa.editor.gwt.core.client.ui.overlay.Locator;
import org.nsesa.editor.gwt.core.client.ui.overlay.NumberingType;

import java.util.HashMap;
import java.util.Map;

/**
 * An evaluator for num that will compute the num based on the existing widgets.
 * <p/>
 * Date: 16/08/13 18:35
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Singleton
public class MapEvaluator implements OverlaySnippetEvaluator.Evaluator {

    private final Map<String, Object> lookups;

    @Inject
    public MapEvaluator(Map<String, Object> lookups) {
        this.lookups = lookups;
    }

    @Override
    public String getPlaceHolder() {
        return "${(.)*}";
    }

    @Override
    public String evaluate() {
        return lookups.get(""/*dammit!!!*/).toString();
    }
}
