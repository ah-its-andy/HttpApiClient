package io.standardcore.http.io;

import java.io.*;

public class ByteInputStreamReader extends Reader {
    private final InputStream inputStream;
    private final InputStreamReader inputStreamReader;

    public ByteInputStreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.inputStreamReader = new InputStreamReader(inputStream);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return inputStreamReader.read(cbuf, off, len);
    }

    public byte[] readToEnd() throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] result = swapStream.toByteArray();
        inputStream.close();
        swapStream.close();
        return result;
    }

    @Override
    public void close() throws IOException {
        try {
            inputStreamReader.close();
        } catch (Exception ignore){

        }

        try {
            inputStream.close();
        } catch (Exception ignore){

        }
    }
}
