package com.bondarenko.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_CAPACITY = 1024;
    private final InputStream inputStream;
    private byte[] buffer;
    private int index;
    private int count;
    private boolean isClosed = false;

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, DEFAULT_BUFFER_CAPACITY);
    }

    public BufferedInputStream(InputStream inputStream, int customCapacity) {
        if (customCapacity <= 0) {
            throw new IllegalArgumentException("Incorrect buffer size: " + customCapacity + ", should be more than 0");
        }
        this.inputStream = inputStream;
        this.buffer = new byte[customCapacity];
    }

    @Override
    public int read() throws IOException {
        insureStreamIsNotClosed();
        fillBuffer();
        return count == -1 ? -1 : buffer[index++];
    }

    @Override
    public int read(byte[] array) throws IOException {
        insureStreamIsNotClosed();
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        }
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int off, int length) throws IOException {
        insureStreamIsNotClosed();
        validateParameters(array, off, length);
        if (fillBuffer() == -1) {
            return -1;
        }
        if (length == 0) {
            return 0;
        }
        if (length > buffer.length) {
            System.arraycopy(new byte[array.length], 0, array, 0, length);
            return inputStream.read(array, off, length);
        }
        int availableBytes = count - index;
        int copiedBytes;

        if (length <= availableBytes) {
            System.arraycopy(buffer, index, array, off, length);
            index += length;
            copiedBytes = length;
        } else {
            System.arraycopy(buffer, index, array, off, availableBytes);
            index += availableBytes;
            copiedBytes = availableBytes;

            int extraBytes = length - copiedBytes;

            if (fillBuffer() == -1) {
                System.arraycopy(new byte[array.length], off + copiedBytes, array, off + copiedBytes, extraBytes);
            } else {
                if (count < extraBytes) {
                    System.arraycopy(buffer, index, array, off + copiedBytes, count);
                    copiedBytes += count;
                    index += count;
                    System.arraycopy(new byte[array.length], off + copiedBytes, array, off + copiedBytes, off + length);
                } else {
                    System.arraycopy(buffer, index, array, off + copiedBytes, extraBytes);
                    copiedBytes += extraBytes;
                    index += extraBytes;
                }
            }
        }
        return copiedBytes;
    }

    @Override
    public void close() throws IOException {
        index = 0;
        count = 0;
        buffer = null;
        inputStream.close();
        isClosed = true;
    }

    private int fillBuffer() throws IOException {
        if (index == count) {
            count = inputStream.read(buffer);
            index = 0;
        }
        return count;
    }

    private void insureStreamIsNotClosed() throws IOException {
        if (isClosed) {
            throw new IOException("The input stream has been closed");
        }
    }

    private void validateParameters(byte[] array, int off, int length) {
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        } else if (off < 0 || length < 0 || length > array.length - off) {
            throw new IndexOutOfBoundsException("Position or length can`t be less than zero. Length can`t be more than " + (array.length - off));
        }
    }
}

