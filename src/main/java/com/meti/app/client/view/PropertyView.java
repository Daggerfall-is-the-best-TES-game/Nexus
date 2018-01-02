package com.meti.app.client.view;

import com.meti.lib.io.client.ClientState;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.function.BiConsumer;

import static com.meti.app.Main.getAppState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/23/2017
 */
public class PropertyView implements View {

    @FXML
    private GridPane propertyPane;

    @Override
    public void init() {
        ClientState state = getAppState().getClient().getState();
        state.getProperties().forEach(new BiConsumer<Object, Object>() {
            int counter = 0;

            @Override
            public void accept(Object o, Object o2) {
                Label name = new Label(o.toString());
                propertyPane.add(name, 0, counter);
                name.setAlignment(Pos.CENTER);

                TextField value = new TextField(o2.toString());
                propertyPane.add(value, 1, counter);
                value.setAlignment(Pos.CENTER);

                counter++;

                value.setOnAction(event -> state.getProperties().setProperty(name.getText(), value.getText()));
            }
        });
    }
}
