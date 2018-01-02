package com.meti.app.startup;

import com.meti.app.Main;
import com.meti.app.client.ClientDisplay;
import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.Servers;
import com.meti.lib.io.sources.ObjectSource;
import com.meti.lib.io.sources.Sources;
import com.meti.lib.util.fx.stageable.StageableImpl;
import com.meti.lib.util.thread.execute.Executables;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.*;
import static com.meti.lib.util.Utility.assertNullParameters;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Local extends StageableImpl implements Initializable {
    private static final File defaultLocation = new File("Server");
    //for the fxml here, we show the absolute path
    //but sometimes we have to use the relative path
    //therefore we have a default path
    @FXML
    private TextField fileField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileField.setText(defaultLocation.getAbsolutePath());
    }

    @FXML
    public void openViewer() {
        DirectoryChooser chooser = new DirectoryChooser();
        File chosen = chooser.showDialog(stage);
        fileField.setText(chosen.getPath());
    }

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
        int port = loadServer();
        Client client = loadClient(port);

        loadDisplay(client);
    }

    private void loadDisplay(Client client) {
        assertNullParameters(client);

        try {
            ClientDisplay controller = this.<ClientDisplay>load(new File(resources, "ClientDisplay.fxml")).getController();
            controller.run();

            console.log(Level.FINE, "Loaded client display");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load display", e);
        }
    }

    private int loadServer() {
        try {
            Server server = Servers.create(0, Main.console);

            String path = fileField.getText();
            if (path.equals(defaultLocation.getAbsolutePath())) {
                path = defaultLocation.getPath();
            }

            List<File> loadedFiles = server.getState().getManager().load(new File(path));
            console.log(Level.FINE, "Loaded " + loadedFiles.size() + " files");
            console.log(Level.FINE, "Loaded server");

            Executables.execute(service, server);

            getAppState().setServer(server);
            return server.getPort();
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load server", e);
        }

        return -1;
    }

    private Client loadClient(int port) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
            ObjectSource source = Sources.createObjectSource(socket);
            Client client = Clients.create(source);

            Executables.execute(service, client);

            console.log(Level.FINE, "Loaded client");

            getAppState().setClient(client);
            return client;
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load client", e);
        }

        return null;
    }
}
