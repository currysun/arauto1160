package com.lombardrisk.stepdef.analysismodule.grid;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.google.common.collect.ImmutableSet;
import com.lombardrisk.config.Config;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.ExportType;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.stepdef.analysismodule.SelectorPanel;
import com.lombardrisk.stepdef.analysismodule.grid.checker.DownloadedFilesChecker;
import com.lombardrisk.stepdef.analysismodule.grid.checker.ExportedVarianceCsvChecker;
import com.lombardrisk.stepdef.analysismodule.grid.checker.ExportedVarianceExcelChecker;
import com.lombardrisk.stepdef.analysismodule.grid.converter.ExpectedCsvVarianceConverter;
import com.lombardrisk.config.driver.ChromeHeadlessDownloadEnabler;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.analysismodule.grid.ExportType.CSV;
import static com.lombardrisk.page.analysismodule.grid.ExportType.XLSX;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.io.FileUtils.listFiles;

public class Export extends StepDef {

    @Autowired
    private SelectorPanel selectorPanel;

    @Autowired
    private Config config;
    @Autowired
    private ExpectedCsvVarianceConverter expectedCsvVarianceConverter;
    @Autowired
    private ExportedVarianceCsvChecker exportedCsvVarianceGridChecker;
    @Autowired
    private ExportedVarianceExcelChecker exportedExcelVarianceGridChecker;
    @Autowired
    private DownloadedFilesChecker downloadedFilesChecker;
    @Autowired
    private ChromeHeadlessDownloadEnabler chromeHeadlessDownloadEnabler;

    @Autowired
    private GridPage gridPage;

    private static final Set<ExportType> SUPPORTED_EXPORT_TYPES = ImmutableSet.of(CSV, XLSX);
    private final Set<ExportType> expectedExportedTypes = EnumSet.noneOf(ExportType.class);

    @When("^I export the grid as a \"([^\"]*)\" file$")
    public void iExportTheGridAs(final ExportType exportType) {
        chromeHeadlessDownloadEnabler.enableDownloads();
        gridPage.export(exportType);

        expectedExportedTypes.add(exportType);
    }

    @Then("^the exported variance files should match the expected file \"([^\"]*)\"$")
    public void theExportedVarianceFileShouldMatch(final String expectedVarianceFileName) {
        checkExpectedTypesCanBeChecked();

        Map<CellReference, List<PeriodDiff>> expectedPeriodDiffsByCellReference =
                expectedCsvVarianceConverter.toPeriodDiffsByCellReference(expectedVarianceFileName);

       if (expectedExportedTypes.contains(CSV)) {
            exportedCsvVarianceGridChecker.shouldHaveDiffs(
                    expectedPeriodDiffsByCellReference, selectorPanel.getReturnSelection());
        }
        if (expectedExportedTypes.contains(XLSX)) {
            exportedExcelVarianceGridChecker.shouldHaveDiffs(
                    expectedPeriodDiffsByCellReference, selectorPanel.getReturnSelection());
        }
    }

    private void checkExpectedTypesCanBeChecked() {
        Set<ExportType> unexpectedExportTypes =
                expectedExportedTypes.stream()
                        .filter(expectedType -> !SUPPORTED_EXPORT_TYPES.contains(expectedType))
                        .collect(toSet());

        if (!unexpectedExportTypes.isEmpty())
            throw new AssertionError("No export checker defined for export types " + unexpectedExportTypes);
    }

    @Then("^the exported files should be named \"([^\"]*)\"$")
    public void anExportedFileShouldBeNamed(final String expectedFileName) {
        Selenide.sleep(2000);
        expectedExportedTypes.forEach(
                expectedType -> checkFileDownloaded(expectedFileName, expectedType));
    }

    private void checkFileDownloaded(final String expectedFileName, final ExportType expectedType) {
        downloadedFilesChecker.shouldBeDownloaded(
                new NameFileFilter(expectedFileName + "." + expectedType, IOCase.INSENSITIVE));
    }

    @And("^I order the cells in ascending order$")
    public void iOrderTheCellsInAscendingOrder() {
        if(!$(By.xpath("//span[@class='ag-header-icon ag-sort-ascending-icon']")).isDisplayed()){
        gridPage.cellReferenceHeader().shouldBe(Condition.visible).click();}
    }

    @And("^I order the cells descending$")
    public void iOrderTheCellsDescending() {
        if(!$(By.xpath("//span[@class='ag-header-icon ag-sort-descending-icon']")).isDisplayed()){
            gridPage.cellReferenceHeader().shouldBe(Condition.visible).click();}
    }
}
