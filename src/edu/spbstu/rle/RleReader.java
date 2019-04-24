package edu.spbstu.rle;

import java.io.IOException;
import java.io.Reader;

public class RleReader extends Reader {

    private StringBuilder sb = new StringBuilder();
    private Reader reader;

    private int curInt;
    private char ch;

    private char[] buf = new char[1024];

    public RleReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (sb.length() == 0) {
            int read = reader.read(buf);
            if (read == -1) return read;

            for (int i = 0; i < read; i++) {
                char c = buf[i];
                if (Character.isDigit(c)) {
                    curInt = (curInt * 10) + Character.getNumericValue(c);
                } else {
                    for (int j = 0; j < curInt; j++) {
                        sb.append(c);
                    }
                    curInt = 0;
                }
            }
        }
        int cntToCopy = Math.min(len, sb.length());
        sb.getChars(0, cntToCopy, cbuf, off);
        sb.delete(0, cntToCopy);
        return cntToCopy;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    public String getData() {
        return sb.toString();
    }
}
