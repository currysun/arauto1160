package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.analysismodule.ReturnAnalysisReportPanel;
import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.stepdef.analysismodule.SelectorPanel;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.stepdef.analysismodule.grid.converter.PeriodDiffsDataTableConverter.toPeriodDiffsByCell;

public class ReturnAnalysisReport extends StepDef {

    @Autowired
    private ReturnAnalysisReportPanel returnAnalysisReportPanel;

    @Autowired
    private SelectorPanel selectorPanel;

    @Then("^I should see RAR with following details$")
    public void iShouldSeeRARWithFollowingDetails(final List<List<String>> dataTable) {
        returnAnalysisReportPanel.shouldHaveReportDetails(dataTable);
    }

    @And("^I open the Return Comments panel$")
    public void iOpenTheReturnCommentsPanel() {
        returnAnalysisReportPanel.shouldHaveReturnCommentsPanel();
    }

    @Then("^the comments are displayed with the following details$")
    public void theCommentsAreDisplayedWithTheFollowingDetails(final List<List<String>> dataTable) {
        returnAnalysisReportPanel.shouldHaveReturnCommentsDetails(dataTable);
    }

    @And("^I open the Cell level comments panel$")
    public void iOpenTheCellAuditPanel() {
        returnAnalysisReportPanel.shouldHaveCellLevelComments();
    }

    @Then("^the cell level comments are displayed with following details$")
    public void theCellLevelCommentsAreDisplayedWithFollowingDetails(final List<List<String>> dataTable) {
        returnAnalysisReportPanel.shouldHaveCellLevelCommentDetails(dataTable);
    }

    @And("^When I click on Show Details$")
    public void whenIClickOnShowDetails() {
        returnAnalysisReportPanel.clickOnShowDetails();
    }

    @Then("^the trend analysis details grid contains$")
    public void theTrendAnalysisDetailsGridContains(final List<List<String>> dataTable) {
        returnAnalysisReportPanel.shouldHaveTrendAnalysisDetailsGrid(dataTable);
    }

    @And("^I close the RAR$")
    public void iCloseTheRAR() {
        returnAnalysisReportPanel.closeRar();
    }

    @And("^I filter the grid by \"([^\"]*)\"$")
    public void iFilterTheGridBy(String columnName) {
        returnAnalysisReportPanel.filterBy(columnName);
    }

    @And("^I export the RAR$")
    public void iExportTheRAR() {
        $("#qa-rar-export-btn").click();
        $("app-return-analysis-report-export-dialog mat-dialog-actions button").click();
    }

    @Then("^the exported PDF file should not match the expected file \"([^\"]*)\"$")
    public void theExportedPDFFileShouldNotMatchTheExpectedFile(String fileName) {
        returnAnalysisReportPanel.pdfShouldNotMatch(fileName);
    }

    @Then("^the exported PDF file should match the expected file \"([^\"]*)\"$")
    public void theExportedPDFFileShouldMatchTheExpectedFile(String fileName) {
        returnAnalysisReportPanel.pdfShouldMatch(fileName);
    }

    @Then("^the exported RAR filename should match the expected pattern$")
    public void theExportedRARFilenameShouldMatchTheExpectedPattern() {
        returnAnalysisReportPanel.ShouldHaveNameMatchingPattern();
    }
}
