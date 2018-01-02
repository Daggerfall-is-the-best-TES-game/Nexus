package com.meti.lib.io.server.command;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.ServerState;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public abstract class Command implements Serializable {
    public abstract void perform(ServerState state, Client client) throws IOException;
}
