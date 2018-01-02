package com.meti.lib.io.server.asset;

import com.meti.lib.io.sources.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public interface AssetBuilder<T, A extends InputStream, B extends OutputStream> {
    Asset<T> build(File location, Source<A, B> source) throws IOException;
}
