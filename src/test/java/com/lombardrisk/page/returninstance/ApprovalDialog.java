package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.approval.ApprovalStatus.APPROVED;
import static com.lombardrisk.page.approval.ApprovalStatus.PENDING_APPROVAL;

public class ApprovalDialog extends CommentAttachmentDialog {

    private static final String APPROVE_TITLE = "Approve";

    private final SelenideElement approvalStatusCountLabel = $(By.id("formHeader:returnInstanceStatusCount"));
    private final SelenideElement approveDialogTitle = $(By.id("approveCommentForm:approveCommentDialog_title"));
    private final SelenideElement approveOkButton = $(By.id("approveCommentForm:addApproveComment"));

    private final Notification notification;

    public ApprovalDialog(final Config config, final Notification notification) {
        super(config, notification);

        this.notification = notification;
    }

    @Override
    protected String getFormCommentAttachmentId() {
        return "approveCommentForm:approvalCommentAttachment";
    }

    @Override
    public void shouldBeDisplayed() {
        shouldHaveCompletedMakerStep();
        approveDialogTitle.shouldHave(text(APPROVE_TITLE));
        super.shouldBeDisplayed();
    }

    public void submit() {
        approveOkButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();

//        String pendingOrApprovedCondition = PENDING_APPROVAL.label() + " or " + APPROVED.label();
//        approvalStatusCountLabel.shouldHave(
//                or(
//                        pendingOrApprovedCondition,
//                        PENDING_APPROVAL.text(),
//                        APPROVED.text()));
        //TODO add lock assert also return functionality after instance count is added back
    }

    private static void shouldHaveCompletedMakerStep() {
        SelenideElement progressIndicator =
                $(By.xpath("//div[@id='rejectCommentForm:rejectCommentDialog']//table/tbody/tr[1]/td[1]"));

        progressIndicator.shouldHave(cssClass("doneLeftApproval"));
    }
}
