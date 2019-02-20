package com.lombardrisk.fixtures.AnalysisModule;

import com.lombardrisk.page.analysismodule.grid.ChartsPage;
import com.lombardrisk.page.analysismodule.grid.ExportType;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.stepdef.analysismodule.grid.ChartViewType;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.screenshot;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class CreateChartFixture {

    private static final Random RANDOM = new Random();

    private final ReturnSelectorPanel returnSelectorPanel;
    private final ChartsPage chartsPage;
    private final GridPage gridPage;
    private static final String[] EXPORT_CHART_TYPES = new String[]{"PNG", "JPEG", "PDF"};

    public CreateChartFixture(
            final ReturnSelectorPanel returnSelectorPanel,
            final ChartsPage chartsPage,
            final GridPage gridPage) {
        this.returnSelectorPanel = requireNonNull(returnSelectorPanel);
        this.chartsPage = requireNonNull(chartsPage);
        this.gridPage = requireNonNull(gridPage);
    }

    public CreateChartFixture createTrendsAnalysisForReturn(final ReturnSelection returnSelection) {
        returnSelectorPanel.open()
                .selectRegulator(returnSelection.regulator().fullName())
                .selectEntity(returnSelection.entityName())
                .selectReturn(returnSelection.returnName())
                .selectReferenceDate(returnSelection.referenceDate())
                .createTrendAnalysisForNumberOfPeriods(2);

        return this;
    }

    public List<Integer> selectRandomRows() {
        List<Integer> availableCellIndices = chartsPage.findUncheckedVisibleRowIndices();

        int totalCellsToCheck = nextInt(5, availableCellIndices.size() - 1);

        List<Integer> randomlySelectedRowIds =
                RANDOM.ints(totalCellsToCheck, 0, availableCellIndices.size() - 1)
                        .distinct()
                        .map(availableCellIndices::get)
                        .peek(chartsPage::checkCell)
                        .boxed()
                        .collect(toList());

        screenshot("BeforeCellRemovalInChart");
        return randomlySelectedRowIds;
    }

    public void removeRandomCells() {
        List<Integer> checkedCellReferences = chartsPage.findCheckedVisibleRowIndices();

        int totalCellsToRemove = nextInt(1, checkedCellReferences.size() - 1);

        RANDOM.ints(totalCellsToRemove, 0, checkedCellReferences.size() - 1)
                .distinct()
                .map(checkedCellReferences::get)
                .forEach(chartsPage::uncheckCell);

        screenshot("AfterCellRemovalInChart");
    }

    public void exportAllChartViews() {
        gridPage.export(ExportType.CSV);

        exportAllViewsAndTypes();

        chartsPage.openFullScreen();
        exportAllViewsAndTypes();
    }

    private void exportAllViewsAndTypes() {
        for (ChartViewType chartViewType : ChartViewType.values()) {
            chartsPage.selectView(chartViewType);

            screenshot("Charts_" + "Gridview" + "_" + chartViewType);

            exportAllTypes();
        }
    }

    private void exportAllTypes() {
        for (String exportType : EXPORT_CHART_TYPES) {
            chartsPage.exportType(exportType);
        }
    }
}


