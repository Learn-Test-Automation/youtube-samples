package com.github.wasiqb.yt.appium.wdio;

import static com.google.common.truth.Truth.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import com.github.wasiqb.yt.appium.AndroidDriverManager;
import com.github.wasiqb.yt.appium.AppiumServiceManager;
import com.github.wasiqb.yt.appium.w3c.FingerGestureUtils;
import com.github.wasiqb.yt.appium.wdio.pages.HomePage;
import com.github.wasiqb.yt.appium.wdio.pages.SwipePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AndroidSwipeTest {
    private AndroidDriver                     driver;
    private FingerGestureUtils<AndroidDriver> fingerGesture;
    private AppiumDriverLocalService          service;
    private WebDriverWait                     wait;

    @BeforeTest (alwaysRun = true)
    public void setupTest () {
        this.service = AppiumServiceManager.composeService ()
            .composed ()
            .buildService ();
        this.service.start ();
        this.driver = AndroidDriverManager.createDriver ()
            .waitActivity ("com.wdiodemoapp.MainActivity")
            .appName ("wdio-demo")
            .service (this.service)
            .create ()
            .getDriver ();
        this.wait = new WebDriverWait (this.driver, Duration.ofSeconds (10));
        this.fingerGesture = new FingerGestureUtils<> (this.driver);
    }

    @AfterTest (alwaysRun = true)
    public void tearDownTest () {
        this.driver.quit ();
        this.service.stop ();
    }

    @Test
    public void testSwipeTillElementUsingScript () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();

        performActions (
            this.fingerGesture.tap (this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))));

        final var scrollView = this.wait.until (visibilityOfElementLocated (swipePage.getScrollView ()));

        final var args = new HashMap<String, Object> ();
        args.put ("elementId", ((RemoteWebElement) scrollView).getId ());
        args.put ("strategy", "accessibility id");
        args.put ("selector", "WebdriverIO logo");
        this.driver.executeScript ("mobile: scroll", args);

        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getPlainLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }

    @Test
    public void testSwipeTillElementUsingSelector () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();

        performActions (
            this.fingerGesture.tap (this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))));

        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getScrolledLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }

    @Test
    public void testSwipeTillElementUsingSwipe () {
        final var homePage = new HomePage ();
        final var swipePage = new SwipePage ();

        performActions (
            this.fingerGesture.tap (this.wait.until (visibilityOfElementLocated (homePage.getSwipeTab ()))));

        final var maxSwipe = 5;
        var swipeCount = 0;
        while (SwipePage.isNotDisplayed (swipePage.getPlainLogo (), this.wait) && swipeCount++ < maxSwipe) {
            performActions (this.fingerGesture.swipe (FingerGestureUtils.Direction.UP, 70));
        }
        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getPlainLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }

    private void performActions (final Sequence... sequences) {
        this.driver.perform (Arrays.asList (sequences));
    }
}
