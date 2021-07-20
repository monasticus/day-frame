package com.arch.dayframe.model.ringer;

import com.arch.dayframe.controller.utils.DayFrameAudioSystem;
import com.arch.dayframe.model.time.BPSimpleTime;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public final class CycleRinger extends HourRinger {

    private final File bellFile;
    private final int minutesInterval;

    public CycleRinger(String hourBellPath, String cycleBellPath) {
        this(hourBellPath, cycleBellPath, 15);
    }

    public CycleRinger(String hourBellPath, String cycleBellPath, int minutesInterval) {
        super(hourBellPath);
        this.bellFile = new File(cycleBellPath);
        this.minutesInterval = minutesInterval;
    }

    public int getMinutesInterval() {
        return minutesInterval;
    }

    @Override
    public synchronized boolean tryRing() {
        boolean hourRingerRang = super.tryRing();
        if (!hourRingerRang && isIntervalClosed()) {
            ring();
            lastRing = new BPSimpleTime();
        }
        return rang();
    }

    private boolean isIntervalClosed() {
        return new BPSimpleTime().getMinutes() % minutesInterval == 0;
    }

    private int getIntervalsCount() {
        return new BPSimpleTime().getMinutes() / minutesInterval;
    }

    private void ring() {
        try (AudioInputStream ringInputStream = AudioSystem.getAudioInputStream(bellFile.getAbsoluteFile())) {
            int ringCount = getIntervalsCount();
            ringInLoop(ringInputStream, ringCount);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void ringInLoop(AudioInputStream ringInputStream, int ringCount) throws LineUnavailableException, IOException {
        Clip clip = DayFrameAudioSystem.getAutoClosingClip();
        clip.open(ringInputStream);
        clip.loop(ringCount - 1);
    }
}
