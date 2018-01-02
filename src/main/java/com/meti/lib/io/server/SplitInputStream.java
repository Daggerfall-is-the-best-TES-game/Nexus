package com.meti.lib.io.server;

import com.meti.lib.util.thread.Loop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class SplitInputStream extends Loop {
    private final ObjectInputStream inputStream;
    private final ConcurrentHashMap<Class<?>, Queue<Object>> queueHashMap = new ConcurrentHashMap<>();

    public SplitInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void loop() throws Exception {
        Object obj = inputStream.readObject();
        Class<?> objClass = obj.getClass();
        buildIfNotExist(objClass);

        queueHashMap.get(objClass).add(obj);
    }

    private void buildIfNotExist(Class c) {
        if (!queueHashMap.containsKey(c)) {
            queueHashMap.put(c, new PriorityQueue<>());
        }
    }

    public <T> T pollClass(Class<T> c) {
        checkLoopRunning();
        buildIfNotExist(c);

        return c.cast(queueHashMap.get(c).poll());
    }

    private void checkLoopRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("Not reading from stream, cannot poll object");
        }
    }

    public boolean hasClass(Class c) {
        return queueHashMap.containsKey(c) && !queueHashMap.get(c).isEmpty();
    }

    public boolean hasSuperClass(Class<?> c) {
        boolean result = false;

        for (Class key : queueHashMap.keySet()) {
            if (c.isAssignableFrom(key)) {
                result = result || !queueHashMap.get(key).isEmpty();
            }
        }

        return result;
    }

    public <T> T pollSuperClass(Class<T> c) {
        checkLoopRunning();

        if (queueHashMap.size() != 0) {
            for (Class key : queueHashMap.keySet()) {
                if (c.isAssignableFrom(key)) {
                    return c.cast(queueHashMap.get(key).poll());
                }
            }

            throw new IllegalArgumentException("Internal queues do not contain class " + c.getName());
        } else {
            return null;
        }
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
