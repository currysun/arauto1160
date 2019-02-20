package com.lombardrisk.stepdef.analysismodule.grid.checker;

import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.lombardrisk.page.analysismodule.grid.GridPage.ANALYSIS_MODULE_AMOUNT_FORMATTER;
import static java.nio.file.Files.newInputStream;

public class ExportedVarianceExcelChecker implements GridChecker {

    private final DownloadedFilesChecker downloadedFilesChecker;

    public ExportedVarianceExcelChecker(final DownloadedFilesChecker downloadedFilesChecker) {
        this.downloadedFilesChecker = downloadedFilesChecker;
    }

    @Override
    public void shouldHaveDiffs(
            final Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReference,
            final ReturnSelection selection) {

        File downloadedExcel = downloadedFilesChecker.shouldBeDownloaded(
                new NameFileFilter(
                        String.format(
                                "%s %s %s %s variance.xlsx",
                                selection.regulator(),
                                selection.entityName(),
                                selection.returnName(),
                                selection.referenceDate()),
                        IOCase.INSENSITIVE));

        try (XSSFWorkbook downloadedWorkbook =
                     new XSSFWorkbook(newInputStream(downloadedExcel.toPath()))) {

            assertRows(expectedPeriodDiffsByCellReference, downloadedWorkbook.getSheetAt(0));
        } catch (IOException e) {
            throw new AssertionError(e.getMessage(), e);
        }
    }

    private static void assertRows(
            final Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReference,
            final XSSFSheet firstSheet) {

        SoftAssertions soft = new SoftAssertions();

        Iterator<Map.Entry<CellReference, List<PeriodDiff>>> iterator =
                expectedPeriodDiffsByCellReference.entrySet().iterator();

        XSSFRow headerRow = firstSheet.getRow(0);
        for (int i = 0; iterator.hasNext(); i++) {
            Map.Entry<CellReference, List<PeriodDiff>> expectedCellReferenceEntry = iterator.next();

            CellReference expectedCellReference = expectedCellReferenceEntry.getKey();
            PeriodDiff expectedPeriodDiff = expectedCellReferenceEntry.getValue().get(0);
            XSSFRow row = firstSheet.getRow(i + 1);

            String rowNumberDescription = "on row " + (i + 2);

            String cellReference = getStringValue(row.getCell(0));
            soft.assertThat(cellReference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedCellReference.getName());

            String cellDescription = getStringValue(row.getCell(1));
            soft.assertThat(cellDescription)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedCellReference.getDescription());

            String startPeriodDateHeader = getStringValue(headerRow.getCell(2));
            soft.assertThat(startPeriodDateHeader)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getStartDate());

            String startPeriodValue = getStringValue(row.getCell(2));
            soft.assertThat(startPeriodValue)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getStartValue());

            String endPeriodDateHeader = getStringValue(headerRow.getCell(3));
            soft.assertThat(endPeriodDateHeader)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getEndDate());

            String endPeriodValue = getStringValue(row.getCell(3));
            soft.assertThat(endPeriodValue)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getEndValue());

            String difference = getStringValue(row.getCell(4));
            soft.assertThat(difference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getDifference());

            String percentDifference = getStringValue(row.getCell(5));
            soft.assertThat(percentDifference)
                    .as(rowNumberDescription)
                    .isEqualTo(expectedPeriodDiff.getPercentDifference());
        }
        soft.assertAll();
    }

    private static String getStringValue(final XSSFCell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getRawValue();
            default:
                throw new AssertionError(
                        String.format(
                                "cell %s has an unexpected type: %s",
                                cell.getReference(), cell.getCellTypeEnum()));
        }
    }
}
