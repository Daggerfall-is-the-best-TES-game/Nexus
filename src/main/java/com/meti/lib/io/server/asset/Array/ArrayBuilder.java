package com.meti.lib.io.server.asset.Array;

import com.meti.lib.io.server.asset.Asset;
import com.meti.lib.io.server.asset.AssetBuilder;
import com.meti.lib.io.sources.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ArrayBuilder implements AssetBuilder<Integer, InputStream, OutputStream> {
    @Override
    public Asset<Integer> build(File location, Source<InputStream, OutputStream> source) throws IOException {
        ArrayList<Integer> byteList = new ArrayList<>();
        if (location.length() != 0) {
            int next;
            while ((next = source.getInputStream().read()) == -1) {
                byteList.add(next);
            }
        }

        Integer[] array = new Integer[byteList.size()];
        byteList.toArray(array);
        return new ArrayAsset(location, array);
    }
}
