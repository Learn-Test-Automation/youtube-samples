package com.github.wasiqb.yt.appium.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebElement;

@Getter
@AllArgsConstructor
public class ProductItem {
    private final WebElement container;

    public WebElement addButton () {
        final var addButton = accessibilityId ("test-ADD TO CART");
        return this.container.findElement (addButton);
    }

    public WebElement dragHandle () {
        final var dragHandle = accessibilityId ("test-Drag Handle");
        return this.container.findElement (dragHandle);
    }

    public WebElement getTitle () {
        final var title = accessibilityId ("test-Item title");
        return this.container.findElement (title);
    }

    public String price () {
        final var price = accessibilityId ("test-Price");
        return this.container.findElement (price)
            .getText ();
    }

    public String title () {
        return getTitle ().getText ();
    }
}
