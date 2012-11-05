package org.nsesa.editor.gwt.core.client.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to document the scope on various components used in the editor.
 *
 * Date: 05/11/12 15:48
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RUNTIME)
public @interface Scope {
    ScopeValue value() default ScopeValue.EDITOR;

    public static enum ScopeValue {
        EDITOR, DOCUMENT, AMENDMENT, DIALOG
    }
}