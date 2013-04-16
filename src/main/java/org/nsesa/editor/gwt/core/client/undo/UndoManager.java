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
package org.nsesa.editor.gwt.core.client.undo;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * Date: 15/04/13 23:26
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class UndoManager {

    private static final Logger LOG = Logger.getLogger(UndoManager.class.getName());

    private final Stack<Action> actions = new Stack<Action>();

    void execute(final Action action) {
        actions.add(action);
        action.execute();
        LOG.info("Executing action " + action.getDescription());
    }

    void undo() {
        final Action pop = actions.pop();
        if (pop != null) {
            if (pop instanceof UndoableAction) {
                final UndoableAction undoableAction = (UndoableAction) pop;
                undoableAction.undo();
                LOG.info("Undo action " + undoableAction.getDescription());
            }
        }
    }

    void clear() {
        actions.clear();
    }
}
