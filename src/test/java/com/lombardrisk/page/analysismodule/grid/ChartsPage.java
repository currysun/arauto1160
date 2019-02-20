package com.lombardrisk.page.analysismodule.grid;

import com.codeborne.selenide.SelenideElement;
import com.google.common.base.MoreObjects;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.SuppliedAssertionError;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import com.lombardrisk.stepdef.analysismodule.grid.ChartViewType;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;
import static com.lombardrisk.page.analysismodule.grid.GridPage.ANALYSIS_MODULE_AMOUNT_FORMATTER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CELL_REFERENCE_HEADER;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnHeaderMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findCellByColId;
import static com.lombardrisk.page.analysismodule.grid.GridPage.findColumnHeaderByColId;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public class ChartsPage {

    private static final String START_DATE_LABELS_KEY = "startDateLabels";
    private static final String DATASETS_KEY = "datasets";
    private static final String CELL_LABEL_KEY = "cellLabel";
    private static final String START_VALUES_KEY = "startValues";

    private static final String CHART_DATA_JS_FUNCTION_BODY =
            "let charts = Object.values(window.Chart.instances);" +
                    "let lastChart = charts[charts.length - 1];" +
                    "return {" +
                    START_DATE_LABELS_KEY + ": lastChart.config.data.labels," +
                    DATASETS_KEY + ": lastChart.config.data.datasets.map(" +
                    "    dataset => ({"
                    + CELL_LABEL_KEY + ": dataset.label, "
                    + START_VALUES_KEY + ": dataset.data"
                    + "})" +
                    ")}";

    private final SelenideElement chartFullScreenButton = $(By.xpath("//app-analysis-chart-controls/div/mat-icon"));
    private final SelenideElement chartFullScreenTitle = $(By.xpath("//*[@class='mat-dialog-title']"));

    private final SelenideElement fullScreenCloseButton = $(By.xpath("//app-analysis-chart-modal//mat-toolbar/button"));
    private final SelenideElement fullScreenGraph = $(By.xpath("//app-full-screen-chart/app-chart/div/canvas"));
    private final SelenideElement gridViewGraph = $(By.xpath("//app-analysis-chart/app-chart/div/canvas"));

    private final GridPage gridPage;
    private final AnalysisModuleWaiter analysisModuleWaiter;
    private final Config config;

    public ChartsPage(
            final Config config,
            final GridPage gridPage,
            final AnalysisModuleWaiter analysisModuleWaiter) {
        this.config = requireNonNull(config);
        this.gridPage = requireNonNull(gridPage);
        this.analysisModuleWaiter = requireNonNull(analysisModuleWaiter);
    }

    public List<Integer> findUncheckedVisibleRowIndices() {
        SelenideElement gridContainer = $x("//app-grid");
        gridContainer.shouldBe(visible);

        Optional<String> optionalAvailableRowIds =
                Optional.ofNullable(gridContainer.getAttribute("uncheckedvisible"));

        return optionalAvailableRowIds
                .map(ChartsPage::splitToList)
                .orElseThrow(() -> new AssertionError(
                        gridContainer + "\n  does not have the attribute 'uncheckedvisible'"));
    }

    private static List<Integer> splitToList(final String availableRowIds) {
        return Arrays.stream(availableRowIds.split(","))
                .map(Integer::valueOf)
                .collect(toList());
    }

    public List<Integer> findCheckedVisibleRowIndices() {
        return $$(By.xpath("//div[@class='ag-body ag-row-no-animation']//div[@col-id='checkbox']"
                + "/span/span/span[@class='ag-icon ag-icon-checkbox-checked']"
                + "/../../../.."))
                .stream()
                .map(cell -> cell.getAttribute("row-index"))
                .map(Integer::valueOf)
                .collect(toList());
    }

    public void checkCell(final int rowIndex) {
        SelenideElement cellReferenceCheckbox = $x("//div[@row-index='" + rowIndex
                + "']//div[@col-id='checkbox']//span[@class='ag-icon ag-icon-checkbox-unchecked']");
        executeJavaScript("arguments[0].click();", cellReferenceCheckbox);

        sleep(config.minTimeout());
    }

    public void uncheckCell(final int rowIndex) {
        SelenideElement cellReferenceCheckbox = findCheckboxByRow(rowIndex);

        executeJavaScript("arguments[0].click();", cellReferenceCheckbox);

        sleep(config.minTimeout());
    }

    public SelenideElement findCheckboxByRow(final int rowIndex) {
        return $x("//div[@row-index='" + rowIndex
                + "']//div[@col-id='checkbox']//span[@class='ag-icon ag-icon-checkbox-checked']");
    }

    public void openFullScreen() {
        chartFullScreenButton.click();
        analysisModuleWaiter.waitForTransitionAnimation();

        chartFullScreenTitle.shouldBe(visible);
    }

    public void selectView(final ChartViewType chartViewType) {
        SelenideElement viewRadioButton = $$x("//app-analysis-chart-controls/div/"
                + "mat-radio-group/mat-radio-button[@value='" + chartViewType.selectValueName() + "']").last();
        viewRadioButton.click();
        analysisModuleWaiter.waitForTransitionAnimation();

        viewRadioButton.shouldHave(cssClass("mat-radio-checked"));
    }

    public void exportType(final String exportType) {
        SelenideElement chartExportButton = $$x("//app-export-chart/button").last();
        chartExportButton.click();

        SelenideElement exportTypeMenuItem =
                $(By.xpath("//div[@role='menu']//button[contains(text(),'" + exportType + "')]"));

        exportTypeMenuItem.click();
        analysisModuleWaiter.waitForQuickAnimation();
    }

    public ChartsPage closeFullScreenChart() {
        fullScreenCloseButton.shouldBe(visible);
        fullScreenCloseButton.click();
        analysisModuleWaiter.waitForTransitionAnimation();

        chartFullScreenTitle.shouldNotBe(visible);
        return this;
    }

    public void shouldNotHaveVisibleFullScreenGraph() {
        fullScreenGraph.shouldNotBe(visible);
    }

    public void shouldHaveVisibleGridAndGraph() {
        gridViewGraph.shouldBe(visible);
        gridPage.shouldBeVisible();
    }

    public void shouldHaveValueViewChart(final List<Integer> selectedRowIds) {
        Map<String, Object> chartData = findLastChartData();

        List<ChartCellValueView> chartCellValueViews = toChartCellValueViews(chartData);

        assertNumberOfGraphedCells(selectedRowIds, chartCellValueViews);

        List<String> chartStartDates = toStartDates(chartData);

        int colIdOffset = 1;
        for (int periodIndex = 0; periodIndex < chartStartDates.size(); periodIndex++) {
            String chartStartDate = chartStartDates.get(periodIndex);
            gridPage.scrollToHeaderWithId(colIdOffset);

            findColumnHeaderByColId(colIdOffset)
                    .shouldBe(visible.because(columnHeaderMsg(chartStartDate)));

            shouldHaveStartValuesForEachPeriod(chartCellValueViews, colIdOffset, periodIndex, chartStartDate);
            colIdOffset = colIdOffset + 3;
        }
        gridPage.resetCellReferenceFilter();
    }

    private void shouldHaveStartValuesForEachPeriod(
            final List<ChartCellValueView> chartCellValueViews,
            final int colIdOffset,
            final int periodIndex,
            final String chartStartDate) {

        for (ChartCellValueView chartCellValueView : chartCellValueViews) {
            gridPage.filterBy(CELL_REFERENCE_HEADER, "Starts with", chartCellValueView.cellReference);

            findCellByColId(colIdOffset)
                    .shouldHave(text(chartCellValueView.startValues.get(periodIndex))
                            .because(columnMsg(chartStartDate)));
        }
    }

    private static Map<String, Object> findLastChartData() {
        return executeJavaScript(CHART_DATA_JS_FUNCTION_BODY);
    }

    @SuppressWarnings("unchecked")
    private static List<String> toStartDates(final Map<String, Object> chartDataObj) {
        Object labels = chartDataObj.get(START_DATE_LABELS_KEY);

        if (labels != null && labels instanceof List) {
            List<String> startDates = (List<String>) labels;

            if (!startDates.isEmpty()) {
                return startDates;
            }
        }
        throw unexpectedChartDataError(labels);
    }

    @SuppressWarnings("unchecked")
    private static List<ChartCellValueView> toChartCellValueViews(
            final Map<String, Object> chartDataObj) {
        Object datasets = chartDataObj.get(DATASETS_KEY);

        if (datasets != null && datasets instanceof List) {
            List<Map<String, Object>> startValuesAndCell = (List<Map<String, Object>>) datasets;

            return startValuesAndCell.stream()
                    .map(ChartsPage::toChartCellValueView)
                    .collect(toList());
        }
        throw unexpectedChartDataError(datasets);
    }

    private static ChartCellValueView toChartCellValueView(final Map<String, Object> cellAndStartValues) {
        return new ChartCellValueView(
                (String) cellAndStartValues.get(CELL_LABEL_KEY),
                toStartValues(cellAndStartValues));
    }

    @SuppressWarnings("unchecked")
    private static List<String> toStartValues(final Map<String, Object> startValuesAndCell) {
        Object startValuesArray = startValuesAndCell.get(START_VALUES_KEY);

        if (startValuesArray != null && startValuesArray instanceof List) {
            List<Number> startValues = (List<Number>) startValuesArray;

            return startValues.stream()
                    .map(ANALYSIS_MODULE_AMOUNT_FORMATTER::format)
                    .map(Objects::toString)
                    .map(ChartsPage::anyNegativeToBracketFormat)
                    .collect(toList());
        }
        throw unexpectedChartDataError(startValuesArray);
    }

    private static String anyNegativeToBracketFormat(final String startValue) {
        return startValue.startsWith("-")
                ? "(" + startValue.substring(1) + ")"
                : startValue;
    }

    private void assertNumberOfGraphedCells(
            final List<Integer> selectedRowIds, final List<ChartCellValueView> chartCellValueViews) {
        List<Integer> checkedVisibleRowIndices = findCheckedVisibleRowIndices();

        assertThat(checkedVisibleRowIndices)
                .as("Number of graphed cells is different than the checked cell rows%n%s", chartCellValueViews)
                .hasSameSizeAs(chartCellValueViews);
        assertThat(checkedVisibleRowIndices)
                .as("Number of graphed cells changed:%n%s", chartCellValueViews)
                .containsExactlyInAnyOrder(selectedRowIds.toArray(EMPTY_INTEGER_OBJECT_ARRAY));
    }

    private static SuppliedAssertionError unexpectedChartDataError(final Object object) {
        return new SuppliedAssertionError(
                () -> String.format("Unexpected start date labels from Chart.js library:%n%s", object));
    }

    private static class ChartCellValueView {

        private final String cellReference;
        private final List<String> startValues;

        ChartCellValueView(final String cellReference, final List<String> startValues) {
            this.cellReference = cellReference;
            this.startValues = startValues;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("cellReference", cellReference)
                    .add("startValues", startValues)
                    .toString();
        }
    }
}
