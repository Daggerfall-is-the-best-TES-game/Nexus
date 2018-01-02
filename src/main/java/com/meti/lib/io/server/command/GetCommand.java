package com.meti.lib.io.server.command;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.ServerState;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/21/2017
 */
public class GetCommand extends AssetCommand {
    public GetCommand(long assetID) {
        super(assetID);
    }

    @Override
    public void perform(ServerState state, Client client) throws IOException {
        client.write(state.getManager().getAsset(assetID));
        client.flush();
    }
}
