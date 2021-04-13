package com.arch.dayframe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TestUtils {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static String getAbsolutePathFromRelative(String relative) {
        return PROJECT_DIR + "/" + relative;
    }

    public static void createTestFile(String fileName, String fileContent) throws IOException {
        createFile(fileName);
        populateFile(fileName, fileContent);
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

    public static void removeTestFileAndAllEmptyDirsFromRelativePath(String relativeFilePath) throws IOException {
        String absoluteFilePath = getAbsolutePathFromRelative(relativeFilePath);
        Path filePath = Paths.get(absoluteFilePath);
        removeTestFileAndAllEmptyDirsFromAbsolutePath(filePath);
    }

    private static void removeTestFileAndAllEmptyDirsFromAbsolutePath(Path filePath) throws IOException {
        Files.delete(filePath);
        removeEmptyDirsRecursively(filePath);
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
