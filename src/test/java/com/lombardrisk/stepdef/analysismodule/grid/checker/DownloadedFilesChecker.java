package com.lombardrisk.stepdef.analysismodule.grid.checker;

import com.codeborne.selenide.impl.Waiter;
import com.google.common.base.Predicate;
import com.lombardrisk.config.Config;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.Collection;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.io.FileUtils.listFiles;

public class DownloadedFilesChecker {

    private final Config config;
    private final Waiter waiter;

    public DownloadedFilesChecker(final Config config, final Waiter waiter) {
        this.config = config;
        this.waiter = waiter;
    }

    public File shouldBeDownloaded(final IOFileFilter fileFilter) {
        Predicate<IOFileFilter> downloadedFileCondition = downloadedFileCondition();
        waiter.wait(
                fileFilter, downloadedFileCondition,
                config.maxTimeout(), config.minTimeout());

        if (downloadedFileCondition.test(fileFilter))
            return findDownloadedFiles(fileFilter).iterator().next();
        else
            throw new AssertionError(
                    String.format(
                            "Could not find any files in the downloads dir [%s], "
                                    + "after applying the %s.%nDirectory contains:%n%s",
                            config.browserDownloads(),
                            fileFilter,
                            findAllDownloadedFiles()));
    }

    private Predicate<IOFileFilter> downloadedFileCondition() {
        return fileFilter -> !findDownloadedFiles(fileFilter).isEmpty();
    }

    private Collection<File> findDownloadedFiles(final IOFileFilter fileFilter) {
        return listFiles(
                config.browserDownloads().toFile(),
                fileFilter,
                TrueFileFilter.TRUE);
    }

    private String findAllDownloadedFiles() {
        return listFiles(config.browserDownloads().toFile(), null, false)
                .stream()
                .map(File::toString)
                .collect(joining("\n"));
    }
}
