package com.lombardrisk.stepdef.analysismodule;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.fixtures.AnalysisModule.CreateChartFixture;
import com.lombardrisk.page.analysismodule.grid.ChartsPage;
import com.lombardrisk.stepdef.analysismodule.grid.ChartViewType;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Collections.emptyList;

public class Charts extends StepDef {

    @Autowired
    private ChartsPage chartsPage;
    @Autowired
    private CreateChartFixture createChartFixture;
    private List<Integer> randomlySelectedRowIds = emptyList();

    @When("^I choose a random selection of cells to graph$")
    public void iChooseARandomSelectionOfCellsToViewInMyChart() {
        randomlySelectedRowIds = createChartFixture.selectRandomRows();
    }

    @When("^I open the chart in full screen$")
    public void myChartIsInFullScreen() {
        chartsPage.openFullScreen();
    }

    @When("^I close the full screen chart$")
    public void iCloseTheFullScreenChart() {
        chartsPage.closeFullScreenChart()
                .shouldNotHaveVisibleFullScreenGraph();
    }

    @Then("^the grid should be displayed$")
    public void myGirdViewOfTheChartShouldBePresent() {
        chartsPage.shouldHaveVisibleGridAndGraph();
    }

    @And("^I select the \"([^\"]*)\" chart view$")
    public void iSelectTheChartView(final ChartViewType chartViewType) {
        chartsPage.selectView(chartViewType);
    }

    @Then("^the chart should contain the selected cells$")
    public void theChartShouldGraphTheSelectedCells() {
        chartsPage.shouldHaveValueViewChart(randomlySelectedRowIds);
    }
}
