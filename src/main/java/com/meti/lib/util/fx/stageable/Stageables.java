package com.meti.lib.util.fx.stageable;

import com.meti.lib.util.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class Stageables {
    private Stageables() {
    }

    public static void load(URL url, Stageable stageable) throws IOException {
        Utility.load(url, stageable.getStage(), EnumSet.of(Utility.FXML.LOAD_STAGE));
    }
}
