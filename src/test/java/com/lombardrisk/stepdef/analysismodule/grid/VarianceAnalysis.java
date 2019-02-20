package com.lombardrisk.stepdef.analysismodule.grid;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.analysismodule.AddCommentsDialog;
import com.lombardrisk.page.analysismodule.ReturnAnalysisReportPanel;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.page.analysismodule.ViewCellCommentDialog;
import com.lombardrisk.page.analysismodule.ViewReturnCommentDialog;
import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.page.analysismodule.grid.VarianceAnalysisPage;
import com.lombardrisk.page.analysismodule.grid.TrendsAnalysisPage;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.lombardrisk.stepdef.analysismodule.grid.converter.PeriodDiffsDataTableConverter.toPeriodDiffsByCell;

public class VarianceAnalysis extends StepDef {

    private final String uniqueCommentSuffix = ": " + RandomStringUtils.randomAlphanumeric(20);
    @Autowired
    private VarianceAnalysisPage varianceAnalysisPage;
    @Autowired
    private AddCommentsDialog addCommentsDialog;
    @Autowired
    private GridPage gridPage;
    @Autowired
    private ViewReturnCommentDialog viewReturnCommentDialog;
    @Autowired
    private ReturnAnalysisReportPanel returnAnalysisReportPanel;
    @Autowired
    private TrendsAnalysisPage trendsAnalysisPage;
    @Autowired
    private ViewCellCommentDialog viewCellCommentDialog;
    private boolean descendingOrder;

    @When("^I filter the \"([^\"]*)\" column for values(?:\\sthat\\s|\\s)\"([^\"]*)\"(?:\\s|\\sto\\s)\"([^\"]*)\"$")
    public void iFilterColumnByAnd(final String headerName, final String filterCondition, final String cellReference) {
        varianceAnalysisPage.filterBy(headerName, filterCondition, cellReference);
    }

    @And("^I sort the \"([^\"]*)\" column by descending order$")
    public void iSortColumnByDescendingOrder(final String headerName) {
        varianceAnalysisPage.sortDescending(headerName);
        descendingOrder = true;
    }

    @Then("^the grid should contain the following cells and variance difference:$")
    public void theFollowingGridShouldBeDisplayed(final List<List<String>> dataTable) {
        Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference = toPeriodDiffsByCell(0, dataTable);

        varianceAnalysisPage.shouldHaveDiffs(periodDiffsByCellReference);
    }

    @Then("^I should see only (\\d+) rows$")
    public void iShouldSeeOnlyRows(final int rowCount) {
        varianceAnalysisPage.shouldHaveGridSize(rowCount);
    }

    @Then("^the grid rows should have the following:$")
    public void theGridRowsShouldHaveTheFollowing(final List<String> expectedOrderedCellReferences) {
        this.descendingOrder = true;
        varianceAnalysisPage.shouldHaveOrderedRows(expectedOrderedCellReferences, descendingOrder);
    }

    @When("^I Click on Comments button$")
    public void iClickOnCommentsButton() {
        gridPage.openCommentsDialog();
    }

    @Then("^I should see Return-level comments dialog$")
    public void iShouldSeeReturnLevelCommentsDialog() {
        viewReturnCommentDialog.exists();
    }

    @When("^I click on Add Comment button$")
    public void iClickOnAddCommentButton() {
        viewReturnCommentDialog.add();
    }

    @When("^I type a comment \"([^\"]*)\"$")
    public void iTypeACommentAndClick(final String comment) {
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
    }

    @Then("^the \"([^\"]*)\" appears with the following details$")
    public void theAppearsWithTheFollowingDetails(final String comment, final List<String> dataTable) {
        viewReturnCommentDialog.shouldBeVisible();
        viewReturnCommentDialog.shouldHaveDetails(dataTable);
    }

    @Then("^I should see Add Return-level comment dialog$")
    public void iShouldSeeAddReturnLevelCommentPopUp() {
        addCommentsDialog.exists();
    }

    @And("^I close the comment dialog$")
    public void iCloseTheCommentDialog() {
        addCommentsDialog.close();
    }

    @And("^I delete top comment from the list$")
    public void iDeleteTopCommentFromTheList() {
        viewReturnCommentDialog.deleteTopComment();
    }

    @Then("^\"([^\"]*)\" is not shown in the list of comments$")
    public void isNotShownInTheListOfComments(final String comment) {
        viewReturnCommentDialog.shouldNotHaveComment(comment + uniqueCommentSuffix);
    }

    @Then("^\"([^\"]*)\" is not shown in the list of cell comments$")
    public void isNotShownInTheListOfCellComments(final String comment) {
        viewCellCommentDialog.shouldNotHaveComment(comment + uniqueCommentSuffix);
    }

    @When("^I add a comment \"([^\"]*)\"$")
    public void iAddAComment(final String comment) {
        gridPage.openCommentsDialog();
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.save();
    }

    @When("^No comments are available$")
    public void noCommentsAreAvailable() {
        varianceAnalysisPage.viewCellComments(1);
        viewCellCommentDialog.shouldBeVisible();
    }

    @When("^I add a cell comment \"([^\"]*)\" on Variance Analysis Grid$")
    public void iAddACellComment(final String comment) {
        varianceAnalysisPage.viewCellComments(0);
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.save();
    }

    @Then("^the cell comment \"([^\"]*)\" appears with the following details$")
    public void theCellCommentAppearsWithTheFollowingDetails(final String comment, final List<String> dataTable) {
        viewCellCommentDialog.shouldBeVisible();
        viewCellCommentDialog.shouldHaveDetails(dataTable);
    }

    @When("^I view the cell comments for a cell on the grid$")
    public void iViewTheCellCommentsForACellOnTheGrid() {
        varianceAnalysisPage.viewCellComments(0);
    }

    @Then("^I should be presented with a list of comments for that cell for that period$")
    public void iShouldBePresentedWithAListOfCommentsForThatCellForThatPeriod(final List<String> datatable) {
        viewCellCommentDialog.shouldHavePeriod(datatable);
    }

    @And("^I view cell comments for a cell with at least one existing comment$")
    public void iViewCellCommentsForACellWithAtLeastOneExistingComment() {
        varianceAnalysisPage.viewCellComments(0);
    }

    @And("^I delete top cell comment from the list$")
    public void iDeleteTopCellCommentFromTheList() {
        viewCellCommentDialog.deleteTopComment();
    }

    @When("^I Open the RAR in analysis module$")
    public void iOpenTheRARInAnalysisModule() {
        varianceAnalysisPage.openRar();
    }

    @When("^I add a comment \"([^\"]*)\" and include in Return Analysis Report$")
    public void iAddACommentAndIncludeInReturnAnalysisReport(final String comment) {
        gridPage.openCommentsDialog();
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.save();
        viewReturnCommentDialog.close();
    }

    @Then("^the grid should contain the following Allocation cells and variance difference:$")
    public void theGridShouldContainTheFollowingAllocationCellsAndVarianceDifference(final List<List<String>> dataTable) {
        Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference = toPeriodDiffsByCell(1, dataTable);
        varianceAnalysisPage.shouldHaveAllocationCellDiffs(periodDiffsByCellReference);
    }

    @And("^the Sum of allocations values with adjustments should be:$")
    public void theSumOfAllocationsValuesShouldBe(final List<List<String>> dataTable) {
        Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference = toPeriodDiffsByCell(1, dataTable);
        varianceAnalysisPage.shouldHaveAllocationTotalCellDiffs(periodDiffsByCellReference);
    }

    @When("^I add a Cell comment \"([^\"]*)\" and include in Return Analysis Report$")
    public void iAddACellCommentAndIncludeInReturnAnalysisReport(final String comment) {
        varianceAnalysisPage.viewCellComments(0);
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.includeInRAR();
        addCommentsDialog.save();
        viewCellCommentDialog.close();
    }

    @And("^I click on Open Cell Details on the comment$")
    public void iClickOnOpenCellDetailsOnTheComment() {
        varianceAnalysisPage.shouldClickOnOpenCellDetails();
    }

    @Then("^the cell details grid contains$")
    public void theCellDetailsGridContains(final List<List<String>> dataTable) {
        varianceAnalysisPage.shouldHaveOpenCellDetails(dataTable);
    }

    @And("^the delete comment button is enabled$")
    public void theDeleteCommentButtonIsEnabled() {
        viewCellCommentDialog.DeleteButtonShouldBeEnabled();
    }

    @And("^the delete comment button is disabled for the return$")
    public void theDeleteCommentButtonIsDisabledForTheReturn() {
        viewReturnCommentDialog.deleteTopReturnCommentButtonIsDisabled();
    }

    @And("^the delete comment button is disabled for the cell$")
    public void theDeleteCommentButtonIsDisabledForTheCell() {
        viewCellCommentDialog.deleteTopCellCommentButtonIsDisabled();

    }

    @After("@AR-4127")
    public void deleteTopComment() {
        returnAnalysisReportPanel.closeRar();
        trendsAnalysisPage.viewCellComments("PMG1 (Subtotal)");
        viewCellCommentDialog.deleteTopComment();
    }

    @After("@AR-3863")
    public void deleteTopReturnComment() {
        returnAnalysisReportPanel.closeRar();
        gridPage.openCommentsDialog();
        viewReturnCommentDialog.deleteTopComment();
    }
}