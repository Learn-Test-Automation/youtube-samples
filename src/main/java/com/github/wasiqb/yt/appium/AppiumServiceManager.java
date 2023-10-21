package com.github.wasiqb.yt.appium;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOCAL_TIMEZONE;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.USE_DRIVERS;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.nio.file.Path;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Builder;

@Builder (builderMethodName = "composeService", buildMethodName = "composed")
public class AppiumServiceManager {
    private static final String USER_DIR = getProperty ("user.dir");

    @Builder.Default
    private String basePath = "/";
    private String driverName;
    @Builder.Default
    private String host     = "localhost";
    @Builder.Default
    private int    port     = 4723;

    public AppiumDriverLocalService buildService () {
        final var logFile = Path.of (USER_DIR, "logs", format ("appium-{0}.log", this.driverName))
            .toFile ();
        final var builder = new AppiumServiceBuilder ();
        return builder.withIPAddress (this.host)
            .usingPort (this.port)
            .withLogFile (logFile)
            .withArgument (BASEPATH, this.basePath)
            .withArgument (LOG_LEVEL, "info")
            .withArgument (USE_DRIVERS, this.driverName)
            .withArgument (SESSION_OVERRIDE)
            .withArgument (LOCAL_TIMEZONE)
            .build ();
    }
}
