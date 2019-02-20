package com.lombardrisk.stepdef;

import com.lombardrisk.config.StepDef;
import com.lombardrisk.page.returninstance.CommentLogDialog;
import com.lombardrisk.page.returninstance.ReturnInstancePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class ReturnApproval extends StepDef {

    private static final String ATTACHMENT_DEFAULT = "documentAttachment.txt";

    private final String uniqueSuffix = " - " + randomAlphanumeric(20);

    @Autowired
    private ReturnInstancePage returnInstancePage;
    @Autowired
    private CommentLogDialog commentLogDialog;

    @When("^I make the return ready for approval with no comment$")
    public void iMakeTheReturnReadyForApprovalWithNoComment() {
        returnInstancePage
                .unlock()
                .openReadyForApprovalDialog()
                .addComment("")
                .submit();
    }

    @When("^I make the return ready for approval with comment \"([^\"]*)\"$")
    public void iMakeTheReturnReadyForApproval(final String comment) {
        returnInstancePage
                .unlock()
                .openReadyForApprovalDialog()
                .addComment(comment + uniqueSuffix)
                .submit();
    }

    @When("^I make the return ready for approval with an attachment$")
    public void iMakeTheReturnReadyForApprovalWithAnAttachment() {
        returnInstancePage
                .unlock()
                .openReadyForApprovalDialog()
                .addAttachment("", ATTACHMENT_DEFAULT)
                .submit();
    }

    @When("^I make the return ready for approval with comment \"([^\"]*)\" and attachment$")
    public void iMakeTheReturnReadyForApprovalWithCommentAndAttachment(final String comment) {
        returnInstancePage
                .unlock()
                .openReadyForApprovalDialog()
                .addAttachment(comment + uniqueSuffix, ATTACHMENT_DEFAULT)
                .submit();
    }

    @When("^I approve the return with the no comment$")
    public void iApproveTheReturnWithTheNoComment() {
        returnInstancePage
                .openApproveDialog()
                .addComment("")
                .submit();
    }

    @When("^I make the return ready for approval with comment \"([^\"]*)\" and add attachment \"([^\"]*)\"$")
    public void iMakeTheReturnReadyForApprovalWithCommentAndAddAttachment(
            final String comment,
            final String attachment) {
        returnInstancePage
                .unlock()
                .openReadyForApprovalDialog()
                .addAttachment(comment + uniqueSuffix, attachment)
                .submit();
    }

    @When("^I approve the return with the comment \"([^\"]*)\"$")
    public void iApproveTheReturnWithTheComment(final String comment) {
        returnInstancePage
                .openApproveDialog()
                .addComment(comment + uniqueSuffix)
                .submit();
    }

    @When("^I approve the return with an attachment$")
    public void iApproveTheReturnWithAnAttachment() {
        returnInstancePage
                .openApproveDialog()
                .addAttachment("", ATTACHMENT_DEFAULT)
                .submit();
    }

    @When("^I approve the return with the comment \"([^\"]*)\" and attachment$")
    public void iApproveTheReturnWithTheCommentAndAttachment(final String comment) {
        returnInstancePage
                .openApproveDialog()
                .addAttachment(comment + uniqueSuffix, ATTACHMENT_DEFAULT)
                .submit();
    }

    @When("^I send the return for review to \"([^\"]*)\", adding no comment$")
    public void iSendTheReturnForReviewToAddingNoComment(final String reviewingGroupName) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendForReviewTo(reviewingGroupName)
                .addComment("")
                .submit();
    }

    @When("^I send the return for review to \"([^\"]*)\", adding the comment \"([^\"]*)\"$")
    public void iSendTheReturnForReviewAddingTheComment(final String reviewingGroupName, final String comment) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendForReviewTo(reviewingGroupName)
                .addComment(comment + uniqueSuffix)
                .submit();
    }

    @When("^I send the return for review to \"([^\"]*)\", adding an attachment$")
    public void iSendTheReturnForReviewToAddingAnAttachment(final String reviewingGroupName) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendForReviewTo(reviewingGroupName)
                .addAttachment("", ATTACHMENT_DEFAULT)
                .submit();
    }

    @When("^I send the return for review to \"([^\"]*)\", adding the comment \"([^\"]*)\" and attachment$")
    public void iSendTheReturnForReviewToAddingTheCommentAndAttachment(
            final String reviewingGroupName,
            final String comment) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendForReviewTo(reviewingGroupName)
                .addAttachment(comment + uniqueSuffix, ATTACHMENT_DEFAULT)
                .submit();
    }

    @Then("^the return approval status and count should be \"([^\"]*)\"$")
    public void theReturnApprovalStatusShouldBe(final String approvalStatusCount) {
        returnInstancePage.shouldHaveApprovalStatusAndCount(approvalStatusCount);
    }

    @And("^the top comment in the comment log should be actioned by \"([^\"]*)\"$")
    public void theTopCommentInTheCommentLogShouldBeActionedBy(final String expectedUsername) {
        commentLogDialog
                .shouldBeTopActionedBy(expectedUsername)
                .shouldHaveTopTimeStamp();
    }

    @And("^the top comment in the comment log should have action \"([^\"]*)\"$")
    public void theTopCommentInTheCommentLogShouldHaveAction(final String expectedApprovalStatus) {
        commentLogDialog.shouldHaveTopAction(expectedApprovalStatus);
    }

    @And("^the top comment in the comment log should be \"([^\"]*)\"$")
    public void theTopCommentInTheCommentLogShouldBe(final String expectedComment) {
        commentLogDialog.shouldBeTopComment(expectedComment + uniqueSuffix);
    }

    @And("^the top comment in the comment log should be empty$")
    public void theTopCommentInTheCommentLogShouldBeEmpty() {
        commentLogDialog
                .shouldHaveEmptyTopComment()
                .close();
    }

    @When("^I send the return back to maker, adding the comment \"([^\"]*)\"$")
    public void iSendTheReturnBackToMakerAddingTheComment(final String comment) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendToMakers()
                .addComment(comment + uniqueSuffix)
                .submit();
    }

    @When("^I send the return for review to \"([^\"]*)\", adding the comment \"([^\"]*)\" and add attachment \"([^\"]*)\"$")
    public void iSendTheReturnForReviewToAddingTheCommentAndAddAttachment(
            final String reviewingGroupName,
            final String comment,
            final String attachment) {
        returnInstancePage
                .openSendForReviewDialog()
                .sendForReviewTo(reviewingGroupName)
                .addAttachment(comment + uniqueSuffix, attachment)
                .submit();
    }

    @And("^I unlock the return$")
    public void iUnlockTheReturn() {
        returnInstancePage.unlock();
    }

    @And("^I close the return$")
    public void iCloseTheReturn() {
        returnInstancePage.close();
    }

    @And("^I directly edit one of the cells on the return$")
    public void iDirectlyEditOneOfTheCellsOnTheReturn() {
        returnInstancePage
                .modifyFirstCellOfReturn("999999")
                .shouldBeNoErrors();
    }

    @When("^I approve the return with the comment \"([^\"]*)\" and add attachment \"([^\"]*)\"$")
    public void iApproveTheReturnWithTheCommentAndAddAttachment(final String comment, final String attachment) {
        returnInstancePage
                .openApproveDialog()
                .addAttachment(comment + uniqueSuffix, attachment)
                .submit();
    }

    @And("^I lock the return$")
    public void iLockTheReturn() {
        returnInstancePage.lock();
    }

    @And("^I validate the return")
    public void iValidateTheReturn() {
        returnInstancePage.validateReturn();
    }
}
