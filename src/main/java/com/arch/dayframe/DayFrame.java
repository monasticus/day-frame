package com.arch.dayframe;

import com.arch.dayframe.controller.DayFrameController;
import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.model.bp.BreakPointException;
import com.arch.dayframe.model.splash.SplashMessageDTO;
import com.arch.dayframe.model.splash.SplashMessageFactory;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DayFrame {

    private static final String DAY_FRAME_TITLE = "Day Frame";
    private static final String ICON_PATH = "src/main/resources/pics/icon.png";
    private static final String BREAK_POINTS_PATH = "src/main/resources/break-points.txt";
    private static final String SPLASH_MESSAGE_PATH = "src/main/resources/splash-message.txt";
    private static final String IO_EXCEPTION_MESSAGE =
            "Something went wrong.\n" +
            "Be sure that path you specified matches existing file.\n" +
            "Stop all processes that uses specified file.";

    private static DayFrameFrame frame;
    private static DayFrameController controller;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frame = getDayFrame();
                controller = new DayFrameController(frame, BREAK_POINTS_PATH);

                showSplashDialogIfExists();
                controller.start();
                frame.setVisible(true);
            } catch (BreakPointException | IOException e) {
                String message = isIOExceptionButNotFileNotFound(e) ? IO_EXCEPTION_MESSAGE : e.getMessage();
                showErrorDialog(message);
            }
        });
    }

    private static DayFrameFrame getDayFrame() throws IOException {
        Image image = new ImageIcon(ICON_PATH).getImage();
        DayFrameFrame frame = new DayFrameFrame();
        frame.setTitle(DAY_FRAME_TITLE);
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static void showSplashDialogIfExists() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_PATH);
        if (!splashMessage.content.isEmpty())
            JOptionPane.showMessageDialog(frame, splashMessage.content, splashMessage.title, JOptionPane.PLAIN_MESSAGE);
    }

    private static boolean isIOExceptionButNotFileNotFound(Exception e) {
        return e instanceof IOException && !(e instanceof FileNotFoundException);
    }

    private static void showErrorDialog(String message) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
}
