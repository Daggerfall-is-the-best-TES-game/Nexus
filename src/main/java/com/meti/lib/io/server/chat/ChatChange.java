package com.meti.lib.io.server.chat;

import com.meti.lib.io.server.Change;
import com.meti.lib.io.server.ServerState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ChatChange implements Change {
    private final Message content;

    public ChatChange(Message content) {
        this.content = content;
    }

    @Override
    public void update(ServerState state) {
        state.getChatState().add(content);
    }

    public Message getContent() {
        return content;
    }
}
