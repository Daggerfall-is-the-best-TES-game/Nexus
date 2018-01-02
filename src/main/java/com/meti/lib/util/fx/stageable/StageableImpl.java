package com.meti.lib.util.fx.stageable;

import com.meti.lib.util.Utility;
import com.meti.lib.util.fx.FXMLBundle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class StageableImpl implements Stageable {
    protected Stage stage;

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public <T> FXMLBundle<T> load(URL url) throws IOException {
        return Utility.load(url, stage, EnumSet.of(Utility.FXML.LOAD_STAGE));
    }

    public <T> FXMLBundle<T> load(File file) throws IOException {
        return Utility.load(file, stage, EnumSet.of(Utility.FXML.LOAD_STAGE));
    }
}
