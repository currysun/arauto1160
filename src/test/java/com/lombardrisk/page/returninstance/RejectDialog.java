package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.lombardrisk.page.approval.ApprovalStatus.UNDER_REVIEW;

public class RejectDialog extends CommentAttachmentDialog {

    private static final String SEND_FOR_REVIEW_TITLE = "Send for Review";

    private final SelenideElement sendForReviewDialogTitle = $(By.id("rejectCommentForm:rejectCommentDialog_title"));
    private final SelenideElement sendBackToLabel = $(By.id("rejectCommentForm:rejectBackToStep_label"));
    private final SelenideElement sendForReviewOkButton = $(By.id("rejectCommentForm:addRejectComment"));
    private final SelenideElement approvalStatusCountLabel = $(By.id("formHeader:returnInstanceStatusCount"));

    private final Notification notification;

    public RejectDialog(final Config config, final Notification notification) {
        super(config, notification);

        this.notification = notification;
    }

    @Override
    protected String getFormCommentAttachmentId() {
        return "rejectCommentForm:rejectCommentAttachment";
    }

    @Override
    public void shouldBeDisplayed() {
        sendForReviewDialogTitle.shouldHave(text(SEND_FOR_REVIEW_TITLE));
        super.shouldBeDisplayed();
    }

    public RejectDialog sendForReviewTo(final String reviewingGroupName) {
        selectGroupToSendBackTo(reviewingGroupName);
        shouldHaveCompletedStepByGroup(reviewingGroupName);
        return this;
    }

    public void submit() {
        sendForReviewOkButton.click();
        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();
        //approvalStatusCountLabel.shouldHave(UNDER_REVIEW.text());
        //TODO add lock assert also return functionality after instance count is added back
    }

    private static SelenideElement sendBackToSelectOption(final String groupName) {
        return $(By.xpath("//div[@id='rejectCommentForm:rejectBackToStep_panel']" +
                "/div/ul/li[text()='" + groupName + "']"));
    }

    private void selectGroupToSendBackTo(final String groupName) {
        sendBackToLabel.click();
        sendBackToSelectOption(groupName).click();
    }

    public RejectDialog sendToMakers() {
        sendBackToLabel.click();
        sendBackToMakers().click();
        return this;
    }

    private static void shouldHaveCompletedStepByGroup(final String groupName) {
        List<String> groupNames =
                $$(By.xpath("//div[@id='rejectCommentForm:rejectCommentDialog']//table/tbody/tr[2]/td")).texts();

        int cellIndex = 0;
        for (int i = 0; i < groupNames.size(); i++) {
            if (groupName.equalsIgnoreCase(groupNames.get(i))) {
                cellIndex = i + 1;
            }
        }
        SelenideElement progressIndicator =
                $(By.xpath("//div[@id='rejectCommentForm:rejectCommentDialog']//table/tbody/tr[1]/td["
                        + cellIndex + "]"));
        if (cellIndex == 1) {
            progressIndicator.shouldHave(cssClass("doneLeftApproval"));
        }
        if (cellIndex > 1
                && cellIndex < groupNames.size()) {
            progressIndicator.shouldHave(cssClass("doneMidApproval"));
        }
    }

    private static SelenideElement sendBackToMakers() {
        return $(By.xpath("//div[@id='rejectCommentForm:rejectBackToStep_panel']/div/ul/li[1]"));
    }
}
