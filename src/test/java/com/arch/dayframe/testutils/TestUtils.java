package com.arch.dayframe.testutils;

import com.arch.dayframe.model.bp.BPSimpleTime;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.model.bp.SimpleTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TestUtils {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    /**
     * Returns break points file content basis on the break points count and minutes since the current time
     * that will be added to each break point in the default order.
     *
     * Each break point will have following form:
     *  [time-after-postponement] - break point [ordinal-number]
     * For example:
     *  breakPointsCount = 3
     *  minutesSinceNow = {0, 5, 10}
     *  current time is 10:30
     *
     *  *** break point file content ***
     *  10:30 - break point 1
     *  10:35 - break point 2
     *  10:40 - break point 3
     *  ***
     *
     * @param breakPointsCount - indicates how many break points content will contain
     * @param minutesSinceNow  - indicates minutes added to current time for each break point
     * @return break points file content as a String value
     */
    public static String getBreakPointsFileContent(int breakPointsCount, int... minutesSinceNow) {
        validateContentParams(breakPointsCount, minutesSinceNow);
        List<BreakPoint> breakPoints = getBreakPointsFromParams(breakPointsCount, minutesSinceNow);
        return buildContentForBreakPoints(breakPoints);
    }

    private static void validateContentParams(int breakPointsCount, int[] minutesSinceNow) {
        if (breakPointsCount <= 0)
            throw new InvalidParameterException("breakPointsCount has to be a positive number");
        else if (minutesSinceNow.length != breakPointsCount)
            throw new InvalidParameterException("minutesSinceNow length has to be equal to breakPointsCount");
    }

    private static List<BreakPoint> getBreakPointsFromParams(int breakPointsCount, int[] minutesSinceNow) {
        List<BreakPoint> breakPoints = new LinkedList<>();
        for (int i = 0; i < breakPointsCount; i++) {
            int orderNum = i + 1;
            BreakPoint breakPoint = getOrderedBreakPointForMinutesSinceNow(orderNum, minutesSinceNow[i]);
            breakPoints.add(breakPoint);
        }
        return breakPoints;
    }

    private static BreakPoint getOrderedBreakPointForMinutesSinceNow(int ordinalNum, int minutesSinceNow) {
        SimpleTime simpleTime = getBreakPointTimeForMinutesSinceNow(minutesSinceNow);
        String message = getOrderedBreakPointMessage(ordinalNum);
        return BreakPointFactory.fromTimeAndMessage(simpleTime, message);
    }

    private static SimpleTime getBreakPointTimeForMinutesSinceNow(int minutes) {
        SimpleTime simpleTime = new BPSimpleTime();
        simpleTime.add(minutes);
        return simpleTime;
    }

    private static String getOrderedBreakPointMessage(int ordinalNum) {
        return "break point " + ordinalNum;
    }

    private static String buildContentForBreakPoints(List<BreakPoint> breakPoints) {
        StringBuilder builder = new StringBuilder();
        for (BreakPoint breakPoint : breakPoints)
            builder.append(breakPoint.getTimeValue()).append(" - ").append(breakPoint.getMessage()).append("\n");

        return builder.toString();
    }

    /**
     * Retrieves absolute path basis on the relative path provided.
     *
     * @param relative - relative path
     * @return absolute path
     */
    public static String getAbsolutePathFromRelative(String relative) {
        return PROJECT_DIR + "/" + relative;
    }

    /**
     * Creates file basis on the parameters provided.
     * If the file directory doesn't exists - it will create it.
     * If the file already exists it will replace it.
     *
     * @param fileRelPath - relative path of the file to create
     * @param fileContent - content of the file to create
     * @throws IOException whenever any IOException will be thrown in child processes
     */
    public static void createTestFile(String fileRelPath, String fileContent) throws IOException {
        String fileAbsPath = getAbsolutePathFromRelative(fileRelPath);
        createFile(fileAbsPath);
        populateFile(fileAbsPath, fileContent);
    }

    private static void createFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        createFile(filePath);
    }

    private static void createFile(Path filePath) throws IOException {
        createFileDirectory(filePath);
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);
    }

    private static void createFileDirectory(Path filePath) {
        Path fileDirectoryPath = filePath.getParent();
        File fileDirectory = fileDirectoryPath.toFile();
        fileDirectory.mkdirs();
    }

    private static void populateFile(String fileName, String fileContent) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(fileContent);
        }
    }

    /**
     * Removes file under relative path provided.
     * It clears all empty folders from the path.
     *
     * @param fileRelPath - relative path of the file to be removed
     * @throws IOException whenever any IOException will be thrown in child processes
     */
    public static void removeTestFileAndAllEmptyDirs(String fileRelPath) throws IOException {
        String absoluteFilePath = getAbsolutePathFromRelative(fileRelPath);
        Path filePath = Paths.get(absoluteFilePath);
        removeTestFileAndAllEmptyDirs(filePath);
    }

    private static void removeTestFileAndAllEmptyDirs(Path fileAbsPath) throws IOException {
        Files.delete(fileAbsPath);
        removeEmptyDirsRecursively(fileAbsPath);
    }

    private static void removeEmptyDirsRecursively(Path path) throws IOException {
        Path parent = path.getParent();
        if (isFile(path))
            removeEmptyDirsRecursively(parent);
        else if (isEmpty(path)) {
            Files.delete(path);
            removeEmptyDirsRecursively(parent);
        }
    }

    private static boolean isFile(Path dirPath) {
        return !Files.isDirectory(dirPath);
    }

    private static boolean isEmpty(Path dirPath) {
        File directory = dirPath.toFile();
        File[] directoryContent = directory.listFiles();
        return Objects.requireNonNull(directoryContent).length == 0;
    }
}
