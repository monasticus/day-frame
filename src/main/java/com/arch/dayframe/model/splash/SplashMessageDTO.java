package com.arch.dayframe.model.splash;

public class SplashMessageDTO {

    public String title;
    public String content;

    public SplashMessageDTO() {
        this("", "");
    }

    protected SplashMessageDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
