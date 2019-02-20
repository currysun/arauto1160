package com.lombardrisk.stepdef.analysismodule;

import com.codeborne.selenide.Condition;
import com.lombardrisk.page.analysismodule.AddCommentsDialog;
import com.lombardrisk.page.analysismodule.grid.GridPage;
import com.lombardrisk.stepdef.analysismodule.grid.checker.DownloadedFilesChecker;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class Attachments {

    @Autowired
    private GridPage gridPage;
    @Autowired
    private AddCommentsDialog addReturnLevelCommentsDialog;
    @Autowired
    private DownloadedFilesChecker downloadedFilesChecker;

    private static final String ATTACHMENT_DEFAULT = "documentAttachment.txt";
    private static final String ATTACHMENT_DEFAULT1 = "documentAttachment_1025Bytes.txt";
    private final String uniqueCommentSuffix = ": " + RandomStringUtils.randomAlphanumeric(20);

    @Given("^I add attachment at a Return Level by drag and drop$")
    public void iAddAttachmentAtAReturnLevelByDragAndDrop() {

    }

    @Then("^submit button is enabled$")
    public void submitButtonIsEnabled() {
        addReturnLevelCommentsDialog.submitButtonEnabled();
    }

    @Given("^I add a return level comment \"([^\"]*)\" with attachments and save$")
    public void iAddAReturnLevelCommentForAttachmentAndSave(String comment) {
        gridPage.openCommentsDialog();
        addReturnLevelCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addReturnLevelCommentsDialog.addAttachmentsAndSave(ATTACHMENT_DEFAULT, ATTACHMENT_DEFAULT1);
    }

    @When("^I remove all attachments$")
    public void iRemoveAllAttachments() {
        addReturnLevelCommentsDialog.removeAllAttachments();
    }

    @Then("^no attachments are available to view$")
    public void noAttachmentsAreAvailableToView() {
        addReturnLevelCommentsDialog.checkNoAttachments();
    }

    @When("^I download the \"([^\"]*)\" attachment$")
    public void iDownloadTheAttachment(final String fileName) {
        addReturnLevelCommentsDialog.downloadAttachment(fileName);
    }

    @Then("^the attachment \"([^\"]*)\" be downloaded successfully$")
    public void theAttachmentBeDownloadedSuccessfully(String expectedFileName) {
        downloadedFilesChecker.shouldBeDownloaded(new NameFileFilter(expectedFileName));
    }

    @And("^I add a return level comment \"([^\"]*)\" with attachments$")
    public void iAddAReturnLevelCommentWithAttachments(final String comment) {
        gridPage.openCommentsDialog();
        addReturnLevelCommentsDialog.addComment(comment + uniqueCommentSuffix);
        addReturnLevelCommentsDialog.addAttachments(ATTACHMENT_DEFAULT, ATTACHMENT_DEFAULT1);
    }

    @Then("^I remove an attachment others should remain$")
    public void iRemoveAnAttachmentOthersShouldRemain() {
        addReturnLevelCommentsDialog.removeAttachment(ATTACHMENT_DEFAULT);
        addReturnLevelCommentsDialog.attachedItem(ATTACHMENT_DEFAULT1).shouldBe(Condition.visible);
    }
}
