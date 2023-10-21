package com.github.wasiqb.yt.appium;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import java.nio.file.Path;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Builder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class AndroidDriverManager implements IDriverManager<AndroidDriver> {
    private static final String USER_DIR = getProperty ("user.dir");

    private String                   appName;
    @Builder.Default
    private String                   deviceName      = "Pixel_6_Pro";
    private boolean                  isFlutter;
    private boolean                  isHeadless;
    @Builder.Default
    private String                   platformVersion = "11";
    private AppiumDriverLocalService service;
    private String                   waitActivity;

    @Override
    public AndroidDriver getDriver () {
        return new AndroidDriver (this.service.getUrl (), getCapabilities (this.appName));
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

    private Capabilities buildFlutterCapabilities (final String appName) {
        final var options = new DesiredCapabilities ();
        options.setCapability ("appium:automationName", "Flutter");
        options.setCapability (PLATFORM_NAME, Platform.ANDROID);
        options.setCapability ("appium:platformVersion", this.platformVersion);
        options.setCapability ("appium:deviceName", this.deviceName);
        options.setCapability ("appium:avd", this.deviceName);
        options.setCapability ("appium:app", Path.of (USER_DIR, format ("src/test/resources/apps/{0}.apk", appName))
            .toString ());
        options.setCapability ("appium:isHeadless", this.isHeadless);
        options.setCapability ("appium:commandTimeout", 30000);
        options.setCapability ("appium:newCommandTimeout", 30000);
        options.setCapability ("appium:adbExecTimeout", 40000);
        return options;
    }

    private Capabilities getCapabilities (final String appName) {
        if (this.isFlutter) {
            return buildFlutterCapabilities (appName);
        }
        return buildCapabilities (appName);
    }
}
