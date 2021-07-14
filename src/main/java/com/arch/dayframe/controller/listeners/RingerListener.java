package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.controller.utils.DayFrameAudioSystem;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class RingerListener implements ActionListener {

    private static final int QUARTER_OF_AN_HOUR = 15;

    private final File hourSoundFile;
    private final File quarterSoundFile;
    private int currentMinute;
    private boolean rang;

    public RingerListener(String hourSoundPath, String quarterSoundPath) {
        this.hourSoundFile = new File(hourSoundPath);
        this.quarterSoundFile = new File(quarterSoundPath);
        this.rang = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        if (!rang) {
            File soundFile = getSoundFile();
            if (soundFile != null) {
                ring(soundFile);
                rang = true;
            }
        } else if (hasMinutePassed()) {
            rang = false;
        }
    }

    private File getSoundFile() {
        return isFullHour() ? hourSoundFile : isQuarterOfAnHour() ? quarterSoundFile : null;
    }

    private boolean isFullHour() {
        return currentMinute == 0;
    }

    private boolean isQuarterOfAnHour() {
        return currentMinute % QUARTER_OF_AN_HOUR == 0;
    }

    private boolean hasMinutePassed() {
        return currentMinute == 1 || currentMinute % QUARTER_OF_AN_HOUR == 1;
    }

    private void ring(File soundFile) {
        try (AudioInputStream ringInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile())) {
            int ringCount = isFullHour() ? 1 : getMultiplesOfQuarters();
            ringSoundInLoop(ringInputStream, ringCount);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private int getMultiplesOfQuarters() {
        return currentMinute / QUARTER_OF_AN_HOUR;
    }

    private void ringSoundInLoop(AudioInputStream ringInputStream, int ringCount) throws LineUnavailableException, IOException {
        Clip clip = DayFrameAudioSystem.getAutoClosingClip();
        clip.open(ringInputStream);
        clip.loop(ringCount -1);
    }
}
