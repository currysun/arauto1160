package com.lombardrisk.config;

import com.google.common.base.MoreObjects;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isExecutable;
import static java.nio.file.Files.isRegularFile;
import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * <p>All configurations should be accessible through this class.</p>
 *
 * <p>Configurations should be composable through Spring profiles.</p>
 *
 * @see Profiles
 */
public class Config {

    private final String driverType;
    private final String driverProperty;
    private final Path driverBin;
    private final Path browserBinary;
    private final Path browserDownloads;

    private final Path reportsDir;
    private final Path attachmentsFolder;
    private final Path exportDocuments;

    private final URL arBaseUrl;
    private final URL arFullUrl;
    private final URL fullAnalysisModuleUrl;
    private final URL fcrBaseUrl;
    private final URL fcrFullUrl;
    private final String hubUrl;

    private final String version;

    private final long minTimeout;
    private final long defaultTimeout;
    private final long maxTimeout;

    private final Path populatedReturns;
    private final Path importSettingsFile;
    private final Path importDir;
    private final long importTimeout;
    private final String arCookie;

    Config(final Builder builder) {
        driverType = builder.driverType;
        driverProperty = builder.driverProperty;
        driverBin = builder.driverBin;
        browserBinary = builder.browserBinary;
        browserDownloads = builder.browserDownloads;

        reportsDir = builder.reportsDir;
        attachmentsFolder = builder.attachmentDocuments;
        exportDocuments = builder.exportDocuments;

        arBaseUrl = builder.arBaseUrl;
        arFullUrl = builder.arFullUrl;
        fcrBaseUrl = builder.fcrBaseUrl;
        fcrFullUrl = builder.fcrFullUrl;
        hubUrl = builder.hubUrl;
        version = builder.version;
        fullAnalysisModuleUrl = builder.fullAnalysisModuleUrl;
        minTimeout = builder.minTimeout;
        defaultTimeout = builder.defaultTimeout;
        maxTimeout = builder.maxTimeout;

        populatedReturns = builder.populatedReturns;
        importDir = builder.importDir;
        importSettingsFile = builder.importSettingsFile;
        importTimeout = builder.importTimeout;
        arCookie =builder.arCookie;
    }

    public String arCookie(){
        return arCookie;
    }

    /**
     * <p>The full class name of the custom driver provider in use.</p>
     *
     * @return driverType - driver's full class name
     * @see com.lombardrisk.config.driver.ChromeDriverProvider
     */
    public String driverType() {
        return driverType;
    }

    /**
     * <p>The Selenium driver-specific property name for setting the path to a web driver</p>
     *
     * @return driverProperty - web driver property name
     * @see #driverBin()
     * @see StepDefConfig
     */
    public String driverProperty() {
        return driverProperty;
    }

    /**
     * <p>The relative web driver path currently used</p>
     *
     * @return driverBin - web driver path
     * @see #driverProperty()
     * @see StepDefConfig
     */
    public Path driverBin() {
        return driverBin;
    }

    /**
     * <p>The path to the browser that should be used for running tests in.</p>
     * <p>When not set, Selenium will delegate the search for the binary to the specified driver.</p>
     *
     * @return browserBinary - browser binary path
     */
    public Optional<Path> browserBinary() {
        return Optional.ofNullable(browserBinary);
    }

    /**
     * <p>The custom downloads directory that should be used by the specified driver.</p>
     * <p>This path can also be accessed though the system property: <code>browser.downloads.dir</code></p>
     *
     * @return browserDownloads - browser's downloads path
     */
    public Path browserDownloads() {
        return browserDownloads;
    }

    /**
     * <p>The custom reports directory where Selenide will put all the failure screenshots/html.</p>
     *
     * @return reportsDir - reports directory
     */
    public Path reportsDir() {
        return reportsDir;
    }

    /**
     * <p>The directory that contains expected attachment documents.</p>
     *
     * @return attachmentsFolder - attachments directory
     */
    public Path attachmentsFolder() {
        return attachmentsFolder;
    }

    /**
     * <p>The directory that contains expected export documents.</p>
     *
     * @return exportDocuments - exports directory
     */
    public Path exportDocuments() {
        return exportDocuments;
    }

    /**
     * <p>
     * The base URL for the site under test,
     * used by Selenide to prefix when calling {@link com.codeborne.selenide.Selenide#open(String)}
     * </p>
     *
     * @return baseUrl - URL with host and port only
     */
    public URL arBaseUrl() {
        return arBaseUrl;
    }

    /**
     * <p>
     * The full URL for the site under test,
     * used by Selenide to prefix when calling {@link com.codeborne.selenide.Selenide#open(URL)}
     * </p>
     *
     * @return fullUrl - URL with host, port and context path
     */
    public URL arFullUrl() {
        return arFullUrl;
    }

    /**
     * <p>
     * The full URL for the site under test,
     * used by Selenide to prefix when calling {@link com.codeborne.selenide.Selenide#open(URL)}
     * </p>
     *
     * @return fullAnalysisModuleUrl - URL with host, port and context path
     */
    public URL fcrBaseUrl() {
        return fcrBaseUrl;
    }

    /**
     * <p>
     * The full URL for the site under test,
     * used by Selenide to prefix when calling {@link com.codeborne.selenide.Selenide#open(URL)}
     * </p>
     *
     * @return fullUrl - URL with host, port and context path
     */
    public URL fcrFullUrl() {
        return fcrFullUrl;
    }

    /**
     * <p>
     * The full URL for the site under test,
     * used by Selenide to prefix when calling {@link com.codeborne.selenide.Selenide#open(URL)}
     * </p>
     *
     * @return fullAnalysisModuleUrl - URL with host, port and context path
     */
    public URL fullAnalysisModuleUrl() {
        return fullAnalysisModuleUrl;
    }

    /**
     * <p>
     * The URL for the Selenium grid hub,
     * </p>
     */
    public Optional<String> hubUrl() {
        return Optional.ofNullable(hubUrl);
    }

    /**
     * <p>
     * The IE/Chrome version
     * </p>
     */
    public Optional<String> version() {
        return Optional.ofNullable(version);
    }
    /**
     * <p>Minimum timeout that should be used to wait for any custom condition to apply.</p>
     * <p>This timeout should be less than the other timeouts.</p>
     * <p>It should be small enough that it could be used for polling.</p>
     *
     * @return minTimeout - minimum timeout in millis
     * @see #defaultTimeout()
     * @see #maxTimeout()
     */
    public long minTimeout() {
        return minTimeout;
    }

    /**
     * <p>Default timeout that is used to wait for any condition to apply.</p>
     * <p>This timeout should be greater than {@link #minTimeout} and less than {@link #maxTimeout}.</p>
     *
     * @return defaultTimeout - default timeout in millis
     * @see #minTimeout()
     * @see #maxTimeout()
     */
    public long defaultTimeout() {
        return defaultTimeout;
    }

    /**
     * <p>Maximum timeout amount that should be used to wait for any custom condition to apply.</p>
     * <p>This timeout should be greater than the other timeouts.</p>
     *
     * @return maxTimeout - maximum timeout in millis
     * @see #defaultTimeout()
     * @see #minTimeout()
     */
    public long maxTimeout() {
        return maxTimeout;
    }

    /**
     * <p>
     * The directory containing import files with populated cell values
     * that should be used when importing OFSAA for Returns.
     * </p>
     *
     * @return populatedReturnsDir - populated returns directory
     */
    public Path populatedReturnsDir() {
        return populatedReturns;
    }

    public Path importDir() {
        return importDir;
    }

    public Path importSettingsFile() {
        return importSettingsFile;
    }

    /**
     * <p>
     * A special timeout used when importing Returns
     * as this process takes longer based on how many cells will be imported.
     * </p>
     *
     * @return importTimeout - import timeout in millis
     */
    public long importTimeout() {
        return importTimeout;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("driverType", driverType)
                .add("driverProperty", driverProperty)
                .add("driverBin", driverBin)
                .add("browserBinary", browserBinary)
                .add("browserDownloads", browserDownloads)
                .add("reportsDir", reportsDir)
                .add("attachmentDocuments", attachmentsFolder)
                .add("exportDocuments", exportDocuments)
                .add("arBaseUrl", arBaseUrl)
                .add("arFullUrl", arFullUrl)
                .add("fcrBaseUrl", fcrBaseUrl)
                .add("fcrFullUrl", fcrFullUrl)
                .add("hubUrl", hubUrl)
                .add("version", version)
                .add("minTimeout", minTimeout)
                .add("defaultTimeout", defaultTimeout)
                .add("maxTimeout", maxTimeout)
                .add("populatedReturns", populatedReturns)
                .add("importDir", importDir)
                .add("importSettingsFile", importSettingsFile)
                .add("importTimeout", importTimeout)
                .add("arCookie", arCookie)
                .toString();
    }

    static class Builder {

        private static final long MIN_TIMEOUT = 200;
        private static final long DEFAULT_TIMEOUT = 4000;

        private String driverType;
        private String driverProperty;
        private Path driverBin;
        private Path browserBinary;
        private Path browserDownloads;
        private Path reportsDir;

        private URL arBaseUrl;
        private URL arFullUrl;
        private URL fullAnalysisModuleUrl;
        private URL fcrBaseUrl;
        private URL fcrFullUrl;
        private String hubUrl;
        private String version;
        private long defaultTimeout;
        private long minTimeout = MIN_TIMEOUT;
        private long maxTimeout;

        private Path populatedReturns;
        private Path importDir;
        private Path importSettingsFile;
        private Path attachmentDocuments;
        private long importTimeout;
        private Path exportDocuments;
        private String arCookie;

        Builder arBaseUrl(final URL arBaseUrl) {
            this.arBaseUrl = arBaseUrl;
            return this;
        }

        Builder arFullUrl(final URL arFullUrl) {
            this.arFullUrl = arFullUrl;
            return this;
        }

        Builder arCookie(final String arCookie) {
            this.arCookie = arCookie;
            return this;
        }

        Builder fcrBaseUrl(final URL fcrBaseUrl) {
            this.fcrBaseUrl = fcrBaseUrl;
            return this;
        }

        Builder fcrFullUrl(final URL fcrFullUrl) {
            this.fcrFullUrl = fcrFullUrl;
            return this;
        }

        Builder fullAnalysisModuleUrl(final URL fullAnalysisModuleUrl) {
            this.fullAnalysisModuleUrl = fullAnalysisModuleUrl;
            return this;
        }

        Builder hubUrl(final String hubUrl) {
            this.hubUrl = hubUrl;
            return this;
        }

        Builder version(final String version) {
            this.version = version;
            return this;
        }

        Builder driverType(final String driverType) {
            this.driverType = checkNotNull(trimToNull(driverType));
            return this;
        }

        Builder driverProperty(final String driverProperty) {
            this.driverProperty = checkNotNull(trimToNull(driverProperty));
            return this;
        }

        Builder driverBin(final Path driverBin) {
            checkFile(driverBin);

            this.driverBin = driverBin.toAbsolutePath();
            return this;
        }

        Builder browserBinary(final Path browserBinaryPath) {
            if (browserBinaryPath != null) {
                browserBinary = browserBinaryPath.toAbsolutePath();

                checkState(
                        isExecutable(browserBinary),
                        "Browser binary does not exist or is not executable [" + browserBinary + "]");
            }
            return this;
        }

        Builder browserDownloadsDir(final Path browserDownloadsPath) {
            browserDownloads = browserDownloadsPath.toAbsolutePath();
            try {
                createDirectories(browserDownloads);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return this;
        }

        Builder reportsDir(final Path reportsDirPath) {
            reportsDir = reportsDirPath.toAbsolutePath();
            try {
                createDirectories(reportsDir);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return this;
        }

        Builder minTimeout(final long timeout) {
            minTimeout = timeout < MIN_TIMEOUT
                    ? MIN_TIMEOUT
                    : timeout;
            return this;
        }

        Builder defaultTimeout(final long timeout) {
            defaultTimeout = timeout < minTimeout ? DEFAULT_TIMEOUT : timeout;
            return this;
        }

        Builder maxTimeout(final long timeout) {
            maxTimeout = timeout < defaultTimeout || timeout < DEFAULT_TIMEOUT
                    ? DEFAULT_TIMEOUT * 2
                    : timeout;
            return this;
        }

        Builder populatedReturnsDir(final Path populatedReturns) {
            checkDir(populatedReturns);

            this.populatedReturns = populatedReturns.toAbsolutePath();
            return this;
        }

        Builder importDir(final Path importDir) {
            checkDir(importDir);

            this.importDir = importDir.toAbsolutePath();
            return this;
        }

        Builder importSettingsFile(final Path importSettingsFile) {
            checkFile(importSettingsFile);

            this.importSettingsFile = importSettingsFile.toAbsolutePath();
            return this;
        }

        Builder importTimeout(final long importTimeout) {
            this.importTimeout = importTimeout;
            return this;
        }

        Builder attachmentDocuments(final Path attachmentDocuments) {
            checkDir(attachmentDocuments);

            this.attachmentDocuments = attachmentDocuments.toAbsolutePath();
            return this;
        }

        Builder exportDocuments(final Path exportDocuments) {
            checkDir(exportDocuments);

            this.exportDocuments = exportDocuments;
            return this;
        }

        Config build() {
            return new Config(this);
        }

        private static void checkDir(final Path dir) {
            checkState(exists(dir), "Could not find path [" + dir + "]");
            checkState(isDirectory(dir), "Path [" + dir + "] is not a dir");
        }

        private static void checkFile(final Path file) {
            checkState(exists(file), "Could not find path [" + file + "]");
            checkState(isRegularFile(file), "Path [" + file + "] is not a file");
        }

    }
}
