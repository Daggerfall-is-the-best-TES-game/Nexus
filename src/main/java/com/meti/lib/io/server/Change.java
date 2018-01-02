package com.meti.lib.io.server;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public interface Change extends Serializable {
    void update(ServerState state);
}
