package com.meti.lib.io.server.asset.text;

import com.meti.lib.io.server.asset.Asset;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class TextAsset extends Asset<Character> {
    public TextAsset(File location, Character[] content) {
        super(location, content);
    }
}
