package com.meti.lib.util;

import com.meti.lib.io.sources.Source;
import com.meti.lib.io.sources.Sources;
import com.meti.lib.util.fx.FXMLBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Utility {
    private Utility() {
    }

    //ugh, why java???

    /**
     * Converts an array of primitive chars into an array of Character objects.
     *
     * @param chars The primitive array.
     * @return The Character object array.
     */
    public static Character[] toCharObjectArray(char[] chars) {
        Character[] array = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            array[i] = chars[i];
        }
        return array;
    }

    /**
     * Scans a directory for files with specific extensions, adding them to an internal list.
     * All directories are added to the list. If no extensions are written, then all files will be added.
     *
     * @param directory  The directory.
     * @param extensions The extensions.
     * @return The found files.
     */
    public static List<File> scan(File directory, String... extensions) {
        List<File> fileList = new ArrayList<>();
        scan(directory, fileList, extensions);
        return fileList;
    }

    private static void scan(File file, List<File> files, String[] extensions) {
        if (file.isDirectory()) {
            files.add(file);

            File[] directoryFiles = file.listFiles();

            if (directoryFiles != null && directoryFiles.length != 0) {
                for (File f : directoryFiles) {
                    scan(f, files, extensions);
                }
            }
        } else {
            if (extensions.length != 0) {
                String fileExtension = getExtension(file);

                for (String ext : extensions) {
                    if (fileExtension.equals(ext)) {
                        files.add(file);
                        return;
                    }
                }
            } else {
                files.add(file);
            }
        }
    }

    /**
     * Returns the extension of a file.
     *
     * @param file The file.
     * @return The extension of the file.
     */
    public static String getExtension(File file) {
        String[] splitName = file.getName().split("\\.");
        return splitName[splitName.length - 1];
    }

    public static <T> FXMLBundle<T> load(File file, Stage stage, EnumSet<FXML> flags) throws IOException {
        return load(Sources.createBasicSource(file), stage, flags);
    }

    public static <T> FXMLBundle<T> load(URL url, Stage stage, EnumSet<FXML> loadStage) throws IOException {
        return load(Sources.createBasicSource(url), stage, loadStage);
    }

    /**
     * Checks that the parameters given are not null. If elements aren't null, a NullPointerException is thrown.
     *
     * @param parameters The parameters.
     */
    public static void assertNullParameters(Object... parameters) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Object obj = parameters[i];
            if (obj == null) {
                indexes.add(i);
            }
        }

        if (indexes.size() != 0) {
            throw new NullPointerException("Object at indexes " + indexes + " cannot be null");
        }
    }

    public static <T> FXMLBundle<T> load(File file, EnumSet<FXML> flags) throws IOException {
        return load(Sources.createBasicSource(file), flags);
    }

    public static <T> FXMLBundle<T> load(Source<? extends InputStream, ?> source, EnumSet<FXML> flags) throws IOException {
        Stage stage = null;
        if (flags.contains(FXML.LOAD_STAGE)) {
            stage = new Stage();
        }
        return load(source, stage, flags);
    }

    public static <T> FXMLBundle<T> load(URL url, EnumSet<FXML> flags) throws IOException {
        return load(Sources.createBasicSource(url), flags);
    }

    /**
     * <p>
     * Loads an FXMLBundle of controller type T from the specified source from {@link FXMLLoader#load(InputStream)}.
     * Constructs a Scene with the loaded Parent inside, then checks the flags.
     * If LOAD_STAGE is present, then the scene is assigned to the stage, which is then shown.
     * If type T is invalid or the FXML returned a type not assignable to T, then a ClassCastException is thrown.
     * </p>
     *
     * @param source The source.
     * @param stage  The stage.
     * @param flags  The flags.
     * @param <T>    The controller type.
     * @return The bundle.
     * @throws IOException If an I/O error occurred.
     */
    public static <T> FXMLBundle<T> load(Source<? extends InputStream, ?> source, Stage stage, EnumSet<FXML> flags) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent parent = loader.load(source.getInputStream());
        Scene scene = new Scene(parent);

        if (flags.contains(FXML.LOAD_STAGE) && stage != null) {
            stage.setScene(scene);
            stage.show();
        }

        //unchecked
        return new FXMLBundle<>(parent, loader.getController(), stage);
    }

    public enum FXML {
        NONE,
        LOAD_STAGE
    }
}
