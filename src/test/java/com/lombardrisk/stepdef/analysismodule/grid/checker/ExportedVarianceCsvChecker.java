package com.lombardrisk.stepdef.analysismodule.grid.checker;

import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newBufferedReader;

public class ExportedVarianceCsvChecker implements GridChecker {

    private final DownloadedFilesChecker downloadedFilesChecker;

    public ExportedVarianceCsvChecker(final DownloadedFilesChecker downloadedFilesChecker) {
        this.downloadedFilesChecker = downloadedFilesChecker;
    }

    @Override
    public void shouldHaveDiffs(
            final Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReference,
            final ReturnSelection selection) {

        File downloadedCsv = downloadedFilesChecker.shouldBeDownloaded(
                new NameFileFilter(
                        String.format(
                                "%s %s %s %s variance.csv",
                                selection.regulator(),
                                selection.entityName(),
                                selection.returnName(),
                                selection.referenceDate()),
                        IOCase.INSENSITIVE));

        try (CSVParser csvParser = CSVFormat.EXCEL.parse(
                newBufferedReader(downloadedCsv.toPath()))) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            List<CSVRecord> downloadedCsvRecords = csvRecords.subList(1, csvRecords.size());
            CSVRecord headerRecord = csvRecords.get(0);

            assertRows(expectedPeriodDiffsByCellReference, downloadedCsvRecords, headerRecord);
        } catch (IOException e) {
            throw new AssertionError(e.getMessage(), e);
        }
    }

    private static void assertRows(
            final Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReference,
            final List<CSVRecord> downloadedCsvRecords,
            final CSVRecord headerRecord) {

        SoftAssertions soft = new SoftAssertions();

        Iterator<Map.Entry<CellReference, List<PeriodDiff>>> iterator =
                expectedPeriodDiffsByCellReference.entrySet().iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            Map.Entry<CellReference, List<PeriodDiff>> expectedCellReferenceEntry = iterator.next();

            CellReference expectedCellReference = expectedCellReferenceEntry.getKey();
            PeriodDiff expectedPeriodDiff = expectedCellReferenceEntry.getValue().get(0);

            CSVRecord downloadedCsvRecord = downloadedCsvRecords.get(i);

            String rowNumberDescription = "on row " + (i + 2);

            String cellReference = downloadedCsvRecord.get(0);
            soft.assertThat(cellReference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedCellReference.getName());

            String cellDescription = downloadedCsvRecord.get(1);
            soft.assertThat(cellDescription)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedCellReference.getDescription());

            String startPeriodDateHeader = headerRecord.get(2);
            soft.assertThat(startPeriodDateHeader)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getStartDate());

            String startPeriodValue = downloadedCsvRecord.get(2);
            soft.assertThat(startPeriodValue)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getStartValue());

            String endPeriodDateHeader = headerRecord.get(3);
            soft.assertThat(endPeriodDateHeader)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getEndDate());

            String endPeriodValue = downloadedCsvRecord.get(3);
            soft.assertThat(endPeriodValue)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getEndValue());

            String difference = downloadedCsvRecord.get(4);
            soft.assertThat(difference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getDifference());

            String percentDifference = downloadedCsvRecord.get(5);
            soft.assertThat(percentDifference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getPercentDifference());
        }
        soft.assertAll();
    }
}
