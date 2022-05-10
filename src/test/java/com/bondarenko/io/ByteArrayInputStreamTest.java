package com.bondarenko.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteArrayInputStreamTest {

    @Test
    @DisplayName("read Bytes By ByteArrayInputStream")
    public void testReadBytesByByteArrayInputStream() {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals('H', (char) byteArrayInputStream.read());
        assertEquals('e', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('o', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    @DisplayName("when Read Not Available Bytes by ByteArrayInputStream then Minus One Returned")
    public void whenReadNotAvailableBytes_byByteArrayInputStream_thenMinusOneReturned() {
        String content = "";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());

        int expected = -1;
        int actual = byteArrayInputStream.read();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("read Array Of Bytes by ByteArrayInputStream")
    public void readArrayOfBytes_byByteArrayInputStream() throws IOException {
        byte[] array = new byte[5];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
        byteArrayInputStream.read(array);

        String expected = "Hello";
        String actual = new String(array);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("read Array Of Bytes ByteArrayInputStream with Parameters")
    public void readArrayOfBytes_ByteArrayInputStream_withParameters() throws IOException {
        byte[] array = new byte[5];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());

        int expected = 5;
        int actual = byteArrayInputStream.read(array, 0, 5);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("read by ByteArrayInputStream with Incorrect Parameter Length")
    public void readArrayOfBytes_byByteArrayInputStream_withIncorrectParameterLength() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
            byteArrayInputStream.read(array, 1, 5);
        });
    }

    @Test
    @DisplayName("read By byteArrayInputStream with Incorrect Parameter Off")
    public void readByByteArrayInputStream_withIncorrectParameterOff() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
            byteArrayInputStream.read(array, 0, 6);
        });
    }

    @Test
    @DisplayName("when Read Array Of Bytes Which Is Null then NullPointerException Returned")
    public void whenReadArrayOfBytes_WhichIsNull_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            byte[] array = null;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
            byteArrayInputStream.read(array, 0, 6);
        });
    }

    @Test
    @DisplayName("read Array Of Bytes By byteArrayInputStream with Parameter Length Less Than Zero")
    public void readArrayOfBytes_byteArrayInputStream_withParameterLengthLessThanZero() {
        Assertions.assertThrows(NegativeArraySizeException.class, () -> {
            byte[] array = new byte[-1];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
            byteArrayInputStream.read(array, 0, array.length);
        });
    }

    @Test
    @DisplayName("when Read Empty Array Of Bytes than Null Pointer Exception Returned")
    public void whenReadEmptyArrayOfBytes_thenNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            byte[] buffer = null;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("".getBytes());
            byteArrayInputStream.read(buffer);
        });
    }
}
