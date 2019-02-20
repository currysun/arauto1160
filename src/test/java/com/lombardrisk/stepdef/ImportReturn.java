package com.lombardrisk.stepdef;

import com.lombardrisk.page.returninstance.ImportDialog;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportReturn {

    @Autowired
    private ImportDialog importDialog;

    @And("^I choose to import \"([^\"]*)\"$")
    public void iChooseToImport(String fileName) {
        ReturnInstancePage.importAdjustment();
        importDialog.upload(fileName);
    }

    @And("^I select the scaled option$")
    public void iSelectTheScaledOption() {
        importDialog.selectScaled();
    }

    @And("^I select the no scale option$")
    public void iSelectTheNoScaleOption() {
        importDialog.selectNoScale();
    }

    @And("^I import the file$")
    public void iImportTheFile() {
        importDialog.importFile();
    }

    @Then("^value in cell \"([^\"]*)\" equals \"([^\"]*)\"$")
    public void valueInCellEquals(String cellId, String cellValue) {
        ReturnInstancePage.cellShouldHaveValue(cellId, cellValue);
    }
}