package com.lombardrisk.stepdef.analysismodule.grid.converter;

import com.lombardrisk.config.Config;
import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newBufferedReader;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;

public class ExpectedCsvVarianceConverter {

    private final Config config;

    public ExpectedCsvVarianceConverter(final Config config) {
        this.config = config;
    }

    public Map<CellReference, List<PeriodDiff>> toPeriodDiffsByCellReference(final String expectedVarianceFileName) {
        Path expectedVarianceCsv = config.exportDocuments().resolve(expectedVarianceFileName);

        return toPeriodDiffsByCellReference(expectedVarianceCsv);
    }

    private static Map<CellReference, List<PeriodDiff>> toPeriodDiffsByCellReference(final Path expectedCsv) {
        try (CSVParser csvParser = CSVFormat.EXCEL
                .parse(newBufferedReader(expectedCsv))) {

            List<CSVRecord> records = csvParser.getRecords();
            CSVRecord headerRecord = records.get(0);

            return records.stream()
                    .skip(1)
                    .map(csvRecord -> toCellPeriodDiffsPair(csvRecord, headerRecord))
                    .collect(toMap(
                            Pair::getLeft, Pair::getRight,
                            (first, second) -> second,
                            LinkedHashMap::new));
        } catch (IOException e) {
            throw new AssertionError("Could not convert " + expectedCsv, e);
        }
    }

    private static Pair<CellReference, List<PeriodDiff>> toCellPeriodDiffsPair(
            final CSVRecord csvRecord, final CSVRecord headerRecord) {
        CellReference cellReference = new CellReference(csvRecord.get(0), csvRecord.get(1));

        return Pair.of(
                cellReference,
                singletonList(
                        new PeriodDiff.Builder()
                                .startPeriodDate(headerRecord.get(2))
                                .startPeriodValue(csvRecord.get(2))
                                .endPeriodDate(headerRecord.get(3))
                                .endPeriodValue(csvRecord.get(3))
                                .difference(csvRecord.get(4))
                                .percentDifference(csvRecord.get(5))
                                .build()));
    }
}
