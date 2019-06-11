package edu.spbstu.rle;

import java.io.IOException;
import java.io.Writer;

/**
 * RLE encoder. Writes encoded text to underlying stream
 */
public class RleWriter extends Writer {

    private Writer writer;
    private Character curChar = null;
    private int counter = 0;

    public RleWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (len > 0) {
            if (curChar == null) {
                curChar = cbuf[off];
            }
            for (int i = off; i < off + len; i++) {
                if (curChar.equals(cbuf[i])) {
                    counter++;
                } else {
                    writer.append("" + counter);
                    writer.append(curChar);
                    curChar = cbuf[i];
                    counter = 1;
                }
            }
        }
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.append("" + counter);
        writer.append(curChar);
    }
}
