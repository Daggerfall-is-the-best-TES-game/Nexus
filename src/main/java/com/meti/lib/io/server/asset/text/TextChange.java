package com.meti.lib.io.server.asset.text;

import com.meti.lib.io.server.asset.AssetChange;

import static com.meti.lib.util.Utility.toCharObjectArray;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class TextChange extends AssetChange<Character> {
    public void index(String oldString, String newString) {
        index(toCharObjectArray(oldString.toCharArray()), toCharObjectArray(newString.toCharArray()));
    }
}
