package org.nsesa.editor.gwt.core.client.util;

/**
 * Date: 03/12/12 11:06
 *
 * @author <a href="philip.luppens@gmail.com">Philip Luppens</a>
 * @version $Id$
 */
public final class Counter {
    final int initialValue;
    int value = 0;

    public Counter() {
        this.initialValue = 0;
    }

    public Counter(int initialValue) {
        this.initialValue = initialValue;
        this.value = initialValue;
    }

    public void reset() {
        this.value = this.initialValue;
    }

    public void decrement() {
        value--;
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
