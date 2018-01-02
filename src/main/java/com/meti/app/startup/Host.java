package com.meti.app.startup;

import com.meti.app.server.ServerDisplay;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.Servers;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.stageable.StageableImpl;
import com.meti.lib.util.thread.execute.Executables;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Host extends StageableImpl implements Initializable {
    private static final File serverDisplayLocation = new File(resources, "ServerDisplay.fxml");
    private static final File defaultLocation = new File("Server");

    @FXML
    private TextField fileField;
    @FXML
    private TextField portField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileField.setText(defaultLocation.getAbsolutePath());

        //TODO: display local port to user
        portField.setText(String.valueOf(0));
    }

    @FXML
    public void openViewer() {
        DirectoryChooser chooser = new DirectoryChooser();
        File returned = chooser.showDialog(stage);
        fileField.setText(returned.getPath());
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
        try {
            int port = Integer.parseInt(portField.getText());
            Server server = Servers.create(port, console);

            String path = fileField.getText();
            if (path.equals(defaultLocation.getAbsolutePath())) {
                path = defaultLocation.getPath();
            }

            List<File> loadedFiles = server.getState().getManager().load(new File(path));
            console.log(Level.FINE, "Loaded " + loadedFiles.size() + " files");

            Executables.execute(service, server);

            console.log(Level.FINE, "Loaded server");

            FXMLBundle bundle = load(serverDisplayLocation);
            Object controller = bundle.getController();
            if (controller instanceof ServerDisplay) {
                ServerDisplay display = (ServerDisplay) controller;
                display.setServer(server);
                display.init(loadedFiles);

                getAppState().setServer(server);
            } else {
                throw new IllegalStateException("ServerDisplay controller is not equal to ServerDisplay class!");
            }
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load server", e);
        }
    }
}
