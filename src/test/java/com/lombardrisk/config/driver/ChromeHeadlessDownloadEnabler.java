package com.lombardrisk.config.driver;

import com.lombardrisk.config.Config;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.session.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.lombardrisk.config.driver.ChromeDriverProvider.DRIVER_LOG_FILE;
import static io.webfolder.cdp.type.constant.DownloadBehavior.Allow;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class ChromeHeadlessDownloadEnabler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeHeadlessDownloadEnabler.class);
    private static final Pattern PORT_EXTRACTOR_REGEX = Pattern.compile("^.*--remote-debugging-port=(\\d+).*$");

    private static boolean pageDownloadsAllowed;

    private final Config config;

    public ChromeHeadlessDownloadEnabler(final Config config) {
        this.config = config;
    }

    public void enableDownloads() {
        if (!pageDownloadsAllowed
                && ChromeHeadless.class.getCanonicalName().equals(config.driverType())) {

            findRemoteDebugPort()
                    .ifPresent(this::enableDownloadsByRemoteInterface);

            pageDownloadsAllowed = true;
        }
    }

    private static Optional<Integer> findRemoteDebugPort() {
        try (Stream<String> driverLogStream = Files.lines(DRIVER_LOG_FILE.toPath())) {

            return driverLogStream
                    .filter(logLine -> logLine.contains("--remote-debugging-port"))
                    .map(ChromeHeadlessDownloadEnabler::parsePort)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(Integer::parseInt);
        } catch (IOException e) {
            LOGGER.error("Could not enable downloads for headless Chrome", e);
            return Optional.empty();
        }
    }

    private static String parsePort(final String cliArgsLine) {
        Matcher portMatcher = PORT_EXTRACTOR_REGEX.matcher(cliArgsLine);

        return portMatcher.find() ? portMatcher.group(1) : null;
    }

    private void enableDownloadsByRemoteInterface(final int debugPort) {
        SessionFactory sessionFactory = new Launcher(debugPort).launch();

        Optional<String> sessionId =
                sessionFactory.list().stream()
                        .filter(session -> equalsIgnoreCase("AgileREPORTER", session.getTitle()))
                        .findFirst()
                        .map(SessionInfo::getId);

        sessionId.ifPresent(
                enableHeadlessChromeDownloads(sessionFactory, config.browserDownloads().toString()));
    }

    private static Consumer<String> enableHeadlessChromeDownloads(
            final SessionFactory sessionFactory,
            final String browserDownloadsDir) {
        return sessionId -> {
            LOGGER.info("Enable downloads for headless Chrome");

            Session session = sessionFactory.connect(sessionId);
            session.getCommand()
                    .getPage()
                    .setDownloadBehavior(Allow, browserDownloadsDir);
        };
    }
}
