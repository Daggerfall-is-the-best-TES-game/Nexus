package com.meti.lib.io.sources;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public interface Source<A extends InputStream, B extends OutputStream> {
    A getInputStream();

    B getOutputStream();
}
