package com.lombardrisk.stepdef.analysismodule.grid;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class DerivedDrillDown extends StepDef {

    @Autowired
    private GridPage gridPage;

    @When("^I drilldown(?: further)? into the derived cell \"([^\"]*)\"$")
    public void iDrilldownIntoDerivedCell(final String cellReference) {
        gridPage.drilldownInCell(cellReference)
                .shouldHaveDrilldownHeader(cellReference);
    }
    @When("^I drilldown(?: further)? into the Allocation cell \"([^\"]*)\"$")
    public void iDrilldownIntoAllocationCell(final String cellReference) {
        gridPage.drilldownInCell(cellReference)
                .shouldHaveDrilldownHeader(cellReference);
    }

}
