package com.lombardrisk.stepdef.analysismodule;

import com.codeborne.selenide.Selenide;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.analysismodule.returnselectionpanel.CheckboxEntry;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelection;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import com.lombardrisk.stepdef.transformer.ToLocalDates;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SelectorPanel extends StepDef {

    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private ReturnSelectorPanel returnSelectorPanel;
    @Autowired
    private ReturnSelectorPanel returnSelector;

    private ReturnSelection returnSelection;

    public ReturnSelection getReturnSelection() {
        return returnSelection;
    }

    @Given("^I select the \"([^\"]*)\" Regulator in the Selection Panel$")
    public void iSelectTheRegulatorInTheSelectionPanel(final String regulator) {
        returnSelector.selectRegulator(regulator);
    }

    @When("^I select the \"([^\"]*)\" Entity in the Selection Panel$")
    public void iSelectTheEntityInTheSelectionPanel(final String entity) {
        returnSelector.selectEntity(entity);
    }

    @And("^I should see listed all regulators for which I have Access Rights which are:$")
    public void iShouldSeeListedAllRegulatorsForWhichIHaveAccessRightsWhichAre(final DataTable dataTable) {
        List<String> regulatorsList = dataTable.asList(String.class);
        returnSelector.checkRegulatorDropdownOptions(regulatorsList);
    }

    @And("^I should see listed all entities for which I have Access Rights which are:$")
    public void iShouldSeeListedAllEntitiesForWhichIHaveAccessRightsWhichAre(final DataTable dataTable) {
        List<String> entitiesList = dataTable.asList(String.class);
        returnSelector.checkEntityDropdownOptions(entitiesList);
    }

    @And("^I should see listed all returns for which I have Access Rights which are:$")
    public void iShouldSeeListedAllReturnsForWhichIHaveAccessRightsWhichAre(final DataTable dataTable) {
        List<String> returnList = dataTable.asList(String.class);
        returnSelector.checkReturnDropdownOptions(returnList);
    }

    @And("^I select the \"([^\"]*)\" Return in the Selection Panel$")
    public void iSelectTheReturnInTheSelectionPanel(final String returns) {
        returnSelector.selectReturn(returns);
    }

    @And("^I should see listed all reporting date/s for which I have Access Rights, which are:$")
    public void iShouldSeeListedAllReportingDatesForWhichIHaveAccessRightsWhichAre(final DataTable dataTable) {
        List<String> reportingDateList = dataTable.asList(String.class);
        returnSelector.checkReportingDateOptions(reportingDateList);
    }

    @And("^I should see listed all Instances for which I have Access Rights, Which Are:$")
    public void iShouldSeeListedAllInstancesForWhichIHaveAccessRightsWhichAre(final DataTable dataTable) {
        List<String> instanceList = dataTable.asList(String.class);
        returnSelector.checkInstanceDropdown(instanceList);
    }

    @Then("^I should see a message informing me that variance needs a previous return$")
    public void inPreviousReportingDateIShouldSeeAMessageInformingMeThatNoPreviousReturnExists() {
        returnSelector.checkVariancePreviousReportingDateErrorMessage();
    }

    @Then("^I should see a message informing me that trends needs at least two previous returns$")
    public void iShouldSeeAMessageInformingMeThatTrendsNeedsAtLeastTwoPreviousDates() {
        returnSelector.checkTrendsPreviousReportingDateErrorMessage();
    }

    @And("^I select the \"([^\"]*)\" Reporting Date in the Selection Panel$")
    public void iSelectTheReportingDateInTheSelectionPanel(final String reportingDate) {
        returnSelector.selectReferenceDate(reportingDate);
    }

    @When("^I select \"([^\"]*)\" as my Instance$")
    public void iSelectAsMyInstance(final String instance) {
        returnSelector.selectInstance(instance);
    }

    @Then("^I should see a list of only the historic return periods of the selected return, which are:$")
    public void iShouldSeeAListOfOnlyTheHistoricReturnPeriodsOfTheSelectedReturnWhichAre(final DataTable dataTable) {
        List<String> previousDateList = dataTable.asList(String.class);
        returnSelector.checkPreviousReportingDate(previousDateList);
    }

    @Then("^I should see listed all Ad Hoc Selection Previous Reporting Dates, which are:$")
    public void iShouldSeeListedAllAdHocSelectionPreviousReportingDatesWhichAre(final DataTable dataTable) {
        List<CheckboxEntry> adHocSelectionPreviousDateList = dataTable.asList(CheckboxEntry.class);
        returnSelector.checkAdHocSelectionPreviousReportingDates(adHocSelectionPreviousDateList);
        returnSelector.clickLombardRiskLogoToResetFocusBackToApp();
    }

    @Then("^I should see listed all radio button options:$")
    public void iShouldSeeListedAllRadioButtonOptions(final DataTable dataTable) {
        List<String> radioButtonOptionsList = dataTable.asList(String.class);
        returnSelector.checkRadioButtonOptions(radioButtonOptionsList);
    }

    @And("^I click on the adHoc selection previous reporting dates:$")
    public void iCanSelectAnotherAdHocSelectionPreviousReportingDateOf(final DataTable dataTable) {
        List<String> myDatesAdHocSelectionPreviousDateList = dataTable.asList(String.class);
        returnSelector.addAdHocSelectionPreviousReportingDate(myDatesAdHocSelectionPreviousDateList);
    }

    @Then("^I should see a Selector Panel with nothing selected$")
    public void iShouldSeeASelectorPanelWithNothingSelected() {
        returnSelectorPanel.shouldHaveNoSelections();
    }

    @When("^I navigate to Analysis Module$")
    public void iNavigateToAnalysisModule() {
        returnSelectorPanel.open();
    }

    @When("^I click the variance button$")
    public void iClickTheVarianceButton() {
        returnSelector.selectVariance();
    }

    @When("^I click the trends button$")
    public void iClickTheTrendsButton() {
        returnSelector.selectTrends();
    }

    @And("^I click the create button$")
    public void iClickTheCreateButton() {
        returnSelector.create();
    }

    @Then("^I should see a prepopulated regulator of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedRegulatorOf(final String prepopulatedRegulator) {
        returnSelector.checkRegulator(prepopulatedRegulator);
    }

    @And("^I should see a prepopulated Entity of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedEntityOf(final String prepopulatedEntity) {
        returnSelector.checkEntity(prepopulatedEntity);
    }

    @And("^I should see a prepopulated return of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedReturnOf(final String prepopulatedReturn) {
        returnSelector.checkReturn(prepopulatedReturn);
    }

    @And("^I should see a prepopulated reporting date (\\d+/\\d+/\\d+)$")
    public void iShouldSeeAPrepopulatedReportingDate(
            final @Transform(ToLocalDate.class) LocalDate prepopulatedReportingDate) {
        returnSelector.checkReportingDate(prepopulatedReportingDate);
    }

    @And("^I should see a prepopulated instance of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedInstanceOf(final String prepopulatedInstance) {
        returnSelector.checkInstance(prepopulatedInstance);
    }

    @And("^I click reset$")
    public void iClickReset(){
        returnSelector.reset();
    }

    @And("^I should see a prepopulated trends previous reporting date of (\\d+/\\d+/\\d+)$")
    public void iShouldSeeAPrepopulatedTrendsPreviousReportingDateOf(@Transform(ToLocalDate.class) final LocalDate date) {
        returnSelector.checkTrendsDateRangePreviousReportingDateDropdown(date);
    }

    @And("^I should see a prepopulated variance previous reporting date of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedVariancePreviousReportingDateOf(final String prepopulatedPreviousReportingDate) {
        returnSelector.checkVarianceDateRangePreviousReportingDateDropdown(prepopulatedPreviousReportingDate);
    }

    @And("^I should see a prepopulated Report Cell Group of \"([^\"]*)\"$")
    public void iShouldSeeAPrepopulatedReportCellGroupOf(final String prepopulatedReportCellGroup) {
        returnSelector.checkReportCellGroup(prepopulatedReportCellGroup);
    }

    @Then("^I should not see a prepopulated Report Cell Group of \"([^\"]*)\"$")
    public void iShouldNotSeeAPrepopulatedReportCellGroupOf(final String prepopulatedReportCellGroup) {
        returnSelector.checkNotReportCellGroup(prepopulatedReportCellGroup);
    }

    @And("^The instance dropdown is hidden$")
    public void theInstanceDropdownIsHidden() {
        returnSelector.checkInstanceIsHidden();
    }

    @When("^I select the Number of Periods Radio Button$")
    public void iSelectTheNumberOfPeriodsRadioButton() {
        returnSelector.clickNumberOfPeriods();
    }

    @Then("^I see (\\d+) periods as default$")
    public void iSeePeriodsAsDefault(final String numberOfPeriods) {
        returnSelector.checkLimitsNumber(numberOfPeriods);
    }

    @When("^I select the Ad Hoc Selection option$")
    public void iSelectTheAdHocSelectionOption() {
        returnSelector.clickAdHocRadioButton();
    }

    @And("^I get a too many periods selected error$")
    public void iGetATooManyPeriodsSelectedError() {
        returnSelector.checkTooManyPeriodsError();
    }

    @And("^I update the number of periods to \"([^\"]*)\"$")
    public void iCanUpdateTheNumberOfPeriodsTo(final String numberOfPeriods) {
        returnSelector.updateTheNumberOfPeriods(numberOfPeriods);
    }

    @Then("^I get a too few periods selected error$")
    public void iGetATooFewPeriodsSelectedError() {
        returnSelector.checkTooFewPeriodsError();
    }

    @Then("^I get too many adHoc dates selected error$")
    public void iGetTooManyAdHocDatesSelectedError() {
        returnSelector.checkAdHocSelectionErrorMessage();
    }

    @Then("^I get too few adHoc dates selected error$")
    public void iGetTooFewAdHocDatesSelectedError() {
        returnSelector.checkAdHocSelectionMinErrorMessage();
    }

    @Then("the Date Range option is set as selected by default$")
    public void optionIsSetAsSelectedByDefault() {
        returnSelector.shouldHaveDateRangeSelected();
    }

    @Then("^the following no previous returns are available message is displayed")
    public void theFollowingMessageIsDisplayed() {
        String noPreviousError = "No previous dates are available for this Reporting Date. \n"
                + "You can try a different Reporting Date or Return.";
        $(By.xpath("//div[@id='qa-reference-date-group']/..//p[contains(@class,'message')]"))
                .shouldHave(text(noPreviousError));
    }

    @Then("^I should see a message informing me that only one historic form exists and inviting me to switch to Variance View$")
    public void iShouldSeeAMessageInformingMeThatOnlyOneHistoricFormExistsAndInvitingMeToSwitchToVarianceView() {
        String notEnoughTrendsError =
                "Trends need at least two previous dates - not enough exist for this Return. You can still analyse the Variance.";
        $(By.id("qa-previous-returns-group"))
                .shouldHave(text(notEnoughTrendsError));
    }

    @When("^I create a Date Range Trend Analysis for the previous reference date (\\d+/\\d+/\\d+)$")
    public void iCreateADateRangeTrendAnalysis(
            final @Transform(ToLocalDate.class) LocalDate previousReferenceDate) {
        returnSelector.createDateRangeTrendAnalysis(previousReferenceDate);
    }

    @When("^I create an Ad-hoc Trend Analysis for the previous reference dates (.*)$")
    public void iCreateAnAdHocTrendAnalysisForThePreviousReferenceDates(
            final @Transform(ToLocalDates.class) List<LocalDate> adHocDates) {

        returnSelector.createAdHocTrendAnalysis(adHocDates);
    }

    @When("^I create a Trend Analysis for the last (\\d+) periods$")
    public void iCreateATrendAnalysisForNumberOfPeriods(final int periods) {
        returnSelector.createTrendAnalysisForNumberOfPeriods(periods);
    }

    @When("^I create a Variance Analysis for previous reference date (\\d+/\\d+/\\d+)$")
    public void iCreateAVarianceAnalysis(final @Transform(ToLocalDate.class) LocalDate previousReferenceDate) {
        returnSelector.createVarianceAnalysis(previousReferenceDate);
    }

    @And("^Analysis Module's selected return details are regulator \"([^\"]*)\", entity \"([^\"]*)\", return \"([^\"]*)\" and reference date (\\d+/\\d+/\\d+)$")
    public void analysisModulesSelectedReturnDetailsAreRegulatorEntityReturnAndReferenceDate(
            final ProductPackage regulator,
            final String entityName,
            final String returnName,
            final @Transform(ToLocalDate.class) LocalDate referenceDate) {

        returnSelection = new ReturnSelection(regulator, entityName, returnName, null, referenceDate);

        returnSelector
                .selectRegulator(regulator.fullName())
                .selectEntity(entityName)
                .selectReturn(returnName)
                .selectReferenceDate(referenceDate);
    }

    @Then("^I should see regulator \"([^\"]*)\", entity \"([^\"]*)\", return \"([^\"]*)\", reporting date (\\d+/\\d+/\\d+)$")
    public void iShouldSeePrepopulatedRegulatorEntityReturnReportingDate(
            final ProductPackage productPackage,
            final String entity,
            final String returnName,
            final @Transform(ToLocalDate.class) LocalDate date) {
        returnSelector.checkRegulator(" "+productPackage.fullName()+" ");
        returnSelector.checkEntity(entity);
        returnSelector.checkReturn(returnName);
        returnSelector.checkReportingDate(date);
    }

    @When("^I select the \"([^\"]*)\" cellgroup and create$")
    public void iSelectTheCellgroupAndCreate(final String cellGroupName) {
        returnSelector.selectCellGroup(cellGroupName);
        returnSelector.create();
    }

    @Then("^I refresh the current page$")
    public void iRefreshTheCurrentPage() {
        Selenide.refresh();
    }

    @Then("^I should be able to access Analysis Module$")
    public void iShouldBeAbleToAccessAnalysisModule() {
        returnSelectorPanel.analysisModuleIsVisible();
    }

    @Then("^I should not see Analysis Module button$")
    public void iShouldNotSeeAnalysisModuleButton() {
        returnSelectorPanel.analysisModuleIsNotVisible();
    }

    @And("^I close the Analysis Module window$")
    public void iCloseTheAnalysisModuleWindow() {
        Selenide.close();
    }

    @And("^I close Analysis Module$")
    public void iCloseAnalysisModule() {
        Selenide.switchTo().window("Lombard Risk").close();
    }
}