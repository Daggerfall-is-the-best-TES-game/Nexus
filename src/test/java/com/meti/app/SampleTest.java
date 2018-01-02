package com.meti.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/27/2017
 */
@ExtendWith(ApplicationExtension.class)
public class SampleTest {
    private Button button;

    @Start
    public void onStart(Stage stage) {
        button = new Button("click me!");
        button.setOnAction(actionEvent -> button.setText("clicked!"));
        stage.setScene(new Scene(new StackPane(button), 100, 100));
        stage.show();
    }

    @Test
    public void should_contain_button() {
        assertTrue(button.getText().equals("click me!"));
    }

    @Test
    public void should_click_on_button(FxRobot robot) {
        robot.clickOn(button);

        assertTrue(button.getText().equals("clicked!"));
    }
}