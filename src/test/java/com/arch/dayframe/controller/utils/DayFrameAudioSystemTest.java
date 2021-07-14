package com.arch.dayframe.controller.utils;

import org.junit.jupiter.api.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("DayFrameAudioSystem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DayFrameAudioSystemTest {

    private static final String SOUND_PATH = "src/test/resources/model/controller/utils/sound.wav";
    private static final long THREAD_SLEEP_TIME_BUFFER = 500L;

    private static File soundFile;
    private static long soundLengthInMillis;

    @BeforeAll
    static void beforeAll() throws UnsupportedAudioFileException, IOException {
        soundFile = new File(SOUND_PATH);
        soundLengthInMillis = getSoundLengthInMillis(soundFile);
    }

    @Test @Order(1)
    @DisplayName("01. test standard clip - audible but not closed (no direct closing)")
    void testStandardClipWithoutDirectClosing() throws LineUnavailableException {
        Clip stdClip = AudioSystem.getClip();

        playSound(stdClip);

        assertTrue(stdClip.isOpen());
        stdClip.close();
    }

    @Test @Order(2)
    @DisplayName("02. test standard clip - closed but inaudible (direct closing just after start)")
    void testStandardClip() throws LineUnavailableException {
        Clip stdClip = AudioSystem.getClip();

        // a clip will be closed just after the clip start (a sound won't be audible)
        playSound(stdClip);
        stdClip.close();

        assertFalse(stdClip.isOpen());
    }

    @Test @Order(3)
    @DisplayName("03. test standard clip - closed and audible")
    void testStandardClipWithWhileLoop() throws LineUnavailableException {
        Clip stdClip = AudioSystem.getClip();

        // a clip will be closed after a sound stop, because of a while loop (the sound will be audible)
        playSound(stdClip);
        while (stdClip.isActive()) { /* Wait for sound stop */ }
        stdClip.close();

        assertFalse(stdClip.isOpen());
    }

    @Test @Order(4)
    @DisplayName("04. test standard clip using try-with-resources - closed but inaudible")
    void testStandardClipUsingTryWithResources() throws LineUnavailableException {
        Clip stdClipRef = null;

        // a clip will be closed just after the clip start (a sound won't be audible)
        try (Clip stdClip = AudioSystem.getClip()) {
            stdClipRef = stdClip;
            playSound(stdClip);
        } finally {
            if (stdClipRef != null) assertFalse(stdClipRef.isOpen());
        }
    }

    @Test @Order(5)
    @DisplayName("05. test standard clip using try-with-resources - closed and audible")
    void testStandardClipWithWhileLoopUsingTryWithResources() throws LineUnavailableException {
        Clip stdClipRef = null;

        // a clip will be closed after a sound stop, because of a while loop (the sound will be audible)
        try (Clip stdClip = AudioSystem.getClip()) {
            stdClipRef = stdClip;
            playSound(stdClip);
            while (stdClip.isActive()) { /* Wait for sound stop */ }
        } finally {
            if (stdClipRef != null) assertFalse(stdClipRef.isOpen());
        }
    }

    @Test @Order(6)
    @DisplayName("06. test auto-closing clip - closed and audible")
    void testAutocloseableClip() throws LineUnavailableException, InterruptedException {
        Clip autoClosingClip = DayFrameAudioSystem.getAutoClosingClip();

        playSound(autoClosingClip);

        // NOTICE: this is not a part of logic (clip will be closed by itself, after a sound stop)
        Thread.sleep(soundLengthInMillis + THREAD_SLEEP_TIME_BUFFER);
        assertFalse(autoClosingClip.isOpen());
    }

    private static long getSoundLengthInMillis(File soundFile) throws UnsupportedAudioFileException, IOException {
        try (AudioInputStream soundInputStream = AudioSystem.getAudioInputStream(soundFile)) {
            AudioFormat format = soundInputStream.getFormat();

            float frameLength = (float) soundInputStream.getFrameLength();
            float frameRate = format.getFrameRate();
            float soundLengthInSeconds = frameLength / frameRate;
            return (long) Math.ceil(soundLengthInSeconds * 1000);
        }
    }

    private void playSound(Clip clip) {
        try (AudioInputStream ringInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile())) {
            clip.open(ringInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}