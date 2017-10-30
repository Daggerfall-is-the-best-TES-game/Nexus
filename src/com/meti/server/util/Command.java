package com.meti.server.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2017
 */
public class Command implements Serializable {
    private final String name;
    private final Serializable[] args;

    public Command(String name, Serializable... args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Serializable[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
