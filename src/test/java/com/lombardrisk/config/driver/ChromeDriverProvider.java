package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getProperty;

/**
 * <p>Custom provider for a chrome driver which contains some default options</p>
 *
 * @see com.codeborne.selenide.WebDriverProvider
 */
public abstract class ChromeDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    abstract List<String> getExtraArguments();

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments(getAllArguments());

        findBrowserBinPath()
                .ifPresent(chromeOptions::setBinary);

        chromeOptions.setExperimentalOption("prefs", getPreferences());

        ChromeDriverService service =
                new ChromeDriverService.Builder()
                        .usingAnyFreePort()
                        .withLogFile(DRIVER_LOG_FILE)
                        .build();

        WebDriver driver = new ChromeDriver(service, chromeOptions);

        return driver;
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
