package com.github.wasiqb.yt.appium.wdio.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import io.appium.java_client.AppiumBy;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class SwipePage {
    public static boolean isNotDisplayed (final By locator, final WebDriverWait wait) {
        try {
            return !wait.until (visibilityOfElementLocated (locator))
                .isDisplayed ();
        } catch (final TimeoutException e) {
            return true;
        }
    }

    private final By plainLogo    = AppiumBy.accessibilityId ("WebdriverIO logo");
    private final By scrollView   = AppiumBy.androidUIAutomator ("new UiSelector().description(\"Swipe-screen\")");
    private final By scrolledLogo = AppiumBy.androidUIAutomator (
        "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\"WebdriverIO logo\"))");
}
