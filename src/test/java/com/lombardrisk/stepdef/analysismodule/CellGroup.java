package com.lombardrisk.stepdef.analysismodule;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.analysismodule.AnalysisModuleWaiter;
import com.lombardrisk.page.analysismodule.cellgroup.AddCellGroupDialog;
import com.lombardrisk.page.analysismodule.cellgroup.CellGroupManagerDialog;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class CellGroup extends StepDef {

    private static final int CELL_GROUP_NAME_MAX_LENGTH = 25;

    @Autowired
    private CellGroupManagerDialog cellGroupManagerDialog;
    @Autowired
    private AnalysisModuleWaiter analysisModuleWaiter;
    @Autowired
    private AddCellGroupDialog addCellGroupDialog;

    private String selectedCellGroupName;

    @After("@CellGroupCleanUp")
    public void afterAttachmentsConfiguration() {
        if (selectedCellGroupName != null) {

            openCellGroupReturn();

            cellGroupManagerDialog.open()
                    .deleteCellGroup(selectedCellGroupName);
        }
    }

    @Then("^I should see the cell group in the list on cell group manager window$")
    public void iShouldSeeTheCellGroupInTheList() {
        cellGroupManagerDialog.open()
                .shouldHaveCellGroup(selectedCellGroupName);
    }

    @Given("^I create a cell group \"([^\"]*)\"$")
    public void iCreateACellGroup(final String cellGroupName) {
        selectedCellGroupName = generateUniqueCellGroupName(cellGroupName);

        cellGroupManagerDialog.open()
                .createCellGroup(selectedCellGroupName);
    }

    private static String generateUniqueCellGroupName(final String cellGroupNamePrefix) {
        if (cellGroupNamePrefix.length() == CELL_GROUP_NAME_MAX_LENGTH) {
            return cellGroupNamePrefix;
        }
        String cellGroupName = cellGroupNamePrefix + SPACE + randomAlphanumeric(CELL_GROUP_NAME_MAX_LENGTH);

        return cellGroupName.substring(0, CELL_GROUP_NAME_MAX_LENGTH - 1);
    }

    @Then("^I should see error message \"([^\"]*)\"$")
    public void iShouldSeeErrorMessage(final String errorMessage) {
        cellGroupManagerDialog.shouldDisplayErrorMessage(errorMessage);
    }

    @Then("^the cell group no longer includes the cells$")
    public void iShouldSeeTheEditedCellsRetainedInTheCellGroup(final DataTable dataTable) {
        cellGroupManagerDialog.shouldNotBeIncludedInCellGroup(
                selectedCellGroupName, dataTable.asList(String.class));
    }

    @Then("^I should not be able to save the cell group$")
    public void iShouldNotBeAbleToSaveTheCellGroup() {
        cellGroupManagerDialog.shouldNotBeSavable();
    }

    @Given("^I try to create a cell group \"([^\"]*)\" with no cells$")
    public void iTryToCreateACellGroupWithNoCells(final String cellGroupName) {
        cellGroupManagerDialog.open()
                .createCellGroupsWithNoCells(cellGroupName)
                .shouldNotContainAnyIncludedCells();
    }

    @Then("^If I try to rename the cell group to a blank name I shouldn't be able to save$")
    public void ifITryToRenameTheCellGroupToABlankNameIShouldnTBeAbleToSave() {
        final String selectedCellGroupName = "        ";
        cellGroupManagerDialog.open()
                .tryCreateCellGroup(selectedCellGroupName);
        cellGroupManagerDialog.shouldNotBeSavable();
        addCellGroupDialog.close();
    }

    @When("^If I try to create a cell group with the same name I should see an error stating so$")
    public void ifITryToCreateACellGroupWithTheSameNameIShouldSeeAnErrorStatingSo() {
        final String errorMessage = "This name is already taken";
        cellGroupManagerDialog
                .shouldBeVisible();
        cellGroupManagerDialog
                .open()
                .tryCreateCellGroup(selectedCellGroupName);
        cellGroupManagerDialog.shouldDisplayErrorMessage(errorMessage);
    }

    @When("^I edit the cell group to remove the cells$")
    public void iEditTheCellGroupToRemoveTheCells(final DataTable dataTable) {
        cellGroupManagerDialog
                .shouldBeVisible();
        cellGroupManagerDialog
                .open()
                .removeCellsForGroup(selectedCellGroupName, dataTable.asList(String.class));
    }

    @When("^I delete the cell group$")
    public void deleteTheCellGroupWithName() {
        cellGroupManagerDialog.open()
                .deleteCellGroup(selectedCellGroupName)
                .close();
        selectedCellGroupName = null;
    }

    @And("^I make the cell group my default$")
    public void iMakeMyDefault() {
        cellGroupManagerDialog.open()
                .makeDefault(selectedCellGroupName);
        analysisModuleWaiter.waitForFetch();
        cellGroupManagerDialog
                .close();
    }

    @Then("^I should not be able to delete \"([^\"]*)\"$")
    public void iShouldNotBeAbleToDeleteTheDefaultCellGroupCalled(final String cellGroupName) {
        cellGroupManagerDialog.open()
                .checkDeleteIsDisabled(cellGroupName);
    }

    @Then("^If I try to rename the cell group to an existing name I should see an existing name error$")
    public void ifITryToRenameTheCellGroupToAnExistingNameIShouldSeeAnExistingNameError() {
        final String existingCellgroup = "Cell Group 1";
        final String errorMessage = "This name is already taken";
        cellGroupManagerDialog.open()
                .renameTheCellGroup(selectedCellGroupName, existingCellgroup);
        cellGroupManagerDialog.shouldDisplayErrorMessage(errorMessage);
        addCellGroupDialog.close();
    }

    @Then("^I am unable to create a cell group with a special character in$")
    public void iAmUnableToCreateACellGroupWithASpecialCharacterIn() {
        cellGroupManagerDialog.open()
                .shouldDisplaySpecialCharacterNameError();
    }

    @Then("^since the cell group includes a special character I am unable to save$")
    public void sinceTheCellGroupIncludesASpecialCharacterIAmUnableToSave() {
        cellGroupManagerDialog
                .shouldNotBeSavable();
        addCellGroupDialog
                .close();
    }

    @When("^I remove all the cells from the cell group$")
    public void iRemoveAllTheCellsFromTheCellGroup() {
        cellGroupManagerDialog.open()
                .removeAllCells(selectedCellGroupName);
    }

    @Then("^the default cell group should be \"([^\"]*)\"$")
    public void theDefaultCellGroupShouldBe(final String cellGroup) {
        cellGroupManagerDialog.open()
                .systemCellGroupIsDefaulted(cellGroup);
    }

    @When("^I start to delete a cell group but cancel deletion$")
    public void iStartToDeleteACellGroupButDoNotConfirmDeletion() {
        cellGroupManagerDialog.open()
                .deleteCellGroupWithoutConfirmation(selectedCellGroupName)
                .close();
    }

    @Then("^there should be no option to delete a system cell group$")
    public void thereShouldBeNoOptionToDeleteASystemCellGroup() {
        cellGroupManagerDialog.open()
                .shouldNotHaveOptionToDeleteSystemCellGroup();
    }

    @And("^I open the cell group return in analysis module$")
    public void iOpenTheCellGroupReturnInAnalysisModule() {
        openCellGroupReturn();
    }

    private void openCellGroupReturn() {
        cellGroupManagerDialog.openReturnWithAllCellGroup(ProductPackage.ECR, 243056, "variance");
    }

    @Then("^I should see the cell group with return version$")
    public void iShouldSeeTheCellGroupWithReturnVersionOf() {
        cellGroupManagerDialog.open()
                .shouldHaveCellGroup(selectedCellGroupName)
                .verifyReturnVersion(selectedCellGroupName);
    }

    @Given("^I start to create a cell group \"([^\"]*)\"$")
    public void iStartToCreateACellGroup(final String selectedCellGroupName) {
        cellGroupManagerDialog
                .open()
                .tryCreateCellGroup(selectedCellGroupName);
    }

    @Then("^If I try to rename the cell group to a full alphabet I shouldn't be able to save$")
    public void ifITryToRenameTheCellGroupToAFullAlphabetIShouldnTBeAbleToSave() {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        cellGroupManagerDialog
                .open()
                .renameTheCellGroup(selectedCellGroupName, alphabet);
        cellGroupManagerDialog.shouldNotBeSavable();
        addCellGroupDialog.close();
    }
}
