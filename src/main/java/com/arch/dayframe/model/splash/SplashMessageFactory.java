package com.arch.dayframe.model.splash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashMessageFactory {

    private static final Pattern TITLE_PATTERN = Pattern.compile("^(\\[(?<title>.{1,50})])$");

    public static SplashMessageDTO getSplashMessageFromPath(String sourcePath) throws IOException {
        Path path = Paths.get(sourcePath);
        return getSplashMessageFromPath(path);
    }

    public static SplashMessageDTO getSplashMessageFromPath(Path sourcePath) throws IOException {
        return Files.exists(sourcePath) ? getSplashMessageFromLines(Files.readAllLines(sourcePath)) : new SplashMessageDTO();
    }

    private static SplashMessageDTO getSplashMessageFromLines(List<String> contentLines) {
        if (contentLines.size() == 0)
            return new SplashMessageDTO();
        else {
            String firstLine = contentLines.get(0);
            String title = getTitle(firstLine);

            contentLines = title.isEmpty() ? contentLines : contentLines.subList(1, contentLines.size());
            String content = getContent(contentLines);
            return new SplashMessageDTO(title, content);
        }
    }

    private static String getTitle(String firstLine) {
        Matcher matcher = TITLE_PATTERN.matcher(firstLine);
        return matcher.find() ? matcher.group("title") : "";
    }

    private static String getContent(List<String> contentLines) {
        StringBuilder contentBuilder = new StringBuilder();

        contentBuilder.append(contentLines.get(0));
        contentLines.stream().skip(1).forEach(line -> contentBuilder.append("\n").append(line));

        String content = contentBuilder.toString();
        return content.isBlank() ? "" : content;
    }
}
