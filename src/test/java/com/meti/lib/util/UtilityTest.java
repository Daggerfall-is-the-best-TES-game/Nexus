package com.meti.lib.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.meti.lib.util.Tests.directory;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/26/2017
 */
class UtilityTest {
    @Test
    void toCharObjectArray() {
        char[] test = new char[]{'a', 'b', 'c'};
        Character[] result = new Character[]{'a', 'b', 'c'};
        assertArrayEquals(result, Utility.toCharObjectArray(test));
    }

    @Test
    void scan() throws IOException {
        assertTrue(directory.mkdirs());

        File test1 = new File(directory, "test1.txt");
        assertTrue(test1.createNewFile());

        File test2 = new File(directory, "test2.png");
        assertTrue(test2.createNewFile());

        List<File> files1 = Utility.scan(directory);
        assertTrue(files1.size() == 3 && files1.containsAll(Arrays.asList(
                directory,
                new File(directory, "test1.txt"),
                new File(directory, "test2.png")
        )));

        List<File> files2 = Utility.scan(directory, "txt");
        assertTrue(files2.size() == 2 && files2.containsAll(Arrays.asList(
                directory,
                new File(directory, "test1.txt")
        )));

        assertTrue(test1.delete());
        assertTrue(test2.delete());
        assertTrue(directory.delete());
    }

    @Test
    void getExtension() throws IOException {
        assertTrue(directory.mkdirs());

        File test = new File(directory, "test.txt");
        assertTrue(test.createNewFile());

        String ext = Utility.getExtension(test);
        assertEquals("txt", ext);

        assertTrue(test.delete());
        assertTrue(directory.delete());
    }

    @Test
    void assertNullParameters() {
        Utility.assertNullParameters("1", 2);

        assertThrows(NullPointerException.class, () -> Utility.assertNullParameters(null, "test"));
    }
}