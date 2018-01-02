package com.meti.lib.io.sources;

import java.io.*;
import java.net.Socket;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Sources {
    private Sources() {
    }

    public static BasicSource createBasicSource(File file) throws FileNotFoundException {
        return new BasicSource(new FileInputStream(file), new FileOutputStream(file));
    }

    public static BasicSource createBasicSource(Socket socket) throws IOException {
        return new BasicSource(socket.getInputStream(), socket.getOutputStream());
    }

    public static ObjectSource createObjectSource(File file) throws IOException {
        return new FileObjectSource(file);
    }

    public static ObjectSource createObjectSource(Socket socket) throws IOException {
        return new SocketObjectSource(socket);
    }

    public static BasicSource createBasicSource(URL url) throws IOException {
        return new BasicSource(url.openStream(), null);
    }

    public static BasicSource createBasicSource(InputStream inputStream, OutputStream outputStream) {
        return new BasicSource(inputStream, outputStream);
    }

    private static class BasicSource implements Source<InputStream, OutputStream> {
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public BasicSource(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return outputStream;
        }
    }

    private static class SocketObjectSource implements ObjectSource {
        private final ObjectOutputStream outputStream;
        private final ObjectInputStream inputStream;

        public SocketObjectSource(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public ObjectInputStream getInputStream() {
            return inputStream;
        }

        @Override
        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }

    private static class FileObjectSource implements ObjectSource {
        private final ObjectInputStream inputStream;
        private final ObjectOutputStream outputStream;

        public FileObjectSource(File file) throws IOException {
            this.outputStream = new ObjectOutputStream(new FileOutputStream(file));
            this.inputStream = new ObjectInputStream(new FileInputStream(file));
        }

        @Override
        public ObjectInputStream getInputStream() {
            return inputStream;
        }

        @Override
        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }
}
