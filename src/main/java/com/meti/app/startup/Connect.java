package com.meti.app.startup;

import com.meti.app.client.ClientDisplay;
import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.sources.ObjectSource;
import com.meti.lib.io.sources.Sources;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.stageable.StageableImpl;
import com.meti.lib.util.thread.execute.Executables;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.app.Main.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Connect extends StageableImpl {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    public void back() {
        try {
            load(new File(resources, "Startup.fxml"));
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void next() {
        loadClient();
        loadDisplay();
    }

    private void loadDisplay() {
        try {
            FXMLBundle bundle = load(new File(resources, "ClientDisplay.fxml"));
            ClientDisplay controller = (ClientDisplay) bundle.getController();
            controller.run();

            console.log(Level.FINE, "Loaded client display");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load display", e);
        }
    }

    private void loadClient() {
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            Socket socket = new Socket(address, port);
            ObjectSource source = Sources.createObjectSource(socket);
            Client client = Clients.create(source);

            Executables.execute(service, client);

            getAppState().setClient(client);

            console.log(Level.FINE, "Loaded client");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load client", e);
        }
    }
}
