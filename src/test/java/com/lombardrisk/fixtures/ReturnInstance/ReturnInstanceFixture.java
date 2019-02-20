package com.lombardrisk.fixtures.ReturnInstance;

import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.returninstance.ReturnInstancePage;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.sleep;

public class ReturnInstanceFixture {

    private final DashboardPage dashboardPage;
    private final ReturnInstancePage returnInstancePage;

    public ReturnInstanceFixture(
            final DashboardPage dashboardPage,
            final ReturnInstancePage returnInstancePage) {
        this.dashboardPage = dashboardPage;
        this.returnInstancePage = returnInstancePage;
    }

    public ReturnInstanceFixture openReturn(
            final String product,
            final String entity,
            final String returnName,
            final int returnVersion,
            final LocalDate referenceDate) {
        dashboardPage
                .selectRegulator(ProductPackage.valueOf(product))
                .selectEntity(entity)
                .selectReturn(returnName+" v"+returnVersion)
                .openReturn(returnName, returnVersion, referenceDate);
        sleep(2000);
        return this;
    }

    public ReturnInstanceFixture exportToFile(final String exportType){
        returnInstancePage
                .exportToFile(exportType);
        return this;
    }

    public void closeReturn() {
        returnInstancePage
                .close();
    }
}
