package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.function.Consumer;

public class Dashboard extends StepDef {

    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private ReturnInstancePage returnInstancePage;

    @Given("^I select regulator \"([^\"]*)\", entity \"([^\"]*)\", return type \"([^\"]*)\" on the dashboard$")
    public void iSelectRegulatorEntityReturnTypeOnTheDashboard(
            final ProductPackage productPackage,
            final String entity,
            final String returns) {
        dashboardPage.selectRegulator(productPackage);
        dashboardPage.selectEntity(entity);
        dashboardPage.selectReturn(returns);
    }

    @And("^I select the regulator \"([^\"]*)\"$")
    public void iSelectTheRegulator(final ProductPackage productPackage) {
        dashboardPage.selectRegulator(productPackage);
    }

    @And("^I select the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+)$")
    public void iSelectTheReturnVWithReferenceDate(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.openReturn(returnName, returnVersion, returnDate);
    }

    @And("^I open or create the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+)$")
    public void iOpenOrCreateTheReturnVWithReferenceDate(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        if (dashboardPage.returnInstanceRow(returnName, returnVersion, returnDate).isDisplayed()) {
            dashboardPage
                    .openReturn(returnName, returnVersion, returnDate);
        } else {
            importReturn(returnName);
        }
    }

    @Given("^I recreate the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+)$")
    public void iRecreateTheReturnVWithReferenceDate(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.deleteReturn(returnName, returnVersion, returnDate);
        importReturn(returnName);
    }

    @And("^the \"([^\"]*)\" return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) " +
            "should have the dashboard status \"([^\"]*)\"$")
    public void theReturnVWithReferenceDateShouldHaveTheDashboardStatus(
            final ProductPackage expectedProductPackage,
            final String expectedReturnName,
            final int expectedReturnVersion,
            final @Transform(ToLocalDate.class) LocalDate expectedReturnDate,
            final String expectedApprovalStatusAndCount) {

        returnInstancePage.close();

        dashboardPage.shouldHaveReturnWithApprovalStatus(
                expectedProductPackage,
                expectedReturnName,
                expectedReturnVersion,
                expectedReturnDate,
                expectedApprovalStatusAndCount);
    }

    @Then("^I see the dashboard$")
    public void iSeeTheDashboard() {
        dashboardPage.shouldBeOpen();
    }

    @When("^I open the Export to \"([^\"]*)\" dialog from the Dashboard$")
    public void iOpenTheExportToDialogFromTheDashboard(final String returnToSubmit) {
        dashboardPage
                .submitToExportType(returnToSubmit);
    }

    @And("^I select the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) and unlock the return$")
    public void iSelectTheReturnVWithReferenceDateAndUnlockTheReturn(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        uploadAndUnlockReturn(returnName, returnVersion, returnDate, this::importReturn);
    }

    @And("^I select a validation failure return of \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) and unlock the return$")
    public void iSelectAValidationFailureReturnOfVWithReferenceDateAndUnlockTheReturn(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        uploadAndUnlockReturn(returnName, returnVersion, returnDate, r -> dashboardPage
                .openCreateFromExcelDialog()
                .uploadReturn(r, "validationFailure")
                .importReturn());
    }

    @And("^I select a cross validation failure return of \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) and unlock the return$")
    public void iSelectACrossValidationFailureReturnOfVWithReferenceDateAndUnlockTheReturn(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        uploadAndUnlockReturn(returnName, returnVersion, returnDate, r -> dashboardPage
                .openCreateFromExcelDialog()
                .uploadReturn(r, "crossValidationFailure")
                .importReturn());
    }

    @And("^I select the entity \"([^\"]*)\"$")
    public void iSelectTheEntity(final String entity) {
        dashboardPage.selectEntity(entity);
    }

    @And("^I open the comment log from the dashboard page for return \"([^\"]*)\" version \"([^\"]*)\" and reference date (\\d+/\\d+/\\d+)$")
    public void iOpenTheCommentLogFromTheDashboardPageForReturnVersionAndReferenceDate(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.openReturnCommentLog(returnName, returnVersion, returnDate);
    }

    private void importReturn(final String returnName) {
        dashboardPage
                .openCreateFromExcelDialog()
                .uploadReturn(returnName)
                .importReturn();
    }

    private void uploadAndUnlockReturn(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate,
            final Consumer<String> uploadReturn) {
        dashboardPage.shouldBeOpen();

        if (dashboardPage.returnIsDisplayed(returnName, returnVersion, returnDate)) {
            boolean hasAnyActiveStates = DashboardPage.hasAnyActiveStates(returnName, returnVersion, returnDate);
            if (hasAnyActiveStates) {
                dashboardPage
                        .openReturn(returnName, returnVersion, returnDate)
                        .lock()
                        .unlock()
                        .close();
            }
        } else {
            uploadReturn.accept(returnName);
            returnInstancePage.close();
        }
    }




    @When("^I click Analysis Module button from the header$")
    public void iClickAnalysisModuleButtonFromTheHeader() {
        dashboardPage
                .openAnalysisModule();
    }

    @When("^I click the Variance button from within a return$")
    public void iClickTheVarianceButtonFromWithinAReturn() {
        returnInstancePage.clickVarianceButton();
    }

    @When("^I click the Trends button from within a return$")
    public void iClickTheTrendsButtonFromWithinAReturn() {
        returnInstancePage.clickTrendsButton();
    }

    @When("^I click the Trends button on the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) from the dashboard$")
    public void iClickTheTrendsButtonOnTheReturnVWithReferenceDateFromTheDashboard(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.clickTrendsButtonForReturn(returnName, returnVersion, returnDate);
    }

    @When("^I click the Variance button on the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) from the dashboard$")
    public void iClickTheVarianceButtonOnTheReturnVWithReferenceDateFromTheDashboard(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.clickVarianceButtonForReturn(returnName, returnVersion, returnDate);
    }

    @When("^I click on Help link$")
    public void whenIClickOnHelpLink() {
        dashboardPage.clickOnHelpLink();
    }

    @Then("^I can view the help content$")
    public void iCanViewTheHelpContent() {
        dashboardPage
                .switchToHelpTab()
                .shouldHaveAnalysisModuleIntroductionHelpLinkOpen();
    }

    @And("^the Trend button is disabled for the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) from the dashboard$")
    public void theTrendButtonIsDisabledForTheReturnVWithReferenceDateFromTheDashboard(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.trendButtonIsDisabledForReturn(returnName, returnVersion, returnDate);
    }

    @Then("^the Variance button is disabled within a return$")
    public void theVarianceButtonIsDisabledWithinAReturn() {
        returnInstancePage.varianceButtonIsDisabled();
    }

    @And("^the Trends button is disabled within a return$")
    public void theTrendsButtonIsDisabledWithinAReturn() {
        returnInstancePage.trendButtonIsDisabled();
    }

    @Then("^the Variance button is disabled for the return \"([^\"]*)\" v(\\d+) with reference date (\\d+/\\d+/\\d+) from the dashboard$")
    public void theVarianceButtonIsDisabledForTheReturnVWithReferenceDateFromTheDashboard(
            final String returnName,
            final int returnVersion,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        dashboardPage.varianceButtonIsDisabledForReturn(returnName, returnVersion, returnDate);
    }
}
