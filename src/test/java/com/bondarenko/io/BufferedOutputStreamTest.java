package com.bondarenko.io;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BufferedOutputStreamTest {
    private BufferedOutputStream bufferedOutputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] content;

    @BeforeEach
    public void before() {
        content = "Hello world".getBytes();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    }

    @AfterEach
    public void after() throws Exception {
        byteArrayOutputStream.close();
    }

    @Test
    @DisplayName("Write the specified byte by BufferedOutputStream")
    public void writeTheSpecifiedByte_byBufferedOutputStream() throws Exception {
        bufferedOutputStream.write(72);
        bufferedOutputStream.close();
        assertEquals(72, byteArrayOutputStream.toByteArray()[0]);
    }

    @Test
    @DisplayName("write Bytes By BufferedOutputStream to Output Stream")
    public void writeBytesByBufferedOutputStream_toOutputStream() throws Exception {
        bufferedOutputStream.write(content);
        bufferedOutputStream.close();
        int i = 0;
        for (byte b : byteArrayOutputStream.toByteArray()) {
            assertEquals(content[i], b);
            i++;
        }
    }

    @Test
    @DisplayName("write Array Of Bytes By BufferedOutputStream")
    public void writeArrayOfBytesByBufferedOutputStream() throws IOException {
        byte[] b = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(b);

        String expected = "[1, 2, 3, 4, 5]";
        String actual = Arrays.toString(byteArrayOutputStream.toByteArray());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("write With Parameters array Of Bytes to Output Stream")
    public void writeWithParameters_arrayOfBytes_toOutputStream() throws Exception {
        bufferedOutputStream.write(content, 2, 2);
        bufferedOutputStream.close();

        assertEquals(108, byteArrayOutputStream.toByteArray()[0]);
        assertEquals(108, byteArrayOutputStream.toByteArray()[1]);
    }

    @Test
    @DisplayName("when Write Empty Array Of Bytes than Null Pointer Exception Returned")
    public void whenWriteEmptyArrayOfBytes_thanNullPointerException_Returned() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            bufferedOutputStream.write(null);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by BufferedOutStream with Parameter Length Less than OffSet Plus Length")
    public void writeArrayOfBytes_byBufferedOutStream_withParameterLengthLess_thanOffSetPlusLength() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int off = 6;
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, off, 1);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by Buffered Out Stream with Parameter OffSet Equals Zero")
    public void writeArrayOfBytes_byBufferedOutStream_withParameterOffSetEqualsZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int len = 6;
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, 0, len);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by Buffered Out Stream with Parameter OffSet Less Than Zero")
    public void writeArrayOfBytes_byBufferedOutStream_withParameterOffSetLessThanZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            int len = 6;
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, -1, len);
        });
    }

    @Test
    @DisplayName("write Array Of Bytes by Buffered Out Stream with Parameter Length Less Than Zero")
    public void writeArrayOfBytes_byBufferedOutStream_withParameterLengthLessThanZero() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, 0, -1);
        });
    }
}

