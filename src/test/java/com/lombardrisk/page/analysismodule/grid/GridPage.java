package com.lombardrisk.page.analysismodule.grid;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.collect.ImmutableMap;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.SuppliedAssertionError;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import org.openqa.selenium.By;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Supplier;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class GridPage {

    static final String CELL_REFERENCE_HEADER = "Cell Reference";
    static final String CELL_DESCRIPTION_HEADER = "Cell Description";
    static final String TYPE_OF_CHANGE_HEADER = "TYPE OF CHANGE";
    static final String PREVIOUS_PERIOD_HEADER = "Previous Period";
    static final String CURRENT_PERIOD_HEADER = "Current Period";
    static final String DIFFERENCE_HEADER = "Difference";
    static final String PERCENT_DIFFERENCE_HEADER = "%Difference";
    static final String CELL_REFERENCE_COL = "CELL REFERENCE";
    static final String CELL_DESCRIPTION_COL = "CELL DESCRIPTION";
    static final String TREND_DATE_HEADER = "DATE";
    static final String TREND_VALUE_HEADER = "VALUE";
    static final String RAR_DATE_ADDED = "Date Added";
    private static final String RAR_TYPE = "Type";
    private static final String SCROLL_GRID_LEFT_JS =
            "var grids = document.getElementsByClassName('ag-body-viewport');"
                    + "grids[0].scrollLeft %s %d";
    private static final String SCROLL_RAR_CELL_COMMENT_GRID_LEFT_JS =
            "var grids = document.querySelectorAll('app-cell-audit-grid .ag-body-viewport');"
                    + "grids[0].scrollLeft %s %d";
    private static final String SCROLL_GRID_TOP_JS =
            "var grids = document.getElementsByClassName('ag-body-viewport');"
                    + "grids[grids.length - 1].scrollTop %s %d";
    private static final Map<String, String> COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(CELL_REFERENCE_HEADER, "0")
                    .put(CELL_DESCRIPTION_HEADER, "cell.description")
                    .put(PREVIOUS_PERIOD_HEADER, "1")
                    .put(CURRENT_PERIOD_HEADER, "2")
                    .put(DIFFERENCE_HEADER, "3")
                    .put(PERCENT_DIFFERENCE_HEADER, "4")
                    .put(TYPE_OF_CHANGE_HEADER, "4")
                    .build();

    private static final Map<String, String> TRENDS_COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(TREND_DATE_HEADER, "referenceDate")
                    .put(TREND_VALUE_HEADER, "value")
                    .put(DIFFERENCE_HEADER, "0")
                    .put(PERCENT_DIFFERENCE_HEADER, "1")
                    .build();

    private static final Map<String, String> RAR_CELL_COMMENTS_COL_ID_BY_HEADER_NAME =
            new ImmutableMap.Builder<String, String>()
                    .put(CELL_REFERENCE_HEADER, "0")
                    .put(RAR_DATE_ADDED, "createdTimestamp")
                    .put(RAR_TYPE, "type")
                    .build();

    public static final DateTimeFormatter AM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, YYYY, HH:mm");
    public static final DateTimeFormatter AM_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    public static final DecimalFormat ANALYSIS_MODULE_AMOUNT_FORMATTER = new DecimalFormat("###,##0.00");

    private final SelenideElement mainExportButton = $(".export");

    private final AnalysisModuleWaiter analysisModuleWaiter;
    private final Config config;

    public GridPage(final AnalysisModuleWaiter analysisModuleWaiter, final Config config) {
        this.analysisModuleWaiter = analysisModuleWaiter;
        this.config = config;
    }

    private static void scrollGrid(final int scrollAmountInPixels, final String scrollJs) {
        String unaryOp = "=";
        if (scrollAmountInPixels < 0) {
            unaryOp = "-=";
        }
        if (scrollAmountInPixels > 0) {
            unaryOp = "+=";
        }
        executeJavaScript(
                String.format(scrollJs, unaryOp, scrollAmountInPixels));
    }

    private static String toDrilldownDialogHeader(final String drilldownCellReference) {
        return drilldownCellReference
                .replace("(Subtotal)", "Drilldown")
                .trim();
    }

    private static SelenideElement topLayerDrilldownHeader() {
        return $$x("//app-dialog-header").last();
    }

    private static SelenideElement topLayerExportButton() {
        return $$(".export").last();
    }

    static SelenideElement firstCellReferenceColumnCell() {
        String cellReferenceColId = findColIdByHeaderName(CELL_REFERENCE_HEADER);

        return findCellByColId(cellReferenceColId);
    }

    static SelenideElement firstTotalCellReferenceColumnCell() {
        String cellReferenceColId = findColIdByHeaderName(CELL_REFERENCE_HEADER);

        return findPinnedCellByColId(cellReferenceColId);
    }

    static SelenideElement firstDescriptionColumnCell() {
        String descriptionColId = findColIdByHeaderName(CELL_DESCRIPTION_HEADER);

        return findCellByColId(descriptionColId);
    }

    static SelenideElement firstTypeOfChangeColumnCell() {
        String typeOfChangeColId = findColIdByHeaderName(TYPE_OF_CHANGE_HEADER);

        return findCellByColId(typeOfChangeColId);
    }

    static SelenideElement firstTotalTypeOfChangeColumnCell() {
        String typeOfChangeColId = findColIdByHeaderName(TYPE_OF_CHANGE_HEADER);

        return findPinnedCellByColId(typeOfChangeColId);
    }

    static SelenideElement findCellByColId(final String colId) {
        return topLayerGridBody()
                .$x("*//div[@col-id='" + colId + "']");
    }

    static SelenideElement findPinnedCellByColId(final String colId) {
        return topLayerPinnedRow()
                .$x("*//div[@col-id='" + colId + "']");
    }

    static SelenideElement findCellByColId(final int colId) {
        return findCellByColId(String.valueOf(colId));
    }

    static ElementsCollection cellReferenceColumnRows() {
        String cellReferenceColdId = findColIdByHeaderName(CELL_REFERENCE_HEADER);

        return topLayerGridBody()
                .$$x("*//div[@col-id='" + cellReferenceColdId + "']");
    }

    static ElementsCollection rightGridCellReferenceColumnRows() {

        return rightGrid()
                .$$("div.ag-cell[col-id='cell.name']");
    }

    static ElementsCollection sortableCellReferenceColumnRows() {
        return $$x("//div[@class='ag-pinned-left-cols-container']").last()
                .$$x("*//div/..");
    }

    static SelenideElement findColumnHeaderByColId(final int colId) {
        return findColumnHeaderByColId(String.valueOf(colId));
    }

    static SelenideElement findColumnHeaderByColId(final String colId) {
        ElementsCollection cols = $$(By.xpath("//div[@col-id='" + colId + "']//span[@ref='eText']"));
        return cols.get(0);
    }

    public SelenideElement cellReferenceHeader() {
        return findColumnHeaderByColId(COL_ID_BY_HEADER_NAME.get(CELL_REFERENCE_HEADER));
    }

    private SelenideElement findRarCellCommentColumnHeaderByColId(final String colId) {
        return $("app-cell-audit-grid div[col-id='" + colId + "'] span[ref='eText']");
    }

    static String columnMsg(final String value) {
        return "column " + value;
    }

    static String columnHeaderMsg(final String headerName) {
        return String.format("column %s (header)", headerName);
    }

    static String columnDifferenceMsg(final String date) {
        return String.format("Difference (after %s)", date);
    }

    static String columnPercentDifferenceMsg(final String date) {
        return String.format("\\u0025 Difference (after %s)", date);
    }

    private static SelenideElement topLayerGridBody() {
        return $$x("//div[@class='ag-body ag-row-no-animation']").last();
    }

    private static SelenideElement topLayerPinnedRow() {
        return $$x("//div[@class='ag-floating-top']").last();
    }

    private static SelenideElement leftGrid() {
        return $("app-cell-group-cells-grid.left");
    }

    private static SelenideElement rightGrid() {
        return $("app-cell-group-cells-grid.right");
    }

    private static SelenideElement filterTab() {
        return $(By.xpath("//div[@id='tabHeader']//span[@class='ag-icon ag-icon-filter']"));
    }

    private static SelenideElement filterTypeDropdown() {
        if (!$(By.id("filterType")).is(visible)) {
            return $("#ag-mini-filter");
        } else {
            return $(By.id("filterType"));
        }

    }

    private static SelenideElement filterTextInput() {
        return $(By.id("filterText"));
    }

    private static SelenideElement filterDateInput() {
        return $("#filterDateFromPanel input");
    }

    static String findColIdByHeaderName(final String headerName) {
        String colId = COL_ID_BY_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    static String findRarColIdByHeaderName(final String headerName) {
        String colId = RAR_CELL_COMMENTS_COL_ID_BY_HEADER_NAME.get(headerName);

        if (colId == null) {
            throw new SuppliedAssertionError(toMissingHeaderMappingMessage(headerName));
        }
        return colId;
    }

    static String findTrendsColIdByHeaderName(final String headerName) {
        String colId = TRENDS_COL_ID_BY_HEADER_NAME.get(headerName);

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

    public void shouldBeVisible() {
        mainExportButton.shouldBe(visible);
        analysisModuleWaiter.waitForFetch();
        cellReferenceColumnRows()
                .shouldHave(sizeGreaterThan(0));
    }

    public void export(final ExportType exportType) {
        topLayerExportButton().click();
        sleep(config.minTimeout());

        SelenideElement exportTypeMenuItem =
                $(By.xpath("//div[@role='menu']//button[contains(text(),'" + exportType.name() + "')]"));

        exportTypeMenuItem.click();
        sleep(config.minTimeout());
    }

    void resetTopLayerGridScroll() {
        scrollGrid(0, SCROLL_GRID_LEFT_JS);
        scrollGrid(0, SCROLL_GRID_TOP_JS);
        sleep(config.minTimeout());
    }

    void resetRarCellCommentsGridScroll() {
        scrollGrid(0, SCROLL_RAR_CELL_COMMENT_GRID_LEFT_JS);
        scrollGrid(0, SCROLL_RAR_CELL_COMMENT_GRID_LEFT_JS);
    }

    SelenideElement scrollToHeaderWithId(final int colId) {
        return scrollToHeaderWithId(String.valueOf(colId));
    }

    public SelenideElement scrollToHeaderWithId(final String colId) {
        SelenideElement header = findColumnHeaderByColId(colId);

        analysisModuleWaiter.waitForVisibleAndDo(
                header,
                __ -> scrollRightInGrid());

        header.shouldBe(visible);
        return header;
    }

    SelenideElement scrollToRarCellCommentHeaderWithId(final int colId) {
        return scrollToRarCellCommentHeaderWithId(String.valueOf(colId));
    }

    public SelenideElement scrollToRarCellCommentHeaderWithId(final String colId) {
        SelenideElement header = findRarCellCommentColumnHeaderByColId(colId);

        analysisModuleWaiter.waitForVisibleAndDo(
                header,
                __ -> scrollRightInRarCellCommentGrid());

        header.shouldBe(visible);
        return header;
    }

    SelenideElement scrollToColumnCell(final int colId) {
        SelenideElement columnCell = findCellByColId(colId);

        analysisModuleWaiter.waitForVisibleAndDo(
                columnCell,
                ignored -> scrollRightInGrid());

        columnCell.shouldBe(visible);
        return columnCell;
    }

    private void scrollRightInGrid() {
        scrollGrid(200, SCROLL_GRID_LEFT_JS);

        sleep(config.minTimeout());
    }

    private void scrollRightInRarCellCommentGrid() {
        scrollGrid(200, SCROLL_RAR_CELL_COMMENT_GRID_LEFT_JS);

    }

    public GridPage drilldownInCell(final String cellReference) {
        filterGridByCellReference(cellReference);

        firstCellReferenceColumnCell()
                .$(By.tagName("a")).click();

        return this;
    }

    public void shouldHaveDrilldownHeader(final String drilldownCellReference) {
        String drilldownCellReferenceHeader = toDrilldownDialogHeader(drilldownCellReference);

        topLayerDrilldownHeader()
                .shouldHave(text(drilldownCellReferenceHeader));
    }

    public void filterGridByCellReference(final String cellReference) {

        filterBy(CELL_REFERENCE_HEADER, "Starts with", cellReference);

        cellReferenceColumnRows()
                .shouldHaveSize(1);
    }

    public void filterRarGridByCellReference(final String cellReference) {
        filterRarBy(CELL_REFERENCE_HEADER, "Starts with", cellReference);
    }

    public void filterRarGridBy(final String columnName) {
        resetRarCellCommentsGridScroll();
        filterRarBy(RAR_TYPE, "Starts with", columnName);
    }

    public void filterTrendsGridByDate(final String date) {
        filterTrendsBy(TREND_DATE_HEADER, "Equals", date);
    }

    public void filterCellDetailsGridByDate(final String date) {
        filterCellDetailsBy(TREND_DATE_HEADER, "Equals", date);
    }

    void filterBy(final String headerName, final String filterCondition, final String filterValue) {
        analysisModuleWaiter.waitForFetch();
        findTopLayerHeaderMenu(headerName).click();

        if (!filterTypeDropdown().isDisplayed()) {
            filterTab().click();
        }
        filterTypeDropdown().selectOption(filterCondition);
        filterTextInput().setValue(filterValue);

        filterTab().click();
    }

    void filterRarBy(final String headerName, final String filterCondition, final String filterValue) {
        analysisModuleWaiter.waitForFetch();
        findRarTopLayerHeaderMenu(headerName).shouldBe(visible).click();
        SelenideElement filterTab = filterTab();
        if (!filterTab.parent().shouldBe(visible).has(cssClass("ag-tab-selected"))) {
            filterTab.shouldBe(visible).click();
        }
        SelenideElement dropDown = filterTypeDropdown();

        if (dropDown.getAttribute("id").equalsIgnoreCase("ag-mini-filter")) {
            dropDown.$("input").shouldBe(visible).setValue(filterValue);
        } else {
            filterTypeDropdown().shouldBe(visible).selectOption(filterCondition);
            filterTextInput().shouldBe(visible).setValue(filterValue);
        }

        filterTab().shouldBe(visible).click();
    }

    void filterTrendsBy(final String headerName, final String filterCondition, final String filterValue) {
        analysisModuleWaiter.waitForFetch();
        findTrendsTopLayerHeaderMenu(headerName).click();

        if (!filterTypeDropdown().isDisplayed()) {
            filterTab().click();
        }
        filterTypeDropdown().selectOption(filterCondition);

        filterDateInput().sendKeys(filterValue);

        filterTab().click();
    }

    void filterCellDetailsBy(final String headerName, final String filterCondition, final String filterValue) {
        analysisModuleWaiter.waitForFetch();
        findCellDetailsTopLayerHeaderMenu(headerName).click();

        if (!filterTypeDropdown().isDisplayed()) {
            filterTab().click();
        }
        filterTypeDropdown().selectOption(filterCondition);

        filterDateInput().sendKeys(filterValue);

        filterTab().click();
    }

    public void filterRightGridByCellReference(final String cellReference, int size) {
        rightGridFilterBy(CELL_REFERENCE_HEADER, "Starts with", cellReference);

        rightGridCellReferenceColumnRows()
                .shouldHaveSize(size);
    }

    void rightGridFilterBy(final String headerName, final String filterCondition, final String filterValue) {
        analysisModuleWaiter.waitForFetch();
        findRightGridHeaderMenu(headerName).click();

        if (!filterTypeDropdown().isDisplayed()) {
            filterTab().click();
        }
        filterTypeDropdown().selectOption(filterCondition);
        filterTextInput().setValue(filterValue);

        filterTab().click();
    }

    public void openCommentsDialog() {
        $(By.id("qa-return-comments-btn")).shouldBe(visible);
        $(By.id("qa-return-comments-btn")).shouldBe(enabled)
                .click();
        $("app-return-comments-manager-dialog").shouldBe(visible);
    }

    void sortDescending(final String headerName) {
        String colId = findColIdByHeaderName(headerName);
        SelenideElement sortColumn = findColumnHeaderByColId(colId);

        sortColumn.click();
        sleep(config.minTimeout());

        sortColumn.click();
        sleep(config.minTimeout());

        SelenideElement sortTypeIcon = sortColumn.parent().$(".ag-sort-descending-icon");
        sortTypeIcon.shouldBe(visible);
    }

    private SelenideElement findTopLayerHeaderMenu(final String headerName) {
        String headerColId = findColIdByHeaderName(headerName);
        scrollToHeaderWithId(headerColId);

        return $$x("//div[@class='ag-header-row']//div[@col-id='" + headerColId + "']").last()
                .$x("*//span[@ref='eMenu']/span");
    }

    private SelenideElement findRarTopLayerHeaderMenu(final String headerName) {
        String headerColId = findRarColIdByHeaderName(headerName);
        scrollToRarCellCommentHeaderWithId(headerColId);

        SelenideElement headerMenu = $("app-cell-audit-grid div.ag-header-row div[col-id='" + headerColId + "']")
                .$x("*//span[@ref='eMenu']/span");

        return headerMenu;
    }

    private SelenideElement findRarTopLayerHeader(final String headerName) {
        String headerColId = findRarColIdByHeaderName(headerName);
        scrollToRarCellCommentHeaderWithId(headerColId);

        SelenideElement header = $("app-cell-audit-grid div.ag-header-row div[col-id='" + headerColId + "']");

        return header;
    }

    private SelenideElement findTrendsTopLayerHeaderMenu(final String headerName) {
        String headerColId = findTrendsColIdByHeaderName(headerName);
        scrollToHeaderWithId(headerColId);

        return $$x("//div[@class='ag-header-row']//div[@col-id='" + headerColId + "']").first()
                .$x("*//span[@ref='eMenu']/span");
    }

    private SelenideElement findCellTopLayerHeaderMenu(final String headerName) {
        String headerColId = findTrendsColIdByHeaderName(headerName);
        scrollToHeaderWithId(headerColId);

        return $$x("//div[@class='ag-header-row']//div[@col-id='" + headerColId + "']").last()
                .$x("*//span[@ref='eMenu']/span");
    }

    private SelenideElement findCellDetailsTopLayerHeaderMenu(final String headerName) {
        String headerColId = findTrendsColIdByHeaderName(headerName);

        return $("app-analysis-cell-instance-grid .ag-header-viewport .ag-header-row div[col-id='" + headerColId + "']")
                .$("span[ref='eMenu']");
    }

    private SelenideElement findRightGridHeaderMenu(final String headerName) {

        return rightGrid().$$x("//div[@class='ag-header-row']//div[@col-id='cell.name']").last()
                .$x("*//span[@ref='eMenu']/span");
    }

    public SelenideElement findRowByRowIndex(int rowIndex) {
        analysisModuleWaiter.waitForFetch();

        ElementsCollection grids = $$("app-grid");
        ElementsCollection gridBodies = grids.get(grids.size() - 1).$$("div.ag-body-container");
        SelenideElement grid = null;

        grid = gridBodies.get(gridBodies.size() - 1);

        ElementsCollection rows = grid.$$("div.ag-row[row-index=\"" + Integer.toString(rowIndex) + "\"]");

        return rows.get(rows.size() - 1);
    }

    public void resetCellReferenceFilter() {
        filterBy(CELL_REFERENCE_HEADER, "Not contains", EMPTY);
    }

    public void sortRarGridBy(final String headerName) {
        analysisModuleWaiter.waitForFetch();
        findRarTopLayerHeader(headerName).shouldBe(visible).click();
        findRarTopLayerHeader(headerName).shouldBe(visible).click();
        resetRarCellCommentsGridScroll();
    }
}
