package com.meti.lib.util.fx;

import com.meti.lib.util.fx.stageable.Stageable;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class FXMLBundle<T> {
    private final Parent parent;
    private final T controller;
    private final Stage stage;

    public FXMLBundle(Parent parent, T controller, Stage stage) {
        this.parent = parent;
        this.controller = controller;
        this.stage = stage;

        if (controller instanceof Stageable) {
            ((Stageable) controller).setStage(stage);
        }
    }

    public Parent getParent() {
        return parent;
    }

    public T getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }
}
