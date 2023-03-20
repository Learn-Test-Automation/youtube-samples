package com.github.wasiqb.yt.appium;

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;

import java.nio.file.Path;
import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class AndroidDriverManager {
    private static final String USER_DIR = getProperty ("user.dir");

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public AndroidDriverManager (final AppiumDriverLocalService service) {
        this.driver = new AndroidDriver (service.getUrl (), buildCapabilities ());
        this.wait = new WebDriverWait (this.driver, Duration.ofSeconds (2));
    }

    private Capabilities buildCapabilities () {
        final var deviceName = getProperty ("deviceName", "Pixel_6_Pro");
        final var deviceVersion = getProperty ("deviceVersion", "11");
        final var options = new UiAutomator2Options ();
        options.setPlatformName ("Android")
            .setPlatformVersion (deviceVersion)
            .setDeviceName (deviceName)
            .setAvd (deviceName)
            .setApp (Path.of (USER_DIR, "src/test/resources/apps/wdio-demo.apk")
                .toString ())
            .setAppWaitActivity ("com.wdiodemoapp.MainActivity")
            .setAutoGrantPermissions (true)
            .setFullReset (true)
            .setIsHeadless (parseBoolean (getProperty ("headless", "false")))
            .setCapability ("appium:settings[ignoreUnimportantViews]", true);
        return options;
    }
}
