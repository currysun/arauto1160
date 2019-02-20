package com.lombardrisk.config.driver;

import com.codeborne.selenide.WebDriverProvider;
import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.getProperty;

/**
 * <p>Custom provider for a chrome driver which contains some default options</p>
 *
 * @see WebDriverProvider
 */
public class FirefoxDriverProvider implements WebDriverProvider {

    public static final File DRIVER_LOG_FILE = Paths.get("target", "driver.log").toFile();

    List<String> getExtraArguments(){
        List<String> extras = new ArrayList();

        return extras;
    }

    @Override
    public WebDriver createDriver(final DesiredCapabilities desiredCapabilities) {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty("webdriver.gecko.driver", getProperty("portable.browser.bin"));


        return new FirefoxDriver();
    }

    private List<String> getAllArguments() {
        List<String> arguments = new ArrayList<>();

        arguments.add("disable-infobars");
        arguments.add("lang=en-GB");
        arguments.addAll(getExtraArguments());

        return arguments;
    }
}
