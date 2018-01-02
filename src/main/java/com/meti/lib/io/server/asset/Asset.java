package com.meti.lib.io.server.asset;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Asset<T> {
    private final File location;
    private final T[] content;

    public Asset(File location, T[] content) {
        this.location = location;
        this.content = content;
    }

    public File getLocation() {
        return location;
    }

    public T[] getContent() {
        return content;
    }

    public Class getContentClass() {
        return content.getClass();
    }
}
