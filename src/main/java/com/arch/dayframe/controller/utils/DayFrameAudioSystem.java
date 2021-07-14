package com.arch.dayframe.controller.utils;

import javax.sound.sampled.*;

public class DayFrameAudioSystem {

    /**
     * Returns auto-closing Clip instance
     *
     * @return the Clip instance containing auto-closing listener
     * @throws LineUnavailableException - if a clip object is not available
     */
    public static Clip getAutoClosingClip() throws LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        clip.addLineListener(new ClipClosingListener());

        return clip;
    }

    /**
     * LineListener closing source clip once it will be stopped
     *
     * The implementation is an example way to close Clip just after it will STOP.
     * Clip.close() call after Clip.start() / Clip.loop() will close clip immediately after it will START.
     */
    private static class ClipClosingListener implements LineListener {

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP){
                Clip clip = (Clip)event.getSource();
                clip.close();
            }
        }
    }
}
