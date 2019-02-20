package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.System.getProperty;

/**
 * <p>Custom provider for a chrome driver which contains some default options</p>
 *
 * @see WebDriverProvider
 */
public class ChromeGridDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();

        URL hubUrl = null;
        String hub = getProperty("hubUrl");

        try {
            hubUrl = new URL(hub);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        desiredCapabilities.setVersion(getProperty("version"));

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, desiredCapabilities);

        return remoteWebDriver;
    }
}
