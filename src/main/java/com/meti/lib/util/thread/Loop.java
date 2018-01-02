package com.meti.lib.util.thread;

import com.meti.lib.util.thread.callback.Callback;
import com.meti.lib.util.thread.callback.DefaultCallback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public abstract class Loop implements Runnable {
    private Callback callback = new DefaultCallback();
    private boolean running = false;

    @Override
    public void run() {
        setRunning(true);

        while (!Thread.interrupted() && running) {
            try {
                loop();
            } catch (Exception e) {
                callback.act(e);

                //stop the loop
                break;
            }
        }
    }

    public abstract void loop() throws Exception;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
