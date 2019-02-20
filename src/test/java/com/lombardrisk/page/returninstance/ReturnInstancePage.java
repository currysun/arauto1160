package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.page.analysismodule.returnselectionpanel.ReturnSelectorPanel;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Objects.requireNonNull;

public class ReturnInstancePage {

    private final SelenideElement approvalStatusCountLabel = $(By.id("formHeader:returnInstanceStatusCount"));

    private final SelenideElement validateNowButton = $(By.id("formHeader:validateNowBtn"));
    private final SelenideElement commentLogButton = $(By.id("formHeader:commentLogBtn"));
    private final SelenideElement submitDropDownMenu = $(By.id("formHeader:exportToFile4Fed_button"));

    private final SelenideElement workflowDropdown = $(By.id("formHeader:workflow_button"));
    private final SelenideElement workflowDropdownMenu = $(By.id("formHeader:workflow_menu"));
    private final SelenideElement readyForApprovalMenuButton = $(By.id("formHeader:readyForApproval"));
    private final SelenideElement approveMenuButton = $(By.id("formHeader:approve"));
    private final SelenideElement sendForReviewMenuButton = $(By.id("formHeader:reject"));

    private final SelenideElement varianceButton = $(By.id("formHeader:varianceBtn"));
    private final SelenideElement trendsButton = $(By.id("formHeader:trendsBtn"));

    private final SelenideElement closeButton = $(By.id("formHeader:closeFormInstanceBtn"));
    private final ElementsCollection returnInstanceCells = $$(By.className("c11"));
    private final SelenideElement returnInstanceCellToEdit = $(By.id("cellEditorContainer"));
    private final SelenideElement cellEditorPanel = $(By.id("cellEditorPanel"));
    private final SelenideElement lockWarn = $(By.xpath("//textarea[contains(text(),'Are you sure you want to')]"));

    private final Notification notification;
    private final ReadyForApprovalDialog readyForApprovalDialog;
    private final ApprovalDialog approvalDialog;
    private final RejectDialog rejectDialog;
    private final CommentLogDialog commentLogDialog;
    private final ReturnSelectorPanel returnSelectorPanel;

    private static int MAX_RETRIES = 5;

    public ReturnInstancePage(
            final Notification notification,
            final ReadyForApprovalDialog readyForApprovalDialog,
            final ApprovalDialog approvalDialog,
            final RejectDialog rejectDialog,
            final CommentLogDialog commentLogDialog,
            final ReturnSelectorPanel returnSelectorPanel) {
        this.notification = requireNonNull(notification);
        this.readyForApprovalDialog = requireNonNull(readyForApprovalDialog);
        this.approvalDialog = requireNonNull(approvalDialog);
        this.rejectDialog = requireNonNull(rejectDialog);
        this.commentLogDialog = requireNonNull(commentLogDialog);
        this.returnSelectorPanel = requireNonNull(returnSelectorPanel);
    }

    public static void importAdjustment() {
        $("#formHeader\\:adjust_button").shouldBe(visible).click();
        $("#formHeader\\:importAdjustLog").shouldBe(visible).click();
    }

    public static void cellShouldHaveValue(final String cellId, final String cellValue) {
        String val = $("#" + cellId).shouldBe(visible).getValue();
        $("#" + cellId).shouldHave(value(cellValue));
    }

    public void shouldHaveApprovalStatusAndCount(final String approvalStatusCount) {
        approvalStatusCountLabel.shouldHave(text(approvalStatusCount));
    }

    public void openCommentLog() {
        notification.loadingProgressShouldNotBeDisplayed();
        commentLogButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        sleep(5000);
        notification.loadingProgressShouldNotBeDisplayed();

        commentLogDialog.shouldBeDisplayed();
    }

    public void shouldBeOpen() {

        int retries = 0;
        while(!validateNowButton.exists() && retries < MAX_RETRIES) {
            System.out.println("shouldBeOpen: Waiting on ValdateNowButton");
            Selenide.sleep(2000);

            retries++;
        }

        if(retries == MAX_RETRIES) {
            System.out.println("Problem with validateNowButton. Expect the test to fail");
        }

        validateNowButton.shouldBe(visible);
    }

    public ReadyForApprovalDialog openReadyForApprovalDialog() {
        notification.loadingProgressShouldNotBeDisplayed();
        clickOnReadyForApprovalMenuLink();

        notification.loadingProgressShouldNotBeDisplayed();
        sleep(3000);
        notification.loadingProgressShouldNotBeDisplayed();

        readyForApprovalDialog.shouldBeDisplayed();
        return readyForApprovalDialog;
    }

    public ApprovalDialog openApproveDialog() {
        notification.loadingProgressShouldNotBeDisplayed();
        clickOnApproveMenuLink();

        notification.loadingProgressShouldNotBeDisplayed();
        sleep(3000);
        notification.loadingProgressShouldNotBeDisplayed();

        approvalDialog.shouldBeDisplayed();
        return approvalDialog;
    }

    public RejectDialog openSendForReviewDialog() {
        notification.loadingProgressShouldNotBeDisplayed();
        clickOnSendForReviewMenuLink();
        notification.loadingProgressShouldNotBeDisplayed();
        sleep(3000);
        notification.loadingProgressShouldNotBeDisplayed();

        rejectDialog.shouldBeDisplayed();
        return rejectDialog;
    }

    public void close() {
        closeButton.click();
    }

    public ReturnInstancePage unlock() {
        lock(false);
        return this;
    }

    public ReturnInstancePage lock() {
        lock(true);
        return this;
    }

    public void validateReturn() {
        while(!validateNowButton.exists()) {
            System.out.println("Waiting on ValdateNowButton");
            Selenide.sleep(2000);
        }

        validateNowButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
    }

    public void exportToFile(final String exportOption) {
        SelenideElement dropDown = $(By.id("formHeader:exportToFile_button"));
        dropDown.click();
        SelenideElement selection = $(By.xpath("//div[@id='formHeader:exportToFile_menu']/UL/li/a/span[text()='"
                + exportOption
                + "']")).parent().parent();
        selection.click();
    }

    private static SelenideElement submitType(final String submitType) {
        return $(By.xpath("//span[text()='" + submitType + "']"));
    }

    public void submitToExportType(final String submitType) {
        notification.loadingProgressShouldNotBeDisplayed();
        submitDropDownMenu.click();
        submitType(submitType).click();
        sleep(3000);
        notification.loadingProgressShouldNotBeDisplayed();
        sleep(2000);
        notification.loadingProgressShouldNotBeDisplayed();
    }

    private void clickOnReadyForApprovalMenuLink() {
        openWorkflow();
        readyForApprovalMenuButton.shouldBe(visible);

        readyForApprovalMenuButton.click();
    }

    private void clickOnApproveMenuLink() {
        openWorkflow();
        approveMenuButton.shouldBe(visible);
        approveMenuButton.click();
    }

    private void clickOnSendForReviewMenuLink() {
        openWorkflow();
        sendForReviewMenuButton.shouldBe(visible);
        sendForReviewMenuButton.click();
    }

    private void openWorkflow() {
        workflowDropdown.click();
        workflowDropdownMenu.shouldBe(visible);
    }

    public ReturnInstancePage modifyFirstCellOfReturn(final String newValue) {
        returnInstanceCells.first().find(By.tagName("input")).doubleClick();
        modifyReturnInstanceCell(newValue);
        return this;
    }

    public void shouldBeNoErrors() {
        notification.shouldBeNoErrors();
    }

    private void modifyReturnInstanceCell(final String newValue) {
        returnInstanceCellToEdit.shouldBe(visible);
        cellEditorPanel.shouldBe(visible);

        returnInstanceCellToEdit
                .findAll(By.tagName("input"))
                .filterBy(visible)
                .shouldHave(sizeGreaterThan(0))
                .first()
                .setValue(newValue);

        cellEditorPanel.find(By.id("okBtn")).click();
    }

    private void lock(final boolean lock) {
        notification.loadingProgressShouldNotBeDisplayed();

        String xpath = lock ? "//*[@id='formHeader:lockBtn']" : "//*[@id='formHeader:unlockBtn']";
        SelenideElement padlockButton = $(By.xpath(xpath));
        if (xpath.equals("//*[@id='formHeader:unlockBtn']") & padlockButton.isDisplayed()) {
            padlockButton.click();
            notification.loadingProgressShouldNotBeDisplayed();
            if (lockWarn.isDisplayed()) {
                lockWarn.parent().$x("//table//button/span[text()='OK']").click();
                while (notification.loadingDialog().isDisplayed()) {
                    notification.loadingProgressShouldNotBeDisplayed();
                }
            }
            notification.closeGrowlNotificationIfPresent();
        } else {

        }
    }

    public void clickTrendsButton() {
        trendsButton.shouldBe(visible).click();
        Selenide.sleep(500);
        returnSelectorPanel
                .switchToAnalysisModuleTab()
                .shouldBeVisible();
        returnSelectorPanel
                .resetButton.shouldBe(visible);
    }

    public void clickVarianceButton() {
        varianceButton.click();
        Selenide.sleep(500);
        returnSelectorPanel
                .switchToAnalysisModuleTab()
                .shouldBeVisible();
    }

    public void trendButtonIsDisabled() {
        trendsButton.shouldBe(disabled);
    }

    public void varianceButtonIsDisabled() {
        varianceButton.shouldBe(disabled);
    }
}
