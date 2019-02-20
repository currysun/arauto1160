package com.lombardrisk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URL;
import java.nio.file.Path;

@Configuration
@PropertySource({"classpath:env/application.properties"})
public class PropertiesConfig {

    private static final String NO_DESTROY_METHOD = "";

    @Value("${driver.bin.path}")
    private Path driverBin;
    @Value("${driver.type.property}")
    private String driverProperty;
    @Value("${driver.type}")
    private String driverType;
    @Value("${portable.browser.bin}")
    private Path browserBinary;
    @Value("${browser.downloads.dir}")
    private Path browserDownloads;
    @Value("${hubUrl}")
    private String hubUrl;
    @Value("${version}")
    private String version;

    @Value("${reports.dir}")
    private Path reportsDir;

    @Value("${attachment.documents.dir}")
    private Path attachmentDocuments;
    @Value("${export.documents.dir}")
    private Path exportDocuments;

    @Value("${ar.arBaseUrl}")
    private URL arRootUrl;
    @Value("${ar.arFullUrl}")
    private URL arFullUrl;
    @Value("${ar.fullAnalysisModuleUrl}")
    private URL fullAnalysisModuleUrl;

    @Value("${fcr.fcrBaseUrl}")
    private URL fcrRootUrl;
    @Value("${fcr.fcrFullUrl}")
    private URL fcrFullUrl;

    @Value("${timeout.min}")
    private long minTimeout;
    @Value("${timeout.default}")
    private long defaultTimeout;
    @Value("${timeout.max}")
    private long maxTimeout;
    @Value("${import.dir.populated.returns}")
    private Path populatedReturns;
    @Value("${import.dir}")
    private Path importDir;
    @Value("${import.settings.file}")
    private Path importSettingsFile;
    @Value("${import.timeout}")
    private long importTimeout;
    @Value("${ar.cookie}")
    private String arCookie;

    @Bean(destroyMethod = NO_DESTROY_METHOD)
    public Config config() {
        return new Config.Builder()
                .driverType(driverType)
                .driverProperty(driverProperty)
                .driverBin(driverBin)
                .browserBinary(browserBinary)
                .browserDownloadsDir(browserDownloads)

                .reportsDir(reportsDir)
                .attachmentDocuments(attachmentDocuments)
                .exportDocuments(exportDocuments)

                .arBaseUrl(arRootUrl)
                .arFullUrl(arFullUrl)
                .fcrBaseUrl(fcrRootUrl)
                .fcrFullUrl(fcrFullUrl)
                .fullAnalysisModuleUrl(fullAnalysisModuleUrl)
                .hubUrl(hubUrl)
                .version(version)

                .minTimeout(minTimeout)
                .defaultTimeout(defaultTimeout)
                .maxTimeout(maxTimeout)

                .populatedReturnsDir(populatedReturns)
                .importSettingsFile(importSettingsFile)
                .importDir(importDir)
                .importTimeout(importTimeout)
                .arCookie(arCookie)
                .build();
    }
}
