package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddCommentDialog extends CommentAttachmentDialog {

    private final SelenideElement addCommentDialog = $(By.id("addCommentDialog"));
    private final SelenideElement addCommentButton = $(By.id("addCommentForm:addComment"));
    private final Notification notification;

    public AddCommentDialog(final Config config, final Notification notification) {
        super(config, notification);

        this.notification = notification;
    }

    @Override
    protected String getFormCommentAttachmentId() {
        return "addCommentForm:addCommentAttachment";
    }

    public void submit() {
        addCommentButton.click();
        notification.shouldBeNoErrors();
    }

    @Override
    public void shouldBeDisplayed() {
        addCommentDialog.shouldBe(visible);
        super.shouldBeDisplayed();
    }
}
