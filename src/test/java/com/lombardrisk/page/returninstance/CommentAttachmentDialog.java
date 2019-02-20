package com.lombardrisk.page.returninstance;

import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class CommentAttachmentDialog {

    private final Config config;
    private final Notification notification;

    public CommentAttachmentDialog(final Config config, final Notification notification) {
        this.config = config;
        this.notification = notification;
    }

    public abstract void submit();

    public void attachmentUploadShouldBeDisabled() {
        getCommentTextArea().shouldBe(visible);
        getAttachmentsUploadArea().shouldNot(exist);
    }

    public CommentAttachmentDialog attachmentShouldNotBeAdded() {
        getClearDownloadButton().shouldNotBe(visible);
        return this;
    }

    public void shouldHaveNotificationDisplayed() {
        notification.growlNotificationShouldBeDisplayed();
    }

    public void shouldBeDisplayed() {
        getCommentTextArea().shouldBe(visible);
    }

    public CommentAttachmentDialog addComment(final String comment) {
        getCommentTextArea().setValue(comment);
        getCommentTextArea().shouldHave(value(comment));
        return this;
    }

    public CommentAttachmentDialog addAttachment(final String comment, final String fileName) {
        getCommentTextArea().setValue(comment);

        File file = new File(config.attachmentsFolder().toString(), fileName);
        getAttachmentsUploadArea().clear();
        getAttachmentsUploadArea().sendKeys(file.getAbsolutePath());

        getClearDownloadButton().waitUntil(visible, config.maxTimeout());

        return this;
    }

    public CommentAttachmentDialog tryToAddAttachment(final String comment, final String fileName) {
        getCommentTextArea().setValue(comment);

        File file = new File(config.attachmentsFolder().toString(), fileName);
        getAttachmentsUploadArea().clear();
        getAttachmentsUploadArea().sendKeys(file.getAbsolutePath());

        return this;
    }

    protected abstract String getFormCommentAttachmentId();

    private SelenideElement getCommentTextArea() {
        return $(By.id(getFormCommentAttachmentId() + ":commentTextarea"));
    }

    private SelenideElement getAttachmentsUploadArea() {
        return $(By.id(getFormCommentAttachmentId() + ":fileUpload_start_input"));
    }

    private SelenideElement getClearDownloadButton() {
        return $(By.id(getFormCommentAttachmentId() + ":clearDownloadButton"));
    }
}
