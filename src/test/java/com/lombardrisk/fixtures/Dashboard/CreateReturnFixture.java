package com.lombardrisk.fixtures.Dashboard;

import com.codeborne.selenide.Condition;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.dashboard.RetrieveReturnDialog;
import org.openqa.selenium.By;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;

public class CreateReturnFixture {

    private final DashboardPage dashboardPage;
    private final RetrieveReturnDialog retrieveReturnDiag;

    public CreateReturnFixture(
            final DashboardPage dashboardPage,
            final RetrieveReturnDialog retrieveReturnDiag) {
        this.dashboardPage = dashboardPage;
        this.retrieveReturnDiag = retrieveReturnDiag;
    }

    public CreateReturnFixture filterReturns(
            final String product,
            final String entity,
            final String returnName,
            final int returnVersion) {
        String filteredReturn = returnName + " v" + returnVersion;
        dashboardPage
                .selectRegulator(ProductPackage.valueOf(product))
                .selectEntity(entity)
                .selectReturn(filteredReturn);
        return this;
    }

    public CreateReturnFixture retrieveReturn(
            final String entityName,
            final LocalDate referenceDate,
            final int returnVersion,
            final String returnName,
            final String returnNameAndVersion
            ){
        dashboardPage
                .selectRetrieveReturn();

        while (retrieveReturnDiag.retrieveReturnErrorMessage().isDisplayed()) {
            System.out.println("Waiting for Error message to disappear");
        }

        retrieveReturnDiag
                .selectEntity(entityName)
                .selectReferenceDate(referenceDate)
                .selectReturn(returnNameAndVersion);
        if (retrieveReturnDiag.existingReturnMessage().isDisplayed()) {
            retrieveReturnDiag.checkAndRemoveExistingReturn(returnName, returnVersion, referenceDate);
            dashboardPage
                    .selectRetrieveReturn();
            retrieveReturnDiag
                    .selectEntity(entityName)
                    .selectReferenceDate(referenceDate)
                    .selectReturn(returnNameAndVersion)
                    .startRetrieveReturn();
        }

        if (retrieveReturnDiag.retrieveReturnErrorMessage().isDisplayed()) {
            System.out.println(retrieveReturnDiag.retrieveReturnErrorMessage().text());
            retrieveReturnDiag
                    .startRetrieveReturn();
        } else if (!retrieveReturnDiag.existingReturnMessage().isDisplayed()
                | !retrieveReturnDiag.retrieveReturnErrorMessage().isDisplayed()) {

            if (retrieveReturnDiag.okButton.isDisplayed()) {
                retrieveReturnDiag
                        .startRetrieveReturn();
            }
        }
        return this;
    }
}
