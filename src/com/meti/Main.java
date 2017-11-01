package com.meti;

import com.meti.client.ClientCreator;
import com.meti.server.fxml.ServerCreator;
import com.meti.util.Dialog;
import com.meti.util.Stoppable;
import com.meti.util.Utility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger("Logger");

    private static Main instance;

    //I really do hate IntelliJ's pestering about spelling...
    private final List<Stoppable> stoppables = new ArrayList<>();

    //not sure if there is an internal list for this...
    private final ArrayList<Stage> appStages = new ArrayList<>();
    private AnimationTimer stageTimer;

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        log(Level.INFO, "Starting application");

        buildStage("assets\\fxml\\Main.fxml", primaryStage);

        stageTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boolean allShowing = false;
                for (Stage stage : appStages) {
                    if (stage.isShowing()) {
                        allShowing = true;
                    }
                }

                if (!allShowing) {
                    Platform.exit();
                    System.exit(0);
                }
            }
        };

        stageTimer.start();
    }

    public Logger getLogger() {
        return logger;
    }

    public void log(Level level, String message) {
        logger.log(level, message);
    }

    @Override
    public void stop() {
        log(Level.INFO, "Stopping application");

        stageTimer.stop();
        stoppables.forEach(Stoppable::stop);
    }

    public Object buildStage(String path, Stage preexisting) {
        try {
            URL url = new File(path).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();

            //controller will be null if not all methods are present!!!
            Object controller = loader.getController();

            Scene scene = new Scene(parent);

            Stage stage;
            if (preexisting != null) {
                stage = preexisting;
            } else {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.show();

            addStage(stage);

            return controller;
        } catch (IOException e) {
            log(Level.SEVERE, e);
            return null;
        }
    }

    public void log(Level level, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    private void addStage(Stage stage) {
        appStages.add(stage);
    }

    public void addStoppable(Stoppable stoppable) {
        stoppables.add(stoppable);
    }

    public void createDialog(String message) {
        Utility.castIfOfInstance(buildStage("assets\\fxml\\Dialog.fxml", null), Dialog.class).setMessage(message);
    }

    @FXML
    public void startClient() {
        try {
            new ClientCreator().start();
        } catch (IOException e) {
            log(Level.SEVERE, e);
        }
    }

    @FXML
    public void startServer() {
        new ServerCreator().start();
    }

    public void handle(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        createDialog(writer.toString());
    }
}
