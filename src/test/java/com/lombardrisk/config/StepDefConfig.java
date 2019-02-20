package com.lombardrisk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.collectionsTimeout;
import static com.codeborne.selenide.Configuration.pollingInterval;
import static com.codeborne.selenide.Configuration.reportsFolder;
import static com.codeborne.selenide.Configuration.startMaximized;
import static com.codeborne.selenide.Configuration.timeout;
import static java.lang.System.setProperty;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Configuration
@Component
@Import({
        BeanConfig.class,
        Profiles.BddEnv.class,
        Profiles.SmokeEnv.class,
        Profiles.ChromeHeadless.class,
        Profiles.ChromeLinux.class,
        Profiles.ChromeGrid.class,
        Profiles.NativeBrowser.class,
        Profiles.Firefox.class,
        Profiles.FirefoxLinux.class,
        Profiles.FirefoxHeadless.class,
        Profiles.FirefoxGrid.class,
        Profiles.Ie.class,
        Profiles.IeHeadless.class,
        Profiles.IeGrid.class,
        Profiles.M1Env.class,
        Profiles.M2Env.class,
        Profiles.M3Env.class,
        Profiles.R1Env.class,
        Profiles.R2Env.class,
        Profiles.R3Env.class,
        Profiles.OFSAADownload.class,
        Profiles.FCRTestEnv.class
})
class StepDefConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefConfig.class);
    private final Config config;
    private final Environment environment;
    private String fcrBaseURL;

    @Autowired
    public StepDefConfig(final Config config, final Environment environment) {
        this.config = requireNonNull(config);
        this.environment = environment;
    }

    @PostConstruct
    public void setupSelenide() {
        LOGGER.info("Active profiles  {}", Arrays.toString(environment.getActiveProfiles()));
        LOGGER.info("Default profiles {}", Arrays.toString(environment.getDefaultProfiles()));
        logUnavailableProfiles();

        LOGGER.info("Use {}", config);

        setProperty(config.driverProperty(), config.driverBin().toString());
        setProperty("browser.downloads.dir", config.browserDownloads().toString());

        if (config.browserBinary().isPresent()) {
            setProperty("portable.browser.bin", config.browserBinary().get().toString());
        }

        if (config.hubUrl().isPresent()) {
            setProperty("hubUrl", config.hubUrl().get());
        }

        if (config.version().isPresent()) {
            setProperty("version", config.version().get());
        }

        browser = config.driverType();
        startMaximized = false;
        baseUrl = config.arBaseUrl().toString();
        fcrBaseURL = config.fcrBaseUrl().toString();

        timeout = config.defaultTimeout();
        pollingInterval = config.minTimeout();
        collectionsTimeout = config.defaultTimeout();

        reportsFolder = config.reportsDir().toString();
    }

    private void logUnavailableProfiles() {
        List<String> availableProfiles =
                Stream.of(Profiles.class.getDeclaredClasses())
                        .filter(clss -> clss.isAnnotationPresent(Profile.class))
                        .map(clss -> clss.getAnnotation(Profile.class))
                        .flatMap(annotation -> Stream.of(annotation.value()))
                        .collect(toList());

        String unavailableProfiles =
                Stream.of(environment.getActiveProfiles())
                        .filter(profile -> !availableProfiles.contains(profile))
                        .collect(joining(", "));

        if (isNotBlank(unavailableProfiles)) {
            LOGGER.error("Found unavailable profiles: [{}]", unavailableProfiles);
            LOGGER.warn("Available profiles: {}", availableProfiles);
        }
    }
}
