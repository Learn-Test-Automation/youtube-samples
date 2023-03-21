package com.github.wasiqb.yt.appium;

import static com.google.common.truth.Truth.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.util.HashMap;

import com.github.wasiqb.yt.appium.pages.HomePage;
import com.github.wasiqb.yt.appium.pages.SwipePage;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AndroidSwipeTest {
    private AndroidDriverManager     driverManager;
    private AppiumDriverLocalService service;
    private WebDriverWait            wait;

    @BeforeTest (alwaysRun = true)
    public void setupTest () {
        this.service = AppiumServiceManager.composeService ()
            .composed ()
            .buildService ();
        this.service.start ();
        this.driverManager = new AndroidDriverManager (this.service);
        this.wait = this.driverManager.getWait ();
    }

    @AfterTest (alwaysRun = true)
    public void tearDownTest () {
        this.driverManager.getDriver ()
            .quit ();
        this.service.stop ();
    }

    @Test
    public void testSwipeTillElementUsingScript () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();

        this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))
            .click ();

        final var scrollView = this.wait.until (visibilityOfElementLocated (swipePage.getScrollView ()));

        final var args = new HashMap<String, Object> ();
        args.put ("elementId", ((RemoteWebElement) scrollView).getId ());
        args.put ("strategy", "accessibility id");
        args.put ("selector", "WebdriverIO logo");
        this.driverManager.getDriver ()
            .executeScript ("mobile: scroll", args);

        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getPlainLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }

    @Test
    public void testSwipeTillElementUsingSelector () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();

        this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))
            .click ();
        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getScrolledLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }

    @Test
    public void testSwipeTillElementUsingSwipe () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();
        final var fingerGesture = new FingerGestureUtils<> (this.driverManager.getDriver ());

        this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))
            .click ();
        final var maxSwipe = 5;
        var swipeCount = 0;
        while (SwipePage.isNotDisplayed (swipePage.getPlainLogo (), this.wait) && swipeCount++ < maxSwipe) {
            fingerGesture.swipeUp ();
        }
        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getPlainLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }
}
