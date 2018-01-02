package com.meti.lib.util.thread;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public interface Action<P, R> {
    R act(P... params);
}
