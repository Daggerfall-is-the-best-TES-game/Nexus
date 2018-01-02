package com.meti.lib.io.server;

import com.meti.lib.util.Console;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class Servers {
    private Servers() {
    }

    public static Server create(int port) throws IOException {
        return create(new ServerSocket(port));
    }

    public static Server create(ServerSocket serverSocket) {
        return new Server(serverSocket, new Console());
    }

    public static Server create(int port, Console console) throws IOException {
        return create(new ServerSocket(port), console);
    }

    public static Server create(ServerSocket serverSocket, Console console) {
        return new Server(serverSocket, console);
    }
}
