package edu.spbstu.rle;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MainTest {

    public static final String PACKED_TXT = "packed.txt";
    public static final String UNPACKED_TXT = "unpacked.txt";
    public static final String OUT_TEST_TXT = "outTest.txt";

    @Test
    public void packUnpack() throws IOException {
        try {
            Main.pack("input_test_pack.txt", PACKED_TXT);
            String content = readFile("packed.txt", Charset.defaultCharset());
            assertEquals("z19a11b14c13q", content);

            Main.unpack(PACKED_TXT, UNPACKED_TXT);
            content = readFile(UNPACKED_TXT, Charset.defaultCharset());
            assertEquals("aaaaaaaaaaaaaaaaaaabbbbbbbbbbbccccccccccccccqqqqqqqqqqqqq", content);
        } finally {
            Files.deleteIfExists(Paths.get(PACKED_TXT));
            Files.deleteIfExists(Paths.get(UNPACKED_TXT));
        }
    }

    @Test
    public void cmdLineTest() throws IOException {
        try {
            Main.main("-z", "-out", OUT_TEST_TXT, "input_test_pack.txt");
            String content = readFile(OUT_TEST_TXT, Charset.defaultCharset());
            assertEquals("z19a11b14c13q", content);

            Main.main("-u", "-out", UNPACKED_TXT, "outTest.txt");
            content = readFile(UNPACKED_TXT, Charset.defaultCharset());
            assertEquals("aaaaaaaaaaaaaaaaaaabbbbbbbbbbbccccccccccccccqqqqqqqqqqqqq", content);

        } finally {
            Files.deleteIfExists(Paths.get(OUT_TEST_TXT));
        }

    }


    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}