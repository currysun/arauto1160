package com.lombardrisk.stepdef.analysismodule.grid.converter;

import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import org.assertj.core.api.Condition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PeriodDiffsDataTableConverter {

    private static final String FORMAT_DESCRIPTION =
            "format for OFSAA table grid rows needs to be: \n"
                    + "|                     | <cell name>        | [next cell column] \n"
                    + "| description         | <cell description> |\n"
                    + "| <period start date> | <cell value>       |\n"
                    + "| difference          | <diff value>       |\n"
                    + "| percentDifference   | <diff value %>     |\n"
                    + "| <period end date>   | <cell value>       |\n"
                    + "  [next period diff rows ]";

    private static final String ALLOCATION_FORMAT_DESCRIPTION =
            "format for OFSAA table grid rows needs to be: \n"
                    + "|                     | <cell name>        | [next cell column] \n"
                    + "| <period start date> | <cell value>       |\n"
                    + "| <period end date>   | <cell value>       |\n"
                    + "| difference          | <diff value>       |\n"
                    + "| typeOfChange        | <diff value>       |\n"
                    + "  [next period diff rows ]";

    public static final Condition<? super List<? extends List<String>>> ROWS_FOR_PERIOD_DIFF =
            new Condition<>(tableRows -> tableRows.size() % 3 == 0, FORMAT_DESCRIPTION);

    private PeriodDiffsDataTableConverter() {
        // no-op
    }

    public static Map<CellReference, List<PeriodDiff>> toPeriodDiffsByCell(
            int type,
            final List<List<String>> dataTableRows) {

        List<String> cellReferences = dataTableRows.get(0).subList(1, dataTableRows.get(0).size());
        Map<CellReference, List<PeriodDiff>> periodDiffsByCell = new LinkedHashMap<>();

        if (type == 0) {
            List<String> cellDescriptions = dataTableRows.get(1).subList(1, dataTableRows.get(1).size());

            for (int rowOffset = 2; rowOffset < dataTableRows.size() - 3; rowOffset = rowOffset + 3) {

                addPeriodDiff(
                        periodDiffsByCell,
                        cellReferences,
                        cellDescriptions,
                        dataTableRows,
                        rowOffset);
            }
        } else {
            List<String> typeOfChange = dataTableRows.get(4).subList(1, dataTableRows.get(4).size());
            for (int rowOffset = 1; rowOffset < dataTableRows.size() - 2; rowOffset = rowOffset + 3) {

                addAllocationCellPeriodDiff(
                        periodDiffsByCell,
                        cellReferences,
                        typeOfChange,
                        dataTableRows,
                        rowOffset);
            }
        }

        return periodDiffsByCell;
    }

    private static void addPeriodDiff(
            final Map<CellReference, List<PeriodDiff>> periodDiffsByCell,
            final List<String> cellReferences,
            final List<String> cellDescriptions,
            final List<List<String>> dataTableRows,
            final int rowOffset) {

        List<String> startPeriodRow = dataTableRows.get(rowOffset);
        List<String> differenceRow = dataTableRows.get(rowOffset + 1);
        List<String> differencePercentRow = dataTableRows.get(rowOffset + 2);
        List<String> endPeriodRow = dataTableRows.get(rowOffset + 3);

        for (int j = 0; j < cellReferences.size(); j++) {
            PeriodDiff cellDifference =
                    new PeriodDiff.Builder()
                            .startPeriodDate(startPeriodRow.get(0))
                            .startPeriodValue(startPeriodRow.get(j + 1))
                            .difference(differenceRow.get(j + 1))
                            .percentDifference(differencePercentRow.get(j + 1))
                            .endPeriodDate(endPeriodRow.get(0))
                            .endPeriodValue(endPeriodRow.get(j + 1))
                            .build();

            CellReference currentCell = new CellReference(cellReferences.get(j), cellDescriptions.get(j));
            periodDiffsByCell
                    .computeIfAbsent(currentCell, __ -> new ArrayList<>())
                    .add(cellDifference);
        }
    }

    private static void addAllocationCellPeriodDiff(
            final Map<CellReference, List<PeriodDiff>> periodDiffsByCell,
            final List<String> cellReferences,
            final List<String> typeOfChange,
            final List<List<String>> dataTableRows,
            final int rowOffset) {

        List<String> startPeriodRow = dataTableRows.get(rowOffset);
        List<String> endPeriodRow = dataTableRows.get(rowOffset + 1);
        List<String> differenceRow = dataTableRows.get(rowOffset + 2);
        List<String> typeOfChangeRow = dataTableRows.get(rowOffset + 3);

        for (int j = 0; j < cellReferences.size(); j++) {
            PeriodDiff cellDifference =
                    new PeriodDiff.Builder()
                            .startPeriodDate(startPeriodRow.get(0))
                            .startPeriodValue(startPeriodRow.get(j + 1))
                            .difference(differenceRow.get(j + 1))
                            .typeOfChange(typeOfChangeRow.get(j + 1))
                            .endPeriodDate(endPeriodRow.get(0))
                            .endPeriodValue(endPeriodRow.get(j + 1))
                            .build();

            CellReference currentCell = new CellReference(cellReferences.get(j), "");
            periodDiffsByCell
                    .computeIfAbsent(currentCell, __ -> new ArrayList<>())
                    .add(cellDifference);
        }
    }
}
