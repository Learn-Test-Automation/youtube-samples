package com.github.wasiqb.yt.appium.sauce.pages;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Getter
public class LoginPage<D extends AppiumDriver> extends BasePage<D> {
    public LoginPage(final D driver) {
        super(driver);
    }

    public void login(final String email, final String password) {
        final var userName = accessibilityId("test-Username");
        final var passwordField = accessibilityId("test-Password");
        final var loginButton = accessibilityId("test-LOGIN");

        getWait().until(visibilityOfElementLocated(userName))
                .sendKeys(email);
        getWait().until(visibilityOfElementLocated(passwordField))
                .sendKeys(password);
        getWait().until(visibilityOfElementLocated(loginButton))
                .click();
    }
}
