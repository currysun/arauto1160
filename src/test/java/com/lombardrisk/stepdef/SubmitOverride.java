package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.returninstance.ExportDialog;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import com.lombardrisk.stepdef.transformer.ToLocalDate;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class SubmitOverride extends StepDef {

    @Autowired
    private ExportDialog exportDialog;
    @Autowired
    private Notification notification;
    @Autowired
    private ReturnInstancePage returnInstancePage;

    @And("^I select the \"([^\"]*)\" entity and \"([^\"]*)\" module with reference date (\\d+/\\d+/\\d+)$")
    public void iSelectTheEntityAndModuleWithReferenceDate(
            final String entity,
            final String returnExporting,
            final @Transform(ToLocalDate.class) LocalDate returnDate) {
        exportDialog
                .selectEntity(entity);
        notification
                .loadingProgressShouldNotBeDisplayed();
        exportDialog
                .selectReferenceDate(returnDate);
        notification
                .loadingProgressShouldNotBeDisplayed();
        exportDialog
                .selectModule(returnExporting);
        notification
                .loadingProgressShouldNotBeDisplayed();
    }

    @Then("^The \"([^\"]*)\" return can not be submitted$")
    public void theReturnCanNotBeSubmitted(final String returnToExport) {
        exportDialog
                .returnItemShouldBeDisabled(returnToExport);
    }

    @Then("^The \"([^\"]*)\" return can be submitted$")
    public void theReturnCanBeSubmitted(final String returnToExport) {
        exportDialog
                .selectAReturnAndExport(returnToExport);
    }

    @And("^TEMPORARY STEP PLEASE REMOVE I close the Export window$")
    public void temporarySTEPPLEASEREMOVEICloseTheExportWindow() {
        exportDialog.close();
    }

    @And("^I close the Export window$")
    public void ICloseTheExportWindow() {
        exportDialog.close();
    }

    @When("^I open the Export to \"([^\"]*)\" dialog from the return$")
    public void iOpenTheExportToDialogFromTheReturn(final String submitType) {
        returnInstancePage
                .submitToExportType(submitType);
    }

    @Then("^The \"([^\"]*)\" return can be force submitted but I choose to cancel from the force submit dialog$")
    public void theReturnCanBeForceSubmittedButIChooseToCancelFromTheForceSubmitDialog(final String returnToExport) {
        exportDialog
                .selectAReturnAndForceExport(returnToExport)
                .cancelForceSubmit();
    }

    @Then("^My export type is \"([^\"]*)\"$")
    public void myExportTypeIs(final String exportType) {
        exportDialog
                .shouldBeDisplayed(exportType);
    }

    @Then("^The \"([^\"]*)\" return can be force submitted with a comment of \"([^\"]*)\"$")
    public void theReturnCanBeForceSubmittedWithACommentOf(final String returnToExport, final String comment) {
        exportDialog
                .selectAReturnAndForceExport(returnToExport);
        notification.loadingProgressShouldNotBeDisplayed();

        exportDialog
                .forceSubmitComment(comment);

        notification.loadingProgressShouldNotBeDisplayed();
    }
}
