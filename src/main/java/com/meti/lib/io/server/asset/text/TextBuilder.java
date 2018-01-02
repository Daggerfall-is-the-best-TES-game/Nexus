package com.meti.lib.io.server.asset.text;

import com.meti.lib.io.server.asset.Asset;
import com.meti.lib.io.server.asset.AssetBuilder;
import com.meti.lib.io.sources.Source;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class TextBuilder implements AssetBuilder<Character, InputStream, OutputStream> {
    @Override
    public Asset<Character> build(File location, Source<InputStream, OutputStream> source) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(source.getInputStream()));
        List<Character> charList = new ArrayList<>();

        int next;
        while ((next = reader.read()) == -1) {
            charList.add((char) next);
        }

        Character[] array = new Character[charList.size()];
        charList.toArray(array);
        return new Asset<>(location, array);
    }
}
