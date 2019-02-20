package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.administration.AttachmentConfigurationPage;
import com.lombardrisk.page.analysismodule.AddCommentsDialog;
import com.lombardrisk.page.dashboard.DashboardPage;
import com.lombardrisk.page.header.MainMenu;
import com.lombardrisk.page.returninstance.Comment;
import com.lombardrisk.page.returninstance.CommentLogDialog;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.not;

public class CommentLog extends StepDef {

    private static final int DEFAULT_FILE_SIZE_LIMIT = 5;
    private static final String DEFAULT_FILE_SIZE_UNIT = "MB";
    private static final List<String> DEFAULT_ALLOWED_FILE_EXTENSIONS =
            Arrays.asList(".doc", ".docx", ".csv", ".xls", ".xlsx", ".txt", ".xml", ".png", ".jpg", ".pdf");
    private static final String ATTACHMENT_DEFAULT = "documentAttachment.txt";
    private static final String ATTACHMENT_EQUALS_SIZE_LIMIT = "documentAttachment_1024Bytes.txt";
    private static final String ATTACHMENT_UNDER_SIZE_LIMIT = "documentAttachment_1Byte.txt";
    private static final String ATTACHMENT_OVER_SIZE_LIMIT = "documentAttachment_1025Bytes.txt";
    private final String uniqueCommentSuffix = ": " + RandomStringUtils.randomAlphanumeric(20);
    @Autowired
    private DashboardPage dashboardPage;
    @Autowired
    private ReturnInstancePage returnInstancePage;
    @Autowired
    private CommentLogDialog commentLogDialog;
    @Autowired
    private MainMenu mainMenu;
    @Autowired
    private AttachmentConfigurationPage attachmentConfigurationPage;
    @Autowired
    private AddCommentsDialog addCommentsDialog;

    @When("^I create any return$")
    public void iCreateAnyReturn() {
        dashboardPage.open()
                .createReturn();
    }

    @And("^I open the comment log from the return instance page$")
    public void iOpenTheCommentLogFromTheReturnInstancePage() {
        returnInstancePage.openCommentLog();
    }

    @And("^I open the comment log from the dashboard page$")
    public void iOpenTheCommentLogFromTheDashboardPage() {
        dashboardPage.open()
                .openCommentLog();
    }

    @And("^I close the comment log$")
    public void iCloseTheCommentLog() {

        commentLogDialog.close();
    }

    @And("^I submit a comment \"([^\"]*)\"$")
    public void iSubmitAComment(final String comment) {
        commentLogDialog.addComment(comment + uniqueCommentSuffix);
    }

    @When("^I submit a comment \"([^\"]*)\" with an attachment$")
    public void iSubmitACommentWithAnAttachment(final String comment) {
        commentLogDialog.addCommentWithAttachment(comment + uniqueCommentSuffix, ATTACHMENT_DEFAULT);
    }

    @When("^I submit a comment \"([^\"]*)\" with an attachment equal to the file size limit$")
    public void iSubmitACommentWithAnAttachmentEqualToTheFileSizeLimit(final String comment) {
        commentLogDialog.addCommentWithAttachment(comment + uniqueCommentSuffix, ATTACHMENT_EQUALS_SIZE_LIMIT);
    }

    @When("^I submit a comment \"([^\"]*)\" with an attachment under the file size limit$")
    public void iSubmitACommentWithAnAttachmentUnderTheFileSizeLimit(final String comment) {
        commentLogDialog.addCommentWithAttachment(comment + uniqueCommentSuffix, ATTACHMENT_UNDER_SIZE_LIMIT);
    }

    @When("^I submit a comment \"([^\"]*)\" with an attachment over the file size limit$")
    public void iSubmitACommentWithAnAttachmentOverTheFileSizeLimit(final String comment) {
        commentLogDialog.addCommentWithAttachment(comment + uniqueCommentSuffix, ATTACHMENT_OVER_SIZE_LIMIT);
    }

    @Then("^the top comment should be \"([^\"]*)\"$")
    public void theTopCommentShouldBe(final String comment) {
        commentLogDialog.shouldBeTopComment(comment + uniqueCommentSuffix);
    }

    @Then("^the top force submitted comment should be \"([^\"]*)\"$")
    public void theTopForceSubmittedCommentShouldBe(final String comment) {
        commentLogDialog.shouldBeTopComment(comment);
    }

    @Then("^the top comments should be:$")
    public void theTopNumberOfCommentsShouldBe(final DataTable comments) {
        List<Comment> commentList = comments.asList(Comment.class);
        commentLogDialog.shouldHaveTopComments(commentList);
    }

    @Then("^the comments should not contain \"([^\"]*)\"$")
    public void theCommentsShouldNotContain(final String comment) {
        commentLogDialog.shouldNotHaveComment(comment);
    }

    @When("^I delete the top comment$")
    public void iDeleteTheTopComment() {
        commentLogDialog.deleteTopComment();
    }

    @When("^I show deleted comments$")
    public void iShowDeletedComments() {
        commentLogDialog.showDeletedComments();
    }

    @And("^I show historical revisions$")
    public void iShowHistoricalRevisions() {
        commentLogDialog.showHistoricalRevisions();
    }

    @When("^I filter the comment log by \"([^\"]*)\"$")
    public void iFilterTheCommentLogBy(final CommentLogDialog.Filter filter) {
        commentLogDialog.filterBy(filter);
    }

    @And("^it should be actioned by \"([^\"]*)\"$")
    public void itShouldBeActionedBy(final String user) {
        commentLogDialog.shouldBeTopActionedBy(user);
    }

    @And("^it should have a timestamp$")
    public void itShouldHaveATimestamp() {
        commentLogDialog
                .shouldHaveTopTimeStamp();
    }

    @And("^I am on the Attachment Configuration page$")
    public void iAmOnTheAttachmentConfigurationPage() {
        mainMenu.openSettings().administration().comments();
    }

    @When("^I set the file size limit to (\\d+) ([^\"]*)$")
    public void iSetTheFileSizeLimitTo(final int newFileSizeLimit, final String newFileSizeUnit) {
        attachmentConfigurationPage.setFileSizeLimit(newFileSizeLimit, newFileSizeUnit);
    }

    @Then("^I see that the file size limit is (\\d+) ([^\"]*)$")
    public void iSeeThatTheFileSizeLimitIs(final int fileSizeLimit, final String fileSizeUnit) {
        attachmentConfigurationPage.fileSizeLimitShouldBe(fileSizeLimit, fileSizeUnit);
    }

    @And("^the default file size limit is set$")
    public void theDefaultFileSizeLimitIsIsSet() {
        iSeeThatTheFileSizeLimitIs(DEFAULT_FILE_SIZE_LIMIT, DEFAULT_FILE_SIZE_UNIT);
    }

    @Given("^the default allowed file extensions are set$")
    public void theDefaultAllowedFileExtensionsAreSet() {
        attachmentConfigurationPage.allowedFileExtensionsShouldBe(DEFAULT_ALLOWED_FILE_EXTENSIONS);
    }

    @When("^I add a new file extension \"([^\"]*)\"$")
    public void iAddANewFileExtension(final String fileExtension) {
        attachmentConfigurationPage.addNewFileExtension(fileExtension);
    }

    @Then("^I see that the allowed file extensions contain \"([^\"]*)\"$")
    public void iSeeThatTheAllowedFileExtensionsContain(final String fileExtension) {
        attachmentConfigurationPage.allowedFileExtensionsShouldContain(fileExtension);
    }

    @When("^I delete the file extension \"([^\"]*)\"$")
    public void iDeleteTheFileExtension(final String fileExtension) {
        attachmentConfigurationPage.deleteFileExtension(fileExtension);
    }

    @Then("^I see that the allowed file extensions do not contain \"([^\"]*)\"$")
    public void iSeeThatTheAllowedFileExtensionsDoNotContain(final String fileExtension) {
        attachmentConfigurationPage.allowedFileExtensionsShouldNotContain(fileExtension);
    }

    @Then("^I do not see Comments menu option$")
    public void iDoNotSeeCommentsMenuOption() {
        mainMenu.openSettings().administration().shouldNotHaveComments();
    }

    @Then("^I see that I do not have privileges to configure comments$")
    public void iSeeThatIDoNotHavePrivilegesToConfigureComments() {
        attachmentConfigurationPage.open();
        attachmentConfigurationPage.isAccessDenied();
    }

    @After("@AttachmentsConfiguration")
    public void afterAttachmentsConfiguration() {
        attachmentConfigurationPage.open();
        iSetTheFileSizeLimitTo(DEFAULT_FILE_SIZE_LIMIT, DEFAULT_FILE_SIZE_UNIT);
        attachmentConfigurationPage.setAllowedFileExtensions(DEFAULT_ALLOWED_FILE_EXTENSIONS);
    }

    @And("^it should have an attachment$")
    public void itShouldHaveAnAttachment() {
        commentLogDialog
                .shouldBeTopAttachment(not(empty));
    }

    @And("^it should not have an attachment$")
    public void itShouldNotHaveAnAttachment() {
        commentLogDialog.shouldBeTopAttachment(empty);
    }

    @Then("^I should not be able to upload an attachment$")
    public void iShouldNotBeAbleToUploadAnAttachment() {
        commentLogDialog.attachmentUploadShouldBeDisabled();
    }

    @Given("^there are no file extensions allowed for uploading$")
    public void thereAreNoFileExtensionsAllowedForUploading() {
        attachmentConfigurationPage.open();
        attachmentConfigurationPage.setAllowedFileExtensions(Collections.emptyList());
    }

    @Then("^an error should stop me from uploading an attachment$")
    public void anErrorShouldStopMeFromUploadingAnAttachment() {
        commentLogDialog.shouldBePreventedFromAddingAttachment(ATTACHMENT_DEFAULT);
    }

    @Then("^an error should stop me from uploading a larger attachment$")
    public void anErrorShouldStopMeFromUploadingALargerAttachment() {
        commentLogDialog.shouldBePreventedFromAddingAttachment(ATTACHMENT_OVER_SIZE_LIMIT);
    }

    @And("^I save my comment$")
    public void iSaveMyComment() {
        addCommentsDialog.save();
    }

    @Then("^I reset the file size limit to (\\d+) ([^\"]*)$")
    public void iResetTheFileSizeLimitToMB(final int newFileSizeLimit, final String newFileSizeUnit) {
        attachmentConfigurationPage.setFileSizeLimit(newFileSizeLimit, newFileSizeUnit);
    }
}
