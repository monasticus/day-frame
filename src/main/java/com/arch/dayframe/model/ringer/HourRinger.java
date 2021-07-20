package com.arch.dayframe.model.ringer;

import com.arch.dayframe.controller.utils.DayFrameAudioSystem;
import com.arch.dayframe.model.time.BPSimpleTime;
import com.arch.dayframe.model.time.SimpleTime;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class HourRinger implements Ringer {

    private final File bellFile;
    protected SimpleTime lastRing;

    public HourRinger(String bellPath) {
        this.bellFile = new File(bellPath);
    }

    @Override
    public synchronized boolean rang() {
        return lastRing != null && lastRing.isNow();
    }

    @Override
    public synchronized boolean tryRing() {
        BPSimpleTime currentTime = new BPSimpleTime();
        if (isFullHour(currentTime)) {
            ring();
            lastRing = currentTime;
        }
        return rang();
    }

    private boolean isFullHour(BPSimpleTime currentTime) {
        return currentTime.getMinutes() == 0;
    }

    private void ring() {
        try (AudioInputStream ringInputStream = AudioSystem.getAudioInputStream(bellFile.getAbsoluteFile())) {
            Clip clip = DayFrameAudioSystem.getAutoClosingClip();
            clip.open(ringInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
