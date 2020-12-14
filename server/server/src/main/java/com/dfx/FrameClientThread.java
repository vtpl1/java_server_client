package com.dfx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FrameClientThread extends Thread {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private boolean already_shutting_down = false;
    private Event is_shutdown = new Event();
    private String connectIp = "192.168.1.184";
    private int port = 3003;
    private short channelId = 10001;
    private byte streamType = 0;

    public FrameClientThread() {

    }

    public void run() {
        System.out.println("FrameClientThread running");

        try (Socket socket = new Socket(connectIp, port)) {
            socket.setTcpNoDelay(true);
			socket.setSoTimeout(3000);
            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
                    out.writeInt(0); // no device id
                    out.writeShort(channelId);
                    out.writeByte(streamType);
                    out.writeByte(0);
                    out.writeByte(1);
                    out.flush();
                    LOGGER.info("Successs to connect server " + connectIp + " port " + port + " channelId " + channelId);
                    while (true) {
                        if (is_shutdown.doWait(1)) {
                            break;
                        } else {
                            int frameLength = in.readInt();
                            if (frameLength > 0) {
        
                                byte[] rawFrame = new byte[frameLength];
                                in.readFully(rawFrame);
                                MediaFrame vFrame = new MediaFrame();
                                vFrame.setFrame(rawFrame);
                                vFrame.setMediaType(in.readInt());
                                vFrame.setFrameType(in.readInt());
                                vFrame.setBitrate(in.readInt());
                                vFrame.setFps(in.readInt());
                                vFrame.setTimestamp(in.readLong());
                                vFrame.setMotionAvailable(in.readByte() == 1 ? true
                                        : false);
                                vFrame.setStreamType(in.readByte());
                                vFrame.setChId(in.readShort());
                                LOGGER.info("Frame received" + vFrame);

                            } else {
        
                                /*videoLossCount++;
                                if (videoLossCount >= MAX_VIDEO_LOSS_COUNT) {
                                    videoLossCount = 0;
                                    displayNoVideo();
                                    resetConnection = true;
                                } else {
                                    videoLossCount++;
                                }*/
        
                            }
                            System.out.println("Here");
                        }
                    }    
                }
            }

        } catch (UnknownHostException ex) {
            LOGGER.severe("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            LOGGER.severe("I/O error: " + ex.getMessage());
        }
    }

    public void close() {
        if (already_shutting_down)
            return;
        already_shutting_down = true;
        is_shutdown.doNotify();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Generating some log messages through the  
        // function defined above 
        LogManager lgmngr = LogManager.getLogManager(); 
  
        // lgmngr now contains a reference to the log manager. 
        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
  
        // Getting the global application level logger  
        // from the Java Log Manager 
        log.log(Level.INFO, "This is a log message");
        final FrameClientThread x = new FrameClientThread();
        x.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            public void run() { 
                LOGGER.log(Level.INFO, "This is a log message");
                x.close();
            }
         });        
    }
}
