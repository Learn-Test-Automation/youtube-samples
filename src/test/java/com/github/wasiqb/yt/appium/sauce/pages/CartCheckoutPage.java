package com.github.wasiqb.yt.appium.sauce.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class CartCheckoutPage<D extends AppiumDriver> extends BasePage<D> {
    public CartCheckoutPage(final D driver) {
        super(driver);
    }

    public CartItem cartItem(final int index) {
        final var scrollView = getWait().until(visibilityOfElementLocated(accessibilityId("test-Cart Content")));
        return scrollView.findElements(accessibilityId("test-Item"))
                .stream()
                .map(CartItem::new)
                .collect(Collectors.toList())
                .get(index);
    }

    public WebElement checkout() {
        return getWait().until(visibilityOfElementLocated(accessibilityId("test-CHECKOUT")));
    }

    public WebElement continueShopping() {
        return getWait().until(visibilityOfElementLocated(accessibilityId("test-CONTINUE SHOPPING")));
    }

    public WebElement delete() {
        return getWait().until(elementToBeClickable(accessibilityId("test-Delete")));
    }
}
