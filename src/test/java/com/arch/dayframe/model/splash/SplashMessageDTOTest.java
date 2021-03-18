package com.arch.dayframe.model.splash;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("SplashMessageDTO")
class SplashMessageDTOTest {

    @Test
    @DisplayName("Create SplashMessageDTO by default constructor")
    public void createDefaultSplashMessageDTO() {
        SplashMessageDTO splashMessage = new SplashMessageDTO();

        assertNotNull(splashMessage);
        assertEquals("", splashMessage.title);
        assertEquals("", splashMessage.content);
    }

    @Test
    @DisplayName("Create SplashMessageDTO by constructor with two parameters")
    public void createSplashMessageDTOWithSpecifiedParameters() {
        String title = "test-title";
        String content = "test-content";
        SplashMessageDTO splashMessage = new SplashMessageDTO(title, content);

        assertNotNull(splashMessage);
        assertEquals(title, splashMessage.title);
        assertEquals(content, splashMessage.content);
    }
}