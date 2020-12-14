package com.dfx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class H264FileReader {

    private final static int MAX_ALLOWED_FRAME_LENGTH = 4 * 1024 * 1024;
    private final static String MAGIC_STRING = "00000";
    private final static byte[] MAGIC_BYTES = { 0, 0, 0, 1 };

    private RandomAccessFile avfClipFile = null;
    private String avfClipFileName = null;

    private int fps = 1;
    private int bitrate = 1;
    private long latestGrabbingTimestamp = 0;
    private int fileFramefps = -1;

    private long lastSecond = 0;
    private int fpsCounter = 1;
    private long bitrateCounter = 1;

    private long prevFrameOffset = 0;
    private long nextFrameOffset = 0;

    public void openFile(String fileName) throws Exception {

        if (fileName == null)
            throw new Exception("Empty filename");

        if (avfClipFile != null)
            throw new Exception("Already file " + avfClipFileName + " is open.");

        File file = new File(fileName);
        if (!file.exists()) {
            throw new Exception("File " + avfClipFileName + " doesn't exist.");
        }

        this.avfClipFileName = fileName;

        avfClipFile = new RandomAccessFile(avfClipFileName, "rw");
    }

    public MediaFrame readFirstFrame() throws IOException {
        return readFrame(0);
    }

    public MediaFrame readNextFrame() throws IOException {
        return readFrame(nextFrameOffset);
    }

    public MediaFrame readPrevFrame() throws IOException {
        return readFrame(prevFrameOffset);
    }

    public MediaFrame readLastFrame() throws IOException {
        synchronized (avfClipFile) {
            avfClipFile.seek(avfClipFile.length() - 8);
            long currentFrameStartPointer = avfClipFile.readLong();
            return readFrame(currentFrameStartPointer);
        }
    }

    private MediaFrame readFrame(long frameOffset) throws IOException {
        MediaFrame frame = null;

        synchronized (avfClipFile) {

            System.out.println("\n\nframeOffset " + frameOffset);

            avfClipFile.seek(frameOffset);

            byte[] magicBytes = new byte[MAGIC_BYTES.length];
            avfClipFile.readFully(magicBytes);
            String magicString = new String(magicBytes);

            if (magicString.equals(MAGIC_STRING)) {

                long refFramePointer = avfClipFile.readLong();
                int mediaType = avfClipFile.readInt();
                int frameType = avfClipFile.readInt();
                long timeStamp = avfClipFile.readLong();
                latestGrabbingTimestamp = timeStamp;

                int frameSize = avfClipFile.readInt();

                byte[] frameBytes = null;
                if (frameSize > 0 && frameSize <= MAX_ALLOWED_FRAME_LENGTH) {
                    frameBytes = new byte[frameSize];
                    avfClipFile.readFully(frameBytes);
                }

                // skip frame start offset
                long currentFrameStartPointer = avfClipFile.readLong();

                prevFrameOffset = currentFrameStartPointer;
                nextFrameOffset = avfClipFile.getFilePointer();

                System.out.println("ready for next call: prevFrameOffset " + prevFrameOffset + " nextFrameOffset "
                        + nextFrameOffset);

                frame = new MediaFrame();
                frame.setChId((short) 0);
                frame.setStreamType((byte) 0);
                frame.setMediaType(mediaType);
                frame.setFrameType(frameType);
                frame.setTimestamp(timeStamp);
                frame.setFrame(frameBytes);

                // boolean shouldIgnore = VUtilities.isAudioFrame(mediaType) || frameType ==
                // VMediaProperty.CONNECT_HEADER
                // || frameType == VMediaProperty.H_FRAME || frameSize <= 200;

                boolean shouldIgnore = false;

                if (!shouldIgnore) {
                    // calculation of FPS
                    long currentSecond = latestGrabbingTimestamp / 1000;
                    if (lastSecond == currentSecond) {

                        fpsCounter++;

                        bitrateCounter = bitrateCounter + frameSize;

                    } else {

                        lastSecond = currentSecond;
                        this.bitrate = (int) ((bitrateCounter * 8) / 1024);
                        this.fps = fpsCounter;
                        fpsCounter = 1;
                        bitrateCounter = 1;
                    }
                }

                frame.setFps(fps);
                frame.setBitrate(bitrate);

                if (fileFramefps > 0) {
                    frame.setFps(fileFramefps);
                }

            } else {
                throw new IOException("Courrupted file. Invalid magic string found.");
            }
        }

        return frame;
    }

    public void closeFile() throws IOException {
        synchronized (avfClipFile) {

            avfClipFile.close();
            avfClipFile = null;
        }
    }
}
