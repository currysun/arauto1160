package com.lombardrisk.regressiontests.analysismodule;

import com.lombardrisk.config.BeanConfig;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.fixtures.LoginFixture;
import com.lombardrisk.fixtures.AnalysisModule.CreateChartFixture;
import com.lombardrisk.page.LoginPage;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.analysismodule.grid.ChartsPage;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import com.lombardrisk.regressiontests.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfig.class})
public class ChartsTests extends StepDef {

    @Autowired
    private LoginPage loginPage;
    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private Notification notification;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private ReturnSelectorPanel returnSelectorPanel;
    @Autowired
    private ChartsPage chartsPage;
    @Autowired
    private GridPage gridPage;
    @Autowired
    private CreateChartFixture createChartFixture;

    private LoginFixture loginFixture;

    @Before
    public void setUp() {
        loginFixture = new LoginFixture(loginPage, dashboardPage, notification, mainMenu);
        createChartFixture = new CreateChartFixture(returnSelectorPanel, chartsPage, gridPage);
    }

    @Test
    @Issue("AR-3104")
    public void exportAllTypes() {
        loginFixture.loginAsAdmin();

        ReturnSelection ecrReturn = new ReturnSelection(
                ProductPackage.ECR,
                "ECR2999",
                "CAR",
                "COREP CA1-5",
                LocalDate.of(2015, 12, 31));

        createChartFixture
                .createTrendsAnalysisForReturn(ecrReturn)
                .selectRandomRows();

        createChartFixture.exportAllChartViews();

    /*Manual
      Then I should see a graph, with a separate line for each selected cell, showing time on the x-axis and Cell Values on the y-axis
      And the lines should be individually coloured, with a key to identify the cells which each plots
      And the grid view should remain visible in the lower part of the screen (for grid view only)
    */
    }

    @Test
    @Issue("AR-3108")
    public void removeCellsFromChart() {
        loginFixture.loginAsAdmin();

        ReturnSelection mfsdReturn = new ReturnSelection(
                ProductPackage.MFSD,
                "4Regs1000",
                "BT",
                "BALANCE SHEET",
                LocalDate.of(2017, 7, 31));

        createChartFixture
                .createTrendsAnalysisForReturn(mfsdReturn)
                .selectRandomRows();

        createChartFixture.removeRandomCells();
    }
}
