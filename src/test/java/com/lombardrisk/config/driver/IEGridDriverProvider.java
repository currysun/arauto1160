package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.System.getProperty;

public class IEGridDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability("headless", true);
        caps.setVersion(getProperty("version"));

        URL hubUrl = null;
        String hub = getProperty("hubUrl");
        try {
            hubUrl = new URL(hub);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(hubUrl, caps);
        return remoteWebDriver;
    }
}
