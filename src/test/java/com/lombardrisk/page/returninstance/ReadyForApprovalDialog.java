package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.lombardrisk.page.approval.ApprovalStatus.PENDING_APPROVAL;

public class ReadyForApprovalDialog extends CommentAttachmentDialog {

    private static final String READY_FOR_APPROVAL_TITLE = "Ready for Approval";

    private final SelenideElement readyForApprovalOkButton =
            $(By.id("readyForApprovalCommentForm:addReadyForApprovalComment"));
    private final SelenideElement readyForApprovalDialogTitle =
            $(By.id("readyForApprovalCommentForm:readyForApprovalCommentDialog_title"));
    private final SelenideElement approvalStatusCountLabel = $(By.id("formHeader:returnInstanceStatusCount"));

    private final Notification notification;

    public ReadyForApprovalDialog(final Config config, final Notification notification) {
        super(config, notification);

        this.notification = notification;
    }

    @Override
    protected String getFormCommentAttachmentId() {
        return "readyForApprovalCommentForm:readyForApprovalCommentAttachment";
    }

    @Override
    public void shouldBeDisplayed() {
        shouldHaveInProgressMakerStep();
        readyForApprovalDialogTitle.shouldBe(visible);
        readyForApprovalDialogTitle.shouldHave(text(READY_FOR_APPROVAL_TITLE));
        super.shouldBeDisplayed();
    }

    public void submit() {
        readyForApprovalOkButton.click();

        notification.loadingProgressShouldNotBeDisplayed();
        notification.loadingProgressShouldNotBeDisplayed();
        //approvalStatusCountLabel.shouldHave(PENDING_APPROVAL.text());
        //TODO add lock assert also return functionality after instance count is added back
    }

    private static void shouldHaveInProgressMakerStep() {
        SelenideElement statusIndicator = $(By.xpath("//table[@class='statusIndicatorStages']"));
        if (statusIndicator.isDisplayed()) {
            SelenideElement progressIndicator =
                    $(By.xpath("//div[@id='readyForApprovalCommentForm:readyForApprovalCommentDialog']" +
                            "//table/tbody/tr[1]/td[1]"));

            progressIndicator.shouldHave(cssClass("inProgressLeftApproval"));
        }
    }
}
