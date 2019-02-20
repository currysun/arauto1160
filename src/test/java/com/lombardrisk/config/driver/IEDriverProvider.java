package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.getProperty;

/**
 * <p>Custom provider for a chrome driver which contains some default options</p>
 *
 * @see WebDriverProvider
 */
public class IEDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    List<String> getExtraArguments(){
        List<String> extras = new ArrayList();

        return extras;
    }

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {
        WebDriverManager.iedriver().setup();

        System.setProperty("webdriver.ie.driver", "webdriver\\IEDriverServer.exe");
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability("ignoreZoomSetting", true);
        return new InternetExplorerDriver(caps);
    }

    private List<String> getAllArguments() {
        List<String> arguments = new ArrayList<>();

        arguments.add("disable-infobars");
        arguments.add("lang=en-GB");
        arguments.addAll(getExtraArguments());

        return arguments;
    }

    private static Optional<String> findBrowserBinPath() {
        return Optional.ofNullable(
                getProperty("portable.browser.bin"));
    }

    private static ImmutableMap<String, Object> getPreferences() {
        return ImmutableMap.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "download.default_directory", getProperty("browser.downloads.dir"));
    }
}
