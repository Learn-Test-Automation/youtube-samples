package com.github.wasiqb.yt.appium.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class ProductDetailPage extends HomePage {
    public ProductDetailPage (final AndroidDriver driver) {
        super (driver);
    }

    public WebElement productImage () {
        return getWait ().until (visibilityOfElementLocated (accessibilityId ("test-Image Container")));
    }
}
