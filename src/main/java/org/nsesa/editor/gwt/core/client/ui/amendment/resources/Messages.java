package org.nsesa.editor.gwt.core.client.ui.amendment.resources;

import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Date: 10/01/13 17:08
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface Messages extends ConstantsWithLookup {
    @Key("amendment.status.candidate")
    String candidate();

    @Key("amendment.status.tabled")
    String tabled();

    @Key("amendment.status.deleted")
    String deleted();

    @Key("amendment.status.withdrawn")
    String withdrawn();

    @Key("amendment.status.registered")
    String registered();

    @Key("amendment.status.returned")
    String returned();
}
