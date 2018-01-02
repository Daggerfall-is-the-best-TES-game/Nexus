package com.meti.lib.util.thread.callback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class DefaultCallback implements Callback {
    @Override
    public Boolean act(Exception... params) {
        for (Exception e : params) {
            e.printStackTrace();
        }

        return true;
    }
}
