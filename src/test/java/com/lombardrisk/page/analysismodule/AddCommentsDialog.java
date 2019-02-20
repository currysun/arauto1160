package com.lombardrisk.page.analysismodule;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.lombardrisk.config.Config;
import com.lombardrisk.page.event.Notification;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;

public class AddCommentsDialog {

    private Config config;
    private Notification notification;

    public AddCommentsDialog(Config config, Notification notification) {
        this.config = config;
        this.notification = notification;
    }

    private static final SelenideElement appCommentsEditorDialog = $("app-comments-editor-dialog");
    private static final SelenideElement attachmentFilesLink = $(By.xpath("//mat-dialog-container//div/mat-icon"));
    private static final SelenideElement removeAll = $("app-file-uploads a");
    private static final SelenideElement saveButton =
            $(By.xpath("//mat-dialog-container//app-comments-editor-dialog//mat-dialog-actions/button/span"));
    private static final SelenideElement closeButton = $("app-dialog-header mat-toolbar button");
    private static final SelenideElement commentTextArea = $("app-comments-editor-dialog mat-dialog-content textarea");
    private static final SelenideElement addAttachmentButton =
            $(By.xpath("//a[text()= 'select a file from your computer']"));
    ElementsCollection greenTick = $$("form mat-dialog-content app-file-uploads app-file-uploads-item");
    private static final SelenideElement submitButton =
            $(By.xpath("//mat-dialog-container//app-comments-editor-dialog//mat-dialog-actions/button"));
    private static final SelenideElement includeInRAR = $(By.cssSelector("app-comments-editor-dialog mat-checkbox"));
    private static final SelenideElement addCommentButton = $(By.xpath("//app-dialog-button/button/span/mat-icon"));

    private SelenideElement selectDownloadItem(final String fileName) {
        return $(By.xpath("//*[@download='" + fileName + "']"));
    }

    public SelenideElement attachedItem(final String fileName) {
        return $(By.xpath("//app-file-uploads-item//*[contains(text(),'" + fileName + "')]"));
    }

    public void addComment(final String comment) {
        addCommentButton.shouldBe(visible).click();
        commentTextArea.shouldBe(visible);
        commentTextArea.click();
        Configuration.fastSetValue = true;
        commentTextArea.setValue(comment);
        Configuration.fastSetValue = false;
    }

    public void includeInRAR() {
        includeInRAR.shouldBe(visible);
        if (includeInRAR.$(" label div [aria-checked=true]").isDisplayed()) {
            System.out.println("Already selected to include in RAR");
        } else {
            includeInRAR.click();
        }
    }

    public void shouldBeDefaultedToRAR() {
        includeInRAR.$(" label div [aria-checked=true]").shouldBe(visible);
    }

    public void save() {
        saveButton.shouldBe(enabled);
        saveButton.click();
        saveButton.shouldNotBe(visible);
    }

    public void exists() {
        appCommentsEditorDialog.shouldBe(visible);
    }

    public void close() {
        closeButton.click();
    }

    public void submitButtonEnabled() {
        save();
    }

    public void addAttachmentsAndSave(final String fileName, final String fileName1) {
        File file = new File(config.attachmentsFolder().toString(), fileName);
        $("app-comments-editor-dialog form input").sendKeys(file.getAbsolutePath());

        File file1 = new File(config.attachmentsFolder().toString(), fileName1);
        $("app-comments-editor-dialog form input").sendKeys(file1.getAbsolutePath());
        greenTick.shouldHaveSize(2);
        save();
    }

    public void addAttachments(final String fileName, final String fileName1) {
        File file = new File(config.attachmentsFolder().toString(), fileName);
        $("app-comments-editor-dialog form input").sendKeys(file.getAbsolutePath());

        File file1 = new File(config.attachmentsFolder().toString(), fileName1);
        $("app-comments-editor-dialog form input").sendKeys(file1.getAbsolutePath());
        greenTick.shouldHaveSize(2);
    }

    public void addAttachmentAndSave(final String fileName) {
        File file = new File(config.attachmentsFolder().toString(), fileName);
        $("app-comments-editor-dialog form input").sendKeys(file.getAbsolutePath());

        greenTick.shouldHaveSize(1);
        save();
    }

    public void removeAllAttachments() {
        removeAll.click();
    }

    public void checkNoAttachments() {
        greenTick.shouldHaveSize(0);
    }

    public void downloadAttachment(final String fileName) {
        attachmentFilesLink.shouldBe(visible);
        attachmentFilesLink.click();
        sleep(config.minTimeout());
        selectDownloadItem(fileName).shouldBe(visible);
        selectDownloadItem(fileName).click();
    }

    public void removeAttachment(final String fileName) {
        $(By.xpath("//app-file-uploads-item//*[contains(text(),'" + fileName + "')]/..//*[text()='close']")).click();
        attachedItem(fileName).shouldNotBe(visible);
    }
}
