package com.lombardrisk.page.analysismodule.grid;

import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CELL_DESCRIPTION_COL;
import static com.lombardrisk.page.analysismodule.grid.GridPage.CELL_REFERENCE_COL;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnDifferenceMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnHeaderMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.columnPercentDifferenceMsg;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstCellReferenceColumnCell;
import static com.lombardrisk.page.analysismodule.grid.GridPage.firstDescriptionColumnCell;
import static java.util.Objects.requireNonNull;

public class TrendsAnalysisPage {

    private final GridPage gridPage;

    public TrendsAnalysisPage(final GridPage gridPage) {
        this.gridPage = requireNonNull(gridPage);
    }

    public void shouldHaveDiffs(final Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        periodDiffsByCellReference.entrySet()
                .forEach(this::shouldHaveDiff);
    }

    private void shouldHaveDiff(final Map.Entry<CellReference, List<PeriodDiff>> periodDiffsByCellReference) {
        gridPage.resetTopLayerGridScroll();

        CellReference cellReference = periodDiffsByCellReference.getKey();
        gridPage.filterGridByCellReference(cellReference.getName());

        firstCellReferenceColumnCell().shouldHave(
                text(cellReference.getName())
                        .because(columnMsg(CELL_REFERENCE_COL))).shouldBe(visible);

        firstDescriptionColumnCell().shouldHave(
                text(cellReference.getDescription())
                        .because(columnMsg(CELL_DESCRIPTION_COL))).shouldBe(visible);

        shouldHavePeriodDiffs(periodDiffsByCellReference.getValue());
    }

    private void shouldHavePeriodDiffs(final List<PeriodDiff> periodDiffs) {
        int colId = 1;

        for (PeriodDiff periodDiff : periodDiffs) {
            String startDate = periodDiff.getStartDate();

            SelenideElement startDateHeader = gridPage.scrollToHeaderWithId(colId).shouldBe(visible);
            startDateHeader.shouldHave(
                    text(startDate)
                            .because(columnHeaderMsg(startDate)));

            SelenideElement startValueCell = gridPage.scrollToColumnCell(colId++).shouldBe(visible);
            startValueCell.shouldHave(
                    text(periodDiff.getStartValue())
                            .because(columnMsg(startDate)));

            SelenideElement differenceCell = gridPage.scrollToColumnCell(colId++).shouldBe(visible);
            differenceCell.shouldHave(
                    text(periodDiff.getDifference())
                            .because(columnDifferenceMsg(startDate)));

            SelenideElement differencePercentCell = gridPage.scrollToColumnCell(colId++).shouldBe(visible);
            differencePercentCell.shouldHave(
                    text(periodDiff.getPercentDifference())
                            .because(columnPercentDifferenceMsg(startDate)));

            SelenideElement endDateHeader = gridPage.scrollToHeaderWithId(colId).shouldBe(visible);
            endDateHeader.shouldHave(
                    text(periodDiff.getEndDate())
                            .because(columnHeaderMsg(periodDiff.getEndDate()))).shouldBe(visible);
        }

        PeriodDiff lastPeriodDiff = periodDiffs.get(periodDiffs.size() - 1);

        SelenideElement endValueCell = gridPage.scrollToColumnCell(colId).shouldBe(visible);
        endValueCell.shouldHave(
                text(lastPeriodDiff.getEndValue())
                        .because(columnMsg(lastPeriodDiff.getEndDate()))).shouldBe(visible);
    }

    public void viewCellComments(int rowIndex) {

        gridPage.scrollToHeaderWithId("comments");
        gridPage.findRowByRowIndex(rowIndex).$("app-cell-comment-count-renderer a").click();
    }

    public void viewCellComments(String cellRef) {
        gridPage.filterGridByCellReference(cellRef);
        gridPage.scrollToHeaderWithId("comments");
        SelenideElement row = gridPage.findRowByRowIndex(0);
        row.$("app-cell-comment-count-renderer a").shouldBe(visible).click();
    }
}
