package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.collect.ImmutableMap;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.SuppliedAssertionError;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.stepdef.analysismodule.grid.checker.DownloadedFilesChecker;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ReturnAnalysisReportPanel {

    private final static SelenideElement title = $(By.tagName("mat-card-title"));
    private final static SelenideElement regulator =
            $(By.cssSelector("mat-card-content > div >div >p"));
    private final static SelenideElement entity =
            $(By.cssSelector("mat-card-content > div >div >p:nth-child(2)"));
    private final static SelenideElement reportingDate =
            $(By.cssSelector("mat-card-content > div >div >p:nth-child(3)"));
    private final static SelenideElement edition =
            $(By.cssSelector("mat-card-content > div >div >p:nth-child(4)"));
    private final static SelenideElement maker =
            $(By.cssSelector("mat-card-content > div >div:nth-child(2) >p:nth-child(1)"));
    private final static ElementsCollection approvers =
            $$(By.cssSelector("app-return-details-card .approvers__list-item span"));
    private final static SelenideElement status =
            $(By.cssSelector("mat-card-content > div >div:nth-child(3) >p:nth-child(1)"));
    private final static SelenideElement closeRAR =
            $(By.cssSelector("app-return-analysis-report-container button.mat-icon-button .mat-button-wrapper"));
    private final static SelenideElement showDetails =
            $(By.cssSelector("div[col-id='comparisonContext'] button"));
    private final static SelenideElement returnsCommentPanel = $(By.cssSelector("app-return-comments-panel"));
    private final static SelenideElement cellLevelCommentsPanel = $(By.cssSelector("app-cell-audit-panel"));
    private static final String TYPE_HEADER = "Type";
    private static final String PERIOD_RANGE_HEADER = "Period Range";
    private static final String AUTHOR_HEADER = "Author";
    private static final String DATE_ADDED_HEADER = "Date Added";
    private static final String COMMENT_HEADER = "Comment";
    private static final String ATTACHMENTS_HEADER = "Attachments";
    private static final String CELL_REFERENCE = "cell_reference";
    private static final String CELL_DESCRIPTION = "description";
    private static final String INSTANCE = "instance";
    private static final String TYPE = "Type";
    private static final String PRIOR_DATE = "prior_date";
    private static final String PRIOR_VALUE = "prior_value";
    private static final String CURRENT_DATE = "current_date";
    private static final String CURRENT_VALUE = "Current_value";
    private static final String DIFFERENCE = "difference";
    private static final String PERCENT_DIFFERENCE = "percent_difference";
    private static final String COMMENT = "comment";
    private static final String CONTEXT = "context";
    private static final String DATE_ADDED = "date_added";
    private static final String AUTHOR = "author";
    private static final String ATTACHMENTS = "attachment";
    private static final String TREND_DATE = "date";
    private static final String TREND_VALUE = "value";
    private static final String TREND_DIFFERENCE = "difference";
    private static final String TREND_PERCENT_DIFFERENCE = "percent_difference";
    private static final String TREND_DATE_HEADER = "date";
    private static final String TREND_VALUE_HEADER = "value";
    private static final String TREND_DIFFERENCE_HEADER = "difference";
    private static final String TREND_PERCENT_DIFFERENCE_HEADER = "percent_difference";
    private static final Map<String, String> COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(TYPE_HEADER, "0")
                    .put(PERIOD_RANGE_HEADER, "1")
                    .put(AUTHOR_HEADER, "actionedBy")
                    .put(DATE_ADDED_HEADER, "createdTimestamp")
                    .put(COMMENT_HEADER, "comment")
                    .put(ATTACHMENTS_HEADER, "attachments")
                    .build();
    private static final Map<String, String> COL_ID_BY_CELL_LEVEL_COMMENT_CELL_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(CELL_DESCRIPTION, "cell.description")
                    .put(CELL_REFERENCE, "0")
                    .put(INSTANCE, "cell.pageInstance")
                    .put(TYPE, "type")
                    .put(PRIOR_DATE, "1")
                    .put(PRIOR_VALUE, "2")
                    .put(CURRENT_DATE, "3")
                    .put(CURRENT_VALUE, "4")
                    .put(DIFFERENCE, "5")
                    .put(PERCENT_DIFFERENCE, "6")
                    .put(COMMENT, "comment")
                    .put(CONTEXT, "comparisonContext")
                    .put(DATE_ADDED, "createdTimestamp")
                    .put(AUTHOR, "author")
                    .put(ATTACHMENTS, "attachments")
                    .build();
    private static final Map<String, String> RAR_COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(TREND_DATE_HEADER, "referenceDate")
                    .put(TREND_VALUE_HEADER, "value")
                    .put(TREND_DIFFERENCE_HEADER, "0")
                    .put(TREND_PERCENT_DIFFERENCE_HEADER, "1")
                    .build();
    private final DownloadedFilesChecker downloadedFilesChecker;
    private final GridPage gridPage;

    private final Config config;

    public ReturnAnalysisReportPanel(final Config config, final GridPage gridPage) {
        this.gridPage = requireNonNull(gridPage);
        downloadedFilesChecker = null;
        this.config = config;
    }

    private static SelenideElement findCellByColId(final String colId) {
        return topLayerCommentBody()
                .$x("*//div[@col-id='" + colId + "']");
    }

    private static SelenideElement topLayerCommentBody() {
        return $("app-return-comments-panel .ag-body");
    }

    static String findColIdByHeaderName(final String headerName) {
        String colId = COL_ID_BY_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    private static Supplier<String> toMissingHeaderMappingMessage(final String headerName) {
        return () -> {
            String mappedHeaders = COL_ID_BY_HEADER_NAME.entrySet().stream()
                    .map(entry -> String.format("  '%s' = '%s'%n", entry.getKey(), entry.getValue()))
                    .collect(joining());

            return String.format(
                    "Header with name [%s] does not have a mapping.%nCurrent mappings: %n%s",
                    headerName, mappedHeaders);
        };
    }

    static String columnMsg(final String value) {
        return "column " + value;
    }

    private static SelenideElement findCellLevelCommentByColId(final String colId) {
        SelenideElement body = topLayerCellLevelCommentBody();
        SelenideElement value = body.$("div.ag-cell[col-id='" + colId + "']");
        return value;
    }

    private static SelenideElement topLayerCellLevelCommentBody() {
        return $("app-cell-audit-panel .ag-body");
    }

    static String findCellLevelCommentColIdByHeaderName(final String headerName) {
        String colId = COL_ID_BY_CELL_LEVEL_COMMENT_CELL_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    private static SelenideElement findTrendCellByColId(final String colId) {
        return topLayerTrendBody()
                .$x("*//div[@col-id='" + colId + "']");
    }

    private static SelenideElement topLayerTrendBody() {
        return $("app-cell-audit-grid-detail .ag-body");
    }

    static String findTrendColIdByHeaderName(final String headerName) {
        String colId = RAR_COL_ID_BY_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    public void shouldHaveReportDetails(final List<List<String>> dataTable) {
        Date expectedCreatedDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MMM d, YYYY, HH:mm");

        String expectedTitle = dataTable.get(0).get(1);
        title.shouldHave(text(expectedTitle));

        String expectedRegulator = dataTable.get(1).get(1);
        regulator.shouldHave(text(expectedRegulator));

        String expectedEntity = dataTable.get(2).get(1);
        entity.shouldHave(text(expectedEntity));

        String expectedReportingDate = dataTable.get(3).get(1);
        reportingDate.shouldHave(text(expectedReportingDate));

        String expectedEdition = dataTable.get(4).get(1);
        edition.shouldHave(text(expectedEdition));

        String expectedMaker = dataTable.get(5).get(1);
        maker.shouldHave(text(expectedMaker));

        String expectedApprovers = dataTable.get(6).get(1);
        shouldHaveApprovers(expectedApprovers);

        String expectedStatus = dataTable.get(7).get(1);
        status.shouldHave(text(expectedStatus));

        $(By.cssSelector("mat-card-content > div >div:nth-child(3) >p:nth-child(2)")).shouldHave(text(
                dateFormat.format(expectedCreatedDate)));
    }

    private void shouldHaveApprovers(final String expectedApprovers) {
        String[] expApprovers = expectedApprovers.split(",");
        int i = 0;
        for (SelenideElement actualApprover : approvers) {
            actualApprover.shouldHave(text(expApprovers[i++]));
        }
    }

    public void shouldHaveReturnCommentsPanel() {
        returnsCommentPanel.shouldBe(visible).click();
    }

    public void shouldHaveReturnCommentsDetails(final List<List<String>> dataTable) {
        String type = dataTable.get(0).get(1);
        findCellByColId(findColIdByHeaderName(TYPE_HEADER))
                .shouldBe(visible)
                .shouldHave(text(type).because(columnMsg(TYPE_HEADER)));

        String periodRange = dataTable.get(1).get(1);
        findCellByColId(findColIdByHeaderName(PERIOD_RANGE_HEADER))
                .shouldBe(visible)
                .shouldHave(text(periodRange).because(columnMsg(PERIOD_RANGE_HEADER)));

        String author = dataTable.get(2).get(1);
        findCellByColId(findColIdByHeaderName(AUTHOR_HEADER))
                .shouldBe(visible)
                .shouldHave(text(author).because(columnMsg(AUTHOR_HEADER)));

        String comment = dataTable.get(3).get(1);
        findCellByColId(findColIdByHeaderName(COMMENT_HEADER))
                .shouldBe(visible)
                .shouldHave(text(comment).because(columnMsg(COMMENT_HEADER)));

        String attachment = dataTable.get(4).get(1);
        findCellByColId(findColIdByHeaderName(ATTACHMENTS_HEADER))
                .shouldBe(visible)
                .shouldHave(text(attachment).because(columnMsg(
                ATTACHMENTS_HEADER)));
    }

    public void shouldHaveCellLevelComments() {
        cellLevelCommentsPanel.shouldBe(visible).click();
    }

    public void shouldHaveCellLevelCommentDetails(final List<List<String>> dataTable) {

        String cell_reference = dataTable.get(0).get(1);
        gridPage.filterRarGridByCellReference(cell_reference);
        gridPage.sortRarGridBy(DATE_ADDED_HEADER);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(CELL_REFERENCE))
                .shouldBe(visible)
                .shouldHave(text(cell_reference).because(columnMsg(CELL_REFERENCE)));

        String cell_description = dataTable.get(1).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(CELL_DESCRIPTION))
                .shouldBe(visible)
                .shouldHave(text(cell_description).because(columnMsg(CELL_DESCRIPTION)));

        String instance = dataTable.get(2).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(INSTANCE))
                .shouldBe(visible)
                .shouldHave(text(instance).because(
                columnMsg(INSTANCE)));

        String type = dataTable.get(3).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(TYPE))
                .shouldBe(visible)
                .shouldHave(text(type).because(columnMsg(
                TYPE)));

        String prior_date = dataTable.get(4).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(PRIOR_DATE))
                .shouldBe(visible)
                .shouldHave(text(prior_date).because(
                columnMsg(
                        PRIOR_DATE)));

        String prior_value = dataTable.get(5).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(PRIOR_VALUE))
                .shouldBe(visible)
                .shouldHave(text(prior_value).because(
                columnMsg(
                        PRIOR_VALUE)));

        String current_date = dataTable.get(6).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(CURRENT_DATE))
                .shouldBe(visible)
                .shouldHave(text(current_date).because(
                columnMsg(
                        CURRENT_DATE)));

        String current_value = dataTable.get(7).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(CURRENT_VALUE))
                .shouldBe(visible)
                .shouldHave(text(current_value)
                .because(columnMsg(
                        CURRENT_VALUE)));

        gridPage.scrollToRarCellCommentHeaderWithId("5");

        String difference = dataTable.get(8).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(DIFFERENCE))
                .shouldBe(visible)
                .shouldHave(text(difference).because(
                columnMsg(
                        DIFFERENCE)));

        gridPage.scrollToRarCellCommentHeaderWithId("6");

        String percent_difference = dataTable.get(9).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(PERCENT_DIFFERENCE))
                .shouldBe(visible)
                .shouldHave(text(
                percent_difference).because(columnMsg(
                PERCENT_DIFFERENCE)));

        gridPage.scrollToRarCellCommentHeaderWithId("comment");

        String comment = dataTable.get(10).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(COMMENT))
                .shouldBe(visible)
                .shouldHave(text(comment).because(
                columnMsg(
                        COMMENT)));

        gridPage.scrollToRarCellCommentHeaderWithId("comparisonContext");

        String context = dataTable.get(11).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(CONTEXT))
                .shouldBe(visible)
                .shouldHave(text(context).because(
                columnMsg(
                        CONTEXT)));

        gridPage.scrollToRarCellCommentHeaderWithId("createdTimestamp");

        gridPage.scrollToRarCellCommentHeaderWithId("author");

        String author = dataTable.get(12).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(AUTHOR))
                .shouldBe(visible)
                .shouldHave(text(author).because(
                columnMsg(
                        AUTHOR)));
        gridPage.scrollToRarCellCommentHeaderWithId("attachments");

        String attachments = dataTable.get(13).get(1);
        findCellLevelCommentByColId(findCellLevelCommentColIdByHeaderName(ATTACHMENTS))
                .shouldBe(visible)
                .shouldHave(text(attachments).because(
                columnMsg(
                        ATTACHMENTS)));
    }

    public void clickOnShowDetails() {
        showDetails.click();
    }

    public void shouldHaveTrendAnalysisDetailsGrid(final List<List<String>> dataTable) {
        String date = dataTable.get(0).get(1);
        gridPage.filterTrendsGridByDate(date);
        findTrendCellByColId(findTrendColIdByHeaderName(TREND_DATE)).shouldHave(text(date).because(
                columnMsg(TREND_DATE)));

        String trend_value = dataTable.get(1).get(1);
        findTrendCellByColId(findTrendColIdByHeaderName(TREND_VALUE)).shouldHave(text(trend_value).because(
                columnMsg(TREND_VALUE)));

        String trend_difference = dataTable.get(2).get(1);
        findTrendCellByColId(findTrendColIdByHeaderName(TREND_DIFFERENCE)).shouldHave(text(trend_difference).because(
                columnMsg(TREND_DIFFERENCE)));

        String trend_percent_difference = dataTable.get(3).get(1);
        findTrendCellByColId(findTrendColIdByHeaderName(TREND_PERCENT_DIFFERENCE)).shouldHave(text(
                trend_percent_difference).because(
                columnMsg(TREND_PERCENT_DIFFERENCE)));
    }

    public void closeRar() {
        closeRAR.shouldBe(visible).click();
    }

    public void filterBy(final String columnName) {
        gridPage.filterRarGridBy(columnName);
    }

    public void pdfShouldNotMatch(final String expectedFileName) {
        String pdfFileName = null;

        try {
            Path expectedFilePath = config.exportDocuments().resolve(expectedFileName);
            String expectedText = parsePdfFile(expectedFilePath);

            Path downloadedFilePath = config.browserDownloads();

            pdfFileName = getExportedPdfFileName();

            downloadedFilePath = downloadedFilePath.resolve(pdfFileName);
            String downloadedText = parsePdfFile(downloadedFilePath);

            downloadedText =
                    downloadedText.replaceAll(
                            ".*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}).*|.*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}.*[0-9]{2}:[0-9]{2}:[0-9]{2})|([0-9]{2}:[0-9]{2}:[0-9]{2})",
                            "");
            expectedText =
                    expectedText.replaceAll(
                            ".*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}).*|.*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}.*[0-9]{2}:[0-9]{2}:[0-9]{2})|([0-9]{2}:[0-9]{2}:[0-9]{2})",
                            "");
            assertNotEquals("Exported Rar is the same as expected", expectedText, downloadedText);
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
        }
    }

    public void pdfShouldMatch(final String expectedFileName) {
        String pdfFileName = null;

        try {
            Path expectedFilePath = config.exportDocuments().resolve(expectedFileName);
            String expectedText = parsePdfFile(expectedFilePath);

            Path downloadedFilePath = config.browserDownloads();

            pdfFileName = getExportedPdfFileName();

            downloadedFilePath = downloadedFilePath.resolve(pdfFileName);
            String downloadedText = parsePdfFile(downloadedFilePath);

            downloadedText =
                    downloadedText.replaceAll(
                            ".*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}).*|.*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}.*[0-9]{2}:[0-9]{2}:[0-9]{2})|([0-9]{2}:[0-9]{2}:[0-9]{2})",
                            "");
            expectedText =
                    expectedText.replaceAll(
                            ".*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}).*|.*([0-9]{2}\\/[0-9]{2}\\/[0-9]{4}.*[0-9]{2}:[0-9]{2}:[0-9]{2})|([0-9]{2}:[0-9]{2}:[0-9]{2})",
                            "");
            assertEquals("Exported Rar is different to expected", expectedText, downloadedText);
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
        }
    }

    private String parsePdfFile(final Path filePath) throws IOException {

        URL pdf = filePath.toUri().toURL();
        PDFTextStripper pdfStripper = new PDFTextStripper();

        PDFParser downloadedParser;
        try (BufferedInputStream downloadedFile = new BufferedInputStream(pdf.openStream());
             RandomAccessBuffer randomAccessBuffer = new RandomAccessBuffer(downloadedFile)) {
            downloadedParser = new PDFParser(randomAccessBuffer);

            downloadedParser.parse();
            COSDocument cosDoc = downloadedParser.getDocument();

            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(3);

            PDDocument pdDoc = new PDDocument(cosDoc);

            return pdfStripper.getText(pdDoc);
        }
    }

    public void ShouldHaveNameMatchingPattern() {
        String pdfFileName = null;

        pdfFileName = getExportedPdfFileName();
        Pattern pattern = Pattern.compile("^RAR_for_2999_ECR_MKIR_at_20170910_on_\\d{8}_\\d{6}.pdf$");
        Matcher matcher = pattern.matcher(pdfFileName);
        boolean isMatch = matcher.matches();
        assertTrue(isMatch);
    }

    public String getExportedPdfFileName() {
        String pdfFileName = null;

        Path downloadedFilePath = config.browserDownloads();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(downloadedFilePath)) {
            for (Path file : stream) {
                if (file.getFileName().toString().startsWith("RAR_")) {
                    pdfFileName = file.getFileName().toString();
                    break;
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
        return pdfFileName;
    }
}

