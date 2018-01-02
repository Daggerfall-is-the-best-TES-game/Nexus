package com.meti.app.client.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.meti.app.Main.console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class ConsoleView implements Initializable {
    @FXML
    private TextArea output;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.addHandler(new ConsoleHandler());
    }

    private class ConsoleHandler extends Handler {
        @Override
        public void publish(LogRecord record) {
            output.appendText(record.getLevel() + ": " + record.getMessage());
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
