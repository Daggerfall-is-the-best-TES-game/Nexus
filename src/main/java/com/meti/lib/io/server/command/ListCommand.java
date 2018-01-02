package com.meti.lib.io.server.command;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.ServerState;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/21/2017
 */
public class ListCommand extends Command {
    private final Argument type;

    public ListCommand(Argument type) {
        this.type = type;
    }

    @Override
    public void perform(ServerState state, Client client) throws IOException {
        switch (type) {
            case FILES:
                client.write(state.getManager().getFiles());
                break;
            case CHAT:
                client.write(state.getChatState().getMessages());
                break;
            default:
                client.write(new UnsupportedOperationException("Cannot use argument " + type.name() + " in ListCommand"));
        }

        client.flush();
    }
}
