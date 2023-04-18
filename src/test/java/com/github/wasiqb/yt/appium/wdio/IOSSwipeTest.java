package com.github.wasiqb.yt.appium.wdio;

import static com.google.common.truth.Truth.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import com.github.wasiqb.yt.appium.AppiumServiceManager;
import com.github.wasiqb.yt.appium.IOSDriverManager;
import com.github.wasiqb.yt.appium.w3c.FingerGestureUtils;
import com.github.wasiqb.yt.appium.wdio.pages.HomePage;
import com.github.wasiqb.yt.appium.wdio.pages.SwipePage;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class IOSSwipeTest {
    private IOSDriver                     driver;
    private FingerGestureUtils<IOSDriver> fingerGesture;
    private AppiumDriverLocalService      service;
    private WebDriverWait                 wait;

    @BeforeTest (alwaysRun = true)
    public void setupTest () {
        this.service = AppiumServiceManager.composeService ()
            .port (4724)
            .driverName ("xcuitest")
            .composed ()
            .buildService ();
        this.service.start ();
        this.driver = IOSDriverManager.createDriver ()
            .service (this.service)
            .appName ("wdio-demo")
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

        final var logo = this.driver.findElement (swipePage.getPlainLogo ());

        final var args = new HashMap<String, Object> ();
        args.put ("elementId", ((RemoteWebElement) logo).getId ());
        this.driver.executeScript ("mobile: scrollToElement", args);

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
