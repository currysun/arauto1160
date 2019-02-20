package com.lombardrisk.stepdef.analysismodule.grid;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.analysismodule.AddCommentsDialog;
import com.lombardrisk.page.analysismodule.ViewCellCommentDialog;
import com.lombardrisk.page.analysismodule.grid.CellReference;
import com.lombardrisk.page.analysismodule.grid.PeriodDiff;
import com.lombardrisk.page.analysismodule.grid.TrendsAnalysisPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static com.lombardrisk.stepdef.analysismodule.grid.converter.PeriodDiffsDataTableConverter.toPeriodDiffsByCell;

public class TrendAnalysis extends StepDef {

    @Autowired
    private TrendsAnalysisPage trendsAnalysisPage;
    @Autowired
    private AddCommentsDialog addCommentsDialog;
    @Autowired
    private ViewCellCommentDialog viewCellCommentDialog;

    private final String uniqueCommentSuffix = ": " + RandomStringUtils.randomAlphanumeric(20);

    @Then("^the grid should contain the following cells and trend differences:$")
    public void theGridShouldContainTheFollowingDifferences(final List<List<String>> dataTable) {
        Map<CellReference, List<PeriodDiff>> periodDiffsByCellReference = toPeriodDiffsByCell(0, dataTable);

        trendsAnalysisPage.shouldHaveDiffs(periodDiffsByCellReference);
    }

    @When("^I add a cell comment \"([^\"]*)\" on Trend Analysis Grid$")
    public void iAddACellComment(String comment) {
        trendsAnalysisPage.viewCellComments(0);
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.save();
    }

    @When("^I add a Cell comment \"([^\"]*)\" to cell \"([^\"]*)\" and include in Return Analysis Report$")
    public void iAddACellCommentToCellAndIncludeInReturnAnalysisReport(String comment, String cellRef) {
        trendsAnalysisPage.viewCellComments(cellRef);
        addCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addCommentsDialog.shouldBeDefaultedToRAR();
        addCommentsDialog.save();
        viewCellCommentDialog.close();
    }

    @And("^I open comments for the cell \"([^\"]*)\"$")
    public void iOpenCommentsForTheCell(String cellRef)  {
        trendsAnalysisPage.viewCellComments(cellRef);
    }
}
