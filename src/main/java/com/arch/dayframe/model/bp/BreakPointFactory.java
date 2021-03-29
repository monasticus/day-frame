package com.arch.dayframe.model.bp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.arch.dayframe.model.bp.BreakPointException.ErrorCode;

public class BreakPointFactory {

    private static final Pattern BP_PATTERN = Pattern.compile("^((?<hour>\\d\\d):(?<minute>\\d\\d))( - (?<message>.{1,35}))?$");
    private static final String HOUR_GROUP_NAME = "hour";
    private static final String MINUTE_GROUP_NAME = "minute";
    private static final String MESSAGE_GROUP_NAME = "message";
    private static final String DEFAULT_BP_MESSAGE = "";
    private static final String WRONG_PATH_ERR_MESSAGE = "Specified path doesn't match any file.";

    public static LinkedList<BreakPoint> fromPath(String sourcePath) throws BreakPointException, IOException {
        Path path = Paths.get(sourcePath);
        List<String> bpDescriptions = getDescriptionsFromFile(path);
        return fromDescriptions(bpDescriptions);
    }

    public static LinkedList<BreakPoint> fromDescriptions(List<String> bpDescriptions) throws BreakPointException {
        LinkedList<BreakPoint> breakPoints = new LinkedList<>();
        for (String bpDescription : bpDescriptions) {
            BreakPoint breakPoint = fromDescription(bpDescription);
            if (breakPoints.stream().noneMatch(bp -> bp.hasTheSameTime(breakPoint)))
                breakPoints.add(breakPoint);
            else
                throw new BreakPointException(ErrorCode.TIME_DUPLICATE_ERR, breakPoint.getTimeValue());
        }
        Collections.sort(breakPoints);
        return breakPoints;
    }

    public static BreakPoint fromDescription(String bpDescription) throws BreakPointException {
        Matcher matcher = fetchMatcherForDescription(bpDescription);
        SimpleTime time = fetchSimpleTimeFromMatcher(matcher);
        String message = fetchMessageFromMatcher(matcher);
        return fromTimeAndMessage(time, message);
    }

    public static BreakPoint fromTimeAndMessage(SimpleTime time, String message) {
        return new BreakPoint(time, message);
    }

    private static List<String> getDescriptionsFromFile(Path path) throws IOException {
        if (Files.exists(path)) {
            List<String> bpDescriptions = Files.readAllLines(path);
            return filterOutCommentedAndBlankDescriptions(bpDescriptions);
        } else
            throw new FileNotFoundException(WRONG_PATH_ERR_MESSAGE);
    }

    private static List<String> filterOutCommentedAndBlankDescriptions(List<String> bpDescriptions) {
        return bpDescriptions.stream().filter(l -> !l.startsWith("#") && !l.isBlank()).collect(Collectors.toList());
    }

    private static Matcher fetchMatcherForDescription(String bpDescription) throws BreakPointException {
        Matcher matcher = BP_PATTERN.matcher(bpDescription.trim());
        if (matcher.find())
            return matcher;
        else
            throw new BreakPointException(ErrorCode.DESCRIPTION_FORMAT_ERR, bpDescription);
    }

    private static SimpleTime fetchSimpleTimeFromMatcher(Matcher matcher) throws BreakPointException {
        String hour = matcher.group(HOUR_GROUP_NAME);
        String minutes = matcher.group(MINUTE_GROUP_NAME);
        return fetchSimpleTimeFromHourAndMinutes(hour, minutes);
    }

    private static SimpleTime fetchSimpleTimeFromHourAndMinutes(String hour, String minutes) throws BreakPointException {
        int intHour = Integer.parseInt(hour);
        int intMinutes = Integer.parseInt(minutes);
        return new BPSimpleTime(intHour, intMinutes);
    }

    private static String fetchMessageFromMatcher(Matcher matcher) {
        return Optional.ofNullable(matcher.group(MESSAGE_GROUP_NAME)).orElse(DEFAULT_BP_MESSAGE);
    }
}
