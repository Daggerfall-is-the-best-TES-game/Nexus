package com.meti.lib.io.server.command;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public abstract class AssetCommand extends Command {
    protected final long assetID;

    public AssetCommand(long assetID) {
        this.assetID = assetID;
    }
}
