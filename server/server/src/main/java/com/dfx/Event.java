package com.dfx;

public class Event {

    Object myMonitorObject = new Object();
    boolean wasSignalled = false;

    public boolean doWait() {
        return doWait(1000);
    
    }

    public boolean doWait(long timeOutMillis) {
        boolean ret = wasSignalled;
        synchronized (myMonitorObject) {
            ret = wasSignalled;
        }
        return ret;
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            // myMonitorObject.notify();
        }
    }
    public boolean doWait1(long timeOutMillis) {
        boolean ret = false;
        synchronized (myMonitorObject) {
            while (!wasSignalled) {
                try {
                    myMonitorObject.wait(timeOutMillis);
                    if (wasSignalled) {
                        ret = true;
                    } else {
                        ret = false;
                        // throw new TimeoutException("Wait time out");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // clear signal and continue running.
            wasSignalled = false;
        }
        return ret;
    }

    public void doNotify1() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }
}
