package com.meti.lib.util.thread.execute;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public interface Executable {
    Callable[] getCallables();

    Runnable[] getRunnables();
}
