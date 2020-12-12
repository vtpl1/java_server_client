package com.dfx;

public class MediaFrame {
    public long timeStamp;
    public long frameLength;
    public byte data[];

    public MediaFrame() {
        this.timeStamp = 0;
        this.frameLength = 0;
    }
}
