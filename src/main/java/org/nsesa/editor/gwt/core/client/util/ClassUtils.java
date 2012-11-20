package org.nsesa.editor.gwt.core.client.util;

/**
 * Date: 20/11/12 11:12
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public class ClassUtils {
    public static boolean isAssignableFrom(Class<?> superClass, Class<?> impl) {
        if (impl == null) {
            return false;
        }

        if (superClass.equals(impl)) {
            return true;
        }

        // validate classes
        Class currentSuperClass = impl.getSuperclass();
        if (validateSuperClassOrInterface(superClass, impl.getSuperclass())) {
            return true;
        }
        for (Class<?> inter : impl.getInterfaces()) {
            if (validateSuperClassOrInterface(superClass, inter)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateSuperClassOrInterface(Class<?> superClass, Class<?> currentSuperClass) {
        while (currentSuperClass != null) {
            if (currentSuperClass.equals(superClass)) {
                return true;
            }
            currentSuperClass = currentSuperClass.getSuperclass();
        }
        return false;
    }
}
