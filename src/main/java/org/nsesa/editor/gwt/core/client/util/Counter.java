package org.nsesa.editor.gwt.core.client.util;

/**
 * Date: 03/12/12 11:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public final class Counter {
    int value = 0;

    public Counter() {
    }

    public Counter(int value) {
        this.value = value;
    }

    public void increment() {
        value++;
    }

    public int incrementAndGet() {
        return ++value;
    }

    public int get() {
        return value;
    }
}
