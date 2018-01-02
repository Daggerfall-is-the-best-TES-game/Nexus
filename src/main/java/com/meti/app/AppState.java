package com.meti.app;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.Server;
import javafx.application.Application;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/23/2017
 */
public class AppState {
    private Application application;
    private Server server;
    private Client client;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
