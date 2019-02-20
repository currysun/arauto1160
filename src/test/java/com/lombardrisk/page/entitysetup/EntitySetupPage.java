package com.lombardrisk.page.entitysetup;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.ProductPackage;
import com.lombardrisk.page.event.Notification;
import com.lombardrisk.page.header.MainMenu;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EntitySetupPage {

    private final SelenideElement addNewEntityButton =
            $(By.xpath("//div[@id='layoutPaneNorth']//div[@class='rpImageButtonAddClass']/a/img"));

    private final SelenideElement entitySetupAssignButton = $(By.xpath("//span[@id='editGroupDlg_title']" +
            "[text()='Entity Setup']/../..//div[2]/div/button/span[text()='Assign']"));

    private final SelenideElement saveApprovalWorkflowButton =
            $(By.id("configureUserPrivilegesforReturnsDlgForm:configureUserPrivilegesForReturnsDlgTabContainer:addWorkflow"));

    private final SelenideElement importButton = $(By.id("securityExportFormId:importFile"));

    private final SelenideElement entityNameInput = $(By.id("addEntityDialogForm:EntityName:EntityName"));
    private final SelenideElement entityCodeInput = $(By.id("addEntityDialogForm:EntityCode:EntityCode"));
    private final SelenideElement entityExternalCodeInput = $(By.id("addEntityDialogForm:extCode:extCode"));
    private final SelenideElement entityDescriptionInput = $(By.id("addEntityDialogForm:EntityDescription"));
    private final SelenideElement consolidationBasisDropDown =
            $(By.xpath("//div[contains(@id,'editGroupForm')]/div[3]/span"));

    private final SelenideElement createNewEntityButton = $(By.id("addEntityDialogForm:confirm"));

    private final Config config;
    private final Notification notification;
    private final MainMenu mainMenu;
    private final AddNewEntityDialog addNewEntityDialog;
    private final EntitySetupDialog entitySetupDialog;
    private final ImportSettingsDialog importSettingsDialog;

    public EntitySetupPage(
            final Config config,
            final Notification notification,
            final MainMenu mainMenu,
            final AddNewEntityDialog addNewEntityDialog,
            final EntitySetupDialog entitySetupDialog,
            final ImportSettingsDialog importSettingsDialog) {
        this.config = config;
        this.notification = notification;
        this.mainMenu = mainMenu;
        this.addNewEntityDialog = addNewEntityDialog;
        this.entitySetupDialog = entitySetupDialog;
        this.importSettingsDialog = importSettingsDialog;
    }

    public EntitySetupPage open() {
        mainMenu.openSettings()
                .entitySetup();
        notification.loadingProgressShouldNotBeDisplayed();
        addNewEntityButton.shouldBe(visible);
        return this;
    }

    EntitySetupPage entityName(final String entityName) {
        entityNameInput.setValue(entityName);
        return this;
    }

    EntitySetupPage entityCode(final String entityCode) {
        entityCodeInput.setValue(entityCode);
        return this;
    }

    EntitySetupPage entityExternalCode(final String entityExternalCode) {
        entityExternalCodeInput.setValue(entityExternalCode);
        return this;
    }

    EntitySetupPage entityDescription(final String entityDescription) {
        entityDescriptionInput.setValue(entityDescription);
        return this;
    }

    EntitySetupPage consolidationBasis(final String consoleType) {
        consolidationBasisDropDown.click();
        $(By.xpath("//div[contains(@id,'editGroupForm')]/div/ul/li[text()='" + consoleType + "']")).shouldBe(visible).click();
        return this;
    }

    void addEntity() {
        createNewEntityButton.click();

        notification
                .shouldBeNoErrors()
                .shouldCloseGrowlNotification();
    }

    public EntitySetupPage openOrCreateEntity(
            final String entityName,
            final String entityDescription,
            final String entityCode,
            final String entityExternalCode) {
        openOrCreateEntity(entityName, entityDescription, entityCode, entityExternalCode, null);
        return this;
    }

    public EntitySetupPage openOrCreateEntity(
            final String entityName,
            final String entityDescription,
            final String entityCode,
            final String entityExternalCode,
            final String consoleType) {

        if (findEntityItem(entityName).is(not(visible))) {
            openAddNewEntityDialog()
                    .entityName(entityName)
                    .entityCode(entityCode)
                    .entityExternalCode(entityExternalCode);
            if (consoleType != null) {
            addNewEntityDialog
                .consolidationBasis(consoleType);
            }
            entityDescription(entityDescription);
            addEntity();
            //findEntityItem(entityName).click();
        } else {
            findEntityItem(entityName).click();
        }
        return this;
    }

    public EntitySetupPage selectEntity(final String entityName) {
        entitySetupDialog.open(entityName);
        notification.loadingProgressShouldNotBeDisplayed();
        return this;
    }

    public void openAssignReturnsTab() {
        entitySetupDialog.openAssignReturnsTab();
    }

    public void configureReturn(final ProductPackage productPackage, final String returnName) {
        entitySetupDialog.configureReturn(productPackage, returnName);
    }

    public void assignConfigForEntities() {
        entitySetupAssignButton.click();

        notification.shouldCloseGrowlNotification();
    }

    public EntitySetupPage addPrivilegesToReturn(
            final ProductPackage productPackage, final List<String> returnNames, final List<String> userGroups) {

        for (String returnName : returnNames) {
            addPrivilegeToReturn(productPackage, returnName, userGroups);
        }
        return this;
    }

    private void addPrivilegeToReturn(
            final ProductPackage productPackage,
            final String returnName,
            final List<String> userGroups) {
        entitySetupDialog
                .selectReturnToConfigure(productPackage, returnName)
                .configureReturn(productPackage, returnName)
                .setUpUserGroupsWithPrivileges(userGroups);
    }

    public void assignWorkflow(final String workflow) {
        configureApprovalWorkflowTab()
                .waitUntil(visible, config.maxTimeout())
                .click();

        SelenideElement liveReturnsErrorMessage = $(By.id("workflowLiveReturnsError']"));
        liveReturnsErrorMessage.shouldNotHave(text("live returns"));

        selectWorkflow(workflow);
    }

    public void assignGroupToStep(final String groupName, final String stepName) {
        SelenideElement stepNameDropDown = $(By.xpath("//div/dl/dt//div/span[text()='"
                + stepName + ":']/..//div[@class='ui-selectonemenu-trigger ui-state-default ui-corner-right']"));
        stepNameDropDown.click();

        int stepIndex = 0;
        int stepsInWorkflow = $$(By.xpath("//div/dl/dt")).size();
        for (int i = 1; i <= stepsInWorkflow; i++) {
            String currentStepName = $(By.xpath("//div/dl/dt[" + i + "]//div/span")).getText();
            currentStepName = currentStepName.substring(0, currentStepName.length() - 1);
            if (stepName.equalsIgnoreCase(currentStepName)) {
                stepIndex = i;
            }
        }
        $(By.xpath("(//li[text()='" + groupName + "'])[" + stepIndex + "]")).click();
        notification.loadingProgressShouldNotBeDisplayed();
    }

    public ImportSettingsDialog openImportDialog() {
        importButton.click();

        importSettingsDialog.shouldBeOpen();

        return importSettingsDialog;
    }

    public void saveApprovalWorkflow() {
        saveApprovalWorkflowButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
        saveApprovalWorkflowButton.shouldNotBe(visible);
    }

    private static SelenideElement configureApprovalWorkflowTab() {
        return $(By.xpath("//div[@id='configureUserPrivilegesforReturnsDlgForm:" +
                "configureUserPrivilegesForReturnsDlgTabContainer']" +
                "/ul[@class='ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all']/li[2]/a"));
    }

    private static SelenideElement findEntityItem(final String entityName) {
        return $(By.xpath("//label[normalize-space(text())='" + entityName + "']/../img"));
    }

    private AddNewEntityDialog openAddNewEntityDialog() {
        addNewEntityButton.click();

        return addNewEntityDialog;
    }

    private void selectWorkflow(final String workflow) {
        selectWorkflowLink().click();
        notification.loadingProgressShouldNotBeDisplayed();

        findWorkflowOption(workflow).click();
        applyButton().click();

        notification.loadingProgressShouldNotBeDisplayed();
    }

    private static SelenideElement selectWorkflowLink() {
        return $(By.id("configureUserPrivilegesforReturnsDlgForm:" +
                "configureUserPrivilegesForReturnsDlgTabContainer:changeWorkflow"));
    }

    private static SelenideElement findWorkflowOption(final String workflowName) {
        return $(By.xpath("(//td/label[contains(., '" + workflowName + "')])[1]"));
    }

    private static SelenideElement applyButton() {
        return $(By.id("addWorkflowForm:saveWorkflowforReturns"));
    }
}
