package com.meti.app.client.view;

import com.meti.lib.io.server.command.Argument;
import com.meti.lib.io.server.command.ListCommand;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static com.meti.app.Main.console;
import static com.meti.app.Main.getAppState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class FileView implements View {
    private final HashMap<File, TreeItem<String>> filePaths = new HashMap<>();

    @FXML
    private TreeView<String> fileView;

    private final TreeItem<String> root = new TreeItem<>();

    @Override
    public void init() {
        try {
            ListCommand command = new ListCommand(Argument.FILES);
            List<?> fileList = (List<?>) getAppState().getClient().runCommand(command);
            for (Object o : fileList) {
                if (o instanceof File) {
                    createTreeItem((File) o);
                }
            }

            fileView.setRoot(root);
            fileView.setShowRoot(false);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
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
}
