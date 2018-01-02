package com.meti.lib.io.server.asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class Addition<T> {
    private final T value;
    private final int index;

    public Addition(T value, int index) {
        this.value = value;
        this.index = index;
    }

    public T getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
