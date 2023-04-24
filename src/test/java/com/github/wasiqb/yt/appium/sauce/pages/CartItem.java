package com.github.wasiqb.yt.appium.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;

import lombok.AllArgsConstructor;
import org.openqa.selenium.WebElement;

@AllArgsConstructor
public class CartItem {
    private final WebElement container;

    public WebElement description () {
        return this.container.findElement (accessibilityId ("test-Description"));
    }
}
