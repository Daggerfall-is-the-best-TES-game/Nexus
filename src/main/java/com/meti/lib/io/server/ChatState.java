package com.meti.lib.io.server;

import com.meti.lib.io.server.chat.Message;
import com.meti.lib.util.thread.Handler;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ChatState {
    private final ArrayList<Message> messages = new ArrayList<>();
    private Handler<Message> onAdd;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void add(Message message) {
        messages.add(message);

        onAdd.act(message);
    }

    public void setOnAdd(Handler<Message> onAdd) {
        this.onAdd = onAdd;
    }
}
