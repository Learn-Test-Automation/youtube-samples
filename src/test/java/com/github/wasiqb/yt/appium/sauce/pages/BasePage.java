package com.github.wasiqb.yt.appium.sauce.pages;

import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import lombok.Getter;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class BasePage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    protected BasePage (final AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait (driver, Duration.ofSeconds (10));
    }
}
