package com.meti.server;

import com.meti.util.Dialog;
import com.meti.util.FXBundle;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class ServerCreator {
    private static final URL advancedServerDisplayFXMLLocation = AdvancedServerCreator.class
            .getResource("/fxml/server/AdvancedServerCreator.fxml");
    private static final URL serverDisplayFXMLLocation = ServerCreator.class
            .getResource("/fxml/server//ServerDisplay.fxml");

    @FXML
    private TextField portField;

    private ServerSocket serverSocket;

    @FXML
    public void host() {
        try {
            createServer();
        } catch (NumberFormatException e) {
            try {
                Dialog.loadDialog().setMessage("Invalid port!");
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        } catch (IOException e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        }
    }

    private void createServer() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(Integer.parseInt(portField.getText()));
        }

        FXMLLoader loader = new FXMLLoader(serverDisplayFXMLLocation);
        Parent parent = loader.load();
        ServerDisplay display = loader.getController();

        Utility.buildStage(parent);

        Server server = new Server(serverSocket);

        display.setServer(server);
        display.buildListeners();

        ServerThread serverThread = new ServerThread(server);
        server.getExecutorService().submit(serverThread);
    }

    @FXML
    public void generateLocalPort() {
        try {
            this.serverSocket = new ServerSocket(0);

            portField.setText(String.valueOf(serverSocket.getLocalPort()));
        } catch (IOException e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        }
    }

    @FXML
    public void openAdvancedSettings() {
        //TODO: advanced settings
        try {
            FXBundle bundle = Utility.buildStage(advancedServerDisplayFXMLLocation);
            //continue  handling
        } catch (IOException e) {
            //TODO: handle logger
        }
    }

    private class ServerThread implements Runnable {

        private final Server server;

        public ServerThread(Server server) {
            this.server = server;
        }

        @Override
        public void run() {
            server.run();
        }
    }
}
