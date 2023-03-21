package com.github.wasiqb.yt.appium;

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;

import java.nio.file.Path;
import java.time.Duration;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class IOSDriverManager {
    private static final String USER_DIR = getProperty ("user.dir");

    private final IOSDriver     driver;
    private final WebDriverWait wait;

    public IOSDriverManager (final AppiumDriverLocalService service) {
        this.driver = new IOSDriver (service.getUrl (), buildCapabilities ());
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (5));
        this.wait = new WebDriverWait (this.driver, Duration.ofSeconds (2));
    }

    private Capabilities buildCapabilities () {
        final var deviceName = getProperty ("deviceName", "iPhone 14");
        final var deviceVersion = getProperty ("deviceVersion", "16.2");
        final var options = new XCUITestOptions ();
        options.setPlatformName ("iOS")
            .setPlatformVersion (deviceVersion)
            .setDeviceName (deviceName)
            .setApp (Path.of (USER_DIR, "src/test/resources/apps/wdio-demo.app.zip")
                .toString ())
            .setAutoAcceptAlerts (true)
            .setFullReset (false)
            .setIsHeadless (parseBoolean (getProperty ("headless", "false")));
        return options;
    }
}
