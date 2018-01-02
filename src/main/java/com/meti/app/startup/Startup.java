package com.meti.app.startup;

import com.meti.lib.util.fx.stageable.StageableImpl;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static com.meti.app.Main.console;
import static com.meti.app.Main.resources;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Startup extends StageableImpl {
    public static final File connectFXML;
    public static final File hostFXML;
    public static final File localFXML;

    static {
        connectFXML = new File(resources, "Connect.fxml");
        hostFXML = new File(resources, "Host.fxml");
        localFXML = new File(resources, "Local.fxml");
    }

    @FXML
    public void connect() {
        try {
            load(connectFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void host() {
        try {
            load(hostFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void local() {
        try {
            load(localFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
