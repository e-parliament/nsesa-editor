package org.nsesa.editor.gwt.core.client.ui.overlay;

/**
 * Date: 30/06/12 19:10
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public interface AmendableWidgetListener {
    void onAmend(AmendableWidget sender);

    void onAdd(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild);

    void onAddWithExternalSource(AmendableWidget sender, AmendableWidget amendableWidget, boolean asChild);

    void onAmendWithChildren(AmendableWidget sender);

    void onAmendWithFootnotes(AmendableWidget sender);

    void onDelete(AmendableWidget sender);

    void onTranslate(AmendableWidget sender, String languageIso);

    void onTransfer(AmendableWidget sender);

    void onClick(AmendableWidget sender);

    void onDblClick(AmendableWidget sender);

    void onMouseOver(AmendableWidget sender);

    void onMouseOut(AmendableWidget sender);

}
