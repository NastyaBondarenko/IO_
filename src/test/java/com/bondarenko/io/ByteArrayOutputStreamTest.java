package com.bondarenko.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteArrayOutputStreamTest {
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Test
    @DisplayName("write Array Of Bytes by ByteArrayOutputStream")
    public void writeArrayOfBytes_byByteArrayOutputStream() throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream);
        byte[] b = {1, 2, 3, 4, 5};
        outputStream.write(b);
        outputStream.close();

        String expected = "[1, 2, 3, 4, 5]";
        String actual = Arrays.toString(byteArrayOutputStream.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("write Array Of Bytes by ByArrayOutputStream_withParameters")
    public void writeArrayOfBytes_byByteArrayOutputStream_withParameters() throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream);
        byte[] b = {1, 2, 3, 4, 5};
        outputStream.write(b, 0, 3);
        outputStream.close();

        String expected = "[1, 2, 3]";
        String actual = Arrays.toString(byteArrayOutputStream.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("when Write Empty Array Of Bytes than Null Pointer Exception Returned")
    public void whenWriteEmptyArrayOfBytes_thanNullPointerException_Returned() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> {
            byteArrayOutputStream.write(null);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by byByteArrayOutputStream with Parameter Length Less than OffSet Plus Length")
    public void writeArrayOfBytes_byByteArrayOutputStream_withParameterLengthLess_thanOffSetPlusLength() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int off = 6;
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, off, 1);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by ByteArrayOutputStream with Parameter OffSet Equals Zero")
    public void writeArrayOfBytes_byByteArrayOutputStream_withParameterOffSetEqualsZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int len = 6;
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, 0, len);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by ByteArrayOutputStream with Parameter OffSet Less Than Zero")
    public void writeArrayOfBytes_byByteArrayOutputStream_withParameterOffSetLessThanZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int len = 6;
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, -1, len);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by ByteArrayOutputStream with Parameter Length Less Than Zero")
    public void writeArrayOfBytes_byByteArrayOutputStream_withParameterLengthLessThanZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, 0, -1);
        });
    }
}