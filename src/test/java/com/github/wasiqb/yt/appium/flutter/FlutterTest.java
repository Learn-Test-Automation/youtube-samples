package com.github.wasiqb.yt.appium.flutter;

import static java.time.Duration.ofSeconds;

import com.github.wasiqb.yt.appium.AndroidDriverManager;
import com.github.wasiqb.yt.appium.AppiumServiceManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.github.ashwith.flutter.FlutterFinder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlutterTest {
    private AndroidDriver            driver;
    private AppiumDriverLocalService service;

    @BeforeClass
    public void setupClass () {
        this.service = AppiumServiceManager.composeService ()
            .driverName ("flutter")
            .composed ()
            .buildService ();
        this.service.start ();

        this.driver = AndroidDriverManager.createDriver ()
            .appName ("flutter")
            .isFlutter (true)
            .service (this.service)
            .create ()
            .getDriver ();
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (ofSeconds (5));
    }

    @AfterClass (alwaysRun = true)
    public void tearDownClass () {
        this.driver.quit ();
        this.service.stop ();
    }

    @Test
    public void testLogin () {
        final var finder = new FlutterFinder (this.driver);

        System.out.println (this.driver.getContext ());
        System.out.println (this.driver.getContextHandles ());
        //        this.driver.context ("FLUTTER");

        finder.byValueKey ("u_name")
            .sendKeys ("Admin");
        finder.byValueKey ("pass_code")
            .sendKeys ("Admin");
        finder.byValueKey ("login_button")
            .click ();

        Assert.assertEquals (finder.byText ("Catalog")
            .getText (), "Catalog");

        //        this.driver.context ("NATIVE_APP");
    }
}
