package com.dfx;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Frame Client Thread.
 */
public class FrameClientThreadTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        FrameClientThread x = new FrameClientThread();
        x.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x.close();
        assertTrue( false );
    }
}
