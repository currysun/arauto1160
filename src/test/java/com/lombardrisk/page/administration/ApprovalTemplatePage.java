package com.lombardrisk.page.administration;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.PageUtils.setValue;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ApprovalTemplatePage {

    private final ElementsCollection templates = $$("#approvalTemplates_data tr");
    private final SelenideElement nameInput = $(By.id("approvalTemplateDetails:name"));

    private final SelenideElement addNewTemplateButton = $(By.id("addTemplate"));

    private final SelenideElement nameErrorMessage = $(By.id("approvalTemplateDetails:nameMessage"));
    private final SelenideElement descriptionInput = $(By.id("approvalTemplateDetails:description"));
    private final SelenideElement activeCheckbox = $(By.xpath("//*[@id='approvalTemplateDetails:active']/div[2]"));

    private final SelenideElement firstStepNameInput = $(By.id("approvalTemplateDetails:approvalSteps:0:stepName"));
    private final SelenideElement firstStepNameBlankErrorMessage =
            $(By.id("approvalTemplateDetails:approvalSteps:0:stepNameMessage"));
    private final SelenideElement firstStepNameExistsErrorMessage =
            $$(By.cssSelector(".errorDuplicateStepNameMessage")).first();
    private final SelenideElement stepsErrorMessages = $(By.id("approvalTemplateDetails:approvalStepsMessages"));
    private final SelenideElement firstStepCountInput =
            $(By.id("approvalTemplateDetails:approvalSteps:0:stepApprovalCount_input"));
    private final SelenideElement addNewStepButton = $(By.id("approvalTemplateDetails:addNewStep"));

    private final SelenideElement lastStepNameInput =
            $(By.cssSelector("#approvalTemplateDetails\\3a approvalSteps_list > dt:last-child > div > " +
                    "div:nth-child(1) > div:last-child > input"));
    private final SelenideElement lastStepCountInput =
            $(By.cssSelector("#approvalTemplateDetails\\3a approvalSteps_list > dt:last-child > div > " +
                    "div:nth-child(2) > div:last-child > span > input"));

    private final SelenideElement createTemplateButton = $(By.id("approvalTemplateDetails:create"));
    private final SelenideElement cancelButton = $(By.id("approvalTemplateDetails:cancel"));
    private final SelenideElement updateButton = $(By.id("approvalTemplateDetails:update"));

    private final SelenideElement yesDialogButton = $("#yesButton");

    private final MainMenu mainMenu;
    private final Notification notification;

    public ApprovalTemplatePage(final MainMenu mainMenu, final Notification notification) {
        this.mainMenu = requireNonNull(mainMenu);
        this.notification = requireNonNull(notification);
    }

    public ApprovalTemplatePage open() {
        mainMenu.openSettings()
                .administration().approvalWorkflowTemplates();

        addNewTemplateButton
                .shouldBe(visible)
                .shouldBe(enabled);
        return this;
    }

    public ApprovalTemplatePage addNewTemplate() {
        addNewTemplateButton.click();

        createTemplateButton
                .shouldBe(visible)
                .shouldBe(enabled);
        return this;
    }

    public String selectAnyTemplate() {
        templates.shouldHave(sizeGreaterThan(0));

        templates.get(nextInt(0, templates.size())).click();
        return nameInput.getValue();
    }

    public ApprovalTemplatePage editTemplate(final String templateName, final String newTemplateName) {
        findTemplateByName(templateName).parent().parent().find("img").click();
        nameInput.setValue(newTemplateName);

        return this;
    }

    public ApprovalTemplatePage selectTemplateWithName(final String templateName) {
        findTemplateByName(templateName).click();

        return this;
    }

    public ApprovalTemplatePage templateName(final String templateName) {
        nameInput.setValue(templateName);

        return this;
    }

    public String setUniqueTemplateName(final String templatePrefix) {
        String templateName;

        if (isBlank(templatePrefix)) {
            templateName = EMPTY;
        } else {
            templateName = templatePrefix + " - " + randomAlphanumeric(30);
        }
        setValue(nameInput, templateName);

        return templateName;
    }

    public ApprovalTemplatePage templateDescription(final String description) {
        setValue(descriptionInput, description);

        return this;
    }

    public ApprovalTemplatePage setFirstStep(final String stepName, final int stepCount) {
        firstStepName(stepName);
        setValue(firstStepCountInput, stepCount);

        return this;
    }

    public void firstStepName(final String stepName) {
        setValue(firstStepNameInput, stepName);
    }

    public void addNewStep(final String stepName, final int stepCount) {
        addNewStepButton.click();

        setValue(lastStepNameInput, stepName);
        setValue(lastStepCountInput, stepCount);
    }

    public void shouldHaveDefaults() {
        nameInput
                .shouldBe(empty)
                .shouldBe(enabled);
        descriptionInput
                .shouldBe(empty)
                .shouldBe(enabled);
        shouldBeActive();

        firstStepNameInput
                .shouldBe(empty)
                .shouldBe(enabled);
        firstStepCountInput
                .shouldBe(enabled)
                .shouldHave(value("1"));
    }

    public ApprovalTemplatePage shouldHaveReadOnlyInputs() {
        nameInput.shouldBe(disabled);
        descriptionInput.shouldBe(disabled);

        activeCheckbox.shouldHave(cssClass("ui-state-disabled"));

        firstStepNameInput.shouldBe(disabled);
        firstStepCountInput.shouldBe(disabled);
        return this;
    }

    public void shouldHaveStep(final int stepOrder, final String expectedStepName, final int expectedStepCount) {
        stepNameInput(stepOrder)
                .shouldHave(value(expectedStepName));

        stepCountInput(stepOrder)
                .shouldHave(value(Integer.toString(expectedStepCount)));
    }

    public ApprovalTemplatePage shouldBeActive() {
        activeCheckbox.shouldHave(cssClass("ui-state-active"));
        return this;
    }

    public ApprovalTemplatePage shouldHaveTemplateName(final String templateName) {
        nameInput.shouldHave(value(templateName));
        return this;
    }

    public ApprovalTemplatePage shouldNotHaveTemplateName(final String templateName) {
        nameInput.shouldNotHave(value(templateName));
        return this;
    }

    public void shouldHaveTemplateNameExistsErrorMessage() {
        nameErrorMessage.shouldHave(text("Template name already exists"));
    }

    public void shouldHaveTemplateNameBlankErrorMessage() {
        nameErrorMessage.shouldHave(text("Name cannot be blank"));
    }

    public ApprovalTemplatePage shouldHaveTemplateDescription(final String templateDescription) {
        descriptionInput.shouldHave(value(templateDescription));
        return this;
    }

    public ApprovalTemplatePage shouldNotHaveTemplateDescription(final String templateDescription) {
        descriptionInput.shouldNotHave(value(templateDescription));
        return this;
    }

    public ApprovalTemplatePage shouldHaveFirstStepName(final String firstStepName) {
        firstStepNameInput.shouldHave(value(firstStepName));
        return this;
    }

    public void shouldHaveFirstStepCount(final int firstStepCount) {
        firstStepCountInput.shouldHave(value(Integer.toString(firstStepCount)));
    }

    public void shouldNotHaveFirstStepName(final String firstStepName) {
        firstStepNameInput.shouldNotHave(value(firstStepName));
    }

    public void shouldHaveFirstStepNameExistsErrorMessage() {
        firstStepNameExistsErrorMessage.shouldHave(text("Step name already exists"));
        stepsErrorMessages.shouldHave(matchesText("Change the following step names to be unique: '.*'"));
    }

    public void shouldHaveFirstStepNameBlankErrorMessage() {
        firstStepNameBlankErrorMessage.shouldHave(text("Name cannot be blank"));
    }

    public void createTemplate() {
        createTemplateButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        notification.shouldCloseGrowlNotification();
    }

    public void tryCreateTemplate() {
        createTemplateButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
    }

    public void updateTemplate() {
        updateButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        notification.shouldCloseGrowlNotification();
    }

    public void cancelTemplate() {
        cancelButton.click();
        yesDialogButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
    }

    public ApprovalTemplatePage shouldNotHaveCreateButton() {
        createTemplateButton.shouldNotBe(visible);
        return this;
    }

    public void shouldNotHaveCancelButton() {
        cancelButton.shouldNotBe(visible);
    }

    public ApprovalTemplatePage shouldNotHaveUpdateButton() {
        updateButton.shouldNotBe(visible);
        return this;
    }

    public ApprovalTemplatePage shouldNotHaveAddStepButton() {
        addNewStepButton.shouldNotBe(visible);
        return this;
    }

    private static SelenideElement stepNameInput(final int stepOrder) {
        return $(By.id("approvalTemplateDetails:approvalSteps:" + stepOrder + ":stepName"));
    }

    private static SelenideElement stepCountInput(final int stepOrder) {
        return $(By.id("approvalTemplateDetails:approvalSteps:" + stepOrder + ":stepApprovalCount_input"));
    }

    private static SelenideElement findTemplateByName(final String templateName) {
        return $(By.xpath("//*[@id='approvalTemplates_data']//*[text()='" + templateName + "']"));
    }
}
