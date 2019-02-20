package com.lombardrisk.page.analysismodule.grid;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.collect.ImmutableMap;
import com.lombardrisk.page.SuppliedAssertionError;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CELL_DESCRIPTION_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CELL_REFERENCE_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CURRENT_PERIOD_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.DIFFERENCE_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.PERCENT_DIFFERENCE_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.PREVIOUS_PERIOD_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.cellReferenceColumnRows;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnDifferenceMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnHeaderMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findCellByColId;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findColIdByHeaderName;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findColumnHeaderByColId;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findPinnedCellByColId;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstCellReferenceColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstDescriptionColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstTotalCellReferenceColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstTotalTypeOfChangeColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstTypeOfChangeColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.sortableCellReferenceColumnRows;
import static java.lang.Integer.parseInt;
import static java.lang.String.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class VarianceAnalysisPage {

    private final static List<SelenideElement> openCellDetails = $$(".mat-expansion-panel");

    private static final String CELL_DATE = "date";
    private static final String CELL_VALUE = "value";
    private static final String CELL_DIFFERENCE = "difference";
    private static final String CELL_PERCENT_DIFFERENCE = "percent_difference";

    private static final String CELL_DATE_HEADER = "date";
    private static final String CELL_VALUE_HEADER = "value";
    private static final String CELL_DIFFERENCE_HEADER = "difference";
    private static final String CELL_PERCENT_DIFFERENCE_HEADER = "percent_difference";

    private static final Map<String, String> CELL_DETAILS_COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(CELL_DATE_HEADER, "referenceDate")
                    .put(CELL_VALUE_HEADER, "value")
                    .put(CELL_DIFFERENCE_HEADER, "0")
                    .put(CELL_PERCENT_DIFFERENCE_HEADER, "1")
                    .build();

    private final Pattern EXTRACT_TOP_PROPERTY_PATTERN = Pattern.compile("^.*top:\\s*(\\d+).*$");

    private final GridPage gridPage;

    public VarianceAnalysisPage(final GridPage gridPage) {
        this.gridPage = gridPage;
    }

    private SelenideElement firstCurrentPeriodCell() {
        String colId = findColIdByHeaderName(CURRENT_PERIOD_HEADER);

        return findCellByColId(colId);
    }

    private SelenideElement firstTotalCurrentPeriodCell() {
        String colId = findColIdByHeaderName(CURRENT_PERIOD_HEADER);

        return findPinnedCellByColId(colId);
    }

    private SelenideElement firstPreviousPeriodCell() {
        String colId = findColIdByHeaderName(PREVIOUS_PERIOD_HEADER);

        return findCellByColId(colId);
    }

    private SelenideElement firstTotalPreviousPeriodCell() {
        String colId = findColIdByHeaderName(PREVIOUS_PERIOD_HEADER);

        return findPinnedCellByColId(colId);
    }

    private SelenideElement firstDifferenceCell() {
        String colId = findColIdByHeaderName(DIFFERENCE_HEADER);

        return findCellByColId(colId);
    }

    private SelenideElement firstTotalDifferenceCell() {
        String colId = findColIdByHeaderName(DIFFERENCE_HEADER);

        return findPinnedCellByColId(colId);
    }

    private SelenideElement firstPercentDifferenceCell() {
        String colId = findColIdByHeaderName(PERCENT_DIFFERENCE_HEADER);

        return findCellByColId(colId);
    }

    private Comparator<SelenideElement> byCssTopProperty(final boolean descendingOrder) {
        Comparator<SelenideElement> elementComparator = topPropertyValueComparator();

        return descendingOrder ? elementComparator : elementComparator.reversed();
    }

    private Comparator<SelenideElement> topPropertyValueComparator() {
        return (firstElement, secondElement) ->
                findTopPropertyValue(firstElement)
                        .compareTo(
                                findTopPropertyValue(secondElement));
    }

    private Integer findTopPropertyValue(final SelenideElement firstElement) {
        firstElement.shouldHave(attribute("style"));

        String firstElementInlineStyle = firstElement.attr("style");
        assertThat(firstElementInlineStyle)
                .as("style contains 'top' property for element%n%s", firstElement)
                .contains("top");

        return toTopPropertyValue(firstElementInlineStyle);
    }

    private Integer toTopPropertyValue(final String elementInlineStyle) {
        Matcher topValueMatcher = EXTRACT_TOP_PROPERTY_PATTERN.matcher(elementInlineStyle);

        if (topValueMatcher.find()) {
            String topValue = topValueMatcher.group(1);

            return parseInt(topValue);
        } else {
            throw new AssertionError(format("style %s does not contain top css property", elementInlineStyle));
        }
    }

    public void shouldHaveDiffs(final Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        periodDiffsByCellReference.entrySet()
                .forEach(this::shouldHaveDiff);
    }

    private void shouldHaveDiff(final Map.Entry<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        CellReference cellReference = periodDiffsByCellReference.getKey();
        gridPage.filterGridByCellReference(cellReference.getName());

        firstCellReferenceColumnCell().shouldHave(
                text(cellReference.getName())
                        .because(columnMsg(CELL_REFERENCE_HEADER)));
        firstDescriptionColumnCell().shouldHave(
                text(cellReference.getDescription())
                        .because(columnMsg(CELL_DESCRIPTION_HEADER)));

        PeriodDiff periodDiff = periodDiffsByCellReference.getValue().get(0);

        String startDate = periodDiff.getStartDate();
        SelenideElement startDateHeader = findColumnHeaderByColId(1);
        startDateHeader.shouldHave(
                text(startDate)
                        .because(columnHeaderMsg(startDate)));

        firstPreviousPeriodCell().shouldHave(
                text(periodDiff.getStartValue())
                        .because(columnMsg(startDate)));

        String endDate = periodDiff.getEndDate();
        SelenideElement endDateHeader = findColumnHeaderByColId(2);
        endDateHeader.shouldHave(
                text(endDate)
                        .because(columnHeaderMsg(endDate)));

        firstCurrentPeriodCell().shouldHave(
                text(periodDiff.getEndValue())
                        .because(columnMsg(endDate)));

        firstDifferenceCell().shouldHave(
                text(periodDiff.getDifference())
                        .because(columnDifferenceMsg(endDate)));

        firstPercentDifferenceCell().shouldHave(
                text(periodDiff.getPercentDifference())
                        .because(columnDifferenceMsg(endDate)));
    }

    public void shouldHaveAllocationCellDiffs(final Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        periodDiffsByCellReference.entrySet()
                .forEach(this::shouldHaveAllocationCellDiff);
    }

    private void shouldHaveAllocationCellDiff(final Map.Entry<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        CellReference cellReference = periodDiffsByCellReference.getKey();
        gridPage.filterGridByCellReference(cellReference.getName());

        firstCellReferenceColumnCell().shouldHave(
                text(cellReference.getName())
                        .because(columnMsg(CELL_REFERENCE_HEADER)));

        PeriodDiff periodDiff = periodDiffsByCellReference.getValue().get(0);

        String startDate = periodDiff.getStartDate();
        SelenideElement startDateHeader = findColumnHeaderByColId(1);
        startDateHeader.shouldHave(
                text(startDate)
                        .because(columnHeaderMsg(startDate)));

        firstPreviousPeriodCell().shouldHave(
                text(periodDiff.getStartValue())
                        .because(columnMsg(startDate)));

        String endDate = periodDiff.getEndDate();
        SelenideElement endDateHeader = findColumnHeaderByColId(2);
        endDateHeader.shouldHave(
                text(endDate)
                        .because(columnHeaderMsg(endDate)));

        firstCurrentPeriodCell().shouldHave(
                text(periodDiff.getEndValue())
                        .because(columnMsg(endDate)));

        firstDifferenceCell().shouldHave(
                text(periodDiff.getDifference())
                        .because(columnDifferenceMsg(endDate)));

        firstTypeOfChangeColumnCell().shouldHave(
                text(periodDiff.getTypeOfChange())
                        .because(columnDifferenceMsg(endDate)));
    }

    public void filterBy(final String headerName, final String filterCondition, final String cellReference) {
        gridPage.filterBy(headerName, filterCondition, cellReference);
    }

    public void sortDescending(final String headerName) {
        gridPage.sortDescending(headerName);
    }

    public void shouldHaveGridSize(final int rowCount) {
        cellReferenceColumnRows().shouldHaveSize(rowCount);
    }

    public void shouldHaveOrderedRows(final List<String> expectedCellReferences, final boolean descendingSortOrder) {
        if (descendingSortOrder == true) {
            gridPage.cellReferenceHeader().click();
        } else {
            gridPage.cellReferenceHeader().doubleClick();
        }
        ElementsCollection cellReferenceColumnRows = sortableCellReferenceColumnRows();
        cellReferenceColumnRows.shouldHaveSize(expectedCellReferences.size());

        List<String> sortedExpectedCellReferences = new ArrayList<String>(expectedCellReferences);

        List<String> sortedCellReferences =
                cellReferenceColumnRows.stream()
                        .map(SelenideElement::text)
                        .collect(toList());
        Collections.sort(sortedExpectedCellReferences);
        Collections.sort(sortedCellReferences);
        assertThat(sortedCellReferences).containsSequence(sortedExpectedCellReferences);
    }

    public void openCommentsDialog() {
        $(By.id("qa-return-comments-btn")).shouldBe(visible);
        $(By.id("qa-return-comments-btn")).shouldBe(enabled)
                .click();
        $("app-return-comments-manager-dialog").shouldBe(visible);
    }

    public void viewCellComments(int rowIndex) {

        gridPage.findRowByRowIndex(rowIndex).$("app-cell-comment-count-renderer a").click();
    }

    public void shouldHaveAllocationTotalCellDiffs(final Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        periodDiffsByCellReference.entrySet()
                .forEach(this::shouldHaveAllocationTotalCellDiff);
    }

    private void shouldHaveAllocationTotalCellDiff(final Map.Entry<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        CellReference cellReference = periodDiffsByCellReference.getKey();
        gridPage.filterGridByCellReference(cellReference.getName());

        firstTotalCellReferenceColumnCell().shouldHave(
                text(cellReference.getName())
                        .because(columnMsg(CELL_REFERENCE_HEADER)));

        PeriodDiff periodDiff = periodDiffsByCellReference.getValue().get(0);

        String startDate = periodDiff.getStartDate();
        SelenideElement startDateHeader = findColumnHeaderByColId(1);
        startDateHeader.shouldHave(
                text(startDate)
                        .because(columnHeaderMsg(startDate)));

        firstTotalPreviousPeriodCell().shouldHave(
                text(periodDiff.getStartValue())
                        .because(columnMsg(startDate)));

        String endDate = periodDiff.getEndDate();
        SelenideElement endDateHeader = findColumnHeaderByColId(2);
        endDateHeader.shouldHave(
                text(endDate)
                        .because(columnHeaderMsg(endDate)));

        firstTotalCurrentPeriodCell().shouldHave(
                text(periodDiff.getEndValue())
                        .because(columnMsg(endDate)));

        firstTotalDifferenceCell().shouldHave(
                text(periodDiff.getDifference())
                        .because(columnDifferenceMsg(endDate)));

        firstTotalTypeOfChangeColumnCell().shouldHave(
                text(periodDiff.getTypeOfChange())
                        .because(columnDifferenceMsg(endDate)));
    }

    public void openRar() {
        $(By.id("qa-rar-btn")).shouldBe(enabled).click();
    }

    public void shouldClickOnOpenCellDetails() {
        openCellDetails.get(2).click();
    }

    private static SelenideElement findCellDetailsByColId(final String colId) {
        return topLayerCellDetailsBody()
                .$("div[col-id='" + colId + "']");
    }

    private static SelenideElement topLayerCellDetailsBody() {
        SelenideElement topLayerGrid = $$("app-comment-card mat-expansion-panel ag-grid-angular").get(0);
        return topLayerGrid.$$("div.ag-row").get(2);
    }

    static String findCellDetailsColIdByHeaderName(final String headerName) {
        String colId = CELL_DETAILS_COL_ID_BY_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    private static Supplier<String> toMissingHeaderMappingMessage(final String headerName) {
        return () -> {
            String mappedHeaders = CELL_DETAILS_COL_ID_BY_HEADER_NAME.entrySet().stream()
                    .map(entry -> String.format("  '%s' = '%s'%n", entry.getKey(), entry.getValue()))
                    .collect(joining());

            return String.format(
                    "Header with name [%s] does not have a mapping.%nCurrent mappings: %n%s",
                    headerName, mappedHeaders);
        };
    }

    public void shouldHaveOpenCellDetails(final List<List<String>> dataTable) {
        String date = dataTable.get(0).get(1);
        gridPage.filterCellDetailsGridByDate(date);

        findCellDetailsByColId(findCellDetailsColIdByHeaderName(CELL_DATE)).shouldHave(text(date).because(
                columnMsg(CELL_DATE)));
        String cell_value = dataTable.get(1).get(1);
        findCellDetailsByColId(findCellDetailsColIdByHeaderName(CELL_VALUE)).shouldHave(text(cell_value).because(
                columnMsg(CELL_VALUE)));
        String cell_difference = dataTable.get(2).get(1);
        findCellDetailsByColId(findCellDetailsColIdByHeaderName(CELL_DIFFERENCE)).shouldHave(text(cell_difference).because(
                columnMsg(CELL_DIFFERENCE)));
        String cell_percent_difference = dataTable.get(3).get(1);
        findCellDetailsByColId(findCellDetailsColIdByHeaderName(CELL_PERCENT_DIFFERENCE)).shouldHave(text(
                cell_percent_difference).because(
                columnMsg(CELL_PERCENT_DIFFERENCE)));
    }
}
