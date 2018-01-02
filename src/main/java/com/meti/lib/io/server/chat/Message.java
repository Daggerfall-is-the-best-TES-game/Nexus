package com.meti.lib.io.server.chat;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/23/2017
 */
public class Message implements Serializable {
    private final String name;
    private final String content;

    public Message(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "[" + name + "]: " + content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
