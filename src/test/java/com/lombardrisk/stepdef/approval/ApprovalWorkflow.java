package com.lombardrisk.stepdef.approval;

import com.codeborne.selenide.Selenide;
import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.administration.ApprovalTemplatePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ApprovalWorkflow extends StepDef {

    @Autowired
    private ApprovalTemplatePage approvalTemplatePage;

    private String templateName;
    private String templateDescription;
    private String firstStepName;
    private int firstStepCount;
    private final List<Pair<String, Integer>> otherSteps = new ArrayList<>(5);

    @Given("^I am on the Approval Workflow Template page$")
    public void IAmOnTheApprovalTemplatePage() {
        approvalTemplatePage.open();
    }

    @When("^I have created the new template$")
    public void iHaveCreatedTheNewTemplate() {
        approvalTemplatePage.createTemplate();
    }

    @When("^I try to create a new template$")
    public void iTryToCreateANewTemplate() {
        approvalTemplatePage.tryCreateTemplate();
    }

    @Then("^my created template should be available$")
    public void myCreatedTemplateShouldBeAvailable() {
        approvalTemplatePage
                .selectTemplateWithName(templateName)
                .shouldHaveFirstStepName(firstStepName)
                .shouldHaveFirstStepCount(firstStepCount);

        for (int i = 0; i < otherSteps.size(); i++) {
            Pair<String, Integer> anotherStep = otherSteps.get(i);
            int stepOrder = i + 1;
            approvalTemplatePage.shouldHaveStep(
                    stepOrder, anotherStep.getLeft(), anotherStep.getRight());
        }
    }

    @When("^I press the cancel button$")
    public void iPressTheCancelButton() {
        approvalTemplatePage.cancelTemplate();
    }

    @Then("^all details entered should be removed from my template$")
    public void allDetailsEnteredShouldBeRemovedFromMyTemplate() {
        approvalTemplatePage
                .addNewTemplate()
                .shouldHaveDefaults();
    }

    @When("^I press the add a new template button from the left hand pane$")
    public void iPressTheAddANewTemplateButtonFromTheLeftHandPane() {
        approvalTemplatePage.addNewTemplate();
    }

    @When("^I select any existing template$")
    public void iSelectAnyExistingTemplate() {
        templateName = approvalTemplatePage.selectAnyTemplate();
    }

    @Then("^all details should be updated$")
    public void allDetailsEnteredShouldBeAsTheSelectedTemplate() {
        approvalTemplatePage
                .shouldHaveTemplateName(templateName)
                .shouldHaveTemplateDescription(templateDescription)
                .shouldHaveFirstStepName(firstStepName);
    }

    @Then("^all details entered should be different from the selected template$")
    public void allDetailsEnteredShouldNotBeAsTheSelectedTemplate() {
        approvalTemplatePage.selectAnyTemplate();

        approvalTemplatePage
                .shouldNotHaveTemplateName(templateName)
                .shouldNotHaveTemplateDescription(templateDescription)
                .shouldNotHaveFirstStepName(firstStepName);
    }

    @When("^I refresh the page$")
    public void iHitRefreshOnTheScreen() {
        Selenide.refresh();
    }

    @Then("^all my details should still remain on screen$")
    public void allMyDetailsShouldStillRemainOnScreen() {
        approvalTemplatePage
                .shouldHaveTemplateName(templateName)
                .shouldHaveTemplateDescription(templateDescription);
    }

    @Then("^an error should stop me from entering the same template name$")
    public void anErrorShouldStopMeFromEnteringTheSameTemplateName() {
        approvalTemplatePage.shouldHaveTemplateNameExistsErrorMessage();
    }

    @And("^I request a new template with the same name$")
    public void iRequestANewTemplateWithSameName() {
        Pair<String, Integer> firstStepNameAndCount = otherSteps.iterator().next();

        approvalTemplatePage
                .addNewTemplate()
                .shouldBeActive()
                .templateName(templateName)
                .templateDescription(templateDescription)

                .setFirstStep(firstStepName, firstStepCount)
                .addNewStep(firstStepNameAndCount.getLeft(), firstStepNameAndCount.getRight());
    }

    @Then("^an error should stop me from entering a value in my approval step$")
    public void anErrorShouldStopMeFromEnteringNoValueInMyApprovalStep() {
        approvalTemplatePage.shouldHaveFirstStepNameBlankErrorMessage();
    }

    @Then("^an error should stop me from leaving the template name blank$")
    public void anErrorShouldStopMeFromLeavingTheTemplateNameBlank() {
        approvalTemplatePage.shouldHaveTemplateNameBlankErrorMessage();
    }

    @Then("^an error should stop me from entering a duplicate step name$")
    public void anErrorShouldStopMeFromEnteringADuplicateStepName() {
        approvalTemplatePage.shouldHaveFirstStepNameExistsErrorMessage();
    }

    @Then("^my highlighted template should be in read only mode$")
    public void myHighlightedTemplateShouldBeInReadOnlyMode() {
        approvalTemplatePage
                .shouldHaveReadOnlyInputs()
                .shouldNotHaveAddStepButton()
                .shouldNotHaveCreateButton()
                .shouldNotHaveUpdateButton()
                .shouldNotHaveCancelButton();
    }

    @When("^I navigate away from the template page and return$")
    public void iNavigateAwayFromTheTemplatePageAndReturn() {
        Selenide.back();
        $(By.id("formFilter:regulator")).shouldBe(visible);

        Selenide.forward();
        approvalTemplatePage.addNewTemplate();
    }

    @And("^I edit the template name to \"([^\"]*)\", the description to \"([^\"]*)\"$")
    public void iEditTheTemplateNameAndDescription(
            final String newTemplateName, final String newTemplateDescription) {

        approvalTemplatePage
                .editTemplate(templateName, newTemplateName)
                .templateDescription(newTemplateDescription);

        templateName = approvalTemplatePage.setUniqueTemplateName(newTemplateName);
        templateDescription = newTemplateDescription;
    }

    @And("^the first step name to \"([^\"]*)\"$")
    public void theFirstStepNameTo(final String newFirstStepName) {
        approvalTemplatePage.firstStepName(newFirstStepName);

        firstStepName = newFirstStepName;
    }

    @And("^I update the template$")
    public void iUpdateTheTemplate() {
        approvalTemplatePage.updateTemplate();
    }

    @And("^I request a new template with the name \"([^\"]*)\", the description \"([^\"]*)\"$")
    public void iRequestANewTemplateWithNameAndDescription(
            final String templatePrefix, final String templateDescription) {

        approvalTemplatePage.addNewTemplate();
        templateName = approvalTemplatePage.setUniqueTemplateName(templatePrefix);

        approvalTemplatePage
                .shouldBeActive()
                .templateDescription(templateDescription);

        this.templateDescription = templateDescription;
    }

    @And("^a step with the name \"([^\"]*)\", the approval count (\\d+)$")
    public void approvalStepsOfTheFirstStepNameOfAndApprovalCounts(
            final String firstStepName, final int firstStepCount) {

        approvalTemplatePage
                .setFirstStep(firstStepName, firstStepCount);
        this.firstStepName = firstStepName;
        this.firstStepCount = firstStepCount;
    }

    @And("^another step with the name \"([^\"]*)\", the approval count (\\d+)$")
    public void anotherStepWithNameAndApprovalCount(final String anotherStepName, final int anotherStepCount) {
        approvalTemplatePage.addNewStep(anotherStepName, anotherStepCount);

        otherSteps.add(Pair.of(anotherStepName, anotherStepCount));
    }


}
