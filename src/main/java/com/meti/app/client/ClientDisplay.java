package com.meti.app.client;

import com.meti.app.client.view.View;
import com.meti.app.server.ServerDisplay;
import com.meti.app.startup.Startup;
import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.io.server.command.DisconnectCommand;
import com.meti.lib.util.Utility;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.stageable.StageableImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.*;
import static com.meti.lib.util.Utility.FXML.LOAD_STAGE;
import static java.util.EnumSet.of;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class ClientDisplay extends StageableImpl implements Initializable {
    private static final File serverDisplayLocation = new File(resources, "ServerDisplay.fxml");
    private static final HashMap<String, File> viewFXMLMap = new HashMap<>();

    static {
        viewFXMLMap.put("Chat", new File(resources, "ChatView.fxml"));
        viewFXMLMap.put("FileView", new File(resources, "FileView.fxml"));
        viewFXMLMap.put("ConsoleView", new File(resources, "ConsoleView.fxml"));

        //properties are also settings
        viewFXMLMap.put("Properties", new File(resources, "PropertyView.fxml"));
    }

    private final HashMap<String, Tab> currentViews = new HashMap<>();

    @FXML
    private ListView<String> views;
    @FXML
    private TabPane viewPane;
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = getAppState().getClient();

        views.getItems().addAll(viewFXMLMap.keySet());
        views.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadView(newValue);
            } catch (IOException e) {
                console.log(Level.WARNING, e);
            }
        });
    }

    private void loadView(String viewName) throws IOException {
        File file = viewFXMLMap.get(viewName);
        FXMLBundle bundle = Utility.load(file, of(Utility.FXML.NONE));
        Parent parent = bundle.getParent();
        Object controller = bundle.getController();

        if (parent != null && !currentViews.containsKey(viewName) && controller instanceof View) {
            View viewController = (View) controller;

            Tab tab = new Tab(viewName);
            tab.setContent(parent);

            viewPane.getTabs().add(tab);

            buildContentMenu(viewName, tab);
            currentViews.put(viewName, tab);

            viewController.init();
        }
    }

    private void buildContentMenu(String viewName, Tab tab) {
        ContextMenu menu = new ContextMenu();

        MenuItem close = new MenuItem("Close");
        close.setOnAction(event -> closeView(viewName));

        MenuItem separateWindow = new MenuItem("Open in a Separate Window");
        separateWindow.setOnAction(event -> separateWindow(viewName));

        MenuItem[] menuItems = {
                separateWindow,
                close
        };

        menu.getItems().addAll(menuItems);
        tab.setContextMenu(menu);
    }

    private void separateWindow(String viewName) {
        Tab tab = currentViews.get(viewName);
        Node node = tab.getContent();

        if (node instanceof Parent) {
            tab.setContent(null);

            Scene scene = new Scene((Parent) node);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> currentViews.remove(viewName));

            viewPane.getTabs().remove(tab);
        } else {
            throw new UnsupportedOperationException("Node " + node.getClass() + " is not instance of Parent");
        }
    }

    private void closeView(String viewName) {
        viewPane.getTabs().remove(currentViews.get(viewName));

        currentViews.remove(viewName);
    }

    public void run() {
        console.log(Level.FINE, "Initializing controller");

        //TODO: change run
    }

    @FXML
    public void differentServer() {
        disconnect();

        try {
            load(Startup.connectFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void disconnect() {
        console.log(Level.FINE, "Disconnecting from server");

        try {
            Command disconnectCommand = new DisconnectCommand();

            client.write(disconnectCommand);
            client.close();

            console.log(Level.FINE, "Successfully disconnected from server");
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void openServerDisplay() {
        try {
            Server server = getAppState().getServer();
            ServerDisplay controller = Utility.<ServerDisplay>load(serverDisplayLocation, of(LOAD_STAGE)).getController();

            controller.setServer(server);
            controller.init(server.getState().getManager().getFiles());
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void openProblem() {
        getAppState().getApplication().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus/issues/new");
    }

    @FXML
    public void openGitHub() {
        getAppState().getApplication().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus");
    }

    @FXML
    public void openWebsite() {
        getAppState().getApplication().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/");
    }

    @FXML
    public void openDocumentation() {
        getAppState().getApplication().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/javadocs/index.html");
    }
}
