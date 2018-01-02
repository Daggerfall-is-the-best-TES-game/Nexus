package com.meti.lib.io.client;

import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ClientState {
    private final Properties properties = new Properties();

    {
        properties.setProperty("username", "Anonymous");
    }

    public Properties getProperties() {
        return properties;
    }
}
