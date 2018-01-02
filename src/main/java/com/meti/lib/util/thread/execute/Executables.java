package com.meti.lib.util.thread.execute;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Executables {
    private Executables() {
    }

    public static Executable fromCallable(Callable callable) {
        return fromArgs(new Callable[]{callable}, null);
    }

    public static Executable fromArgs(Callable[] callables, Runnable[] runnables) {
        return new Executable() {
            @Override
            public Callable[] getCallables() {
                if (callables == null || callables.length == 0) {
                    return new Callable[0];
                } else {
                    return callables;
                }
            }

            @Override
            public Runnable[] getRunnables() {
                if (runnables == null || runnables.length == 0) {
                    return new Runnable[0];
                } else {
                    return runnables;
                }
            }
        };
    }

    public static Executable fromRunnable(Runnable runnable) {
        return fromArgs(null, new Runnable[]{runnable});
    }

    public static void executeAll(ExecutorService service, Executable... executables) {
        for (Executable executable : executables) {
            execute(service, executable);
        }
    }

    public static void execute(ExecutorService service, Executable executable) {
        for (Callable<?> callable : executable.getCallables()) {
            service.submit(callable);
        }

        for (Runnable runnable : executable.getRunnables()) {
            service.submit(runnable);
        }
    }
}
