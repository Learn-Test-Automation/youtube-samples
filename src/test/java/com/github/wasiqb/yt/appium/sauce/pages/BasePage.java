package com.github.wasiqb.yt.appium.sauce.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public class BasePage<D extends AppiumDriver> {
    private final D driver;
    private final WebDriverWait wait;

    protected BasePage(final D driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
