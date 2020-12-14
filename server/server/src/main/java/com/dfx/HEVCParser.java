package com.dfx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class HEVCParser extends InputStreamReader {

    public HEVCParser(InputStream in) {
        super(in);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int read(char[] cbuf, int offset, int length) throws IOException {
        // TODO Auto-generated method stub
        return super.read(cbuf, offset, length);
    }

    @Override
    public int read(CharBuffer target) throws IOException {
        // TODO Auto-generated method stub
        return super.read(target);
    }

    @Override
    public int read(char[] cbuf) throws IOException {
        // TODO Auto-generated method stub
        return super.read(cbuf);
    }
    
}
