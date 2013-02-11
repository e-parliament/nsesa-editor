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
        if (validateSuperClassOrInterface(superClass, impl.getSuperclass())) {
            return true;
        }
        /*
        // this does NOT work in GWT - Class.getInterfaces() is not supported.
        for (Class<?> inter : impl.getInterfaces()) {
            if (validateSuperClassOrInterface(superClass, inter)) {
                return true;
            }
        }*/
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
