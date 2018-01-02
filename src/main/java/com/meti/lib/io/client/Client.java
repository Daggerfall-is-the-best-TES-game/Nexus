package com.meti.lib.io.client;

import com.meti.lib.io.server.SplitInputStream;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.io.sources.ObjectSource;
import com.meti.lib.util.thread.execute.Executable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Client implements Executable {
    private final SplitInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final ClientState state = new ClientState();

    public Client(ObjectSource objectSource) {
        this.inputStream = new SplitInputStream(objectSource.getInputStream());
        this.outputStream = objectSource.getOutputStream();
    }

    @Override
    public Callable[] getCallables() {
        return new Callable[0];
    }

    @Override
    public Runnable[] getRunnables() {
        return new Runnable[]{
                inputStream
        };
    }

    public <T> T readClass(Class<T> c) {
        boolean contains;
        do {
            contains = hasClass(c);
        } while (!contains);

        return inputStream.pollClass(c);
    }

    public boolean hasClass(Class c) {
        return inputStream.hasClass(c);
    }

    public <T> T readClass(Class<T> c, long time) throws TimeoutException {
        long start = System.currentTimeMillis();
        boolean contains;
        do {
            contains = hasClass(c);

            if (System.currentTimeMillis() - start >= time) {
                throw new TimeoutException("Time of " + time + " has passed before object was received");
            }
        } while (!contains);

        return inputStream.pollClass(c);
    }

    public Object runCommand(Command command) throws IOException {
        write(command);
        flush();

        //TODO: if runCommand is not of returnable type
        return readSuperClass(Object.class);
    }

    public <T> T readSuperClass(Class<T> c) {
        boolean contains;
        do {
            contains = hasSuperClass(c);
        } while (!contains);

        return inputStream.pollSuperClass(c);
    }

    public boolean hasSuperClass(Class<?> c) {
        return inputStream.hasSuperClass(c);
    }

    public void write(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void writeAll(Object... objects) throws IOException {
        if (objects.length == 0) {
            throw new IllegalArgumentException("Objects do not exist");
        }

        for (Object obj : objects) {
            write(obj);
        }
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }

    public Object runCommand(Command command, long time) throws IOException, TimeoutException {
        write(command);
        flush();

        //TODO: if runCommand is not of returnable type

        //might have high cpu usage here
        return readSuperClass(Object.class, time);
    }

    public <T> T readSuperClass(Class<T> c, long time) throws TimeoutException {
        long start = System.currentTimeMillis();
        boolean contains;
        do {
            contains = hasSuperClass(c);

            if (System.currentTimeMillis() - start >= time) {
                throw new TimeoutException("Time of " + time + " has passed before object was received");
            }
        } while (!contains);

        return inputStream.pollSuperClass(c);
    }

    public ClientState getState() {
        return state;
    }
}
