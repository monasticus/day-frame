package com.arch.dayframe.model.splash;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;

@Tag("SplashMessageFactory")
@TestMethodOrder(OrderAnnotation.class)
class SplashMessageFactoryTest {

    private static final String TEST_DATA_DIRECTORY = "src/test/resources/model/splash/";
    private static final String NONEXISTENT_SPLASH_MESSAGE_PATH = TEST_DATA_DIRECTORY + "nonexistent-splash-message.txt";
    private static final String EMPTY_SPLASH_MESSAGE_1_PATH = TEST_DATA_DIRECTORY + "splash-message-empty-single-line.txt";
    private static final String EMPTY_SPLASH_MESSAGE_2_PATH = TEST_DATA_DIRECTORY + "splash-message-empty-two-lines.txt";
    private static final String EMPTY_SPLASH_MESSAGE_3_PATH = TEST_DATA_DIRECTORY + "splash-message-empty-lines-with-space.txt";
    private static final String SPLASH_MESSAGE_WITH_TITLE_1_PATH = TEST_DATA_DIRECTORY + "splash-message-with-title-1.txt";
    private static final String SPLASH_MESSAGE_WITH_TITLE_2_PATH = TEST_DATA_DIRECTORY + "splash-message-with-title-2.txt";
    private static final String SPLASH_MESSAGE_WITHOUT_TITLE_1_PATH = TEST_DATA_DIRECTORY + "splash-message-without-title-1.txt";
    private static final String SPLASH_MESSAGE_WITHOUT_TITLE_2_PATH = TEST_DATA_DIRECTORY + "splash-message-without-title-2.txt";
    private static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_PATH = TEST_DATA_DIRECTORY + "splash-message-with-empty-first-line-1.txt";
    private static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_PATH = TEST_DATA_DIRECTORY + "splash-message-with-empty-first-line-2.txt";
    private static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_PATH = TEST_DATA_DIRECTORY + "splash-message-with-empty-first-line-3.txt";
    private static final String SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_PATH = TEST_DATA_DIRECTORY + "splash-message-with-switched-title-and-content.txt";

    public static final String EMPTY = "";
    public static final String SPLASH_MESSAGE_TITLE = "test-title";
    public static final String SPLASH_MESSAGE_ONE_LINE_CONTENT = "test-content";
    public static final String SPLASH_MESSAGE_TWO_LINE_CONTENT = "test-content-line-1\ntest-content-line-2";
    public static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_CONTENT = "\n[test-title]";
    public static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_CONTENT = "\ntest-content";
    public static final String SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_CONTENT = "\n[test-title]\ntest-content";
    public static final String SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_CONTENT = "test-content\n[test-title]";

    @Test @Order(1)
    @DisplayName("1. getMessageFromPath(String): not-existing-splash-message.txt")
    void testNonexistentSplashMessageFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(NONEXISTENT_SPLASH_MESSAGE_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(2)
    @DisplayName("2. getMessageFromPath(Path): not-existing-splash-message.txt")
    void testNonexistentSplashMessageFromPathUsingPath() throws IOException {
        Path emptyMessagePath = Paths.get(NONEXISTENT_SPLASH_MESSAGE_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(emptyMessagePath);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(3)
    @DisplayName("3. getMessageFromPath(String): splash-message-empty-single-line.txt")
    void testSplashMessageWithEmptySingleLineFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(EMPTY_SPLASH_MESSAGE_1_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(4)
    @DisplayName("4. getMessageFromPath(Path): splash-message-empty-single-line.txt")
    void testSplashMessageWithEmptySingleLineFromPathUsingPath() throws IOException {
        Path emptyMessagePath = Paths.get(EMPTY_SPLASH_MESSAGE_1_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(emptyMessagePath);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(5)
    @DisplayName("5. getMessageFromPath(String): splash-message-empty-two-lines.txt")
    void testSplashMessageWithEmptyTwoLinesFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(EMPTY_SPLASH_MESSAGE_2_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(6)
    @DisplayName("6. getMessageFromPath(Path): splash-message-empty-two-lines.txt")
    void testSplashMessageWithEmptyTwoLinesFromPathUsingPath() throws IOException {
        Path emptyMessagePath = Paths.get(EMPTY_SPLASH_MESSAGE_2_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(emptyMessagePath);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(7)
    @DisplayName("7. getMessageFromPath(String): splash-message-empty-lines-with-space.txt")
    void testEmptySplashMessageWithSpaceFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(EMPTY_SPLASH_MESSAGE_3_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(8)
    @DisplayName("8. getMessageFromPath(Path): splash-message-empty-lines-with-space.txt")
    void testEmptySplashMessageWithSpaceFromPathUsingPath() throws IOException {
        Path emptyMessagePath = Paths.get(EMPTY_SPLASH_MESSAGE_3_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(emptyMessagePath);

        assertSplashMessageValues(splashMessage, EMPTY, EMPTY);
    }

    @Test @Order(9)
    @DisplayName("9. getMessageFromPath(String): splash-message-with-title-1.txt")
    void testSplashMessageWithTitleAndSingleLineContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_TITLE_1_PATH);

        assertSplashMessageValues(splashMessage, SPLASH_MESSAGE_TITLE, SPLASH_MESSAGE_ONE_LINE_CONTENT);
    }

    @Test @Order(10)
    @DisplayName("10. getMessageFromPath(Path): splash-message-with-title-1.txt")
    void testSplashMessageWithTitleAndSingleLineContentFromPathUsingPath() throws IOException {
        Path messageWithTitlePath = Paths.get(SPLASH_MESSAGE_WITH_TITLE_1_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithTitlePath);

        assertSplashMessageValues(splashMessage, SPLASH_MESSAGE_TITLE, SPLASH_MESSAGE_ONE_LINE_CONTENT);
    }

    @Test @Order(11)
    @DisplayName("11. getMessageFromPath(String): splash-message-with-title-2.txt")
    void testSplashMessageWithTitleAndTwoLineContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_TITLE_2_PATH);

        assertSplashMessageValues(splashMessage, SPLASH_MESSAGE_TITLE, SPLASH_MESSAGE_TWO_LINE_CONTENT);
    }

    @Test @Order(12)
    @DisplayName("12. getMessageFromPath(Path): splash-message-with-title-2.txt")
    void testSplashMessageWithTitleAndTwoLineContentFromPathUsingPath() throws IOException {
        Path messageWithTitlePath = Paths.get(SPLASH_MESSAGE_WITH_TITLE_2_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithTitlePath);

        assertSplashMessageValues(splashMessage, SPLASH_MESSAGE_TITLE, SPLASH_MESSAGE_TWO_LINE_CONTENT);
    }

    @Test @Order(13)
    @DisplayName("13. getMessageFromPath(String): splash-message-without-title-1.txt")
    void testSplashMessageWithoutTitleAndWithSingleLineContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITHOUT_TITLE_1_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_ONE_LINE_CONTENT);
    }

    @Test @Order(14)
    @DisplayName("14. getMessageFromPath(Path): splash-message-without-title-1.txt")
    void testSplashMessageWithoutTitleAndWithSingleLineContentFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITHOUT_TITLE_1_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_ONE_LINE_CONTENT);
    }

    @Test @Order(15)
    @DisplayName("15. getMessageFromPath(String): splash-message-without-title-2.txt")
    void testSplashMessageWithoutTitleAndWithTwoLineContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITHOUT_TITLE_2_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_TWO_LINE_CONTENT);
    }

    @Test @Order(16)
    @DisplayName("16. getMessageFromPath(Path): splash-message-without-title-2.txt")
    void testSplashMessageWithoutTitleAndWithTwoLineContentFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITHOUT_TITLE_2_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_TWO_LINE_CONTENT);
    }

    @Test @Order(17)
    @DisplayName("17. getMessageFromPath(String): splash-message-with-empty-first-line-1.txt")
    void testSplashMessageWithEmptyFirstLineAndNoContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_CONTENT);
    }

    @Test @Order(18)
    @DisplayName("18. getMessageFromPath(Path): splash-message-with-empty-first-line-1.txt")
    void testSplashMessageWithEmptyFirstLineAndNoContentFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_1_CONTENT);
    }

    @Test @Order(19)
    @DisplayName("19. getMessageFromPath(String): splash-message-with-empty-first-line-2.txt")
    void testSplashMessageWithEmptyFirstLineAndNoTitleFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_CONTENT);
    }

    @Test @Order(20)
    @DisplayName("20. getMessageFromPath(Path): splash-message-with-empty-first-line-2.txt")
    void testSplashMessageWithEmptyFirstLineAndNoTitleFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_2_CONTENT);
    }

    @Test @Order(21)
    @DisplayName("21. getMessageFromPath(String): splash-message-with-empty-first-line-3.txt")
    void testSplashMessageWithTitleAndContentAndEmptyFirstLineFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_CONTENT);
    }

    @Test @Order(22)
    @DisplayName("22. getMessageFromPath(Path): splash-message-with-empty-first-line-3.txt")
    void testSplashMessageWithTitleAndContentAndEmptyFirstLineFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_EMPTY_FIRST_LINE_3_CONTENT);
    }

    @Test @Order(23)
    @DisplayName("23. getMessageFromPath(String): splash-message-with-switched-title-and-content.txt")
    void testSplashMessageWithSwitchedTitleAndContentFromPathUsingString() throws IOException {
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_PATH);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_CONTENT);
    }

    @Test @Order(24)
    @DisplayName("24. getMessageFromPath(Path): splash-message-with-switched-title-and-content.txt")
    void testSplashMessageWithSwitchedTitleAndContentFromPathUsingPath() throws IOException {
        Path messageWithoutTitlePath = Paths.get(SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_PATH);
        SplashMessageDTO splashMessage = SplashMessageFactory.getSplashMessageFromPath(messageWithoutTitlePath);

        assertSplashMessageValues(splashMessage, EMPTY, SPLASH_MESSAGE_WITH_SWITCHED_TITLE_AND_CONTENT_CONTENT);
    }

    private void assertSplashMessageValues(SplashMessageDTO splashMessage, String title, String content){
        assertNotNull(splashMessage);
        assertEquals(title, splashMessage.title);
        assertEquals(content, splashMessage.content);
    }
}