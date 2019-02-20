package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.net.URL;
import static java.lang.System.getProperty;

public class FirefoxGridDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {

        URL hubUrl=null;
        String hub = getProperty("hubUrl");

        try {
            hubUrl = new URL(hub);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        desiredCapabilities.setCapability("headless", true);
        RemoteWebDriver remoteWebDriver =  new RemoteWebDriver(hubUrl, desiredCapabilities);
        return remoteWebDriver;
    }
}
