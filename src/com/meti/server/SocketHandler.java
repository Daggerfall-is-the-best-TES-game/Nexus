package com.meti.server;

import com.meti.command.Command;
import com.meti.util.Callback;
import com.meti.util.Change;
import com.meti.util.Handler;
import com.meti.util.Loop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class SocketHandler implements Handler<Socket> {
    private final ExecutorService executorService;

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final HashMap<Class<?>, Queue<Object>> queueHashMap = new HashMap<>();

    private final Callback<Exception> exceptionCallback;
    private final Socket socket;

    public SocketHandler(ExecutorService executorService, Callback<Exception> exceptionCallback, Socket socket) throws IOException {
        this.executorService = executorService;
        this.exceptionCallback = exceptionCallback;
        this.socket = socket;

        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void perform(Socket obj) {
        //TODO: change it such that the field and the socket are not the same object, remove one or the other
        StreamLoop streamLoop = new StreamLoop(exceptionCallback, objectInputStream);
        executorService.submit(streamLoop);

        CommandLoop commandLoop = new CommandLoop(exceptionCallback);
        executorService.submit(commandLoop);

        ChangeLoop changeLoop = new ChangeLoop(exceptionCallback);
        executorService.submit(changeLoop);
    }

    private Queue<Object> getQueue(Class<?> c) {
        if (queueHashMap.containsKey(c)) {
            return queueHashMap.get(c);
        } else {
            PriorityQueue<Object> queue = new PriorityQueue<>();
            queueHashMap.put(c, queue);
            return queue;
        }
    }

    private class ChangeLoop extends Loop {
        public ChangeLoop(Callback<Exception> exceptionCallback) {
            super(exceptionCallback);
        }

        @Override
        public void loop() {
            Queue<Object> changeQueue = getQueue(Change.class);
            if (changeQueue.size() != 0) {
                Change change = (Change) changeQueue.poll();

                //TODO: handle changes
            }
        }
    }

    private class CommandLoop extends Loop {
        public CommandLoop(Callback<Exception> exceptionCallback) {
            super(exceptionCallback);
        }

        @Override
        public void loop() {
            Queue<Object> commandQueue = getQueue(Command.class);
            if (commandQueue.size() != 0) {
                Command command = (Command) commandQueue.poll();

                //TODO: handle types of commands here
                command.perform();
            }
        }
    }

    private class StreamLoop extends Loop {
        private final ObjectInputStream objectInputStream;

        public StreamLoop(Callback<Exception> exceptionCallback, ObjectInputStream objectInputStream) {
            super(exceptionCallback);
            this.objectInputStream = objectInputStream;
        }

        @Override
        public void loop() {
            try {
                Object obj = objectInputStream.readObject();

                Class<?> objectClass = obj.getClass();

                if (obj instanceof Command) {
                    getQueue(Command.class).add(obj);
                } else if (obj instanceof Change) {
                    getQueue(Change.class).add(obj);
                } else {
                    getQueue(objectClass).add(obj);
                }

            } catch (IOException | ClassNotFoundException e) {
                setRunning(false);
            }
        }
    }
}