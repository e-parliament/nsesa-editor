package org.nsesa.editor.gwt.editor.client.event.amendments;

import java.util.List;

/**
 * Expose an action that could be executed among a group of amendments identified through their id
 * User: groza
 * Date: 28/11/12
 * Time: 10:29
 */
public interface AmendmentsAction {
    public String getName();
    public void execute(List<String> ids);

}
