package com.meti.app.server;

import com.meti.app.Main;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.ServerLoop;
import com.meti.lib.io.server.chat.Message;
import com.meti.lib.util.Utility;
import com.meti.lib.util.fx.stageable.StageableImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ServerDisplay extends StageableImpl implements Initializable {
    private static final File propertiesFXML = new File(Main.resources, "ServerProperties.fxml");

    private final HashMap<File, TreeItem<String>> filePaths = new HashMap<>();

    @FXML
    private ListView<String> clientView;

    @FXML
    private TextArea console;

    @FXML
    private TextArea chat;

    @FXML
    private TreeView<String> fileView;

    private final TreeItem<String> root = new TreeItem<>();
    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileView.setRoot(root);
        fileView.setShowRoot(false);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @FXML
    public void openProperties() {
        try {
            Utility.load(propertiesFXML, EnumSet.of(Utility.FXML.LOAD_STAGE));
        } catch (IOException e) {
            Main.console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void shutdown() {
        Platform.exit();
    }

    @FXML
    public void addFiles() {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            File returned = chooser.showDialog(stage);

            List<File> loadedFiles = server.getState().getManager().load(returned);
            for (Object o : loadedFiles) {
                if (o instanceof File) {
                    createTreeItem((File) o);
                }
            }

            server.getState().getConsole().log(Level.FINE, "Loaded " + loadedFiles.size() + " files");
        } catch (IOException e) {
            server.getState().getConsole().log(Level.WARNING, e);
        }
    }

    private void createTreeItem(File file) {
        //never create this file, only there for utility methods
        File parentFile = file.getParentFile();

        TreeItem<String> item = new TreeItem<>();
        item.setValue(file.getName());

        if (parentFile != null) {
            if (filePaths.containsKey(parentFile)) {
                filePaths.get(parentFile).getChildren().add(item);
                filePaths.put(file, item);
            } else {
                //probably could replace this with file.getParent()
                createTreeItem(parentFile);
                createTreeItem(file);
            }
        } else {
            root.getChildren().add(item);
            filePaths.put(file, item);
        }
    }

    @FXML
    public void openProblem() {
        Main.getAppState().getApplication().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus/issues/new");
    }

    @FXML
    public void openGitHub() {
        Main.getAppState().getApplication().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus");
    }

    @FXML
    public void openWebsite() {
        Main.getAppState().getApplication().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/");
    }

    @FXML
    public void openDocumentation() {
        Main.getAppState().getApplication().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/javadocs/index.html");
    }

    public void init(List<File> loadedFiles) {
        //clients
        for (ServerLoop loop : server.getState().getServerLoops()) {
            clientView.getItems().add(loop.getSocket().getInetAddress().toString());
        }

        server.getState().setOnAddServerLoop(params -> {
            for (ServerLoop loop : params) {
                clientView.getItems().add(loop.getSocket().getInetAddress().toString());
            }

            return null;
        });

        //files
        for (Object o : loadedFiles) {
            if (o instanceof File) {
                createTreeItem((File) o);
            }
        }

        server.getState().getConsole().log(Level.FINE, "Initialized " + loadedFiles.size() + " files");

        //console
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                console.appendText(getFormatter().format(record) + " ");
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        server.getState().getConsole().addHandler(handler);

        //chat
        server.getState().getChatState().setOnAdd(params -> {
            for (Message message : params) {
                chat.appendText(message.toString() + "\n");
            }

            //returns void
            return null;
        });
    }
}
