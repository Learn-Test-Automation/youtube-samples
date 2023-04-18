package com.github.wasiqb.yt.appium;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.nio.file.Path;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Builder;
import org.openqa.selenium.Capabilities;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class AndroidDriverManager implements IDriverManager<AndroidDriver> {
    private static final String USER_DIR = getProperty ("user.dir");

    private String                   appName;
    @Builder.Default
    private String                   deviceName      = "Pixel_6_Pro";
    private boolean                  isHeadless;
    @Builder.Default
    private String                   platformVersion = "11";
    private AppiumDriverLocalService service;
    private String                   waitActivity;

    @Override
    public AndroidDriver getDriver () {
        return new AndroidDriver (this.service.getUrl (), buildCapabilities (this.appName));
    }

    private Capabilities buildCapabilities (final String appName) {
        final var options = new UiAutomator2Options ();
        options.setPlatformName ("Android")
            .setPlatformVersion (this.platformVersion)
            .setDeviceName (this.deviceName)
            .setAvd (this.deviceName)
            .setApp (Path.of (USER_DIR, format ("src/test/resources/apps/{0}.apk", appName))
                .toString ())
            .setAppWaitActivity (this.waitActivity)
            .setAutoGrantPermissions (true)
            .setFullReset (false)
            .setIsHeadless (this.isHeadless)
            .setCapability ("appium:settings[ignoreUnimportantViews]", true);
        return options;
    }
}
