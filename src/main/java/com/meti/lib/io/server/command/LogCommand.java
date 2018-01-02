package com.meti.lib.io.server.command;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.ServerState;

import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class LogCommand extends Command {
    private final Level level;
    private final String message;

    public LogCommand(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    @Override
    public void perform(ServerState state, Client client) {
        state.getConsole().log(level, message);
    }
}
