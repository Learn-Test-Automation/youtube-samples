package com.github.wasiqb.yt.appium.sauce.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ProductDetailPage<D extends AppiumDriver> extends HomePage<D> {
    public ProductDetailPage(final D driver) {
        super(driver);
    }

    public WebElement productImage() {
        return getWait().until(visibilityOfElementLocated(accessibilityId("test-Image Container")));
    }
}
