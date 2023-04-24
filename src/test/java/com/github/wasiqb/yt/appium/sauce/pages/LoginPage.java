package com.github.wasiqb.yt.appium.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import io.appium.java_client.android.AndroidDriver;
import lombok.Getter;

@Getter
public class LoginPage extends BasePage {
    public LoginPage (final AndroidDriver driver) {
        super (driver);
    }

    public void login (final String email, final String password) {
        final var userName = accessibilityId ("test-Username");
        final var passwordField = accessibilityId ("test-Password");
        final var loginButton = accessibilityId ("test-LOGIN");

        getWait ().until (visibilityOfElementLocated (userName))
            .sendKeys (email);
        getWait ().until (visibilityOfElementLocated (passwordField))
            .sendKeys (password);
        getWait ().until (visibilityOfElementLocated (loginButton))
            .click ();
    }
}
