package com.bondarenko.io;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BufferedInputStreamTest {
    private int count;
    private String content = "Hello world";
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private File file;

    @BeforeEach
    public void before() throws IOException {
        file = new File("File.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
        }
        inputStream = new FileInputStream(file.getAbsolutePath());
        bufferedInputStream = new BufferedInputStream(inputStream);
    }

    @AfterEach
    public void after() throws IOException {
        bufferedInputStream.close();
        inputStream.close();
        file.delete();
    }

    @Test
    @DisplayName("read Bytes By Buffered Input Stream")
    public void readBytesByBufferedInputStream() throws Exception {
        for (int i = 0; i < content.length(); i++) {
            assertEquals(content.charAt(i), bufferedInputStream.read());
            count++;
        }
        assertEquals(count, content.length());
    }

    @Test
    @DisplayName("Read array of bytes from the input stream By BufferedInputStream")
    public void readArrayOfBytes_byBufferedInputStream() throws Exception {
        byte[] array = new byte[3];
        assertEquals(3, bufferedInputStream.read(array));
    }

    @Test
    @DisplayName("read Some Bytes From Input Stream by Buffered Input Stream")
    public void readSomeBytesFromInputStream_byBufferedInputStream() throws Exception {
        byte[] array = new byte[3];
        assertEquals(3, bufferedInputStream.read(array));

        assertEquals('H', array[0]);
        assertEquals('e', array[1]);
        assertEquals('l', array[2]);

        assertEquals(3, bufferedInputStream.read(array));

        assertEquals('l', array[0]);
        assertEquals('o', array[1]);
        assertEquals(' ', array[2]);

        assertEquals(3, bufferedInputStream.read(array));

        assertEquals('w', array[0]);
        assertEquals('o', array[1]);
        assertEquals('r', array[2]);

        assertEquals(2, bufferedInputStream.read(array));

        assertEquals('l', array[0]);
        assertEquals('d', array[1]);
    }

    @Test
    @DisplayName("Reads array of bytes from some position and with some length")
    public void readArrayOfBytes_fromSomePosition_AndWithSomeLength() throws IOException {
        byte[] array = new byte[5];
        int off = 1;
        int length = 3;
        assertEquals(3, bufferedInputStream.read(array, off, length));

        assertEquals(0, array[0]);
        assertEquals('H', array[1]);
        assertEquals('e', array[2]);
        assertEquals('l', array[3]);
        assertEquals(0, array[4]);
    }

    @Test
    @DisplayName("when Read Array Of Bytes and The End Of Stream Is Reached then Return Minus One")
    public void whenReadArrayOfBytes_andTheEndOfStreamIsReached_thenReturnMinusOne() throws Exception {
        for (int i = 0; i < 0; i++) {
            assertEquals(-1, bufferedInputStream.read());
            count++;
        }
    }

    @Test
    @DisplayName("read By Buffered Input Stream with Incorrect Parameter Length")
    public void readByBufferedInputStream_withIncorrectParameterLength() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int off = 6;
            byte[] array = new byte[5];
            bufferedInputStream.read(array, off, 1);
        });
    }

    @Test
    @DisplayName("read By Buffered Input Stream with Incorrect Parameter Off")
    public void readByBufferedInputStream_withIncorrectParameterOff() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedInputStream.read(array, 0, 6);
        });
    }

    @Test
    @DisplayName("when Read Array Of Bytes Which Is Null then NullPointerException Returned")
    public void whenReadArrayOfBytes_WhichIsNull_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            byte[] array = null;
            bufferedInputStream.read(array, 0, 6);
        });
    }

    @Test
    @DisplayName("read Array Of Bytes By Buffered InputStream with Parameter Off Less Than Zero")
    public void readArrayOfBytes_ByBufferedInputStream_withParameterOffLessThanZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedInputStream.read(array, -1, 6);
        });
    }

    @Test
    @DisplayName("read Array Of Bytes By Buffered InputStream with Parameter Length Less Than Zero")
    public void readArrayOfBytes_ByBufferedInputStream_withParameterLengthLessThanZero() {
        Assertions.assertThrows(NegativeArraySizeException.class, () -> {
            byte[] array = new byte[-1];
            bufferedInputStream.read(array, 0, array.length);
        });
    }

    @Test
    @DisplayName("when InputStream is closed than bufferedInputStream cant read and IOException Returned")
    public void whenInputStreamIsClosed_thanBufferedInputStreamCantRead_AndIOExceptionReturned() {
        Assertions.assertThrows(IOException.class, () -> {
            bufferedInputStream.close();
            bufferedInputStream.read();
        });
    }

    @Test
    @DisplayName("when Read Empty Array Of Bytes than Null Pointer Exception Returned")
    public void whenReadEmptyArrayOfBytes_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            bufferedInputStream.read(null);
        });
    }

    @Test
    @DisplayName("read Array Of Bytes By BufferedInputStream with Parameter Length Is Zero")
    public void readArrayOfBytes_ByBufferedInputStream_withParameterLengthIsZero() throws IOException {
        byte[] array = new byte[5];
        assertEquals(0, bufferedInputStream.read(array, 1, 0));
    }
}

