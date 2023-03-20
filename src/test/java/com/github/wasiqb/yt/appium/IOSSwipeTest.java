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

public class IOSSwipeTest {
    private IOSDriverManager         driverManager;
    private AppiumDriverLocalService service;
    private WebDriverWait            wait;

    @BeforeTest (alwaysRun = true)
    public void setupTest () {
        this.service = AppiumServiceManager.composeService ()
            .port (4724)
            .driverName ("xcuitest")
            .composed ()
            .buildService ();
        this.service.start ();
        this.driverManager = new IOSDriverManager (this.service);
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

        final var logo = this.driverManager.getDriver ()
            .findElement (swipePage.getPlainLogo ());

        final var args = new HashMap<String, Object> ();
        args.put ("elementId", ((RemoteWebElement) logo).getId ());
        this.driverManager.getDriver ()
            .executeScript ("mobile: scrollToElement", args);

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
        while (!swipePage.isDisplayed (swipePage.getPlainLogo (), this.wait) && swipeCount++ < maxSwipe) {
            fingerGesture.swipeUp ();
        }
        final var logo = this.wait.until (visibilityOfElementLocated (swipePage.getPlainLogo ()));
        assertThat (logo.isDisplayed ()).isTrue ();
    }
}
