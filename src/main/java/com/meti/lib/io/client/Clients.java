package com.meti.lib.io.client;

import com.meti.lib.io.sources.ObjectSource;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Clients {
    private Clients() {
    }

    public static Client create(ObjectSource objectSource) {
        return new Client(objectSource);
    }
}
