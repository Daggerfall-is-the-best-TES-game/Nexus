package com.meti.lib.io.server.asset.Array;

import com.meti.lib.io.server.asset.Asset;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ArrayAsset extends Asset<Integer> {
    public ArrayAsset(File location, Integer[] content) {
        super(location, content);
    }
}
