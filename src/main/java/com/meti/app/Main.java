package com.meti.app;

import com.meti.lib.util.Console;
import com.meti.lib.util.Utility;
import com.meti.lib.util.fx.FXMLBundle;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class Main extends Application {
    public static final ExecutorService service = Executors.newCachedThreadPool();
    public static final Console console = new Console("Startup");
    public static final File resources = new File("src/resources");
    private static final long SHUTDOWN_WAIT_TIME = 5000;
    private static final File startupFXML = new File(resources, "Startup.fxml");

    private static final AppState appState = new AppState();

    @Override
    public void start(Stage primaryStage) {
        appState.setApplication(this);

        loadFXML(primaryStage);

        console.log(Level.INFO, "Started application.");
    }

    public FXMLBundle<Object> loadFXML(Stage stage) {
        try {
            return Utility.load(startupFXML, stage, EnumSet.of(Utility.FXML.LOAD_STAGE));
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
        return null;
    }

    @Override
    public void stop() {
        service.shutdown();
        console.log(Level.INFO, "Attempting to shut down service.");

        if (!service.isShutdown()) {
            console.log(Level.INFO, "Service not shut down, forcing shut down!");

            try {
                Thread.sleep(SHUTDOWN_WAIT_TIME);
                List<Runnable> runnables = service.shutdownNow();
                console.log(Level.INFO, "Successfully shut down service with " +
                        runnables.size() +
                        " runnables left");
            } catch (InterruptedException e) {
                console.log(Level.SEVERE, "The stop thread has interrupted!", e);
            }
        }
        console.log(Level.INFO, "Stopped application");

        System.exit(0);
    }

    public static AppState getAppState() {
        return appState;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
