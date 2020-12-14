package com.dfx;

import java.util.Date;

public class MediaFrame {

    private byte[] frame;
    private int mediaType;
    private int frameType;
    private int bitrate;
    private int fps;
    private double displayFps;
    private double clientReceiveFps;
    private int desiredTimeDiff;
    private long frameId;
    private long timestamp;
    private boolean motionAvailable;
    private byte streamType;
    private short chId;

    public MediaFrame() {
    }

    /**
     * @return the chId
     */
    public short getChId() {
        return chId;
    }

    /**
     * @param chId the chId to set
     */
    public void setChId(short chId) {
        this.chId = chId;
    }

    /**
     * @return the streamType
     */
    public byte getStreamType() {
        return streamType;
    }

    /**
     * @param streamType the streamType to set
     */
    public void setStreamType(byte streamType) {
        this.streamType = streamType;
    }

    /**
     * @return the motionAvailable
     */
    public boolean isMotionAvailable() {
        return motionAvailable;
    }

    /**
     * @param motionAvailable the motionAvailable to set
     */
    public void setMotionAvailable(boolean motionAvailable) {
        this.motionAvailable = motionAvailable;
    }

    /**
     * @return the frame
     */
    public byte[] getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(byte[] frame) {
        this.frame = frame;
    }

    /**
     * @return the mediaType
     */
    public int getMediaType() {
        return mediaType;
    }

    /**
     * @param mediaType the mediaType to set
     */
    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * @return the frameType
     */
    public int getFrameType() {
        return frameType;
    }

    /**
     * @param frameType the frameType to set
     */
    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    /**
     * @return the bitrate
     */
    public int getBitrate() {
        return bitrate;
    }

    /**
     * @param bitrate the bitrate to set
     */
    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    /**
     * @return the fps
     */
    public int getFps() {
        return fps;
    }

    /**
     * @param fps the fps to set
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    public double getDisplayFps() {
        return displayFps;
    }

    public void setDisplayFps(double displayFps) {
        this.displayFps = displayFps;
    }

    public double getClientReceiveFps() {
        return clientReceiveFps;
    }

    public int getDesiredTimeDiff() {
        return desiredTimeDiff;
    }

    public void setDesiredTimeDiff(int desiredTimeDiff) {
        this.desiredTimeDiff = desiredTimeDiff;
    }

    public long getFrameId() {
        return frameId;
    }

    public void setFrameId(long frameId) {
        this.frameId = frameId;
    }

    public void setClientReceiveFps(double clientReceiveFps) {
        this.clientReceiveFps = clientReceiveFps;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VFrame [frame.length =" + (frame == null ? "null" : frame.length) + ", mediaType=" + mediaType
                + ", frameType=" + frameType + ", frameId=" + frameId + ", bitrate=" + bitrate + ", fps=" + fps
                + ", timestamp=" + timestamp + "(" + new Date(timestamp) + ")" + ", motionAvailable=" + motionAvailable
                + ", streamType=" + streamType + ", chId=" + chId + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
